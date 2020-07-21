package pay.jh.me.moneysprinkling.service;

import pay.jh.me.moneysprinkling.dto.MoneySprinkleRequest;
import pay.jh.me.moneysprinkling.exception.DuplicateTokenException;

public interface MoneySprinklingService {
    String sprinkle(String roomId, String userId, MoneySprinkleRequest moneySprinkle)
            throws DuplicateTokenException;

    double pickup(String token, String userId);
}
