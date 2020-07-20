package com.playbook.processor.service;

import com.common.beans.Code;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class CodeService {
    
    public String packageCode(Code code) {
        
        // TODO: Need to get results
        
        // Create code from template by replacing the required code string
        /*
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
        */
        // Perform maven compilation
        ProcessBuilder processBuilderForMVNPackage = new ProcessBuilder();
        ProcessBuilder processBuilderForLamdbaCreate = new ProcessBuilder();
        // -- Linux --
        
        // Run a shell command
        processBuilderForMVNPackage.command("bash", "-c", "mvn clean package -DskipTests")
                .directory(new File("/Users/johne/Documents/CodeRepository/QuarkusHackathonWorkspace/serverlessapp"));
        processBuilderForLamdbaCreate.command("bash", "-c", "LAMBDA_ROLE_ARN=\"arn:aws:iam::221252253450:role/lambda-role\" sh target/manage.sh create")
                .directory(new File("/Users/johne/Documents/CodeRepository/QuarkusHackathonWorkspace/serverlessapp"));
        // Run a shell script
        //processBuilder.command("path/to/hello.sh");
        
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
            return output.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Temp response";
        // Perform package and upload
        
        // Run the endpoint and get metrics and results
        
    }
    
}
