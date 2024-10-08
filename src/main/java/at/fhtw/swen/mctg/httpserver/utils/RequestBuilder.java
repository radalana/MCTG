package at.fhtw.swen.mctg.httpserver.utils;

import at.fhtw.swen.mctg.httpserver.http.Method;
import at.fhtw.swen.mctg.httpserver.server.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

//
public class RequestBuilder {
    public Request buildRequest(BufferedReader bufferedReader) throws IOException {
        Request request = new Request();
        String line = bufferedReader.readLine();
        System.out.println(line);
        if (line != null) {
            String[] splitFirstLine = line.split(" ");
            System.out.println("splitFirstline[] " + Arrays.toString(splitFirstLine));
            request.setMethod(getMethod(splitFirstLine[0]));
            setPathname(request, splitFirstLine[1]); //bsp "/users"

            line = bufferedReader.readLine();
            System.out.println("method: " + request.getMethod());
            System.out.println("pathname: " + request.getPathname());

            //что это
            while (!line.isEmpty()) {
                request.getHeaderMap().ingest(line);
                line = bufferedReader.readLine();
            }

            //request body
            if(request.getHeaderMap().getContentLength() > 0) {
                char[] charBuffer = new char[request.getHeaderMap().getContentLength()];
                bufferedReader.read(charBuffer, 0, request.getHeaderMap().getContentLength());

                request.setBody(new String(charBuffer));
            }
        }

        return request;
    }

    //читает метод из строки, переводит в верхний регистр и пытается найти подходящий в enum
    private Method getMethod(String methodString) {
        return Method.valueOf(methodString.toUpperCase(Locale.ROOT));
    }

    private void setPathname(Request request, String path) {
        //  https://google.com/search?query=apple&category=fruits
        Boolean hasParams = path.contains("?");
        if (hasParams) {
            String[] pathParts = path.split("\\?");//[" https://google.com/search", "query=apple&category=fruits"]
            request.setPathname(pathParts[0]);
            request.setParams(pathParts[1]);
        }else {
            request.setPathname(path);
            request.setParams(null);
        }
    }
}