package com.hoddmimes.sailtracker.servlets;

import com.hoddmimes.sailtracker.authorize.Authenticator;
import com.hoddmimes.sailtracker.aux.DBAux;
import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.aux.StatusAux;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import com.mongodb.client.model.Filters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.conversions.Bson;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/restricted/deleteAccount")
public class DeleteAccountServlet {
    Logger mLog = LogManager.getLogger( DeleteAccountServlet.class );
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteAccount(@Context HttpServletRequest servletRequest) {

        DBAux db = ServletAux.getInstance().getDB();
        String tUserId = Authenticator.getInstance().getUserId(servletRequest);
        User tUser = db.findUserByMailOrMMSI(tUserId);

        // Delete all positions
        Bson tFilter = Filters.eq("MMSI", tUser.getMMSI().get());
        long tCount = db.deletePosition(tFilter);

        //
        db.deleteUserByMMSI(tUser.getMMSI().get());
        Authenticator.getInstance().logout(servletRequest);

        mLog.info("User account [ " + tUser.getMailAddr().get() + "] mmsi: " + tUser.getMMSI().get() + " is now deleted" );
        return StatusAux.create(true, "Account and positions successfully removed", "/sailtracker/index.html").toString();
    }

}
