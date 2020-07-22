package com.playbook.processor;

import com.common.beans.Result;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Deserializer;

public class ResultDeserializer implements Deserializer<Result> {
    
    @Override
    public Result deserialize(String s, byte[] bytes) {
        return SerializationUtils.deserialize(bytes);
    }
}
