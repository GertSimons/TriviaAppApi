package nl.quad.triviaapi.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckAnswerResponseDTO {
    private boolean isCorrect;
}
