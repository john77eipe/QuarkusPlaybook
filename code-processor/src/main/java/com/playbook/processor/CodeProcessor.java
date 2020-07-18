package com.playbook.processor;

import com.playbook.processor.bean.Code;
import com.playbook.processor.service.CodeService;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CodeProcessor {
    
    @Inject
    CodeService codeService;
    
    /**
     * A bean consuming data from the "code-exec" Kafka topic.
     *
     * @param code
     */
    @Incoming("code-exec")
    @Outgoing("code-output")
    @Broadcast
    public String processExec(Code code) {
        return codeService.packageCode(code);
    }
    
    /**
     * A bean consuming data from the "code-exec" Kafka topic.
     *
     * @param code
     */
    @Incoming("code-output")
    @Broadcast
    public void processOutput(Code code) {
        // update in DB
        // send back to client
    }
    
    
}
