package com.do4you.do4you.chat.room;

import com.do4you.do4you.dto.ChatRoomDto;
import com.do4you.do4you.model.ChatRoom;
import com.do4you.do4you.model.User;
import com.do4you.do4you.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
    }

    public List<ChatRoomDto> getChatRoomsByUserId(String userId){
        return chatRoomRepository.getUserChatRoom(userId);
    }

    /**
     * 채팅방 생성 함수
     * 한 사용자가 생성을 요청하면 대상 유저의 채팅방도 동일한 채팅방ID로 함께 생성한다.
     *
     * @param chatRoomDto
     * @return
     */
    public ChatRoomDto createChatRoom(ChatRoomDto chatRoomDto) {
        String chatRoomId = chatRoomDto.getUserId() + "-" + chatRoomDto.getPartnerId();

        ChatRoom createUserChatRoom = ChatRoom.builder()
                .chatRoomId(chatRoomId)
                .userId(chatRoomDto.getUserId())
                .partnerId(chatRoomDto.getPartnerId())
                .build();
        ChatRoom partnerChatRoom = ChatRoom.builder()
                .chatRoomId(chatRoomId)
                .userId(chatRoomDto.getPartnerId())
                .partnerId(chatRoomDto.getUserId())
                .build();

        chatRoomRepository.saveAll(List.of(createUserChatRoom, partnerChatRoom));

        return getChatRoomById(chatRoomId, chatRoomDto.getUserId());
    }

    public ChatRoomDto getChatRoomById(String chatRoomId, String userId) {
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomIdAndUserId(chatRoomId, userId).orElseThrow();

        System.out.println("getChatRoom: " + chatRoom);

        return ChatRoomDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .userId(chatRoom.getUserId())
                .partnerId(chatRoom.getPartnerId())
                .partner(userRepository.findById(userId).orElseThrow())
                .build();
    }

    // mongoDB의 경우, 특정 필드만 변경하여 저장할 경우 해당 row가 다시 만들어진다. 즉 기존 row가 삭제되고 id가 새로 생성됨
    // 따라서 update 대신 계속 row가 전체적으로 변경될 데이터에 적합하다.
    public ChatRoomDto updateChatRoom(ChatRoomDto chatRoomDto) {
        Optional<ChatRoom> chatRoomOpt = chatRoomRepository.findByChatRoomIdAndUserId(chatRoomDto.getChatRoomId(), chatRoomDto.getUserId());

        chatRoomOpt.ifPresentOrElse(chatRoom -> {
            // TODO - ChatRoomService: status 업데이트 추후 추가
            chatRoomRepository.save(chatRoom);
        },
        ()-> {
            throw new NoSuchElementException("No value present");
        });

        return getChatRoomById(chatRoomDto.getChatRoomId(), chatRoomDto.getUserId());
    }

    public void deleteChatRoomById(String chatRoomId) {
        chatRoomRepository.deleteById(chatRoomId);
    }
}
