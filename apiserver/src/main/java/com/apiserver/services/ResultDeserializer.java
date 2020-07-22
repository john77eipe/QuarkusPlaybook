package com.apiserver.services;

import com.common.beans.Result;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class ResultDeserializer extends JsonbDeserializer<Result> {
    
    public ResultDeserializer() {
        super(Result.class);
    }
}
