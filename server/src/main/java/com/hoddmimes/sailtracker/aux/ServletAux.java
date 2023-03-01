package com.hoddmimes.sailtracker.aux;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


public class ServletAux extends HttpServlet {
    private Mailer mMailer;
    private static volatile ServletAux cInstance = null;
    private DBAux mDbAux;
    private String mServerHost; // e.g http://localhost:8282/
    private Logger mLog = LogManager.getLogger( ServletAux.class);


    public static ServletAux getInstance() {
        return cInstance;
    }

    public ServletAux() {
        super();
        cInstance = this;
    }

    public void init(ServletConfig pConfig) throws ServletException {
        super.init(pConfig);

        String tDbName = pConfig.getInitParameter("dbName");
        String tDbHost = pConfig.getInitParameter("dbHost");
        int tDbPort = Integer.parseInt(pConfig.getInitParameter("dbPort"));

        mDbAux = new DBAux(tDbName, tDbHost, tDbPort);
        mDbAux.connectToDatabase();

        try {
            String tHostName = InetAddress.getLocalHost().getHostName();
            if (tHostName.contentEquals("hoddmimes.com")) {
                mServerHost = "https://hoddmimes.com/";
            } else{
                mServerHost = "http://localhost:8282/";
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            mServerHost = "https://hoddmimes.com/";
        }

        try {
            JsonObject jMailConf = JsonParser.parseReader(new FileReader(pConfig.getInitParameter("mailConfiguration"))).getAsJsonObject();
            mMailer = new Mailer( jMailConf.get("host").getAsString(),
                                  jMailConf.get("port").getAsInt(),
                                  jMailConf.get("user").getAsString(),
                                  jMailConf.get("password").getAsString(),
                       "text/html", true);
        }
        catch( Exception e) {
           e.printStackTrace();
        }

        //String pHost, int pPort, String pUsername, String pPassword, String pContentType, boolean pDebug )

    }

    public String getServerHost() {
        return mServerHost;
    }

    public void sendConfirmationMail( User pUser) {
        ConfirmationCreator tCreator = new ConfirmationCreator(mServerHost, pUser.getConfirmationId().get(), pUser.getMailAddr().get());
        mMailer.sendMessage(false, "sailtracker@hoddmimes.com", pUser.getMailAddr().get(), null,
                "SailTracker registration confirmation", tCreator.toString());
        mLog.info("sending confirmation mail to " + pUser.getMailAddr().get() + " confirmationId: " + pUser.getConfirmed().get());
    }

    public void sendRecoveryMail( User pUser) {
        RecoveryPasswordCreator tCreator = new RecoveryPasswordCreator(mServerHost, pUser.getConfirmationId().get(), pUser.getMailAddr().get());
        mMailer.sendMessage(false, "sailtracker@hoddmimes.com", pUser.getMailAddr().get(), null,
                "SailTracker password recovery confirmation", tCreator.toString());
        mLog.info("sending password recovery mail to " + pUser.getMailAddr().get() + " confirmationId: " + pUser.getConfirmed().get());
    }

    public static void sendResponse(JsonObject pJsonMsg, HttpServletResponse response) throws Exception {
        response.setStatus(200);
        response.setContentType("application/json");

        byte[] tBuffer = pJsonMsg.toString().getBytes();
        response.setContentLength(tBuffer.length);
        ServletOutputStream out = response.getOutputStream();
        out.write(tBuffer);
        out.flush();
    }

    public static String getConfirmationId(String pMailAddress) {
        return hashPassword(pMailAddress, UUID.randomUUID().toString());
    }

    public static String hashPassword(String pSalt, String pPassword) {
        try {
            MessageDigest tDigest = MessageDigest.getInstance("SHA-256");
            String tString = pSalt + pPassword;
            byte[] encodedhash = tDigest.digest(tString.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(encodedhash, true);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public DBAux getDB() {
        return mDbAux;
    }
}
