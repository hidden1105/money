package pay.jh.me.moneysprinkling.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import pay.jh.me.moneysprinkling.dao.MoneySprinkleDtlRepository;
import pay.jh.me.moneysprinkling.dao.MoneySprinkleRepository;
import pay.jh.me.moneysprinkling.endity.MoneySprinkle;

import java.util.Optional;

@Slf4j
@Service
public class MoneySprinklingValidateService {

    private final MoneySprinkleRepository moneySprinkleRepository;
    private final MoneySprinkleDtlRepository moneySprinkleDtlRepository;

    public MoneySprinklingValidateService(MoneySprinkleRepository moneySprinkleRepository, MoneySprinkleDtlRepository moneySprinkleDtlRepository) {
        this.moneySprinkleRepository = moneySprinkleRepository;
        this.moneySprinkleDtlRepository = moneySprinkleDtlRepository;
    }

    public void validate(String token, String userId, Errors errors) {
        MoneySprinkle moneySprinkle = moneySprinkleRepository.findByToken(token);
        if (moneySprinkle == null) {
            errors.reject("token", "존재하지 않는 토큰");
            return;
        }

        if (StringUtils.equals(userId, moneySprinkle.getUserId())) {
            errors.reject("userId", "자신이 뿌린건 받을 수 없음");
        }

        // 뿌리기 당 한 사용자는 한번만 할당

        // 뿌리기 호출방과 토큰할당 된 방이 일치하지 않음.

        // 토큰이 10분 지남...
    }
}
