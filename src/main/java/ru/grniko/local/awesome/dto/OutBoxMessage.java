package ru.grniko.local.awesome.dto;

import lombok.NoArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@NoArgsConstructor
public class OutBoxMessage {
    private Long id;
    private String topic;
    private Integer partition;
    private Long offset;
    private String rawMessage;
    private String status;

    public OutBoxMessage(ConsumerRecord<String, String> km) {
        this.topic = km.topic();
        this.partition = km.partition();
        this.offset = km.offset();
        this.rawMessage = km.value();
    }

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public Integer getPartition() {
        return partition;
    }

    public Long getOffset() {
        return offset;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
