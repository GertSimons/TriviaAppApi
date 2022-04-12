package nl.quad.triviaapi.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenResponse extends Response {
    @JsonProperty("response_message")
    private String responseMessage;
    private String token;
}
