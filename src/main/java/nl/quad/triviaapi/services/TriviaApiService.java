package nl.quad.triviaapi.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.quad.triviaapi.data.QuestionItem;
import nl.quad.triviaapi.data.QuestionResponse;
import nl.quad.triviaapi.data.TokenResponse;
import nl.quad.triviaapi.exceptions.TriviaApiException;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Service
@Slf4j
public class TriviaApiService {
    private static final String BASE_URL = "https://opentdb.com/";
    private static final String API_TOKEN_REQUEST = "api_token.php?command=request";
    private static final String CONTENT_TYPE = "content-type";
    private static final String APPLICATION_JSON = "application/json";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public QuestionResponse getQuestions(int number, String token) throws TriviaApiException {
        try {
            Request request = new Request.Builder()
                    .url(BASE_URL + "api.php?amount=" + number + "&token=" + token)
                    .get()
                    .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                    .build();
            final String response = Objects.requireNonNull(client.newCall(request).execute().body()).string();
            final QuestionResponse questionResponse = objectMapper.readValue(response, QuestionResponse.class);
            if (questionResponse.getResponseCode() != 0) {
                throw new TriviaApiException(questionResponse.getResponseCode());
            }
            return questionResponse;
        } catch (IOException e) {
            log.error("TriviaApiService: ", e);
        }
        return null;
    }

    @Cacheable("questions")
    public List<QuestionItem> getAllQuestions() throws TriviaApiException {
        log.info("start fetching all questions");
        final TokenResponse token = getApiToken();
        List<QuestionItem> allQuestions = new ArrayList<>();
        int statusCode = 0;
        while (statusCode == 0) {
            try {
                QuestionResponse response = getQuestions(50, token.getToken());
                statusCode = response.getResponseCode();
                allQuestions.addAll(response.getResults());
            } catch (TriviaApiException e) {
                if (e.getResponseCode() == 4) {
                    log.info("done fetching all questions");
                    return allQuestions;
                } else {
                    throw new TriviaApiException(e.getResponseCode());
                }
            }
        }
        return allQuestions;
    }

    TokenResponse getApiToken() throws TriviaApiException {
        try {
            Request request = new Request.Builder()
                    .url(BASE_URL + API_TOKEN_REQUEST)
                    .get()
                    .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                    .build();
            final String response = Objects.requireNonNull(client.newCall(request).execute().body()).string();
            final TokenResponse tokenResponse = objectMapper.readValue(response, TokenResponse.class);
            if (tokenResponse.getResponseCode() != 0) {
                throw new TriviaApiException(tokenResponse.getResponseCode());
            }
            return tokenResponse;
        } catch (IOException e) {
            log.error("TriviaApiService: ", e);
        }
        return null;
    }
}
