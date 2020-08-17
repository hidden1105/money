package pay.jh.me.moneysprinkling.service;

import pay.jh.me.moneysprinkling.dto.MoneySprinkleDto;

public interface MoneySprinklingService {
    String sprinkle(MoneySprinkleDto moneySprinkleDto);

    double pickup(String token, String userId);
}
