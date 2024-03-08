package com.do4you.do4you.chat.room;

import com.do4you.do4you.dto.ChatRoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRoomCustomRepositoryImpl implements ChatRoomCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ChatRoomCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<ChatRoomDto> getUserChatRoom(String userId) {
        System.out.println("userId is " + userId);

        MatchOperation matchOp = Aggregation.match(Criteria.where("userId").is(userId));
        LookupOperation lookupOp = LookupOperation.newLookup()
                .from("User")
                .localField("partnerId")
                .foreignField("_id")
                .as("partner");

        ProjectionOperation projectOp = Aggregation.project()
                .and("chatRoomId").as("chatRoomId")
                .and("userId").as("userId")
                .and("partnerId").as("partnerId")
                .and(ArrayOperators.ArrayElemAt.arrayOf("partner").elementAt(0))
                .as("partner");

        List<ChatRoomDto> chatRoomDtos = mongoTemplate.aggregate(
                Aggregation.newAggregation(matchOp, lookupOp, projectOp),
                "ChatRoom",
                ChatRoomDto.class
        ).getMappedResults();

        System.out.println("#### chatRoomDto result!!");
        for(ChatRoomDto dto : chatRoomDtos) {
            System.out.println(dto);
        }

        return chatRoomDtos;
    }
}
