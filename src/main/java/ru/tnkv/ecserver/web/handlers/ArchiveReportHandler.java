package ru.tnkv.ecserver.web.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.tnkv.ecserver.utils.Database;
import ru.tnkv.ecserver.utils.QueryUtils;
import ru.tnkv.ecserver.utils.ReportManager;
import ru.tnkv.ecserver.utils.types.Report;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.*;

public class ArchiveReportHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String res;
        try {
            Map<String, String> args = QueryUtils.mapArguments(exchange.getRequestURI().getQuery());

            if(args.getOrDefault("token", null) != null && args.getOrDefault("id", null) != null) {
                Database.archiveReport(args.get("token"), Integer.parseInt(args.get("id")));
                res = "[\"Архивировано.\"]";
                exchange.sendResponseHeaders(200, res.length());
                OutputStream os = exchange.getResponseBody();
                os.write(res.getBytes());
                os.close();
            } else {
                res = "[\"Произошла ошибка. Вы не указали одно из полей: token, id.\"]";
                exchange.sendResponseHeaders(200, res.length());
                OutputStream os = exchange.getResponseBody();
                os.write(res.getBytes());
                os.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
            res = "[\"Произошла ошибка. Сообщите об этом администратору.\"]";
            exchange.sendResponseHeaders(200, res.length());
            OutputStream os = exchange.getResponseBody();
            os.write(res.getBytes());
            os.close();
        }
    }
}
