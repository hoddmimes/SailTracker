package com.hoddmimes.sailtracker.servlets;

import com.google.gson.JsonObject;
import com.hoddmimes.sailtracker.authorize.Authenticator;
import com.hoddmimes.sailtracker.aux.DBAux;
import com.hoddmimes.sailtracker.aux.MapDataLayer;
import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.generated.dbobjects.Position;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import com.hoddmimes.sailtracker.generated.messages.PositionsForMapRqst;
import com.hoddmimes.sailtracker.generated.messages.PositionsForMapRsp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Path("/getmappositions")
public class GetPositionForMapServlet {
    Logger mLog = LogManager.getLogger( GetPositionForMapServlet.class );

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String getmappositions(@Context HttpServletRequest servletRequest, String jRequestString) {
        PositionsForMapRqst tRqst = new PositionsForMapRqst(jRequestString);

        DBAux db = ServletAux.getInstance().getDB();

        PositionsForMapRsp tRsp = new PositionsForMapRsp();


        // if request has "positionId" or "mmsiId"
        if (tRqst.getPositionId().isPresent()) {
            return getSinglePosition(tRqst.getPositionId().get());
        } else if (tRqst.getMmsiId().isPresent()) {
            List<User> tUserList = db.findUserByMMSI(tRqst.getMmsiId().get());
            if ((tUserList == null) || (tUserList.size() == 0)) {
                mLog.warn(" unknown MMSI " + tRqst.getMmsiId().get());
                return tRsp.setSuccess(false).setReason("Unknown MMSI").toJson().toString();
            }
            mLog.info("map positions found for mmsi: " + tRqst.getMmsiId().get());
            return getAllPosition(tRqst.getMmsiId().get());
        }

        // If wildcard, user must be authorized
        String tUserId = Authenticator.getInstance().getUserId(servletRequest);
        if (tUserId == null) {
            mLog.warn(" unspecified MMSI,  " + tRqst.getMmsiId().get());
            return tRsp.setSuccess(false).setReason("Unknown MMSI").toJson().toString();
        }

        User tUser = db.findUserByMailOrMMSI(tUserId);
        mLog.info( "map positions found for user " + tUser.getMailAddr().get() + " mmsi: " + tUser.getMMSI().get());
        return getAllPosition(tUser.getMMSI().get());

    }


    private String getSinglePosition(String pPositionMongoId) {
        DBAux db = ServletAux.getInstance().getDB();
        Position tPosition = db.findPositionByMongoId(pPositionMongoId);
        if (tPosition == null) {
            PositionsForMapRsp tRsp = new PositionsForMapRsp();
            tRsp.setSuccess(false);
            tRsp.setReason("Position not found");
            tRsp.toJson().toString();
        }

        MapDataLayer tMapLayer = new MapDataLayer(tPosition);

        JsonObject jResponse = new JsonObject();
        JsonObject jBody = new JsonObject();
        jBody.add("features", tMapLayer.mapLayerToJson());
        jBody.addProperty("success", true);
        jBody.addProperty("reason", "success");
        jResponse.add("PositionsForMapRsp", jBody);
        return jResponse.toString();
    }

    private String getAllPosition(String pMMSI) {
        DBAux db = ServletAux.getInstance().getDB();
        List<Position> tPositions = db.findPositionByMMSI(pMMSI);
        if ((tPositions == null) || (tPositions.size() == 0)) {
            PositionsForMapRsp tRsp = new PositionsForMapRsp();
            tRsp.setSuccess(false);
            tRsp.setReason("No positionsfound");
            tRsp.toJson().toString();
        }

        Collections.sort(tPositions, new PositionSorter());


        MapDataLayer tMapLayer = new MapDataLayer(tPositions);

        JsonObject jResponse = new JsonObject();
        JsonObject jBody = new JsonObject();
        jBody.add("features", tMapLayer.mapLayerToJson());
        jBody.addProperty("success", true);
        jBody.addProperty("reason", "success");
        jResponse.add("PositionsForMapRsp", jBody);
        return jResponse.toString();
    }

    static class PositionSorter implements Comparator<Position> {
        @Override
        public int compare(Position p1, Position p2) {
            return p2.getTime().get().compareTo(p1.getTime().get());
        }
    }
}
