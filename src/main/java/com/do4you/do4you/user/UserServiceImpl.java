package com.do4you.do4you.user;

import com.do4you.do4you.dto.UserDto;
import com.do4you.do4you.model.User;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userRepository.save(User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .nickname(userDto.getNickname())
                .phoneNumber(userDto.getPhoneNumber())
                .build());

        return getUserById(userDto.getUserId());
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .build();
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
