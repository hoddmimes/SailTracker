package com.hoddmimes.sailtracker.servlets;

import com.hoddmimes.sailtracker.authorize.Authenticator;
import com.hoddmimes.sailtracker.aux.DBAux;
import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.aux.StatusAux;
import com.hoddmimes.sailtracker.generated.dbobjects.Position;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import com.hoddmimes.sailtracker.generated.messages.AddPositionRqst;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("/restricted/addposition")
public class AddPositionServlet {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String addposition(@Context HttpServletRequest servletRequest, String pJsonRequest) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        double tLong, tLat;
        AddPositionRqst tRqst = new AddPositionRqst(pJsonRequest);
        DBAux db = ServletAux.getInstance().getDB();

        String tUserId = Authenticator.getInstance().getUserId(servletRequest);
        User tUser = db.findUserByMailOrMMSI(tUserId);

        try {
            tLat = parseLat(tRqst.getLatitude().get());
            tLong = parseLat(tRqst.getLatitude().get());
        } catch (Exception e) {
            return StatusAux.create(false, e.getMessage()).toJson().toString();
        }

        // Check Latitude
        if ((tLat < -90.0d) || (tLat > 90.0d)) {
            return StatusAux.create(false, "Latitude value out of range").toJson().toString();
        }
        if ((tLong < -180.0d) || (tLong > 180.0d)) {
            return StatusAux.create(false, "Longitude value out of range").toJson().toString();
        }

        Position tPosition = new Position();
        tPosition.setLat(tLat);
        tPosition.setLong(tLong);
        tPosition.setTime(tRqst.getTime().get());
        tPosition.setReportTime(sdf.format(System.currentTimeMillis()));
        tPosition.setSource("manual");
        tPosition.setMMSI(tUser.getMMSI().get());


        db.insertPosition(tPosition);

        return StatusAux.create(true, "Position successfully added").toJson().toString();
    }


    private double parseLat(String pLatString) throws Exception {
        Pattern decPattern = Pattern.compile("^[+,-]?\\d+\\.\\d+$");
        Pattern arcPattern = Pattern.compile("^(\\d+)[^0-9]+(\\d+)[^0-9]+(\\d+\\.?\\d*)[^0-9]+([N|S])");
        double tLat;

        try {
            if (decPattern.matcher(pLatString).matches()) {
                return Double.parseDouble(pLatString);
            }

            Matcher m = arcPattern.matcher(pLatString);
            if (!m.matches()) {
                throw new Exception("Invalid Latitude format");
            }

            double hh = Double.parseDouble(m.group(1));
            double mm = Double.parseDouble(m.group(2));
            double ss = Double.parseDouble(m.group(3));

            tLat = hh + ((mm + (ss / 60.0d)) / 60.0d);
            tLat = (m.group(4).equalsIgnoreCase("S")) ? (tLat * -1) : (tLat * 1);
        } catch (Exception e) {
            throw new Exception("Invalid latitude format");
        }
        return tLat;
    }

    private double parseLong(String pLongString) throws Exception {
        Pattern decPattern = Pattern.compile("^[+,-]?\\d+\\.\\d+$");
        Pattern arcPattern = Pattern.compile("^(\\d+)[^0-9]+(\\d+)[^0-9]+(\\d+\\.?\\d*)[^0-9]+([W|E])");
        double tLong;

        try {
            if (decPattern.matcher(pLongString).matches()) {
                return Double.parseDouble(pLongString);
            }

            Matcher m = arcPattern.matcher(pLongString);
            if (!m.matches()) {
                throw new Exception("Invalid Longitude format");
            }

            double hh = Double.parseDouble(m.group(1));
            double mm = Double.parseDouble(m.group(2));
            double ss = Double.parseDouble(m.group(3));

            tLong = hh + ((mm + (ss / 60.0d)) / 60.0d);
            tLong = (m.group(4).equalsIgnoreCase("W")) ? (tLong * -1) : (tLong * 1);
        } catch (Exception e) {
            throw new Exception("Invalid longitude format");
        }
        return tLong;
    }
}
