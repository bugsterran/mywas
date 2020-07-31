package com.was.app.http;

import com.was.app.http.servlet.HttpResponse;

import java.io.PrintWriter;

public class MyHttpResponse implements HttpResponse {

    private PrintWriter writer;

    private int contentLength = 0;

    @Override
    public void setHeader(String header) {

    }

    public void setWriter(PrintWriter writer){
        this.writer = writer;
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    public int getContentLength() {
        return contentLength;
    }

    @Override
    public void setContentLength(int i) {
        this.contentLength = i;
    }

}
