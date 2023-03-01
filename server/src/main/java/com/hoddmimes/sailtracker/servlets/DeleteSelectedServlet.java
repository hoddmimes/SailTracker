package com.hoddmimes.sailtracker.servlets;

import com.hoddmimes.sailtracker.authorize.Authenticator;
import com.hoddmimes.sailtracker.aux.DBAux;
import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.aux.StatusAux;
import com.hoddmimes.sailtracker.generated.dbobjects.Position;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import com.hoddmimes.sailtracker.generated.messages.DeleteSelectedRqst;
import com.hoddmimes.sailtracker.generated.messages.Pos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/restricted/deleteSelected")
public class DeleteSelectedServlet {
    Logger mLog = LogManager.getLogger( DeleteSelectedServlet.class );
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteSelected(@Context HttpServletRequest servletRequest, String jRequestString) {

        DBAux db = ServletAux.getInstance().getDB();
        String tUserId = Authenticator.getInstance().getUserId(servletRequest);
        User tUser = db.findUserByMailOrMMSI(tUserId);


        DeleteSelectedRqst tRqst = new DeleteSelectedRqst(jRequestString);

        if (tRqst.getPositions().get().size() == 0) {
            mLog.warn(" no positions found for user " + tUser.getMailAddr().get() + " mmsi: " + tUser.getMMSI().get());
            return StatusAux.create(true, "No position delete, none selected").toString();
        }

        long tDeletedCount = 0;
        for (Pos p : tRqst.getPositions().get()) {

            Position tPosition = db.findPositionByMongoId(p.getId().get());
            if (tPosition.getMMSI().get().contentEquals(tUser.getMMSI().get())) {
                tDeletedCount += db.deletePositionByMongoId(p.getId().get());
            }
        }
        mLog.info( String.valueOf( tDeletedCount) + " positions were deleted " + tUser.getMailAddr().get() + " mmsi: " + tUser.getMMSI().get());
        return StatusAux.create(true, tDeletedCount + " position delete").toString();
    }

}
