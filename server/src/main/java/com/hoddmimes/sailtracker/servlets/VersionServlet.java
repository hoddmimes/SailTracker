package com.hoddmimes.sailtracker.servlets;

import com.google.gson.JsonObject;
import com.hoddmimes.sailtracker.generated.Version;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/version")
public class VersionServlet {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String isAuthorized(@Context HttpServletRequest servletRequest) {
        JsonObject jRsp = new JsonObject();
        JsonObject jBody = new JsonObject();

        jBody.addProperty("build", Version.build);
        jBody.addProperty("major", Version.major );
        jBody.addProperty( "minor", Version.minor );
        jBody.addProperty("buildDate", Version.buildDate );

        jRsp.add("Version", jBody );
        return jRsp.toString();
    }
}
