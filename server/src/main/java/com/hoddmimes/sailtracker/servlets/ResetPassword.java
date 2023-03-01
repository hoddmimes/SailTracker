package com.hoddmimes.sailtracker.servlets;

import com.hoddmimes.sailtracker.aux.DBAux;
import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.aux.StatusAux;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import com.hoddmimes.sailtracker.generated.messages.UpdatePasswordRqst;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/reset")
public class ResetPassword {

    Logger mLog = LogManager.getLogger( ResetPassword.class );
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String rsetPassword(@Context HttpServletRequest servletRequest, String jRequestString) {

        UpdatePasswordRqst tRqst;
        try {
            tRqst = new UpdatePasswordRqst(jRequestString);
        } catch (Exception e) {
            mLog.warn("invalid request, rqst: " + jRequestString );
            return StatusAux.create(false, "invalid request").toJson().toString();
        }


        DBAux db = ServletAux.getInstance().getDB();

        User tUser = db.findUserByMailAddress(tRqst.getMailAddress().get());
        if (tUser == null) {
            try {
                Thread.sleep(2000L);
            } catch (Exception e) {
            }
            mLog.warn("unknown user,  " + tRqst.getMailAddress().get() );
            return StatusAux.create(false, "no such user").toJson().toString();
        }
        if (!tRqst.getConfirmationId().get().contentEquals(tUser.getConfirmationId().get())) {
            mLog.warn("unknown confirmation id for user,  " + tRqst.getMailAddress().get() + " confirmationId: " +  tRqst.getConfirmationId().get());
            return StatusAux.create(false, "unknown confirmation id").toJson().toString();
        }


        String tHashedPassword = ServletAux.hashPassword(tUser.getSalt().get(), tRqst.getPassword().get());
        tUser.setPassword(tHashedPassword);
        db.updateUser( tUser, false );

        mLog.warn("password updated for user,  " + tRqst.getMailAddress().get());
        return StatusAux.create(true, "Password updated", "/sailtracker/login.html").toJson().toString();


    }
}
