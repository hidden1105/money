package pay.jh.me.moneysprinkling.service;

import pay.jh.me.moneysprinkling.dto.MoneySprinkleRequest;

public interface MoneySprinklingService {
    String sprinkle(MoneySprinkleRequest moneySprinkleRequest);

    double pickup(String token, String userId);
}
