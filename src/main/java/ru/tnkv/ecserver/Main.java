package ru.tnkv.ecserver;

import ru.tnkv.ecserver.utils.Database;
import ru.tnkv.ecserver.utils.ReportManager;
import ru.tnkv.ecserver.web.WebServer;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.setProperty("file.encoding", "UTF-8");
        Database.connect();
        Database.create();
        WebServer.startServer(4411);
    }
}