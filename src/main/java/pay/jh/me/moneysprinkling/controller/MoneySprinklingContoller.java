package pay.jh.me.moneysprinkling.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pay.jh.me.moneysprinkling.dto.MoneySprinkleDto;
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
    public String sprinkle(@RequestHeader("X-ROOM-ID") String roomId, @RequestHeader("X-USER-ID") String userId, @RequestBody MoneySprinkleDto moneySprinkleDto) {
        log.info("roomId={}, userId={}, body={}", roomId, userId, moneySprinkleDto);
        moneySprinkleDto.setRoomId(roomId);
        moneySprinkleDto.setUserId(userId);
        return moneySprinklingService.sprinkle(moneySprinkleDto);
    }

    @PostMapping(value = "/pick-up")
    @ResponseBody
    public double pickup(@RequestHeader("X-USER-ID") String userId, String token) {
        log.info("userId={}, token={}", userId, token);
        moneySprinklingValidateService.isValidate(userId, token);
        return moneySprinklingService.pickup(token, userId);
    }
}
