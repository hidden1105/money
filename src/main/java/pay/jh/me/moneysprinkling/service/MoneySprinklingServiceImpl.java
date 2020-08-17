package pay.jh.me.moneysprinkling.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pay.jh.me.moneysprinkling.dao.MoneySprinkleDtlRepository;
import pay.jh.me.moneysprinkling.dao.MoneySprinkleRepository;
import pay.jh.me.moneysprinkling.dto.MoneySprinkleRequest;
import pay.jh.me.moneysprinkling.endity.MoneySprinkle;
import pay.jh.me.moneysprinkling.endity.MoneySprinkleDtl;
import pay.jh.me.moneysprinkling.util.TokenGenerator;

import java.util.Random;

@Slf4j
@Service
public class MoneySprinklingServiceImpl implements MoneySprinklingService {
    private final TokenGenerator tokenGenerator;
    private final MoneySprinkleRepository moneySprinkleRepository;
    private final MoneySprinkleDtlRepository moneySprinkleDtlRepository;

    public MoneySprinklingServiceImpl(TokenGenerator tokenGenerator, MoneySprinkleRepository moneySprinkleRepository, MoneySprinkleDtlRepository moneySprinkleDtlRepository) {
        this.tokenGenerator = tokenGenerator;
        this.moneySprinkleRepository = moneySprinkleRepository;
        this.moneySprinkleDtlRepository = moneySprinkleDtlRepository;
    }

    @Transactional
    @Override
    public String sprinkle(MoneySprinkleRequest moneySprinkleRequest) {
        ModelMapper modelMapper = new ModelMapper(); // TODO autowire로 개선
        MoneySprinkle moneySprinkle = modelMapper.map(moneySprinkleRequest, MoneySprinkle.class);
        moneySprinkle.setToken(tokenGenerator.generate());
        moneySprinkleRepository.save(moneySprinkle);

        double balance = moneySprinkle.getAmount();
        Random random = new Random();
        for (int index = 0; index < moneySprinkle.getCount(); index++) {
           MoneySprinkleDtl moneySprinkleDtl = new MoneySprinkleDtl(moneySprinkle.getToken());
           moneySprinkleDtl.setAmount(isLast(moneySprinkle.getCount(), index) ? balance : getRandomAmount(balance, random));
           moneySprinkleDtlRepository.save(moneySprinkleDtl);
           balance -= moneySprinkleDtl.getAmount();
        }
        return moneySprinkle.getToken();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public double pickup(String token, String userId) {
        MoneySprinkleDtl moneySprinkleDtl = moneySprinkleDtlRepository.findTop1ByToken(token);
        moneySprinkleDtl.setReceiver(userId);
        moneySprinkleDtlRepository.save(moneySprinkleDtl);
        return moneySprinkleDtl.getAmount();
    }

    private boolean isLast(Integer sprinkleCount, int index) {
        return index == sprinkleCount - 1;
    }

    private double getRandomAmount(double balance, Random random) {
        return balance * (getNumbersFromOneToNine(random) * 0.1);
    }

    private int getNumbersFromOneToNine(Random random) {
        return random.nextInt(9) + 1;
    }
}
