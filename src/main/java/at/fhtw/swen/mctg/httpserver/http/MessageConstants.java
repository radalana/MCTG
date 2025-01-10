package at.fhtw.swen.mctg.httpserver.http;

public class MessageConstants {
    public static final String USER_NOT_FOUND = "{ \"message\": \"User's token not found, please login first.\" }";
    public static final String REQUEST_BODY_NOT_ALLOWED = "{ \"message\": \"Request body is not allowed for this endpoint.\" }";
    public static final String REQUEST_BODY_REQUIRED ="{ \"message\": \"Missing request data. Please check your input and try again.\" }";
    public static final String INTERNAL_SERVER_ERROR = "{ \"message\": \"An unexpected error occurred while processing your request. Please try again later.\" }";
    public static final String NO_CARDS_PROVIDED = "{ \"message\": \"No cards were provided.\" }";
    public static final String CARD_NOT_FOUND = "{ \"message\": \"Card not found.\" }";
    public static final String INVALID_NUMBER_OF_CARDS = "{ \"message\": \"Invalid number of cards provided.\" }";
    public static final String NOT_ENOUGH_COINS = "{ \"message\": \"Not enough coins available.\" }";
    public static final String CARDS_PURCHASED_SUCCESSFULLY = "{ \"message\": \"You have successfully acquired new cards.\" }";
    public static final String INVALID_HTTP_METHOD = "{ \"message\": \"Invalid HTTP method for this endpoint.\" }";
    public static final String INVALID_JSON_FORMAT = "{ \"message\": \"Invalid JSON format.\" }";
}
