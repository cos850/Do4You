package com.do4you.do4you;

import com.do4you.do4you.dto.UserDto;
import com.do4you.do4you.user.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class MongoDbConnectionTest {

	@Autowired
	UserService userService;

	private UserDto userDto;

	@BeforeAll
	void BeforeAll(){
		userDto = UserDto.builder()
				.userId("testUser")
				.name("테스트사용자명")
				.email("testUser@example.com")
				.password("testUser123")
				.nickname("테스트사용자닉네임")
				.phoneNumber("01020635065")
				.build();
	}

	@Test
	void addTest() {
		System.out.println("1. 사용자 등록 : " + userDto);
		System.out.println("result: " + userService.createUser(userDto));
	}

	@Test
	void find() {
		System.out.println("2. 사용자 조회: " + userDto.getUserId());
		System.out.println("result: " + userService.getUserById(userDto.getUserId()));
	}

	@Test
	void modifyTest() {
		System.out.println("3. 사용자 변경: " + userDto);
		userDto.setNickname("닉네임 변경");
		userDto.setEmail("이메일변경@example.com");
		System.out.println("result: " + userService.updateUser(userDto));
	}

	@Test
	void delete() {
		System.out.println("4. 사용자 삭제: " + userDto);
		userService.deleteUserById(userDto.getUserId());
		System.out.println("result: " + "OK");
	}

	@AfterAll
	void afterAll(){
		try {
			userService.getUserById(userDto.getUserId());
			userService.deleteUserById(userDto.getUserId());
			System.out.println("### AfterAll 테스트 데이터 삭제 완료");
		} catch (NoSuchElementException e){
			System.out.println("### AfterAll 테스트 데이터 이미 삭제되어 있음");
		}
	}

}
