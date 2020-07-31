package com.was.app.http;

import com.was.app.http.servlet.HttpRequest;

public class MyHttpRequest implements HttpRequest {
    private String method;
    private String path;
    private String version;
    private String host;
    private int contentLength;
    private String userAgent;
    private String contentType;

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getContentLength() {
        return contentLength;
    }

    @Override
    public String getUserAgent() {
        return userAgent;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String toString() {
        return "HttpRequestHeader{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", version='" + version + '\'' +
                ", host='" + host + '\'' +
                ", contentLength=" + contentLength +
                ", userAgent='" + userAgent + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }


}
