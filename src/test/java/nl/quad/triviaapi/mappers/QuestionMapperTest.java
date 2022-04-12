package nl.quad.triviaapi.mappers;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.quad.triviaapi.data.QuestionItem;
import nl.quad.triviaapi.data.QuestionResponse;
import nl.quad.triviaapi.data.dto.QuestionResponseDTO;

import static junit.framework.TestCase.assertTrue;


class QuestionMapperTest {
    QuestionMapper questionMapper = new QuestionMapper();
    QuestionResponse questionResponse;

    @BeforeEach
    public void setup() {
        List<String> incorrectAnswers = new ArrayList<>();
        incorrectAnswers.add("1");
        incorrectAnswers.add("2");
        incorrectAnswers.add("3");
        final QuestionItem questionItem = QuestionItem.builder()
                .question("who came first, the chicken or the egg")
                .correctAnswer("only god knows")
                .incorrectAnswers(incorrectAnswers)
                .build();
        List<QuestionItem> questionItems = new ArrayList<>();
        questionItems.add(questionItem);
        this.questionResponse = QuestionResponse.builder().results(questionItems).build();
    }

    @Test
    public void test_should_return_correct_dto() {
        //Given
        //When
        final QuestionResponseDTO questionResponseDTO = questionMapper.toQuestionResponseDTO(this.questionResponse);
        //Then
        assertTrue(questionResponseDTO.getResults().get(0).getAnswers().contains("only god knows"));
        assertTrue(questionResponseDTO.getResults().get(0).getAnswers().contains("1"));
        assertTrue(questionResponseDTO.getResults().get(0).getAnswers().contains("2"));
        assertTrue(questionResponseDTO.getResults().get(0).getAnswers().contains("3"));
        assertTrue(questionResponseDTO.getResults().get(0).getQuestion().contains("who came first, the chicken or the egg"));

    }

}