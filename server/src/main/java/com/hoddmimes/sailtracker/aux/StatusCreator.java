package com.hoddmimes.sailtracker.aux;


import com.googlecode.jatl.HtmlWriter;

import java.io.StringWriter;

public class StatusCreator extends HtmlWriter {
    private final String mTitle;
    private final String mMessage;
    private final boolean mSuccess;

    public StatusCreator(boolean pSuccess, String pTitle, String pMessage) {
        mSuccess = pSuccess;
        mTitle = pTitle;
        mMessage = pMessage;
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
        this.h1().style("text-align: center;font-family:Arial, Helvetica, sans-serif;").text(mTitle).end();
        this.p().style("text-align: center;margin: 20px 30px 20px 30px; font-family:Arial, Helvetica, sans-serif;").text(mMessage).end();
        this.br();

        if (mSuccess) {
            this.p().style("margin: 20px 30px 20px 30px; font-family:Arial, Helvetica, sans-serif; text-align: center; font-style: bold;")
                    .a().style("background-color:blue; color:white;")
                    .href("/sailtracker/index.html").text("Visit Sail Tracker").end();
            this.end();
        }

        this.end(); // end div

        this.end();
        this.end();
    }
}
