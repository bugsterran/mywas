package com.was.app.http;

import com.was.app.http.servlet.SimpleServlet;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

/**
 *  응답 헤더 구현..
 *
 */
public abstract class AbstractHttpServlet implements SimpleServlet {

    protected void sendHeader(Writer out, String contentType, int length) throws IOException {
        out.write("HTTP/1.1 200 OK\r\n");
        out.write("Date: " + new Date() + "\r\n");
        out.write("Server: myWAS\r\n");
        out.write("Content-type: " + contentType + "\r\n");
        out.write("Content-length: " + length + "\r\n\r\n");
    }
}
