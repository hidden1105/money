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

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;

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

    @Override
    @Transactional(isolation = SERIALIZABLE)
    public double pickup(String token, String userId) {
        MoneySprinkleDtl moneySprinkleDtl = null; // 테이블 MONEY_SPRINKLE_DTL에서 reviver 가 null 인 데이터 가져오기
        //moneySprinkleDtlRepository.update(moneySprinkleDtl.getMoneySprinkleDtlSeq(), userId); receiver, receiver at 업데이트
        return moneySprinkleDtl.getAmount();
    }

    private boolean isLast(MoneySprinkle moneySprinkle, int index) {
        return index == moneySprinkle.getCount() -1;
    }

    private double getRandomAmount(double balance, Random random) {
        return balance * (getNumbersFromOneToNine(random) * 0.1);
    }

    private int getNumbersFromOneToNine(Random random) {
        return random.nextInt(9) +1;
    }
}
