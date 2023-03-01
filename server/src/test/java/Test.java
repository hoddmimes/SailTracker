import java.net.InetAddress;

public class Test
{
    public static void main(String[] args) {
        try {
            String str  = InetAddress.getLocalHost().getHostName();
            System.out.println( str );
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
