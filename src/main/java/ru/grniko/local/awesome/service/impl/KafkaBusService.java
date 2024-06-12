package ru.grniko.local.awesome.service.impl;

import jakarta.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.grniko.local.awesome.dao.MessageDao;
import ru.grniko.local.awesome.dto.OutBoxMessage;
import ru.grniko.local.awesome.service.BusService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static ru.grniko.local.awesome.configuration.KafkaConf.INCOME_TOPIC;
import static ru.grniko.local.awesome.configuration.KafkaConf.OUTCOME_TOPIC;

public class KafkaBusService implements BusService {

    private final KafkaConsumer<String, String> consumer;
    private final MessageDao dao;

    @Inject
    public KafkaBusService(KafkaConsumer<String, String> consumer, MessageDao dao) {
        this.consumer = consumer;
        this.dao = dao;
    }

    @Override
    public void handle() {
        ConsumerRecords<String, String> messages = consumer.poll(Duration.ofMillis(500));
        List<OutBoxMessage> wrappedMessages = new ArrayList<>();
        messages.forEach(km -> wrappedMessages.add(new OutBoxMessage(km)));
        wrappedMessages.forEach(m-> m.setStatus("N"));
        dao.persist(wrappedMessages);
    }

    @Override
    public void putOnIncomeTopic(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(INCOME_TOPIC, message);
        KafkaProducer<String, String> producer = producer();
        producer.send(record);
        producer.flush();
        producer.close();
        System.err.println("ok");
    }

    @Override
    public void sendToOutcome(OutBoxMessage message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(OUTCOME_TOPIC, message.getRawMessage());
        KafkaProducer<String, String> producer = producer();
        producer.send(record);
        producer.flush();
        producer.close();
    }

    public KafkaProducer<String, String> producer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "PLAINTEXT://127.0.0.1:29092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 1024);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }
}
