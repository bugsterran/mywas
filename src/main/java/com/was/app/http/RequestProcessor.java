package com.was.app.http;

import com.was.app.common.HttpCode;
import com.was.app.http.rule.Rule;
import com.was.app.http.servlet.HttpRequest;
import com.was.app.http.servlet.HttpResponse;
import com.was.app.http.servlet.SimpleServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestProcessor implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(RequestProcessor.class);
    private Socket connection;
    private List<Map<String, Object>> virtualHosts;
    private String baseServletPath = "com.was.app.";

    public RequestProcessor(List<Map<String, Object>> virtualHosts, Socket connection) {
        this.virtualHosts = virtualHosts;
        this.connection = connection;
    }

    @Override
    public void run() {
        try (InputStream in = connection.getInputStream()) {
            MyHttpRequest myHttpRequest = setHttpRequest(in);
            logger.info(myHttpRequest.getHost() + " " + myHttpRequest.getPath() + " " + myHttpRequest.getMethod());
            Map<String, Object> config = findConfig(myHttpRequest);
            String documentRoot = (String) config.get("DocumentRoot");
            if (isForbidden(myHttpRequest, config)) {
                String filePath = ((Map<String, String>) config.get("ErrorDocument")).get("403");
                processError(documentRoot + filePath, HttpCode.FORBIDDEN);
                return;
            }
            doServletService(myHttpRequest, config);
        } catch (IOException  e) {
            logger.warn("Error talking to " + connection.getRemoteSocketAddress(), e);
        } catch (Exception e) {
            logger.error("UnExpected Error ", e);
        }
    }

    private void doServletService(MyHttpRequest myHttpRequest, Map<String, Object> config) throws IOException {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));
        MyHttpResponse myHttpResponse = new MyHttpResponse();
        String documentRoot = (String) config.get("DocumentRoot");
        myHttpResponse.setWriter(pw);
        Class cla = getServletClass(myHttpRequest, config);
        if (cla == null) {
            String filePath = ((Map<String, String>) config.get("ErrorDocument")).get("404");
            processError(documentRoot + filePath, HttpCode.NOT_FOUND);
            return;
        }
        try{
            SimpleServlet httpServlet = (SimpleServlet) cla.getConstructor(null).newInstance();
            Method method = cla.getMethod("service", HttpRequest.class, HttpResponse.class);
            method.invoke(httpServlet, myHttpRequest, myHttpResponse);
            pw.flush();
        }catch (Exception e){
            logger.error("Servlet Service Error : ", e);
            String filePath = ((Map<String, String>) config.get("ErrorDocument")).get("500");
            processError(documentRoot + filePath, HttpCode.INTERNAL_SERVER_ERROR);
        }
    }

    private Class getServletClass(MyHttpRequest myHttpRequest, Map<String, Object> config) {
        StringBuilder servletName = new StringBuilder(baseServletPath);
        if (config.get("ServiceLocation") != null) servletName.append(config.get("ServiceLocation"));

        if (config.get("UseURLMapping") != null && config.get("UseURLMapping").equals("true")) {
            servletName.append(((Map<String, Object>) config.get("MappingUrl")).get(myHttpRequest.getPath()));
        } else {
            servletName.append(myHttpRequest.getPath().replace("/", "."));
        }
        try {
            return Class.forName(servletName.toString());
        } catch (ClassNotFoundException e) {
            logger.error("ServletName : " + servletName, e);
            return null;
        }
    }

    private boolean isForbidden(MyHttpRequest myHttpRequest, Map<String, Object> config) {
        if (config.get("UsePathRules") != null) {
            Map<String, List<String>> rules = (Map<String, List<String>>) config.get("UsePathRules");
            for (Rule rule : Rule.values()) {
                if (rules.get(rule.getName()) != null) {
                    if (rule.isViolate(myHttpRequest, rules.get(rule.getName()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Map<String, Object> findConfig(MyHttpRequest myHttpRequest) {
        Map<String, Object> config = null;
        for (Map<String, Object> virtualHost : virtualHosts) {
            if (virtualHost.get("SeverName").equals(myHttpRequest.getHost())) {
                config = virtualHost;
                break;
            }
        }
        return config;
    }

    private MyHttpRequest setHttpRequest(InputStream in) throws IOException {
        MyHttpRequest httpRequest = new MyHttpRequest();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = br.readLine();
        String[] firstLineArgs = line.split(" ");
        httpRequest.setMethod(firstLineArgs[0]);
        httpRequest.setPath(firstLineArgs[1]);
        httpRequest.setVersion(firstLineArgs[2]);
        while ((line = br.readLine()) != null) {
            if ("".equals(line)) {
                break;
            }
            String[] headerArray = line.split(" ");
            if (headerArray[0].startsWith("Host:")) {
                httpRequest.setHost(headerArray[1].trim());
            } else if (headerArray[0].startsWith("Content-Length:")) {
                int length = Integer.parseInt(headerArray[1].trim());
                httpRequest.setContentLength(length);
            } else if (headerArray[0].startsWith("User-Agent:")) {
                httpRequest.setUserAgent(line.substring(12));
            } else if (headerArray[0].startsWith("Content-Type:")) {
                httpRequest.setContentType(headerArray[1].trim());
            }
        }
        return httpRequest;
    }

    private void processError(String path, HttpCode code) {
        try (OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            PrintWriter pw = new PrintWriter(out);
            InputStream inputStream = HttpServer.class.getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String strHtml = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            long fileLength = strHtml.length();
            pw.println("HTTP/1.1 " + code.getCode() + " " + code.getMsg());
            pw.println("Date: " + new Date());
            pw.println("Server: myWAS");
            pw.println("Content-Type: text/html;");
            pw.println("Content-Length: " + fileLength);
            pw.println();
            pw.flush();
            out.write(strHtml.getBytes("UTF-8"));
            out.flush();
        } catch (IOException ex) {
            logger.error("FAIL ERROR HTML", ex);
        }
    }

}
