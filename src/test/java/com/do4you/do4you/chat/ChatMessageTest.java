package com.do4you.do4you.chat;

import com.do4you.do4you.chat.message.MessageService;
import com.do4you.do4you.dto.MessageDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ChatMessageTest {

    @Autowired
    MessageService messageService;

    class Room{
        String id;
        String userId;
        String partnerId;

        Room(String id, String userId, String partnerId) {
            this.id = id;
            this.userId = userId;
            this.partnerId = partnerId;
        }
    }

    Room room1 = new Room("me-partner-room", "meId", "partnerId");
    Room room2 = new Room("me-other-room", "meId", "otherId");

    List<MessageDto> testDatas = new ArrayList<>();
    List<MessageDto> savedDatas = new ArrayList<>();

    @BeforeAll
    public void makeDatas(){
        boolean isMe = true;
        for(int i=0; i<20; i++){
            Room room = i%2 == 0 ? room1 : room2;
            String userId = isMe ? room.userId : room.partnerId;
            isMe = !isMe;

            testDatas.add(MessageDto.builder()
                    .chatRoomId(room.id)
                    .userId(userId)
                    .content(userId + " : " + i)
                    .sendAt("2024-02-24 10:00:" + String.format("%02d.000", i))
                    .build());
        }
    }

    @Test
    void addTest() {
        for(MessageDto dto : testDatas){
            MessageDto saved = messageService.save(dto);
            if(saved != null) {
                System.out.println(saved);
                savedDatas.add(saved);
            }
        }
    }

    @Test
    void pageTest(){

        List<MessageDto> room1s = new ArrayList<>();
        List<MessageDto> room2s = new ArrayList<>();
        for(int i=0; i<savedDatas.size(); i++)
        {
            if(i%2 == 0)
                room1s.add(savedDatas.get(i));
            else
                room2s.add(savedDatas.get(i));
        }

        MessageDto pageStart1 = room1s.get(5);
        System.out.println("page1: " + pageStart1.getContent());

        MessageDto pageStart2 = room2s.get(5);
        System.out.println("page2: " + pageStart2.getContent());

        System.out.println("\n[room1]");
        List<MessageDto> room1List = messageService.getPage(room1.id, pageStart1, 5);
        room1List.forEach(System.out::println);

        System.out.println("\n[room2]");
        List<MessageDto> room2List = messageService.getPage(room2.id, pageStart2, 5);
        room2List.forEach(System.out::println);
    }

    @AfterAll
    public void removeDatas(){
        for (MessageDto m : savedDatas) {
            messageService.remove(m.getMessageId());
        }
    }

}
