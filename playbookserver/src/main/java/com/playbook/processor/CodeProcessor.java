package com.playbook.processor;

import com.common.beans.Result;
import com.playbook.processor.service.*;
import com.common.beans.Code;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import com.playbook.processor.service.ServerlessService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Message;

@ApplicationScoped
public class CodeProcessor {
    
    @Inject
    ServerlessService serverlessService;

    @Inject
    KafkaWriter kafkaWriter;
    
    /**
     * A bean consuming data from the "code-exec" Kafka topic.
     *
     * @param code
     */
    @Incoming("code-exec")
    public void processExec(Code code) throws Exception {
        System.out.println(code);
        try {
            Result result = new Result();
            result.setResult(serverlessService.packageCode(code));
            result.setName(code.getFilename());
            kafkaWriter.add(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
