package com.apiserver;

import com.apiserver.services.CodeService;
import com.apiserver.services.KafkaWriter;
import com.common.beans.Code;
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

//import io.smallrye.mutiny.Uni;
//import io.smallrye.reactive.messaging.annotations.Channel;
//import org.eclipse.microprofile.reactive.messaging.Emitter;
//import org.eclipse.microprofile.reactive.messaging.Outgoing;

//import io.smallrye.reactive.messaging.annotations.Stream;

@Path("/code")
@ApplicationScoped
public class CodeResource {
    
    @Inject
    CodeService codeService;
    
    @Inject
    KafkaWriter kafkaWriter;

//    @Inject
//    @Stream("code-exec")
//    Emitter<Code> emitter;

//    @Inject
//    @Channel("code-exec")
//    Emitter<Code> emitter;
    
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
        System.out.println("code added");
        String insertedId = "";
        try {
            insertedId = codeService.add(code);
            code.setId(insertedId);
//            this.sendToKafka(code);
//            emitter.send(code);
            kafkaWriter.add(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertedId;
    }

//    /**
//     * A bean consuming data from the "code-exec" Kafka topic.
//     *
//     * @param code
//     */
//    @Outgoing("code-exec")
//    @Broadcast
//    public Code sendToKafka(Code code) {
//        return code;
//    }
    
    /**
     * A bean consuming data from the "code-exec" Kafka topic.
     *
     * @param result
     */
    @Incoming("code-result")
    public void processOutput(String result) {
        System.out.println("Result received: " + result);
        codeService.addResults(result);
    }

//    private Random random = new Random();
//    @Outgoing("code-exec")
//    public Uni<Code> sendToKafka(Code code) {
//        
//         Build an infinite stream of random prices
//         It emits a price every second
//        return Uni.createFrom().item(code);
//}
    
    
}