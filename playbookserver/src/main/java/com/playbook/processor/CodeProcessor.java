package com.playbook.processor;

import com.common.beans.Code;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import com.playbook.processor.service.ServerlessService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CodeProcessor {
    
    @Inject
    ServerlessService serverlessService;
    
    /**
     * A bean consuming data from the "code-exec" Kafka topic.
     *
     * @param code
     */
    @Incoming("code-exec")
    @Outgoing("code-result")
    @Broadcast
    public String processExec(Code code) throws Exception {
        System.out.println(code);
        return serverlessService.packageCode(code);
    }
    
}
