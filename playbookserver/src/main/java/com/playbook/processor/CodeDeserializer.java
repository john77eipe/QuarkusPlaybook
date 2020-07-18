package com.playbook.processor;

import com.playbook.processor.bean.Code;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class CodeDeserializer extends JsonbDeserializer<Code> {
    
    public CodeDeserializer() {
        super(Code.class);
    }
}
