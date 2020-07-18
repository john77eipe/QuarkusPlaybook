package org.playbook;

import org.playbook.bean.Code;
import org.playbook.services.CodeService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/code")
public class CodeResource {

    @Inject
    CodeService codeService;

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
    
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String add(final Code code) {
        String insertedId = "";
        try {
            insertedId = codeService.add(code);
            code.setId(insertedId);
            // TODO: Push into Kafka Queue
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertedId;
    }
}