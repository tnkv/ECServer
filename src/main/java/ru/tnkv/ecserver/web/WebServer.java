package ru.tnkv.ecserver.web;

import com.sun.net.httpserver.HttpServer;
import ru.tnkv.ecserver.web.handlers.ArchiveReportHandler;
import ru.tnkv.ecserver.web.handlers.CreateUserHandler;
import ru.tnkv.ecserver.web.handlers.GetReportsHandler;
import ru.tnkv.ecserver.web.handlers.SendReportHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer {
    public static HttpServer server = null;

    public static void startServer(int port) {
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);

            server.createContext("/getReports", new GetReportsHandler());
            server.createContext("/createUser", new CreateUserHandler());
            server.createContext("/archive", new ArchiveReportHandler());
            server.createContext("/sendReport", new SendReportHandler());

            server.setExecutor(null);
            server.start();
            System.out.println("Веб-сервер запущен.");
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Веб-сервер не запустился.");
        }
    }
}
