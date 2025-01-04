package at.fhtw.swen.mctg.httpserver.server;

import at.fhtw.swen.mctg.httpserver.http.Method;
import com.sun.net.httpserver.Headers;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private Method method;
    private String urlContent;
    private String pathname;
    private List<String> pathParts;
    private String params;
    private HeaderMap headerMap = new HeaderMap();
    private String body;


    public String getServiceRoute() {
        if (this.pathParts == null || this.pathParts.isEmpty()) {
            return null;
        }
        System.out.println("Request.java getServiceRoute()");
        System.out.println("this.pathParts: " + this.pathParts);
        //return '/' + String.join("/", this.pathParts);
        return "/" + this.pathParts.getFirst();
    }

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        // "game/users/123"
        this.pathname = pathname;
        String[] stringParts = pathname.split("/");
        this.pathParts = new ArrayList<>();
        for (String part : stringParts) {
            if (part != null && part.length() > 0) { //т.е только валидные пути попадут (например НЕ попадет game//users////)
                this.pathParts.add(part);
            }
        }
    }

    public String getParams() {
        return params;
    }
    public void setParams(String params) {
        this.params = params;
    }

    public HeaderMap getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(HeaderMap headerMap) {
        this.headerMap = headerMap;
    }
    public Method getMethod() {
        return method;
    }
    public void setMethod(Method method) {
        this.method = method;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
    public List<String> getPathParts() {
        return pathParts;
    }
}
