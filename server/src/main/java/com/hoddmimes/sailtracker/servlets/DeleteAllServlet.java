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

@Path("/restricted/deleteAll")
public class DeleteAllServlet {
    Logger mLog = LogManager.getLogger( DeleteAllServlet.class );
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteAll(@Context HttpServletRequest servletRequest) {

        DBAux db = ServletAux.getInstance().getDB();

        String tUserId = Authenticator.getInstance().getUserId(servletRequest);
        User tUser = db.findUserByMailOrMMSI(tUserId);

        Bson tFilter = Filters.eq("MMSI", tUser.getMMSI().get());

        long tCount = db.deletePosition(tFilter);
        if (tCount == 0) {
            mLog.warn(" no position found for " + tUser.getMailAddr().get());
            return StatusAux.create(true, "No position for user found").toString();
        }

        mLog.info(" all positions for user " + tUser.getMailAddr().get() + " mmsi: " + tUser.getMMSI().get() + " has now been delete");
        return StatusAux.create(true, "All " + tCount + " positions have been delete").toString();
    }


}
