import com.hoddmimes.sailtracker.generated.MongoAux;
import com.hoddmimes.sailtracker.generated.dbobjects.Position;

import java.text.SimpleDateFormat;

public class LoadPosition
{

    public static void main(String[] args) {
        LoadPosition lp = new LoadPosition();
        lp.test();
    }


    private void test() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MongoAux db = new MongoAux( "sailtracker", "192.168.42.11", 27017);
        db.connectToDatabase();

        try {
            long tTimestamp = sdf.parse("2023-01-10 10:00:00").getTime();
            double tLat = 59.1, tLong = 19.3;
            for (int i = 0; i < 100; i++) {
                Position pos = new Position();
                pos.setLat( tLat );
                pos.setLong( tLong );
                pos.setMMSI("123456789");
                pos.setTime( sdf.format( tTimestamp ));
                pos.setSource("test");
                pos.setReportTime( sdf.format(System.currentTimeMillis()));
                db.insertPosition( pos );

                tTimestamp += 60000L;
                tLat += 0.1;
                tLong += 0.1;
            }
        }
        catch( Exception e) {
            e.printStackTrace();
        }
    }
}
