package com.do4you.do4you.chat.message;

import com.do4you.do4you.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class MessageRepositoryImpl implements MessageRepository{
    private final MongoTemplate template;

    @Autowired
    public MessageRepositoryImpl(MongoTemplate template){
        this.template = template;
    }

    @Override
    public Message save(Message message) {
        return template.insert(message);
    }

    @Override
    public void deleteById(String messageId) {
        template.remove(query(where("_id").is(messageId)), Message.class);
    }

    @Override
    public List<Message> page(String roomId, Message last, int pageSize) {
        Criteria where = where("roomId").is(roomId);

        if(last != null)
            where.and("sendAt").lt(last.getSendAt());   // messageId 로 변경

        return template.find(
            query(where)
                .with(Sort.by(Sort.Direction.DESC, "sendAt"))
                .limit(pageSize)
            , Message.class);
    }
}
