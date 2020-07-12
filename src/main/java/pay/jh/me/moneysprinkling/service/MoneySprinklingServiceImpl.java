package pay.jh.me.moneysprinkling.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pay.jh.me.moneysprinkling.dao.MoneySprinkleDtlRepository;
import pay.jh.me.moneysprinkling.dao.MoneySprinkleRepository;
import pay.jh.me.moneysprinkling.model.MoneySprinkle;
import pay.jh.me.moneysprinkling.model.MoneySprinkleDtl;
import pay.jh.me.moneysprinkling.util.TokenGenerator;

import java.util.Random;

@Slf4j
@Service
public class MoneySprinklingServiceImpl implements MoneySprinklingService {
    private final TokenGenerator tokenGenerator;
    private final MoneySprinkleRepository moneySprinkleRepository;
    private final MoneySprinkleDtlRepository moneySprinkleDtlRepository;

    public MoneySprinklingServiceImpl(TokenGenerator tokenGenerator, MoneySprinkleRepository moneySprinkleRepository
            , MoneySprinkleDtlRepository moneySprinkleDtlRepository) {
        this.tokenGenerator = tokenGenerator;
        this.moneySprinkleRepository = moneySprinkleRepository;
        this.moneySprinkleDtlRepository = moneySprinkleDtlRepository;
    }

    @Transactional
    @Override
    public String sprinkle(String roomId, String userId, MoneySprinkle moneySprinkle) {
        moneySprinkle.setToken(tokenGenerator.generate());
        moneySprinkleRepository.save(moneySprinkle);

        double balance = moneySprinkle.getAmount();
        Random random = new Random();
        for (int index = 0; index < moneySprinkle.getCount(); index++) {
           MoneySprinkleDtl moneySprinkleDtl = new MoneySprinkleDtl(moneySprinkle.getToken());
           moneySprinkleDtl.setAmount(isLast(moneySprinkle, index) ? balance : getRandomAmount(balance, random));
           moneySprinkleDtlRepository.save(moneySprinkleDtl);
           balance -= moneySprinkleDtl.getAmount();
        }

        return moneySprinkle.getToken();

    }

    private double getRandomAmount(double balance, Random random) {
        return balance * (random.nextInt(9) +1) * 0.1;
    }

    private boolean isLast(MoneySprinkle moneySprinkle, int index) {
        return index == moneySprinkle.getCount() -1;
    }
}
