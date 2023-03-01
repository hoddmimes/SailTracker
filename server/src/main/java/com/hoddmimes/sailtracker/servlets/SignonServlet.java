package com.hoddmimes.sailtracker.servlets;


import com.hoddmimes.sailtracker.authorize.Authenticator;
import com.hoddmimes.sailtracker.aux.DBAux;
import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.aux.StatusAux;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import com.hoddmimes.sailtracker.generated.messages.LogonRqst;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;

@Path("/signon")
public class SignonServlet {

    private Logger mLog = LogManager.getLogger( SignonServlet.class );

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String signon(@Context HttpServletRequest servletRequest, @Context HttpServletResponse servletRespone, String pJsonRequestString) {


        DBAux tDBAux = ServletAux.getInstance().getDB();

        User tUser = null;
        LogonRqst tRqst = new LogonRqst(pJsonRequestString);

        tUser = tDBAux.findUserByMailAddress(tRqst.getUsername().get());
        if (tUser == null) {
            if (tDBAux.findUserByMMSI(tRqst.getUsername().get()).size() > 0) {
                tUser = tDBAux.findUserByMMSI(tRqst.getUsername().get()).get(0);
            }
        }

        if (tUser == null) {
            mLog.warn("[signon] no such user (" + tRqst.getUsername().get() + ") ");
            return StatusAux.createError("No such user", null).toString();
        }

        if (!tUser.getConfirmed().get()) {
            mLog.warn("[signon] Account has not been confirmed yet for user (" + tRqst.getUsername().get() + ") ");
            return StatusAux.createError("Account has not been confirmed yet", null).toString();
        }

        String tHashedPassword = ServletAux.hashPassword(tUser.getSalt().get(), tRqst.getPassword().get());
        if (!tHashedPassword.contentEquals(tUser.getPassword().get())) {
            mLog.warn("[signon] Invalid password for user (" + tRqst.getUsername().get() + ") ");
            return StatusAux.createError("Invalid password", null).toString();
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tUser.setLastLogin(tUser.getLoginTime().orElse(""));
        tUser.setLoginTime(sdf.format(System.currentTimeMillis()));
        tDBAux.updateUser(tUser, false);

        Authenticator.getInstance().authorize(tRqst, servletRequest);

        Cookie mmsiCookie = new Cookie("mmsi", tUser.getMMSI().get());
        servletRespone.addCookie(mmsiCookie);

        mLog.info("[signon] Successfully logon, user (" + tRqst.getUsername().get() + ") ");
        return StatusAux.create(true, "successfully sign on", "sailtracker.html").toString();
    }


}
