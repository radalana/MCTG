package at.fhtw.swen.mctg.httpserver.server;

import at.fhtw.swen.mctg.httpserver.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Response {
    private static final String CONTENT_TYPE = "application/json";

    private int status;
    private String message;
    private String content;

    public Response(HttpStatus httpStatus, String content) {
        this.status = httpStatus.code;
        this.message = httpStatus.message;
        this.content = content;
    }

    public String get() {
        String localDatetime = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("UTC")));
        return "HTTP/1.1 " + this.status + " " + this.message + "\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Connection: close\r\n" +
                "Date: " + localDatetime + "\r\n" +
                "Expires: " + localDatetime + "\r\n" +
                "Content-Type: " + CONTENT_TYPE + "\r\n" +
                "Content-Length: " + this.content.length() + "\r\n" +
                "\r\n" +
                this.content;
    }
}
