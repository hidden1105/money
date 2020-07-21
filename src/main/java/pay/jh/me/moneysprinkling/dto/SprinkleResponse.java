package pay.jh.me.moneysprinkling.dto;

import lombok.*;

/**
 * @author Snow
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SprinkleResponse {
    ReturnStatusCode statusCode;
    String token;
    String message;

    public static SprinkleResponse ofFail(ReturnStatusCode statusCode,
                                          String message) {
        return SprinkleResponse.builder()
                .statusCode(statusCode)
                .message(message)
                .build();
    }
}
