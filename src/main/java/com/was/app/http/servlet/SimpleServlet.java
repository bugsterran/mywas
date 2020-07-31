package com.was.app.http.servlet;


import java.io.IOException;

public interface SimpleServlet {
    void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}
