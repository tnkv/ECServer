package ru.tnkv.ecserver.web.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.tnkv.ecserver.utils.Database;
import ru.tnkv.ecserver.utils.QueryUtils;
import ru.tnkv.ecserver.utils.types.Report;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GetReportsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String res;
        try {
            Map<String, String> args = QueryUtils.mapArguments(exchange.getRequestURI().getQuery());

            if (args.getOrDefault("token", null) != null) {
                List<String> reports = new ArrayList<>();
                for(Report report : Database.getReportList(args.get("token"))) {
                    reports.add(report.toJson());
                }
                res = "{\"reports\":[" + String.join(",", reports) + "]}";
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, 0);
                OutputStream os = exchange.getResponseBody();
                os.write(res.getBytes());
                os.close();
            } else {
                res = "[\"Введите поле token=, чтобы получить ответ.\"]";
                exchange.sendResponseHeaders(200, 0);
                OutputStream os = exchange.getResponseBody();
                os.write(res.getBytes());
                os.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
            res = "[\"Произошла ошибка. Сообщите об этом администратору.\"]";
            exchange.sendResponseHeaders(200, 0);
            OutputStream os = exchange.getResponseBody();
            os.write(res.getBytes());
            os.close();
        }
    }
}
