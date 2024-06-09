package ru.grniko.local.awesome.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import ru.grniko.local.awesome.dto.OutBoxMessage;
import ru.grniko.local.awesome.service.BusService;
import ru.grniko.local.awesome.service.RequestService;

import java.util.List;

@Controller("/message")
public class AwesomeController {

    private final RequestService requestService;
    private final BusService busService;

    @Inject
    public AwesomeController(RequestService requestService, BusService busService) {
        this.requestService = requestService;
        this.busService = busService;
    }


    @Get("/all")
    @Produces(MediaType.TEXT_PLAIN)
    public List<OutBoxMessage> getActualMessageList() {
            return requestService.allMessages();
    }

    @Get("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public OutBoxMessage getMessageById(@PathVariable("id") Long id) {
        return requestService.messageById(id);
    }

    @Post("/new")
    @Produces(MediaType.TEXT_PLAIN)
    public String putMessage(@Body String message) {
        busService.putOnIncomeTopic(message);
        return "done";
    }


}
