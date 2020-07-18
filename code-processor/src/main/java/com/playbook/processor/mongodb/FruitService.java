package com.playbook.processor.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FruitService {
    
    @Inject
    MongoClient mongoClient;
    
    public List<Fruit> list() {
        List<Fruit> fruits = new ArrayList<>();
        
        try (MongoCursor<Document> mongoCursor = getCollection().find().iterator()) {
            while (mongoCursor.hasNext()) {
                Document document = mongoCursor.next();
                Fruit fruit = new Fruit();
                fruit.setName(document.getString("name"));
                fruit.setDescription(document.getString("description"));
                fruits.add(fruit);
            }
        }
        return fruits;
    }
    
    public void add(Fruit fruit) {
        Document document = new Document()
                .append("name", fruit.getName())
                .append("description", fruit.getDescription());
        getCollection().insertOne(document);
    }
    
    private MongoCollection getCollection() {
        return mongoClient.getDatabase("test").getCollection("fruit");
    }
    
}
