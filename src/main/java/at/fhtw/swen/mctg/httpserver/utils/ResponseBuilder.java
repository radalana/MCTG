package at.fhtw.swen.mctg.httpserver.utils;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;
import at.fhtw.swen.mctg.httpserver.server.Response;

public class ResponseBuilder {
    public static Response createResponse(HttpStatus status, String message) {
        return new Response(status, "{ \"message\": \"" + message + "\" }");
    }
}
