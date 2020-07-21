package pay.jh.me.moneysprinkling.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pay.jh.me.moneysprinkling.dto.ReturnStatusCode;
import pay.jh.me.moneysprinkling.dto.SprinkleResponse;

/**
 * @author Snow
 */
@ControllerAdvice(basePackages = "pay.jh.me.moneysprinkling.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler
    public SprinkleResponse duplicateError(DuplicateTokenException e) {
        return SprinkleResponse.ofFail(ReturnStatusCode.FAIL_DUPLICATE_TOKEN,
                "중복 토큰 방지 재시도 횟수를 초과 했습니다.");
    }
}
