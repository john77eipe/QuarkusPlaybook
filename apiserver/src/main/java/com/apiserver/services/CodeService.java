package com.apiserver.services;

import com.common.beans.Code;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

    public void packageCode(Code code) throws IOException {

        // Create code from template by replacing the required code string
        
        List<String> newLines = new ArrayList<>();
        for (String line : Files.readAllLines(
            Paths.get("/Users/johne/Documents/CodeRepository/QuarkusHackathonWorkspace/serverlessapp/src/main/java/org/qksplaybook/ProcessingService.bak"), 
            StandardCharsets.UTF_8)) {
            if (line.contains("//PLACEHOLDER//")) {
                newLines.add(line.replace("//PLACEHOLDER//", code.getCode()));
            } else {
                newLines.add(line);
            }
        }
        System.out.println(newLines);
        Files.write(Paths.get("/Users/johne/Documents/CodeRepository/QuarkusHackathonWorkspace/serverlessapp/src/main/java/org/qksplaybook/ProcessingService.java"), 
            newLines, StandardCharsets.UTF_8);
        
        // Perform maven compilation
        ProcessBuilder processBuilderForMVNPackage = new ProcessBuilder();

        processBuilderForMVNPackage.command("bash", "-c", "mvn clean package -DskipTests")
        .directory(new File("/Users/johne/Documents/CodeRepository/QuarkusHackathonWorkspace/serverlessapp"));
       
        try {
            System.out.println(processBuilderForMVNPackage.command());
            Process process = processBuilderForMVNPackage.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            boolean exitVal = process.waitFor(20, TimeUnit.SECONDS);
            if (exitVal) {
                System.out.println("Success!");
                System.out.println(output);
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 
        
        ProcessBuilder processBuilderForLamdbaCreate = new ProcessBuilder();
        processBuilderForLamdbaCreate.command("bash", "-c", "FUNCTION_NAME="+code.getFilename()+" ./aws_tool.sh delete create")
        .directory(new File("/Users/johne/Documents/CodeRepository/QuarkusHackathonWorkspace/serverlessapp"));

        try {
            System.out.println(processBuilderForLamdbaCreate.command());
            Process process = processBuilderForLamdbaCreate.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            boolean exitVal = process.waitFor(100, TimeUnit.SECONDS);
            if (exitVal) {
                System.out.println("Success!");
                System.out.println(output);
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ProcessBuilder processBuilderForLamdbaInvoke = new ProcessBuilder();
        processBuilderForLamdbaInvoke.command("bash", "-c", "FUNCTION_NAME="+code.getFilename()+" ./aws_tool.sh invoke")
        .directory(new File("/Users/johne/Documents/CodeRepository/QuarkusHackathonWorkspace/serverlessapp"));

        try {
            System.out.println(processBuilderForLamdbaInvoke.command());
            Process process = processBuilderForLamdbaInvoke.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            boolean exitVal = process.waitFor(80, TimeUnit.SECONDS);
            if (exitVal) {
                System.out.println("Success!");
                System.out.println(output);
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Perform package and upload

        // Run the endpoint and get metrics and results

    }
    
    public String fetchResult(String id) {
        return Optional.ofNullable(mongoClient.getDatabase("test").getCollection("results").find().first()).orElseGet(Document::new).toJson();
    }
    
    public void addResults(String result) {
        Document document = new Document()
                .append("result", result);
        mongoClient.getDatabase("test").getCollection("results").insertOne(document);
    }
    
}
