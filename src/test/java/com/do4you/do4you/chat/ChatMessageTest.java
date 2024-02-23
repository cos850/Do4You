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

    String meId = "me-chatMessageTest";
    String partnerId = "partner-chatMessageTest";
    String roomId = "me-partner-room";

    List<MessageDto> testDatas = List.of(
            MessageDto.builder()
            .roomId(roomId)
            .userId(meId)
            .content("hello~~~~")
            .sendAt("2024-02-24 10:00:00.000")
            .build(),

            MessageDto.builder()
            .roomId(roomId)
            .userId(partnerId)
            .content("world~~~~")
            .sendAt("2024-02-24 10:00:00.001")
            .build()
    );
    List<MessageDto> savedDatas = new ArrayList<>();
//    UserDto me = UserDto.builder()
//            .userId(meId)
//            .nickname("me")
//            .build();
//    UserDto partner = UserDto.builder()
//            .userId(partnerId)
//            .nickname("partner")
//            .build();

    @BeforeAll
    public void makeDatas(){

    }

    @Test
    void addTest() {

        for(MessageDto dto : testDatas){
            MessageDto saved = messageService.save(dto);
            if(saved != null){
                System.out.println(saved);
                savedDatas.add(saved);
            }
        }
    }

    @AfterAll
    public void removeData(){
        savedDatas.stream().forEach(m->{
            messageService.remove(m.getMessageId());
        });
    }
}
