package org.gislers.playground.esb.gateway.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by jim
 * Created on 9/27/15.
 */
@Path("/api")
public class GatewayResource {

    @POST
    @Path("/product")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendProduct( String product ) {
        return Response.accepted().build();
    }
}
