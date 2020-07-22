package com.playbook.processor;

import com.common.beans.Result;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Serializer;

public class ResultSerializer implements Serializer<Result> {
    
    @Override
    public byte[] serialize(String s, Result result) {
        return SerializationUtils.serialize(result);
    }
}
