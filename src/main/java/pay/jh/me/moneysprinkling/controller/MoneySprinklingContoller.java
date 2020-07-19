package pay.jh.me.moneysprinkling.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pay.jh.me.moneysprinkling.model.MoneySprinkle;
import pay.jh.me.moneysprinkling.service.MoneySprinklingService;
import pay.jh.me.moneysprinkling.service.MoneySprinklingValidateService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class MoneySprinklingContoller {
    private final MoneySprinklingService moneySprinklingService;
    private final MoneySprinklingValidateService moneySprinklingValidateService;

    public MoneySprinklingContoller(MoneySprinklingService moneySprinklingService, MoneySprinklingValidateService moneySprinklingValidateService) {
        this.moneySprinklingService = moneySprinklingService;
        this.moneySprinklingValidateService = moneySprinklingValidateService;
    }

    @PostMapping(value = "/sprinkle")
    @ResponseBody
    public String sprinkle(@RequestHeader("X-ROOM-ID") String roomId, @RequestHeader("X-USER-ID") String userId, MoneySprinkle moneySprinkle) {
        log.info("roomId={}, userId={}, body={}", roomId, userId, moneySprinkle);
        moneySprinkle.setRoomId(roomId);
        moneySprinkle.setUserId(userId);
        moneySprinkle.setAmount(300); // param으로 왜 안들어 오는겨???
        moneySprinkle.setCount(3); // param으로 왜 안들어 오는겨???
        return moneySprinklingService.sprinkle(roomId, userId, moneySprinkle);
    }

    @PostMapping(value = "/pick-up")
    @ResponseBody
    public double pickup(@RequestHeader("X-USER-ID") String userId, String token) {
        log.info("userId={}, token={}", userId, token);
        moneySprinklingValidateService.isValidate(userId, token);
        return moneySprinklingService.pickup(token, userId);
    }
}
