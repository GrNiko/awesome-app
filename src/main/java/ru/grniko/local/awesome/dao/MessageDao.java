package ru.grniko.local.awesome.dao;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.grniko.local.awesome.dto.OutBoxMessage;

import java.util.List;
import java.util.Objects;

@Singleton
public class MessageDao {

    private final JdbcTemplate template;

    @Inject
    public MessageDao(JdbcTemplate template) {
        this.template = template;
    }

    @PostConstruct
    public List<OutBoxMessage> getAllMessages() {
        return template.query("select * from out_box.handled_message", new BeanPropertyRowMapper<>(OutBoxMessage.class));
    }

    public void persist(List<OutBoxMessage> wrappedMessages) {
        wrappedMessages.forEach(msg->{
            if (Objects.nonNull(msg.getId())){
                template.update("update out_box.handled_message set topic=? ,partition=?, offset=?, raw_message=?, status=? where id=?"
                        ,msg.getTopic(), msg.getPartition(), msg.getOffset(), msg.getRawMessage(), msg.getStatus(), msg.getId() );
            } else {
                template.update("insert into out_box.handled_message (topic, partition, offset, raw_message, status) values (?, ?, ?, ?, ?)"
                        ,msg.getTopic(), msg.getPartition(), msg.getOffset(), msg.getRawMessage(), msg.getStatus());
            }
        });

    }

    public OutBoxMessage getMessageById(Long id) {
        return template.query("select * from out_box.handled_message where id = ?"
                , new BeanPropertyRowMapper<>(OutBoxMessage.class)
                , id).getFirst();
    }

    public List<OutBoxMessage> getMessageListByStatus(String status) {
        return template.query("select * from out_box.handled_message where status=? "
                , new BeanPropertyRowMapper<>(OutBoxMessage.class)
                , status);
    }
}
