package com.apiserver;

import com.apiserver.services.CodeService;
import com.apiserver.services.KafkaWriter;
import com.common.beans.Code;
import com.common.beans.Result;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


@Path("/code")
@ApplicationScoped
public class CodeResource {
    
    @Inject
    CodeService codeService;
    
    @Inject
    KafkaWriter kafkaWriter;

    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Code> list() {
        return codeService.list();
    }
    
    @GET
    @Path("/result/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String fetchresult(@PathParam("id") String id) {
        return codeService.fetchResult(id);
    }
    
    @GET
    @Path("/results")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Document> fetchresult() {
        
        FindIterable<Document> iterable = codeService.fetchAll().find();
        List<Document> documents = new ArrayList<>();
        for (Document document : iterable) {
            documents.add(document);
        }
        return documents;
    }
    
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String add(final Code code) {
        String insertedId = "";
        try {
            insertedId = codeService.add(code);
            code.setId(insertedId);
            kafkaWriter.add(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertedId;
    }

    
    /**
     * A bean consuming data from the "code-exec" Kafka topic.
     *
     * @param result
     */
    @Incoming("code-result")
    public void processOutput(Result result) {
        codeService.addResults(result);
    }

    
    
}