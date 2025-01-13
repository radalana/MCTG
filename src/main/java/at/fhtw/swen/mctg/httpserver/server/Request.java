package at.fhtw.swen.mctg.httpserver.server;

import at.fhtw.swen.mctg.httpserver.http.Method;
import com.sun.net.httpserver.Headers;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Request {
    private Method method;
    private String urlContent;
    @Getter
    private String pathname;
    private List<String> pathParts;
    @Getter
    @Setter
    private Map<String, String> params;
    @Setter
    @Getter
    private HeaderMap headerMap = new HeaderMap();
    private String body;


    public String getServiceRoute() {
        if (this.pathParts == null || this.pathParts.isEmpty()) {
            return null;
        }
        //System.out.println("Request.java getServiceRoute()");
        //System.out.println("this.pathParts: " + this.pathParts);
        //return '/' + String.join("/", this.pathParts);
        return "/" + this.pathParts.getFirst();
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
    public String getParameter(String key) {
        return params != null ? params.get(key) : null;
    }

    public boolean isParameterEqualTo(String key, String value) {
        String paramValue = getParameter(key);
        return paramValue != null && paramValue.equals(value);
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
