package com.hoddmimes.sailtracker.servlets;

import com.google.gson.JsonObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;

@Path("/test")
public class TestServlet {
    private static int cCount = 1;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String test() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        JsonObject jObject = new JsonObject();
        jObject.addProperty("time", sdf.format(System.currentTimeMillis()));
        jObject.addProperty("message", "message from Test servlet");
        jObject.addProperty("count", cCount++);
        return jObject.toString();
    }
}
