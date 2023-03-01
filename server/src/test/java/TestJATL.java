import com.hoddmimes.sailtracker.aux.ConfirmationCreator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestJATL
{

    public static void main(String[] args) {
        TestJATL t = new TestJATL();
        t.test();
    }

    private void test() {
        ConfirmationCreator c = new ConfirmationCreator( "http://localhost:8282/", "2468016627", "par.bertilsson@yahoo.com");
        try {
            FileOutputStream fp = new FileOutputStream("corfirm-test.html");
            fp.write( c.toString().getBytes(StandardCharsets.UTF_8));
            fp.close();
        }
        catch( IOException e) {
            e.printStackTrace();
        }
        System.out.println(c);
    }
}
