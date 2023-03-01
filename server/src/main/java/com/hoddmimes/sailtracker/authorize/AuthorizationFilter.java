package com.hoddmimes.sailtracker.authorize;

import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.aux.StatusAux;
import com.hoddmimes.sailtracker.generated.messages.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class AuthorizationFilter implements Filter {
    private static final Logger cLogger = LogManager.getLogger(AuthorizationFilter.class);


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession tSession = request.getSession(false);


        String tURI = request.getRequestURI().toString();
        String tRmtAddr = request.getRemoteAddr().toString();


        if ((tSession == null) || ((tSession.getAttribute(Authenticator.SAILTRACKER_SSID) == null) && (tSession.getAttribute(Authenticator.SAILTRACKER_USER) == null))) {
            if (tSession == null) {
                cLogger.warn("[FAILURE] No session, path: " + tURI + " rmtAddr: " + tRmtAddr);
            } else {
                cLogger.warn("[FAILURE]  path: " + tURI + " rmtAddr: " + tRmtAddr +" session: " + displaySession(tSession) );
            }

            /**
             * No authorized session context is  found
             * Reject further processing by sending a response and cut the filter chain
             */
            Response tResponse = StatusAux.create(false, "Authorization failure, not logged in", "/sailtracker/login.html");
            try {
                ServletAux.sendResponse(tResponse.toJson(), response);
            } catch (Exception e) {
                throw new ServletException(e);
            }
            return; // cut the filter chain
        }

        cLogger.info("[Authorized]  path: " + tURI + " rmtAddr: " + tRmtAddr + " session: " + displaySession(tSession));


        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {

    }

    private String displaySession(HttpSession pSession) {
        SimpleDateFormat tSDF = new SimpleDateFormat("HH:mm:ss.SSS");
        return "user: " + pSession.getAttribute(Authenticator.SAILTRACKER_USER) + " cretim: " + tSDF.format(pSession.getCreationTime()) + " id: " + pSession.getId() +
                "acctim: " + tSDF.format(pSession.getLastAccessedTime());
    }



}
