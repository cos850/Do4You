package com.do4you.do4you.chat;

import com.do4you.do4you.chat.room.ChatRoomService;
import com.do4you.do4you.dto.ChatRoomDto;
import com.do4you.do4you.dto.UserDto;
import com.do4you.do4you.user.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ChatRoomsTest {

	@Autowired
	ChatRoomService chatRoomService;

	@Autowired
	UserService userService;

	private List<ChatRoomDto> chatRoomDtos = new ArrayList<>();
	private UserDto fromUserDto;
	private UserDto toUserDto;


	@BeforeAll
	void BeforeAll(){
		fromUserDto = UserDto.builder()
				.userId("chatRoomTestUserFrom")
				.name("from")
				.email("from@example.com")
				.password("testUser123")
				.nickname("from닉네임")
				.phoneNumber("00000000")
				.build();

		toUserDto = UserDto.builder()
				.userId("chatRoomTestUserTo")
				.name("to")
				.email("to@example.com")
				.password("testUser123")
				.nickname("to닉네임")
				.phoneNumber("00000000")
				.build();

		chatRoomDtos.add(ChatRoomDto.builder()
				.chatRoomId("TestChatRoom-1")
				.userId(fromUserDto.getUserId())
				.partnerId(toUserDto.getUserId())
				.build());

		chatRoomDtos.add(ChatRoomDto.builder()
				.chatRoomId("TestChatRoom-1")
				.userId(toUserDto.getUserId())
				.partnerId(fromUserDto.getUserId())
				.build());
	}

	@Test
	void addTest() {
		System.out.println("1. 채팅방 생성");
		System.out.println("1-1. from 사용자 등록\n\t ## " + userService.createUser(fromUserDto));
		System.out.println("1-2. to 사용자 등록\n\t ## " + userService.createUser(toUserDto));

		System.out.println("1-2. 채팅방 등록" );
		chatRoomDtos.forEach(room -> System.out.println("\t ## " + chatRoomService.createChatRoom(room)));
	}

	@Test
	void findTest() {
		System.out.println("2. 사용자 채팅방 조회");

		System.out.println("2-1. from 사용자 채팅방 조회");
		List<ChatRoomDto> fromRooms = chatRoomService.getChatRoomsByUserId(fromUserDto.getUserId());
		System.out.println("[" +fromRooms.size() +"]건");
		for(ChatRoomDto room : fromRooms) {
			System.out.println(room);
		}

		System.out.println("2-2. to 사용자 채팅방 조회: " + chatRoomService.getChatRoomsByUserId(toUserDto.getUserId()));
		List<ChatRoomDto> toRooms = chatRoomService.getChatRoomsByUserId(toUserDto.getUserId());
		System.out.println("[" +toRooms.size() +"]건");
		for(ChatRoomDto room : toRooms) {
			System.out.println(room);
		}
	}

//	@Test
//	void modifyTest() {
//		System.out.println("3. 사용자 변경: " + userDto);
//		userDto.setNickname("닉네임 변경");
//		userDto.setEmail("이메일변경@example.com");
//		System.out.println("result: " + userService.updateUser(userDto));
//	}
//
//	@Test
//	void delete() {
//		System.out.println("4. 사용자 삭제: " + userDto);
//		userService.deleteUserById(userDto.getUserId());
//		System.out.println("result: " + "OK");
//	}

	@AfterAll
	void afterAll(){
		try {
			System.out.println("### AfterAll 테스트 사용자 삭제");
			userService.deleteUserById(fromUserDto.getUserId());
			userService.deleteUserById(toUserDto.getUserId());
			System.out.println("### 사용자 삭제 완료");

			System.out.println("### AfterAll 채팅방 사용자 삭제");
			chatRoomDtos.forEach(rooms -> {
				chatRoomService.deleteChatRoomById(rooms.getChatRoomId());
			});
			System.out.println("### 채팅방 삭제 완료");
		} catch (NoSuchElementException e){
			System.out.println("### 테스트 데이터 이미 삭제되어 있음: " + e.getMessage());
		}
	}

}
