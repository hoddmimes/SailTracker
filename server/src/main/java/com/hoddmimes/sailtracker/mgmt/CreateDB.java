package com.hoddmimes.sailtracker.mgmt;

import com.hoddmimes.sailtracker.generated.MongoAux;

public class CreateDB {

    public static void main(String[] args) {
        CreateDB tDB = new CreateDB();
        tDB.create(args[0]);
    }

    private void create(String pHost) {
        MongoAux db = new MongoAux("sailtracker", pHost, 27017);
        db.createDatabase(true);
        System.out.println("DB create!");
    }
}
