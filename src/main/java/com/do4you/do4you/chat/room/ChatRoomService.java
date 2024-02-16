package com.do4you.do4you.chat.room;

import com.do4you.do4you.dto.ChatRoomDto;
import com.do4you.do4you.model.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public List<ChatRoomDto> getChatRoomsByUserId(String userId){
        List<ChatRoom> userChatRooms = chatRoomRepository.findByFromUserIdOrToUserId(userId);
        List<ChatRoomDto> dtos = new ArrayList<>();

        System.out.println("userChatRooms count: " + userChatRooms.size() );
        for(ChatRoom chatRoom: userChatRooms) {
            System.out.println(chatRoom);
            dtos.add(ChatRoomDto.builder()
                    .chatRoomId(chatRoom.getChatRoomId())
                    .fromUserId(chatRoom.getFromUserId())
                    .toUserId(chatRoom.getToUserId())
                    .build());
        }

        return dtos;
    }

    public ChatRoomDto createChatRoom(ChatRoomDto chatRoomDto) {
        String chatRoomId = UUID.randomUUID().toString();
        chatRoomRepository.save(ChatRoom.builder()
                .chatRoomId(chatRoomId)
                .fromUserId(chatRoomDto.getFromUserId())
                .toUserId(chatRoomDto.getToUserId())
                .build());

        return getChatRoomById(chatRoomId);
    }

    public ChatRoomDto getChatRoomById(String chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow();
        return ChatRoomDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .fromUserId(chatRoom.getFromUserId())
                .toUserId(chatRoom.getToUserId())
                .build();
    }

    public ChatRoomDto updateChatRoom(ChatRoomDto chatRoomDto) {
        Optional<ChatRoom> chatRoomOpt = chatRoomRepository.findById(chatRoomDto.getChatRoomId());

        chatRoomOpt.ifPresentOrElse(chatRoom -> {
            // TODO - ChatRoomService: status 업데이트 추후 추가
            chatRoomRepository.save(chatRoom);
        },
        ()-> {
            throw new NoSuchElementException("No value present");
        });

        return getChatRoomById(chatRoomDto.getChatRoomId());
    }

    public void deleteChatRoomById(String chatRoomId) {
        chatRoomRepository.deleteById(chatRoomId);
    }
}
