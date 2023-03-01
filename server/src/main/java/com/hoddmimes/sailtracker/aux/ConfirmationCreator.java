package com.hoddmimes.sailtracker.aux;

import com.googlecode.jatl.HtmlWriter;

import java.io.StringWriter;

public class ConfirmationCreator extends HtmlWriter {
    private final String mConfirmationId;
    private final String mMailAddr;
    private final String mHost;

    public ConfirmationCreator(String pHost, String pConfirmationId, String pMailAddr) {
        mConfirmationId = pConfirmationId;
        mMailAddr = pMailAddr;
        mHost = pHost;
    }

    public String toString() {
        StringWriter tSw = new StringWriter();
        String tHtmlString = this.write(tSw).getBuffer().toString();
        return tHtmlString;
    }

    @Override
    protected void build() {
        this.html();
        this.body().background("#f2f2f2");
        this.br().br();
        this.div().style("border:2px solid black; width:80%; margin-top: 50px;background-color:#f2f2f2;" +
                "display:block;margin-left: auto;margin-right: auto;");
        this.h1().style("text-align: center;font-family:Arial, Helvetica, sans-serif;").text("Sail Tracker").end();
        this.p().style("text-align: center;margin: 20px 30px 20px 30px; font-family:Arial, Helvetica, sans-serif;")
                .text("You have received this mail since a SailTracker account with mail address (").i().text(mMailAddr).end().text(") ")
                .text("has been created").br().text("Before you can logon you have to confirm your mail address" +
                        " this is done by clicking on the link below").end();
        this.br();
        this.p().style("text-align: center;margin: 20px 30px 20px 30px; font-family:Arial, Helvetica, sans-serif; font-style: italic;")
                .text("If you are not aware about this, ignore and delete this mail").end();
        this.br().br().br();
        this.p().style("margin: 20px 30px 20px 30px; font-family:Arial, Helvetica, sans-serif; text-align: center; font-style: bold;")
                .a().style("background-color:blue; color:white;")
                .href(mHost + "sailtracker/app/confirmation?confirmationid=" + mConfirmationId + "&user="
                        + mMailAddr).text("Confirm Sail Tracker Account").end();
        this.end();

        this.end(); // end div

        this.end();
        this.end();
    }
}
