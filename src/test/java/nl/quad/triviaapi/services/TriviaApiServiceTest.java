package nl.quad.triviaapi.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.quad.triviaapi.data.QuestionResponse;
import nl.quad.triviaapi.exceptions.TriviaApiException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TriviaApiServiceTest {
    TriviaApiService triviaApiService;

    @BeforeEach
    public void setup() {
        triviaApiService = new TriviaApiService();
    }

    @Test
    public void test_get_5_questions() throws TriviaApiException {
        //Given
        //When
        final QuestionResponse response = triviaApiService.getQuestions(5, "");
        //Then
        assertEquals(5, response.getResults().size());
    }

    @Test
    public void test_get_more_than_50_questions_should_return_50_questions() throws TriviaApiException {
        //Given
        //When
        final QuestionResponse response = triviaApiService.getQuestions(100, "");
        //Then
        assertEquals(50, response.getResults().size());
    }

    @Test
    public void test_throw_exception_when_api_doesnt_return_0() {
        assertThrows(TriviaApiException.class, () -> triviaApiService.getQuestions(5, "wrongToken"));
    }
}