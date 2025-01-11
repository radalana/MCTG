package at.fhtw.swen.mctg.httpserver.utils;

import java.util.HashMap;
import java.util.Map;

public class QueryUtils {
    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> result = new HashMap<>();
        if (queryString == null || queryString.isEmpty()) {
            return result;
        }
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2); // Ограничение на 2 части
            String key = keyValue[0];
            String value = keyValue.length > 1 ? keyValue[1] : ""; // Если нет значения, оставляем пустую строку
            result.put(key, value);
        }
        return result;
    }
}
