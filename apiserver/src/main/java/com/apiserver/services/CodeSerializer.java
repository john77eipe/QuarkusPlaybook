package com.apiserver.services;

import com.common.beans.Code;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.common.serialization.Serializer;

public class CodeSerializer implements Serializer<Code> {
    
    @Override
    public byte[] serialize(String s, Code code) {
        return SerializationUtils.serialize(code);
    }
}
