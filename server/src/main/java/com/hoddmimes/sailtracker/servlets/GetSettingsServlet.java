package com.hoddmimes.sailtracker.servlets;

import com.hoddmimes.sailtracker.authorize.Authenticator;
import com.hoddmimes.sailtracker.aux.DBAux;
import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import com.hoddmimes.sailtracker.generated.messages.Settings;
import com.hoddmimes.sailtracker.generated.messages.SettingsRsp;
import com.mongodb.client.model.Filters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.conversions.Bson;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/restricted/getsettings")
public class GetSettingsServlet {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSettings(@Context HttpServletRequest servletRequest) {
        Logger mLog = LogManager.getLogger( GetSettingsServlet.class );

        DBAux db = ServletAux.getInstance().getDB();

        String tUserId = Authenticator.getInstance().getUserId(servletRequest);
        User tUser = db.findUserByMailOrMMSI(tUserId);

        Settings tSettings = new Settings();
        tSettings.setMMSI(tUser.getMMSI().get());
        tSettings.setMailAddress(tUser.getMailAddr().get());
        tSettings.setAutoCollect(tUser.getCollecting().get());
        tSettings.setPassword("********");
        tSettings.setIsBoat(tUser.getIsBoatMMSI().get());
        tSettings.setLastLogin(tUser.getLoginTime().orElse("Not Set"));
        tSettings.setCollectInterval(tUser.getCollectFrequencyHH().orElse(0));


        Bson tFilter = Filters.eq("MMSI", tUser.getMMSI().get());
        int tCount = (int) db.getPositionCollection().countDocuments(tFilter);
        tSettings.setPositionCount(tCount);

        SettingsRsp tResp = new SettingsRsp();
        tResp.setSettings(tSettings);
        mLog.info( "retrieved settings for user " + tUser.getMailAddr().get() + " mmsi: " + tUser.getMMSI().get());
        return tResp.toJson().toString();
    }


}
