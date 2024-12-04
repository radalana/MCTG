package at.fhtw.swen.mctg.httpserver.http;

public class MessageConstants {
    public static final String USER_NOT_FOUND = "{ \"message\": \"User's token not found, please login first.\" }";
    public static final String REQUEST_BODY_NOT_ALLOWED = "{ \"message\": \"Request body is not allowed for this endpoint.\" }";
    public static final String INTERNAL_SERVER_ERROR = "{ \"message\": \"An unexpected error occurred while processing your request. Please try again later.\" }";
    public static final String NO_CARDS_PROVIDED = "{ \"message\": \"No cards were provided.\" }";
    public static final String CARDS_RETRIEVED_SUCCESSFULLY = "{ \"message\": \"Cards retrieved successfully.\" }";
    public static final String DECK_RETRIEVED_SUCCESSFULLY = "{ \"message\": \"Deck retrieved successfully.\" }";
    public static final String NO_CARDS_IN_DECK = "{ \"message\": \"The deck has not been configured yet. Please select up to 4 cards.\" }";
}
