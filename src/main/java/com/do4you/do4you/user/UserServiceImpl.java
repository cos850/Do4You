package com.do4you.do4you.user;

import com.do4you.do4you.common.UserRole;
import com.do4you.do4you.dto.UserDto;
import com.do4you.do4you.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userDto.toDocument();

        // 암호화되지 않은 패스워드는 시큐리티에서 로그인할 수 없음
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // 권한 USER 로 설정
        user.setRole(UserRole.USER);

        userRepository.save(user);

        return getUserById(userDto.getUserId());
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        System.out.println("User: " + user);
        return UserDto.toDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        Optional<User> userOpt = userRepository.findById(userDto.getUserId());

        userOpt.ifPresentOrElse(user -> {
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setNickname(userDto.getNickname());
            user.setPhoneNumber(userDto.getPhoneNumber());

            System.out.println("update User Info : " + user);

            userRepository.save(user);
        },
        ()-> {
            throw new NoSuchElementException("No value present");
        });

        return getUserById(userDto.getUserId());
    }

    @Override
    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }
}
