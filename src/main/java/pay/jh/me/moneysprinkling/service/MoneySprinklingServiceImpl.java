package pay.jh.me.moneysprinkling.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pay.jh.me.moneysprinkling.dao.MoneySprinkleDtlRepository;
import pay.jh.me.moneysprinkling.dao.MoneySprinkleRepository;
import pay.jh.me.moneysprinkling.dto.MoneySprinkleRequest;
import pay.jh.me.moneysprinkling.entity.MoneySprinkle;
import pay.jh.me.moneysprinkling.entity.MoneySprinkleDtl;
import pay.jh.me.moneysprinkling.exception.DuplicateTokenException;
import pay.jh.me.moneysprinkling.util.TokenGenerator;

import java.util.Random;

@Slf4j
@Service
public class MoneySprinklingServiceImpl implements MoneySprinklingService {
    private static final int MAX_RETRY_COUNT = 5;
    private final MoneySprinkleRepository moneySprinkleRepository;
    private final MoneySprinkleDtlRepository moneySprinkleDtlRepository;

    public MoneySprinklingServiceImpl(
            MoneySprinkleRepository moneySprinkleRepository
            , MoneySprinkleDtlRepository moneySprinkleDtlRepository) {
        this.moneySprinkleRepository = moneySprinkleRepository;
        this.moneySprinkleDtlRepository = moneySprinkleDtlRepository;
    }

    @Transactional
    @Override
    @Retryable(
            value = {DuplicateTokenException.class},
            maxAttempts = MAX_RETRY_COUNT,
            backoff = @Backoff(delay = 0))
    public String sprinkle(String roomId, String userId,
                           MoneySprinkleRequest sprinkleRequest) throws DuplicateTokenException {
        String newToken = TokenGenerator.generate();
        moneySprinkleRepository.findByToken(newToken)
                .ifPresent(s -> {
                    throw new DuplicateTokenException();
                });

        moneySprinkleRepository.save(MoneySprinkle.builder()
                .roomId(roomId)
                .userId(userId)
                .amount(sprinkleRequest.getAmount())
                .count(sprinkleRequest.getCount())
                .token(newToken)
                .build());

        double balance = sprinkleRequest.getAmount();
        Random random = new Random();
        for (int index = 0; index < sprinkleRequest.getCount(); index++) {
            MoneySprinkleDtl moneySprinkleDtl =
                    new MoneySprinkleDtl(newToken);
            moneySprinkleDtl.setAmount(isLast(sprinkleRequest.getCount(), index) ?
                    balance : getRandomAmount(balance, random));
            moneySprinkleDtlRepository.save(moneySprinkleDtl);
            balance -= moneySprinkleDtl.getAmount();
        }
        return newToken;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public double pickup(String token, String userId) {
        MoneySprinkleDtl moneySprinkleDtl =
                moneySprinkleDtlRepository.findTop1ByToken(token);
        moneySprinkleDtl.setReceiver(userId);
        moneySprinkleDtlRepository.save(moneySprinkleDtl);
        return moneySprinkleDtl.getAmount();
    }

    private boolean isLast(Integer sprinkleCount, Integer index) {
        return index == sprinkleCount - 1;
    }

    private double getRandomAmount(double balance, Random random) {
        return balance * (getNumbersFromOneToNine(random) * 0.1);
    }

    private int getNumbersFromOneToNine(Random random) {
        return random.nextInt(9) + 1;
    }
}
