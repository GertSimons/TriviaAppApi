package nl.quad.triviaapi.exceptions;

public class TriviaApiException extends Exception {
    private final int responseCode;

    public TriviaApiException(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return this.responseCode;
    }
}
