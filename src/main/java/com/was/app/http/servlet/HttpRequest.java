package com.was.app.http.servlet;

public interface HttpRequest {

    public String getMethod();

    public String getPath();

    public String getVersion();

    public String getHost();

    public int getContentLength() ;

    public String getUserAgent();

    public String getContentType();

}
