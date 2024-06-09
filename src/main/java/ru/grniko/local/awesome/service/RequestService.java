package ru.grniko.local.awesome.service;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import ru.grniko.local.awesome.dao.MessageDao;
import ru.grniko.local.awesome.dto.OutBoxMessage;

import java.util.List;

@Singleton
public class RequestService {

//    private final ObjectMapper mapper = new ObjectMapper();
    private final MessageDao messageDao;

    @Inject
    public RequestService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }


    public OutBoxMessage messageById(Long id) {
        return messageDao.getMessageById(id);
    }

    public List<OutBoxMessage> allMessages() {
        return messageDao.getAllMessages();
    }
}