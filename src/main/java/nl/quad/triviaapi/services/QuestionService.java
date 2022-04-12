package nl.quad.triviaapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import nl.quad.triviaapi.data.CheckAnswerRequestBody;
import nl.quad.triviaapi.data.QuestionItem;
import nl.quad.triviaapi.data.QuestionResponse;
import nl.quad.triviaapi.data.dto.QuestionResponseDTO;
import nl.quad.triviaapi.exceptions.TriviaApiException;
import nl.quad.triviaapi.mappers.QuestionMapper;

@Service
public class QuestionService {
    private final TriviaApiService triviaService;
    private final QuestionMapper questionMapper;

    public QuestionService(TriviaApiService triviaService, QuestionMapper questionMapper) {
        this.triviaService = triviaService;
        this.questionMapper = questionMapper;
    }

    public QuestionResponseDTO getQuestions(int number, String token) throws TriviaApiException {
        final QuestionResponse questions = triviaService.getQuestions(number, token);
        return questionMapper.toQuestionResponseDTO(questions);
    }

    public boolean findQuestionMatch(CheckAnswerRequestBody question) throws TriviaApiException {
        final List<QuestionItem> allQuestions = triviaService.getAllQuestions();
        return findQuestionMatch(question, allQuestions);
    }

    private boolean findQuestionMatch(CheckAnswerRequestBody requestBody, List<QuestionItem> allQuestions) throws TriviaApiException {
        final Optional<QuestionItem> questionMatchOptional = allQuestions.stream().filter(q -> q.getQuestion().equals(requestBody.getQuestion())).findAny();
        if (!questionMatchOptional.isPresent()) {
            throw new TriviaApiException(404);
        } else {
            return questionMatchOptional.get().getCorrectAnswer().equals(requestBody.getAnswer());
        }
    }
}
