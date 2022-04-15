package nl.quad.triviaapi.data.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionResponseDTO {
    private String token;
    private List<QuestionItemDTO> results;
}
