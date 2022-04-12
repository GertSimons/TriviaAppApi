package nl.quad.triviaapi.mappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import nl.quad.triviaapi.data.dto.QuestionItemDTO;
import nl.quad.triviaapi.data.QuestionItem;
import nl.quad.triviaapi.data.QuestionResponse;
import nl.quad.triviaapi.data.dto.QuestionResponseDTO;

@Service
public class QuestionMapper {
    public QuestionResponseDTO toQuestionResponseDTO(QuestionResponse questionResponse) {
        List<QuestionItemDTO> questionItemDTOS = new ArrayList<>();

        for (QuestionItem questionItem : questionResponse.getResults()) {
            questionItemDTOS.add(toQuestionItemDTO(questionItem));
        }

        return QuestionResponseDTO.builder().results(questionItemDTOS).build();
    }

    private QuestionItemDTO toQuestionItemDTO(QuestionItem question) {
        List<String> answers = question.getIncorrectAnswers();
        answers.add(question.getCorrectAnswer());
        Collections.shuffle(answers);

        return QuestionItemDTO.builder()
                .question(question.getQuestion())
                .answers(answers)
                .build();
    }
}
