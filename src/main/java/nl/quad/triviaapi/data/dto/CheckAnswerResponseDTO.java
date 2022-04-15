package nl.quad.triviaapi.data.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckAnswerResponseDTO {
    private String correctAnswer;
    private List<String> inCorrectAnswers;
    private boolean isCorrect;
}
