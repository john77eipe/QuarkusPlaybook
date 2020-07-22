package com.apiserver.services;

import com.common.beans.Code;
import com.common.beans.Result;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CodeService {
    
    @Inject
    MongoClient mongoClient;
    
    public List<Code> list() {
        List<Code> codes = new ArrayList<>();
        
        try (MongoCursor<Document> mongoCursor = getCollection().find().iterator()) {
            while (mongoCursor.hasNext()) {
                Document document = mongoCursor.next();
                Code code = new Code();
                code.setFilename(document.getString("name"));
                code.setCode(document.getString("code"));
                codes.add(code);
            }
        }
        return codes;
    }
    
    public String add(Code code) {
        Document document = new Document()
                .append("name", code.getFilename())
                .append("code", code.getCode());
        return getCollection().insertOne(document).getInsertedId().toString();
    }
    
    private MongoCollection getCollection() {
        return mongoClient.getDatabase("test").getCollection("codebase");
    }
    
    public String fetchResult(String id) {
        return Optional.ofNullable(mongoClient.getDatabase("test").getCollection("results").find().first()).orElseGet(Document::new).toJson();
    }
    
    public MongoCollection<Document> fetchAll() {
        return mongoClient.getDatabase("test").getCollection("results");
    }
    
    public void addResults(Result result) {
        Document document = new Document()
                .append("name", result.getName())
                .append("result", result.getResult());
        mongoClient.getDatabase("test").getCollection("results").insertOne(document);
    }
    
}
