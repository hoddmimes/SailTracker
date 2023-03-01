package com.hoddmimes.sailtracker.servlets;

import com.hoddmimes.sailtracker.authorize.Authenticator;
import com.hoddmimes.sailtracker.aux.DBAux;
import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.generated.dbobjects.Position;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import com.hoddmimes.sailtracker.generated.messages.Pos;
import com.hoddmimes.sailtracker.generated.messages.SummaryRsp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/restricted/summary")
public class SummaryServlet {
    private Logger mLog = LogManager.getLogger( SummaryServlet.class );
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String summary(@Context HttpServletRequest servletRequest) {

        DBAux db = ServletAux.getInstance().getDB();


        String tUserId = Authenticator.getInstance().getUserId(servletRequest);
        User tUser = db.findUserByMailOrMMSI(tUserId);


        SummaryRsp tSummary = new SummaryRsp();

        tSummary.setTotalUsers(db.getTotalConfirmedUsers());
        tSummary.setTotalPositions(db.getTotalPositions());
        Position tFirstEverPosition = db.getFirstEverPosition();
        if (tFirstEverPosition != null) {
            tSummary.setTotalFirstEver(tFirstEverPosition.getTime().get());
        }

        tSummary.setMMSI(tUser.getMMSI().get());
        tSummary.setMmsiPositions(db.getTotalPositions(tUser.getMMSI().get()));
        tSummary.setMmsiLastLogin(tUser.getLastLogin().get());

        Position tPos = db.getFirstMMSIPosition(tUser.getMMSI().get());
        if (tPos != null) {
            tSummary.setMmsiFirstPos(new Pos(tPos));
        }

        tPos = db.getLastMMSIPosition(tUser.getMMSI().get());
        if (tPos != null) {
            tSummary.setMmsiLastPos(new Pos(tPos));
        }
        mLog.info("summary collected for user " + tUser.getMailAddr().get() + " mmsi: " + tUser.getMMSI().get());
        return tSummary.toString();
    }


}
