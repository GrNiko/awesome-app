package ru.grniko.local.awesome.service;

import jakarta.inject.Singleton;
import ru.grniko.local.awesome.dto.OutBoxMessage;

@Singleton
public interface BusService {

    void handle();

    void putOnIncomeTopic(String message);

    void sendToOutcome(OutBoxMessage message);
}
