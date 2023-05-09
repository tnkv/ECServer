package ru.tnkv.ecserver.utils;

import java.util.HashMap;
import java.util.Map;

public class QueryUtils {
    public static Map<String, String> mapArguments(String query) {
        Map<String, String> result = new HashMap<>();
        for (String arg : query.split("&")) {
            String[] entry = arg.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }
}
