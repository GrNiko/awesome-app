package ru.grniko.local.awesome.service;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import ru.grniko.local.awesome.dao.MessageDao;
import ru.grniko.local.awesome.dto.OutBoxMessage;

import java.util.List;

@Singleton
public class SenderService {
    private final MessageDao messageDao;
    private final BusService busService;

    @Inject
    public SenderService(MessageDao messageDao, BusService busService) {
        this.messageDao = messageDao;
        this.busService = busService;
    }


    @Scheduled(fixedRate = "10s")
    public void checkMessageForSend() {
        List<OutBoxMessage> preparedForSend = messageDao.getMessageListByStatus("N");
        for (OutBoxMessage message : preparedForSend) {
            try{
                busService.sendToOutcome(message);
                message.setStatus("S");
            } catch (Exception e) {
                message.setStatus("E");
            }
        }
        messageDao.persist(preparedForSend);
    }


}
