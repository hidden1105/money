package pay.jh.me.moneysprinkling.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MoneySprinklingValidateService {
    public void isValidate(String token, String userId) {
        // 뿌리기 당 한 사용자는 한번만 할당

        // 자신이 뿌린건 받을 수 없음

        // 뿌리기 호출방과 토큰할당 된 방이 일치하지 않음.

        // 토큰이 10분 지남...
    }
}
