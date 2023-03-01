package com.hoddmimes.sailtracker.servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.hoddmimes.sailtracker.aux.DBAux;
import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.aux.StatusAux;
import com.hoddmimes.sailtracker.generated.dbobjects.Position;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.List;

@Path("/phonepos")
public class PhonePosition {
    Logger mLog = LogManager.getLogger( PhonePosition.class );

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String phonePosition(@Context HttpServletRequest servletRequest, String jRequestString) {
        JsonObject jRqst = null;
        String tMMSI, tPassword, tDateTimeUTC;
        double tLat, tLong;


        try {
            jRqst = JsonParser.parseString(jRequestString).getAsJsonObject();
        } catch (JsonSyntaxException e) {
            mLog.warn("Invalid JSON syntax rqst: " + jRequestString );
            return StatusAux.create(false, "Invalid JSON syntax").toJson().toString();
        }

        if (!jRqst.has("mmsi")) {
            mLog.warn("MMSI is missing, rqst: " + jRequestString );
            return StatusAux.create(false, "Invalid request, \"mmsi\" is missing").toJson().toString();
        }
        if (!jRqst.has("password")) {
            mLog.warn("Password is missing, rqst: " + jRequestString );
            return StatusAux.create(false, "Invalid request, \"password\" is missing").toJson().toString();
        }
        if (!jRqst.has("date")) {
            mLog.warn("Date is missing, rqst: " + jRequestString );
            return StatusAux.create(false, "Invalid request, \"date\" is missing").toJson().toString();
        }
        if (!jRqst.has("lat")) {
            mLog.warn("Latitude is missing, rqst: " + jRequestString );
            return StatusAux.create(false, "Invalid request, \"lat\" is missing").toJson().toString();
        }
        if (!jRqst.has("long")) {
            mLog.warn("Longitude is missing, rqst: " + jRequestString );
            return StatusAux.create(false, "Invalid request, \"lat\" is missing").toJson().toString();
        }

        try {
            tMMSI = jRqst.get("mmsi").getAsString();
            tPassword = jRqst.get("password").getAsString();
            tDateTimeUTC = jRqst.get("date").getAsString();
            tLat = jRqst.get("lat").getAsDouble();
            tLong = jRqst.get("long").getAsDouble();
            tDateTimeUTC = tDateTimeUTC.replace("T", " ").substring(0, tDateTimeUTC.length() - 4);
        } catch (Exception e) {
            return StatusAux.create(false, "Invalid request e: " + e.getMessage()).toJson().toString();
        }

        DBAux db = ServletAux.getInstance().getDB();
        List<User> tUserList = db.findUserByMMSI(tMMSI);
        if ((tUserList == null) || (tUserList.size() == 0)) {
            mLog.warn("unknown MMSI " + tMMSI );
            return StatusAux.create(false, "Unknown MMSI").toJson().toString();
        }
        User tUser = tUserList.get(0);

        String tHashedPassword = ServletAux.hashPassword(tUser.getSalt().get(), tPassword);
        if (!tHashedPassword.contentEquals(tUser.getPassword().get())) {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException ie) {
            }
            mLog.warn("invalid password for MMSI " + tMMSI );
            return StatusAux.create(false, "Invalid password").toJson().toString();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Position tPosition = new Position();
        tPosition.setLong(tLong);
        tPosition.setLat(tLat);
        tPosition.setMMSI(tMMSI);
        tPosition.setSource("phone");
        tPosition.setTime(tDateTimeUTC);
        tPosition.setReportTime(sdf.format(System.currentTimeMillis()));

        //db.updatePosition( tPosition, true );

        mLog.info("position successfully added for MMSI " + tMMSI + " lat: " + tLat + " long: " + tLong + " time: " + tDateTimeUTC );
        return StatusAux.create(true, " position successfully added").toJson().toString();


    }
}
