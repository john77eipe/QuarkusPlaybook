package com.playbook.processor.service;

import com.common.beans.Result;
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
    
    private BlockingQueue<Result> messages = new LinkedBlockingQueue<>();
    
    public void add(Result message) {
        messages.add(message);
    }
    
    @Outgoing("code-result")
    @Broadcast
    public CompletionStage<Message<Result>> send() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Result result = messages.take();
                System.out.println("Publishing event for result: " + result);
                return Message.of(result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}