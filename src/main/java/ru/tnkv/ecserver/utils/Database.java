package ru.tnkv.ecserver.utils;

import ru.tnkv.ecserver.utils.types.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public static Connection conn = null;

    public static void connect() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:ECserver.db");
        System.out.println("Connected.");
    }

    public static void create() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'token' text, 'timestamp' LONG);");
        stmt.execute("CREATE TABLE if not exists 'reports' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'token' text, 'application' text, 'location' text, 'exception' text, 'timestamp' LONG, 'archived' BOOL DEFAULT false);");
        stmt.closeOnCompletion();
        System.out.println("Created");
    }

    public static void addUser(String token, long timestamp) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO 'users' ('token', 'timestamp') VALUES(\"" + token + "\"," + timestamp + ");");
        stmt.close();
    }

    public static void addReport(String token, String appName, String exLocation, String exType, long timestamp) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO 'reports' ('token', 'application', 'location', 'exception', 'timestamp') VALUES(\"" + token + "\", \"" + appName + "\", \"" + exLocation + "\", \"" + exType + "\"," + timestamp + ");");
    }

    public static ResultSet getReports(String token) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM 'reports' WHERE token = ? ORDER BY id DESC");
        stmt.setString(1, token);
        return stmt.executeQuery();
    }

    public static boolean isUser(String token) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM 'users' WHERE token = ?");
        stmt.setString(1, token);
        ResultSet resultSet = stmt.executeQuery();
        return resultSet.next() ? true : false;
    }

    public static void removeIfExists(String token, int id) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM reports WHERE id = \"" + id + "\" AND token = \"" + token + "\";");
        System.out.println("deleted. " + token + " " + id);

    }

    public static void close() throws SQLException {
        conn.close();
        System.out.println("Closed");
    }

    public static List<Report> getReportList(String token) throws SQLException {
        List<Report> reports = new ArrayList<>();
        ResultSet resReports = getReports(token);
        while(resReports.next()) {
            reports.add(new Report(
                    resReports.getInt("id"),
                    resReports.getString("token"),
                    resReports.getString("application"),
                    resReports.getString("location"),
                    resReports.getString("exception"),
                    resReports.getLong("timestamp"),
                    resReports.getBoolean("archived")
            ));
        }
        return reports;
    }

    public static void archiveReport(String token, int id) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE reports SET archived = true WHERE id = \"" + id + "\" AND token = \"" + token + "\";");
    }
}