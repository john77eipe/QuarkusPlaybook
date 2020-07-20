package com.playbook.processor;

import com.common.beans.Code;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Deserializer;

public class CodeDeserializer implements Deserializer<Code> {
    
    @Override
    public Code deserialize(String s, byte[] bytes) {
        return SerializationUtils.deserialize(bytes);
    }
}
