package com.was.app.http.service;

import com.was.app.http.AbstractHttpServlet;
import com.was.app.http.servlet.HttpRequest;
import com.was.app.http.servlet.HttpResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 *  일반 메시지를 전달한다.
 */
public class MyHttpServlet2 extends AbstractHttpServlet {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        PrintWriter out = httpResponse.getWriter();
        String body = "TIME TO SAY GOOD BYE!!";
        sendHeader(out, "text/html; charset=UTF-8", body.length());
        out.println(body + "\r\n");
    }

}
