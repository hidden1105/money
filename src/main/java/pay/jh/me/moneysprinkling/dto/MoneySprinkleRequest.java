package pay.jh.me.moneysprinkling.dto;

import com.sun.istack.NotNull;
import lombok.*;

/**
 * @author Snow
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoneySprinkleRequest {
    @NotNull
    private Integer amount;
    @NotNull
    private Integer count;
}
