package com.apiserver.services;

import com.common.beans.Code;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingQueue;

@ApplicationScoped
public class KafkaWriter {
    
    
    private BlockingQueue<Code> messages = new LinkedBlockingQueue<>();
    
    public void add(Code message) {
        messages.add(message);
    }
    
    @Outgoing("code-exec")
    public CompletionStage<Message<Code>> send() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Code productPrice = messages.take();
                return Message.of(productPrice);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}