package com.apiserver.services;

import com.common.beans.Code;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class CodeDeserializer extends JsonbDeserializer<Code> {
    
    public CodeDeserializer() {
        super(Code.class);
    }
}
