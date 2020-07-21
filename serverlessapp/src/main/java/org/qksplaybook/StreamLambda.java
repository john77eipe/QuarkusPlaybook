package org.qksplaybook;

import javax.inject.Named;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.HashMap;

import javax.inject.Inject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Named("stream")
public class StreamLambda implements RequestStreamHandler {

    @Inject
    ProcessingService service;

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        // int letter;
        // while ((letter = inputStream.read()) != -1) {
        //     int character = Character.toUpperCase(letter);
        //     outputStream.write(character);
        // }
       
        LambdaLogger logger = context.getLogger();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("US-ASCII")));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("US-ASCII"))));
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {};
            HashMap<String, String> input = mapper.readValue(reader, typeRef);
            logger.log("STREAM TYPE: " + inputStream.getClass().toString());
            OutputObject output = service.process(input).setRequestId(context.getAwsRequestId());
            writer.write(mapper.writeValueAsString(output));
            if (writer.checkError())
            {
                logger.log("WARNING: Writer encountered an error.");
            }
        }   
        catch (Exception exception)
        {
        logger.log(exception.toString());
        }
        finally
        {
        reader.close();
        writer.close();
        }
    }
}
