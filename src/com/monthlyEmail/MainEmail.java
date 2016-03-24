package com.monthlyEmail;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Student on 3/23/2016.
 */
@Path("/helloworld")
public class MainEmail {
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Hello World";
    }

    @POST
    @Produces("application/xml")
    @Path("/add")
    public Response addUser(
            @FormParam("name") String name) {

        return Response.status(200)
                .entity("Hello " + name)
                .build();
    }
}
