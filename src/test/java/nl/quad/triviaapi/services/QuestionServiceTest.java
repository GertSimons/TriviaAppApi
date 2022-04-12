package nl.quad.triviaapi.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import nl.quad.triviaapi.data.CheckAnswerRequestBody;
import nl.quad.triviaapi.data.QuestionItem;
import nl.quad.triviaapi.data.QuestionResponse;
import nl.quad.triviaapi.data.dto.QuestionResponseDTO;
import nl.quad.triviaapi.exceptions.TriviaApiException;
import nl.quad.triviaapi.mappers.QuestionMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class QuestionServiceTest {
    @Mock
    QuestionMapper questionMapper;

    @Mock
    TriviaApiService triviaApiService;

    @InjectMocks
    QuestionService questionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        assertNotNull(questionService);
        assertNotNull(questionMapper);
        assertNotNull(triviaApiService);
    }
    @Test
    public void test_find_question_correct_match_returns_true() throws TriviaApiException {
        //Given
        List<QuestionItem> questionItems = new ArrayList<>();
        QuestionItem questionItem = new QuestionItem();
        questionItem.setQuestion("question");
        questionItem.setCorrectAnswer("answer");
        questionItems.add(questionItem);
        CheckAnswerRequestBody requestBody = new CheckAnswerRequestBody("question", "answer");
        when(triviaApiService.getAllQuestions()).thenReturn(questionItems);
        //When
        final boolean questionMatch = questionService.findQuestionMatch(requestBody);
        //Then
        assertTrue(questionMatch);
    }

    @Test
    public void test_find_question_bad_match_returns_false() throws TriviaApiException {
        //Given
        List<QuestionItem> questionItems = new ArrayList<>();
        QuestionItem questionItem = new QuestionItem();
        questionItem.setQuestion("question");
        questionItem.setCorrectAnswer("answer");
        questionItems.add(questionItem);
        CheckAnswerRequestBody requestBody = new CheckAnswerRequestBody("question", "wronganswer");
        when(triviaApiService.getAllQuestions()).thenReturn(questionItems);
        //When
        final boolean questionMatch = questionService.findQuestionMatch(requestBody);
        //Then
        assertFalse(questionMatch);
    }
}