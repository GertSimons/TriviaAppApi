package nl.quad.triviaapi.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Response {
    @JsonProperty("response_code")
    private int responseCode;
}
