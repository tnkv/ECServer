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

public class CreateUserHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String res;
        try {
            UUID uuid = UUID.randomUUID();
            if(!Database.isUser(uuid.toString())) {
                ReportManager.createUser(uuid);
                res = "{\"uid\":\"" + uuid.toString() + "\"}";
                exchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
                exchange.sendResponseHeaders(200, res.length());
                OutputStream os = exchange.getResponseBody();
                os.write(res.getBytes());
                os.close();
            } else {
                res = "[\"Произошла ошибка. Попробуйте снова.\"]";
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
