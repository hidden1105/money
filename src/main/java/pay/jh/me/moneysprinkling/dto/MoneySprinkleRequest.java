package pay.jh.me.moneysprinkling.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class MoneySprinkleRequest {
    private String roomId;
    private String userId;
    @NotNull
    private Integer amount;
    @NotNull
    private Integer count;

    @Builder
    public MoneySprinkleRequest(String roomId, String userId, @NotNull Integer amount, @NotNull Integer count) {
        this.roomId = roomId;
        this.userId = userId;
        this.amount = amount;
        this.count = count;
    }
}