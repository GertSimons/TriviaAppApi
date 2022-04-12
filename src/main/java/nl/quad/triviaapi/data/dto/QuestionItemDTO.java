package nl.quad.triviaapi.data.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionItemDTO {
    private String question;
    private List<String> answers;
}
