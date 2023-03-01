package com.hoddmimes.sailtracker.servlets;

import com.hoddmimes.sailtracker.authorize.Authenticator;
import com.hoddmimes.sailtracker.aux.DBAux;
import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.generated.dbobjects.Position;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import com.hoddmimes.sailtracker.generated.messages.Pos;
import com.hoddmimes.sailtracker.generated.messages.PositionRsp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/restricted/positions")
public class GetAllPositionsServlet {
    Logger mLog = LogManager.getLogger( GetAllPositionsServlet.class );
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String positions(@Context HttpServletRequest servletRequest) {

        DBAux db = ServletAux.getInstance().getDB();

        String tUserId = Authenticator.getInstance().getUserId(servletRequest);
        User tUser = db.findUserByMailOrMMSI(tUserId);

        List<Position> tPositions = db.findPosition(tUser.getMMSI().get());

        PositionRsp tRsp = new PositionRsp();
        if (tPositions != null) {
            List<Pos> tPosLst = new ArrayList();
            for (Position p : tPositions) {
                tPosLst.add(new Pos(p));
            }
            tRsp.setPositions(tPosLst);
        }
        if (tPositions == null) {
            mLog.info("no positions found for " + tUser.getMailAddr().get() + " mmsi: " + tUser.getMMSI().get());
        }
        else {
            mLog.info(String.valueOf(tPositions.size()) + " positions found for user " + tUser.getMailAddr().get() + " mmsi: " + tUser.getMMSI().get());
        }
        return tRsp.toString();
    }
}