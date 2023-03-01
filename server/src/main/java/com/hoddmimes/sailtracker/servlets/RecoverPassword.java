package com.hoddmimes.sailtracker.servlets;

import com.hoddmimes.sailtracker.aux.DBAux;
import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.aux.StatusAux;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import com.hoddmimes.sailtracker.generated.messages.RecoverPasswordRqst;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/recover")
public class RecoverPassword {

    Logger mLog = LogManager.getLogger( RecoverPassword.class );

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String recoveryPassword(@Context HttpServletRequest servletRequest, String jRequestString) {

        RecoverPasswordRqst tRqst = new RecoverPasswordRqst(jRequestString);
        DBAux db = ServletAux.getInstance().getDB();

        User tUser = db.findUserByMailAddress(tRqst.getMailAddress().get());
        if (tUser == null) {
            try {
                Thread.sleep(2000L);
            } catch (Exception e) {
            }
            mLog.warn(" unknown mail address " + tRqst.getMailAddress().get());
            return StatusAux.create(false, "no such mail address").toJson().toString();
        }

        if (!tUser.getConfirmed().get()) {
            mLog.warn( "user must be confirmed before passwd can be changed " + tUser.getMailAddr().get() + " mmsi: " + tUser.getMMSI().get());
            return StatusAux.create(false, "you have to confirm mail address first").toJson().toString();
        }

        tUser.setConfirmationId(UUID.randomUUID().toString());
        db.updateUser(tUser, false);


        ServletAux.getInstance().sendRecoveryMail(tUser);
        mLog.info( "user password change has ben requested " + tUser.getMailAddr().get() + " mmsi: " + tUser.getMMSI().get() +
                " confirmationId: " + tUser.getConfirmationId().get());
        return StatusAux.create(true, "Watch your mail for password reset link").toJson().toString();


    }
}
