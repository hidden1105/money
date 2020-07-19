package pay.jh.me.moneysprinkling.service;

import pay.jh.me.moneysprinkling.model.MoneySprinkle;

public interface MoneySprinklingService {
    String sprinkle(String roomId, String userId, MoneySprinkle moneySprinkle);

    double pickup(String token, String userId);
}
