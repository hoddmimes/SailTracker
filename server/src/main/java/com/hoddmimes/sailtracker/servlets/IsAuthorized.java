package com.hoddmimes.sailtracker.servlets;

import com.hoddmimes.sailtracker.authorize.Authenticator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/isAuthorized")
public class IsAuthorized {
    Logger mLog = LogManager.getLogger( IsAuthorized.class );
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String isAuthorized(@Context HttpServletRequest servletRequest) {
        if (Authenticator.getInstance().isAuthorized(servletRequest)) {
            mLog.info( Authenticator.getInstance().getUserId( servletRequest ) + " is authorized");
            return "1";
        } else {
            mLog.warn(" check unauthorized");
            return "0";
        }
    }
}
