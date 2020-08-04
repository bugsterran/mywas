package com.was.app.http;

import com.google.gson.Gson;
import com.was.app.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class HttpServer {

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private static final int NUM_THREADS = 50;
    private static final int DEFAULT_PORT = 80;
    private final int port;
    private List<Config.VirtualHosts> virtualHosts;

    public HttpServer(Config config, int port) {

        try {
            if (port < 0 || port > 65535) port = DEFAULT_PORT;
        } catch (RuntimeException ex) {
            port = DEFAULT_PORT;
        }
        this.virtualHosts = config.getVirtualHosts();
        this.port = port;
    }

    public void start() throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        ServerSocket server = new ServerSocket(port);
        logger.info("Accepting connections on port " + server.getLocalPort());
        while (true) {
            try {
                Socket request = server.accept();
                Runnable r = new RequestProcessor(virtualHosts, request);
                pool.submit(r);
            } catch (IOException ex) {
                logger.error("Error accepting connection", ex);
            }
        }
    }

    public static void main(String[] args) {

        try (InputStream inputStream = HttpServer.class.getResourceAsStream("/config.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){

            String strConfig = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            Gson gson = new Gson();
            Config config = gson.fromJson(strConfig, Config.class);
            HttpServer webServer = new HttpServer(config, config.getPort());
            webServer.start();
        } catch (IOException ex) {
            logger.error("Server could not start", ex);
        }
    }
}
