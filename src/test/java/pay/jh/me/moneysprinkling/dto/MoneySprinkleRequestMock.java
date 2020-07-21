package pay.jh.me.moneysprinkling.dto;

/**
 * @author Snow
 */
public class MoneySprinkleRequestMock {
    public static MoneySprinkleRequest create(Integer amount,
                                              Integer count) {
        return MoneySprinkleRequest.builder()
                .amount(amount)
                .count(count)
                .build();
    }
}
