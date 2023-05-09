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

public class SendReportHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String res;
        try {
            Map<String, String> args = QueryUtils.mapArguments(exchange.getRequestURI().getQuery());
            if(args.getOrDefault("token", null) != null
                    && args.getOrDefault("application", null) != null
                    && args.getOrDefault("location", null) != null
                    && args.getOrDefault("exception", null) != null) {
                ReportManager.sendReport(args.get("token"), args.get("app"), args.get("location"), args.get("exception"));
                res = "[\"Отправлено.\"]";
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, res.length());
                OutputStream os = exchange.getResponseBody();
                os.write(res.getBytes());
                os.close();
            } else {
                res = "[\"Произошла ошибка. Вы не указали одно из полей: token, application, location, exception.\"]";
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, res.length());
                OutputStream os = exchange.getResponseBody();
                os.write(res.getBytes());
                os.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
            res = "[\"Произошла ошибка. Сообщите об этом администратору.\"]";
            exchange.getResponseHeaders().set("Content-Type", "application/text; charset=UTF-8");
            exchange.sendResponseHeaders(200, res.length());
            OutputStream os = exchange.getResponseBody();
            os.write(res.getBytes());
            os.close();
        }
    }
}
