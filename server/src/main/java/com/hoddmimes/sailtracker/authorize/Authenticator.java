package com.hoddmimes.sailtracker.authorize;


import com.hoddmimes.sailtracker.generated.messages.LogonRqst;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.UUID;

public class Authenticator implements HttpSessionListener {
    public static final String SAILTRACKER_SSID = "ST_SSID";
    public static final String SAILTRACKER_USER = "ST_USER";


    private static long cSessionTimeout = 5 * 60 * 1000;
    private static volatile Authenticator cInstance = null;

    private final HashMap<HttpSession, HttpSession> mSessionMap;
    private final Logger mLog = LogManager.getLogger(Authenticator.class);

    public static void setSessionTimeout(long pSeconds) {
        cSessionTimeout = pSeconds * 1000L;
    }

    public static Authenticator getInstance() {
        synchronized (Authenticator.class) {
            if (cInstance == null) {
                return new Authenticator();
            }
        }
        return cInstance;
    }

    public Authenticator() {
        mSessionMap = new HashMap<>();
        cInstance = this;
    }

    public void logout(HttpServletRequest pServletRequest) {
        HttpSession tUserSession = pServletRequest.getSession(false);
        if (tUserSession != null) {
            mLog.info("user: " +  tUserSession.getAttribute(SAILTRACKER_USER) + " session-id: " + tUserSession.getAttribute(SAILTRACKER_SSID));
            tUserSession.removeAttribute(SAILTRACKER_USER);
            tUserSession.removeAttribute(SAILTRACKER_SSID);
            this.mSessionMap.remove(tUserSession);
        }
    }

    public boolean isAuthorized(HttpServletRequest pServletRequest) {
        HttpSession tUserSession = pServletRequest.getSession(false);
        if (tUserSession != null) {
            if ((tUserSession.getAttribute(SAILTRACKER_SSID) != null) && (tUserSession.getAttribute(SAILTRACKER_USER) != null)) {
                String tSessId = tUserSession.getAttribute(SAILTRACKER_SSID).toString();
                synchronized (mSessionMap) {
                    for (HttpSession tSess : mSessionMap.values()) {
                        if (tSess.getAttribute(SAILTRACKER_SSID).toString().contentEquals(tSessId)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public String getUserId(HttpServletRequest pServletRequest) {
        HttpSession tUserSession = pServletRequest.getSession(false);
        if (tUserSession != null) {
            if ((tUserSession.getAttribute(SAILTRACKER_SSID) != null) && (tUserSession.getAttribute(SAILTRACKER_USER) != null)) {
                String tSessId = tUserSession.getAttribute(SAILTRACKER_SSID).toString();
                synchronized (mSessionMap) {
                    for (HttpSession tSess : mSessionMap.values()) {
                        if (tSess.getAttribute(SAILTRACKER_SSID).toString().contentEquals(tSessId)) {
                            return tUserSession.getAttribute(SAILTRACKER_USER).toString();
                        }
                    }
                }
            }
        }
        return null;
    }


    /**
     * This method is called when a user has been authorized
     * The method is setup/authorize  the session for the user
     * The method is invoked from the Logon servlet
     *
     * @param pLogonRqst
     * @param pRequest
     * @return
     * @throws Exception
     */
    public HttpSession authorize(LogonRqst pLogonRqst, HttpServletRequest pRequest) {
        // Check if user is alread logged in
        HttpSession tSession = pRequest.getSession(false);
        if (tSession != null) {
            if ((tSession.getAttribute(SAILTRACKER_SSID) != null) && (tSession.getAttribute(SAILTRACKER_USER) != null)) {
                if (tSession.getAttribute(SAILTRACKER_USER).equals(pLogonRqst.getUsername().get())) {
                    mLog.info("Already logged in user: " +  tSession.getAttribute(SAILTRACKER_USER) + " session-id: " + tSession.getAttribute(SAILTRACKER_SSID));
                    return tSession; // User already logged in
                }
            }
        } else {
            tSession = pRequest.getSession(true);
        }

        // require a fresh login
        String tSSID = UUID.randomUUID() + "_" + pRequest.getRemoteAddr();
        tSession.setAttribute(SAILTRACKER_SSID, tSSID);
        tSession.setAttribute(SAILTRACKER_USER, pLogonRqst.getUsername().get());
        synchronized (mSessionMap) {
            mLog.info("New login user: " +  tSession.getAttribute(SAILTRACKER_USER) + " session-id: " + tSession.getAttribute(SAILTRACKER_SSID));
            this.mSessionMap.put(tSession, tSession);
        }
        return tSession;

    }

    @Override
    public void sessionCreated(HttpSessionEvent pSession) {
        // by design
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent pSession) {
        synchronized (mSessionMap) {
            HttpSession tSession = this.mSessionMap.remove(pSession);
            if (tSession != null) {
                mLog.info("session destroyed user: " + tSession.getAttribute(SAILTRACKER_USER) + " session-id: " + tSession.getAttribute(SAILTRACKER_SSID));
            }
        }
    }
}



