package pay.jh.me.moneysprinkling.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pay.jh.me.moneysprinkling.model.MoneySprinkle;
import pay.jh.me.moneysprinkling.service.MoneySprinklingService;

@Slf4j
@RestController
@RequestMapping
public class MoneySprinklingContoller {
    private final MoneySprinklingService moneySprinklingService;

    public MoneySprinklingContoller(MoneySprinklingService moneySprinklingService) {
        this.moneySprinklingService = moneySprinklingService;
    }

    @PostMapping(value = "/sprinkle")
    @ResponseBody
    public String sprinkle(@RequestHeader("X-ROOM-ID") String roomId, @RequestHeader("X-USER-ID") String userId, MoneySprinkle moneySprinkle) {
        log.info("roomId={}, userId={}, body={}", roomId, userId, moneySprinkle);
        moneySprinkle.setRoomId(roomId);
        moneySprinkle.setUserId(userId);
        moneySprinkle.setAmount(300);   // param으로 왜 안들어 오는겨???
        moneySprinkle.setCount(3);  // param으로 왜 안들어 오는겨???
        return moneySprinklingService.sprinkle(roomId, userId, moneySprinkle);
    }
}
