package pay.jh.me.moneysprinkling.controller;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pay.jh.me.moneysprinkling.dto.MoneySprinkleRequest;
import pay.jh.me.moneysprinkling.service.MoneySprinklingService;
import pay.jh.me.moneysprinkling.service.MoneySprinklingValidateService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class MoneySprinklingContoller {
    private final MoneySprinklingService moneySprinklingService;
    private final MoneySprinklingValidateService moneySprinklingValidateService;

    public MoneySprinklingContoller(
            MoneySprinklingService moneySprinklingService,
            MoneySprinklingValidateService moneySprinklingValidateService) {
        this.moneySprinklingService = moneySprinklingService;
        this.moneySprinklingValidateService = moneySprinklingValidateService;
    }

    @PostMapping(value = "/sprinkle")
    @ResponseBody
    public String sprinkle(
            @RequestHeader("X-ROOM-ID") String roomId,
            @RequestHeader("X-USER-ID") String userId,
            @RequestBody @Valid MoneySprinkleRequest moneySprinkle) {
        log.info("roomId={}, userId={}, body={}", roomId, userId, moneySprinkle);
        return moneySprinklingService.sprinkle(roomId, userId, moneySprinkle);
    }

    @PostMapping(value = "/pick-up")
    @ResponseBody
    public double pickup(@RequestHeader("X-USER-ID") String userId,
                         String token) {
        log.info("userId={}, token={}", userId, token);
        moneySprinklingValidateService.isValidate(userId, token);
        return moneySprinklingService.pickup(token, userId);
    }
}
