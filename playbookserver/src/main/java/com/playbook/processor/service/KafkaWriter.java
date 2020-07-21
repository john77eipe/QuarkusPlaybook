package com.playbook.processor.service;

import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingQueue;

@ApplicationScoped
public class KafkaWriter {
    
    
    private BlockingQueue<String> messages = new LinkedBlockingQueue<>();
    
    public void add(String message) {
        messages.add(message);
    }
    
    @Outgoing("code-result")
    @Broadcast
    public CompletionStage<Message<String>> send() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String result = messages.take();
                System.out.println("Publishing event for result: " + result);
                return Message.of(result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}