package com.hoddmimes.sailtracker.servlets;

import com.hoddmimes.sailtracker.aux.DBAux;
import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.aux.StatusCreator;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/confirmation")
public class ConfirmationServlet {
    Logger mLog = LogManager.getLogger( ConfirmationServlet.class );
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String confirmation(@Context HttpServletRequest servletRequest) {
        String tUserMailAddress = servletRequest.getParameter("user");
        String tConfirmationId = servletRequest.getParameter("confirmationid");

        DBAux tDBAux = ServletAux.getInstance().getDB();

        User tUser = null;

        if ((tUserMailAddress == null) || (tConfirmationId == null)) {
            StatusCreator sc = new StatusCreator(false, "Sail Tracker Confirmation Error", "Invalid confirmation request");
            return sc.toString();
        }


        // Check if an DB entry with the mail address is already registered
        tUser = tDBAux.findUserByMailAddress(tUserMailAddress);
        if (tUser == null) {
            mLog.warn("user mail address not found (" + tUserMailAddress + ")");
            StatusCreator sc = new StatusCreator(false,
                    "Sail Tracker Confirmation Error",
                    "User mail address (" + tUserMailAddress + ") does not exits");
            return sc.toString();
        }

        if (tUser.getConfirmed().get()) {
            mLog.warn("user already confirmed (" + tUserMailAddress + ")");
            StatusCreator sc = new StatusCreator(true, "Sail Tracker Confirmation Warning", "User mail address is already confirmed");
            return sc.toString();
        }

        if (!tUser.getConfirmationId().get().contentEquals(tConfirmationId)) {
            mLog.warn("confirmation id does not match mailaddr (" + tUserMailAddress + ") confId (" + tConfirmationId + " / " + tUser.getConfirmationId().get() + ")" );
            StatusCreator sc = new StatusCreator(true, "Sail Tracker Confirmation Error", "Confirmation id does not match");
            return sc.toString();
        }

        tUser.setConfirmed(true);
        tDBAux.updateUser(tUser, false);


        StatusCreator sc = new StatusCreator(true,
                "Sail Tracker Account Confirmation",
                "Sail Tracker Account, MMSI: " + tUser.getMMSI().get() + " mail: " + tUserMailAddress + " is now confirmed.");
        mLog.info("user successfully confirmed mailaddr (" + tUserMailAddress + ")");
        return sc.toString();
    }
}
