package nl.quad.triviaapi.controllers;

import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import nl.quad.triviaapi.data.CheckAnswerRequestBody;
import nl.quad.triviaapi.data.dto.CheckAnswerResponseDTO;
import nl.quad.triviaapi.data.dto.QuestionResponseDTO;
import nl.quad.triviaapi.exceptions.TriviaApiException;
import nl.quad.triviaapi.services.QuestionService;
import nl.quad.triviaapi.services.TriviaApiService;

@RestController
@Slf4j
public class QuestionController {
    QuestionService questionService;
    TriviaApiService triviaApiService;

    public QuestionController(QuestionService questionService, TriviaApiService triviaApiService) {
        this.questionService = questionService;
        this.triviaApiService = triviaApiService;
    }

    @GetMapping("/questions")
    public QuestionResponseDTO getQuestions(@RequestParam(value = "quantity", defaultValue = "5") int numberOfQuestions,
                                            @RequestParam(value = "token") String token) {
        try {
            return questionService.getQuestions(numberOfQuestions, token);
        } catch (TriviaApiException e) {
            log.error("failed to retrieve questions. Statuscode response {}", e.getResponseCode());
        }
        return null;
    }

    @PostMapping("/checkanswer")
    public CheckAnswerResponseDTO checkAnswer(@RequestBody CheckAnswerRequestBody requestBody) {
        try {
            return new CheckAnswerResponseDTO(questionService.findQuestionMatch(requestBody));
        } catch (TriviaApiException e) {
            log.error("failed to check answer {}", e.getResponseCode());
        }
        return null;
    }
}
