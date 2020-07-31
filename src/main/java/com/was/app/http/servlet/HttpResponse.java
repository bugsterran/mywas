package com.was.app.http.servlet;

import java.io.PrintWriter;

public interface HttpResponse {

    void setHeader(String header);

    PrintWriter getWriter();

    void setContentLength(int i);
}
