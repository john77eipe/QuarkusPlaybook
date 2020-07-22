package com.playbook.processor.service;

import com.common.beans.Code;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ServerlessService {
    
    public String packageCode(Code code) throws IOException {

        StringBuilder result = new StringBuilder();
        
           
        List<String> newLines = null;

        // Write sample input to payload.txt
        newLines = new ArrayList<>();
        for (String line : Files.readAllLines(
            Paths.get("/Users/johne/Documents/CodeRepository/QuarkusHackathonWorkspace/serverlessapp/payload.txt.bak"), 
            StandardCharsets.UTF_8)) {
            if (line.contains("//PLACEHOLDER//")) {
                newLines.add(line.replace("//PLACEHOLDER//", code.getInputSample()));
            }
        }
        System.out.println(newLines);
        Files.write(Paths.get("/Users/johne/Documents/CodeRepository/QuarkusHackathonWorkspace/serverlessapp/payload.txt"), 
            newLines, StandardCharsets.UTF_8);

        // Create code from template by replacing the required code string  
        newLines = new ArrayList<>();
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
                result.append("[Compiled] [Packaged]");
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
                result.append(" [Serverless Function created]");
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
                result.append(" ["+output+"]");
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 
        
        //result.append("str");
        return result.toString();
    }
    
}
