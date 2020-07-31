package com.was.app.http.service;

import com.was.app.http.AbstractHttpServlet;
import com.was.app.http.servlet.HttpRequest;
import com.was.app.http.servlet.HttpResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 *  현재 시각을 전달한다.
 *
 */

public class MyHttpServlet extends AbstractHttpServlet {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Date date = new Date(System.currentTimeMillis());
        PrintWriter out = httpResponse.getWriter();
        String body = date.toString();
        sendHeader(out, "text/html; charset=UTF-8", body.length());
        out.write(body + "\r\n");
    }
}
