package com.jpmc.midascore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class KafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final AtomicInteger counter = new AtomicInteger(0);

    @KafkaListener(topics = "${general.kafka-topic}")
    public void listen(Transaction transaction) {
        int count = counter.incrementAndGet();
        logger.info("Received transaction #{}: {}", count, transaction);

    }

}
