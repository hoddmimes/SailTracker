package com.hoddmimes.sailtracker.aux;

import com.hoddmimes.sailtracker.generated.MongoAux;
import com.hoddmimes.sailtracker.generated.dbobjects.Position;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

public class DBAux extends MongoAux {

    public DBAux(String pDbName, String pDbHost, int pDbPort) {
        super(pDbName, pDbHost, pDbPort);
    }

    public User findUserByMailAddress(String pMailAddr) {
        Bson tFilter = Filters.eq("mailAddr", pMailAddr);
        List<User> tUsers = super.findUser(tFilter);
        if ((tUsers == null) || (tUsers.size() == 0)) {
            return null;
        }
        return tUsers.get(0);
    }

    public User findUserByMailOrMMSI(String pUserId) {
        Bson tFilter = Filters.or(
                Filters.eq("mailAddr", pUserId),
                Filters.eq("MMSI", pUserId));

        List<User> tUsers = super.findUser(tFilter);
        if ((tUsers == null) || (tUsers.size() == 0)) {
            return null;
        }
        return tUsers.get(0);
    }


    public int getTotalConfirmedUsers() {
        Bson tFilter = Filters.eq("confirmed", true);
        return (int) super.getUserCollection().countDocuments(tFilter);
    }

    public int getTotalPositions() {
        return getTotalPositions(null);
    }

    public int getTotalPositions(String pMMSI) {
        Bson tFilter = (pMMSI == null) ? null : Filters.eq("MMSI", pMMSI);
        return (int) super.getPositionCollection().countDocuments(tFilter);
    }

    public Position getFirstEverPosition() {
        BasicDBObject dbObject = new BasicDBObject("_id", 1); // find first
        FindIterable<Document> tDocuments = super.getPositionCollection().find().sort(dbObject).limit(1);
        if (tDocuments == null) {
            return null;
        }
        MongoCursor<Document> tIter = tDocuments.iterator();
        if (tIter == null) {
            return null;
        }
        if (!tIter.hasNext()) {
            return null;
        }
        Position tPos = new Position();
        tPos.decodeMongoDocument(tIter.next());
        return tPos;
    }

    public Position getFirstMMSIPosition(String pMMSI) {
        Bson tFilter = Filters.eq("MMSI", pMMSI);
        BasicDBObject dbObject = new BasicDBObject("_id", 1); // find first
        FindIterable<Document> tDocuments = super.getPositionCollection().find(tFilter).sort(dbObject).limit(1);
        if (tDocuments == null) {
            return null;
        }
        MongoCursor<Document> tIter = tDocuments.iterator();
        if (tIter == null) {
            return null;
        }
        if (!tIter.hasNext()) {
            return null;
        }
        Position tPos = new Position();
        tPos.decodeMongoDocument(tIter.next());
        return tPos;
    }

    public Position getLastMMSIPosition(String pMMSI) {
        Bson tFilter = Filters.eq("MMSI", pMMSI);
        BasicDBObject dbObject = new BasicDBObject("_id", -1); // find first
        FindIterable<Document> tDocuments = super.getPositionCollection().find(tFilter).sort(dbObject).limit(1);
        if (tDocuments == null) {
            return null;
        }
        MongoCursor<Document> tIter = tDocuments.iterator();
        if (tIter == null) {
            return null;
        }
        if (!tIter.hasNext()) {
            return null;
        }
        Position tPos = new Position();
        tPos.decodeMongoDocument(tIter.next());
        return tPos;
    }
}
