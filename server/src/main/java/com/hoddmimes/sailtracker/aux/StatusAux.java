package com.hoddmimes.sailtracker.aux;

import com.hoddmimes.sailtracker.generated.messages.Response;


public class StatusAux {
    public static Response create(boolean pSuccess, String pReason, String pLoadPage) {
        Response tStatus = new Response();
        tStatus.setOptionPage(pLoadPage);
        tStatus.setReason(pReason);
        tStatus.setSuccess(pSuccess);
        return tStatus;
    }

    public static Response createSuccessResponse() {
        Response tStatus = new Response();
        tStatus.setOptionPage(null);
        tStatus.setReason("Successfully completed");
        tStatus.setSuccess(true);
        return tStatus;
    }

    public static Response create(boolean pSuccess, String pReason) {
        Response tStatus = new Response();
        tStatus.setOptionPage(null);
        tStatus.setReason(pReason);
        tStatus.setSuccess(pSuccess);
        return tStatus;
    }

    public static Response createError(String pReason, Throwable pException) {
        Response tStatus = new Response();
        String tReason = pReason + ((pException == null) ? "" : ("  Exec: " + pException.getMessage()));

        tStatus.setOptionPage(null);
        tStatus.setReason(tReason);
        tStatus.setSuccess(false);
        return tStatus;
    }

}
