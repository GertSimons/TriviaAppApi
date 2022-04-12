package nl.quad.triviaapi.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckAnswerRequestBody {
    private String question;
    private String answer;
}
