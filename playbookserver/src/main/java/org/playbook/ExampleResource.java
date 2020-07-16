package org.playbook;

import org.playbook.services.*;
import org.playbook.bean.*;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/hello")
public class ExampleResource {

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
    
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Code> add(final Code code) {
        //codeService.add(code);
        try {
            codeService.packageCode(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list();
    }
}