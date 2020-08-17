package pay.jh.me.moneysprinkling.dto;

import lombok.*;
import com.sun.istack.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoneySprinkleDto {
    private String roomId;
    private String userId;
    @NotNull
    private Integer amount;
    @NotNull
    private Integer count;
}