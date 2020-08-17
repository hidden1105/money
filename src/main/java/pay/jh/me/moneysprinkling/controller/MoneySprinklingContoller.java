package pay.jh.me.moneysprinkling.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
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

    public MoneySprinklingContoller(MoneySprinklingService moneySprinklingService, MoneySprinklingValidateService moneySprinklingValidateService) {
        this.moneySprinklingService = moneySprinklingService;
        this.moneySprinklingValidateService = moneySprinklingValidateService;
    }

    @PostMapping(value = "/sprinkle")
    @ResponseBody
    public ResponseEntity sprinkle(@RequestHeader("X-ROOM-ID") String roomId, @RequestHeader("X-USER-ID") String userId
            , @RequestBody @Valid MoneySprinkleRequest moneySprinkleRequest, Errors errors) {
        log.info("roomId={}, userId={}, body={}", roomId, userId, moneySprinkleRequest);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        moneySprinkleRequest.setRoomId(roomId);
        moneySprinkleRequest.setUserId(userId);
        return ResponseEntity.ok(moneySprinklingService.sprinkle(moneySprinkleRequest));
    }

    @PostMapping(value = "/pick-up")
    @ResponseBody
    public ResponseEntity pickup(@RequestHeader("X-USER-ID") String userId, @RequestBody String token, Errors errors) {
        log.info("userId={}, token={}", userId, token);
        moneySprinklingValidateService.validate(userId, token, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(moneySprinklingService.pickup(token, userId));
    }
}
