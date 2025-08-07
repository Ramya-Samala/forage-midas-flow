package com.jpmc.midascore;

import com.jpmc.midascore.component.DatabaseConduit;
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
    private final DatabaseConduit databaseConduit;

    public KafkaConsumer(DatabaseConduit databaseConduit) {
        this.databaseConduit = databaseConduit;
    }

    @KafkaListener(topics = "${general.kafka-topic}")
    public void listen(Transaction transaction) {
        int count = counter.incrementAndGet();
        logger.info("Received transaction #{}: {}", count, transaction);
        boolean result = databaseConduit.processTransaction(
                transaction.getSenderId(),
                transaction.getRecipientId(),
                transaction.getAmount()
        );

        if (result) {
            logger.info("Transaction processed successfully: {}", transaction);
        } else {
            logger.warn("Transaction processing failed: {}", transaction);
        }
    }


}

