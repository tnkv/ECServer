package ru.tnkv.ecserver.utils;

import java.sql.SQLException;
import java.util.UUID;

public class ReportManager {
    public static String createUser(UUID uid) throws SQLException {
        long timestamp = System.currentTimeMillis() / 1000;
        Database.addUser(uid.toString(), timestamp);
        return uid.toString();
    }

    public static boolean sendReport(String uuid, String appName, String exLocation, String exType) throws SQLException {
        long timestamp = System.currentTimeMillis() / 1000;
        if (Database.isUser(uuid)) {
            Database.addReport(uuid, appName, exLocation, exType, timestamp);
            return true;
        }
        return false;
    }
}
