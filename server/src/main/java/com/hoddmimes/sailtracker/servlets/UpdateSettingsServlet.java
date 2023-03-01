package com.hoddmimes.sailtracker.servlets;

import com.hoddmimes.sailtracker.authorize.Authenticator;
import com.hoddmimes.sailtracker.aux.DBAux;
import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.aux.StatusAux;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import com.hoddmimes.sailtracker.generated.messages.Settings;
import com.hoddmimes.sailtracker.generated.messages.UpdateSettingsRqst;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/restricted/updatesettings")
public class UpdateSettingsServlet {
    private Logger mLog = LogManager.getLogger( UpdateSettingsServlet.class );
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String updateSettings(@Context HttpServletRequest servletRequest, String pJsonRequest) {

        UpdateSettingsRqst tRqst = new UpdateSettingsRqst(pJsonRequest);

        DBAux db = ServletAux.getInstance().getDB();
        String tUserId = Authenticator.getInstance().getUserId(servletRequest);
        User tUser = db.findUserByMailOrMMSI(tUserId);

        Settings tSettings = tRqst.getSettings().get();
        tUser.setMailAddr(tSettings.getMailAddress().get().trim());
        if (!tSettings.getPassword().get().contentEquals("********")) {
            tUser.setPassword(ServletAux.hashPassword(tUser.getSalt().get(), tSettings.getPassword().get()));
        }
        if (tSettings.getCollectInterval().isPresent()) {
            tUser.setCollectFrequencyHH(tSettings.getCollectInterval().get());
        }
        if (tSettings.getAutoCollect().isPresent()) {
            tUser.setCollecting(tSettings.getAutoCollect().get());
        }

        db.updateUser(tUser, false);

        mLog.info("updated settings for user " + tUser.getMailAddr().get() + " mmsi: " + tUser.getMMSI().get());
        return StatusAux.create(true, "User successfully updated").toJson().toString();
    }
}
