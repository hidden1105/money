package pay.jh.me.moneysprinkling.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pay.jh.me.moneysprinkling.dao.MoneySprinkleDtlRepository;
import pay.jh.me.moneysprinkling.dao.MoneySprinkleRepository;
import pay.jh.me.moneysprinkling.dto.MoneySprinkleRequest;
import pay.jh.me.moneysprinkling.endity.MoneySprinkleDtl;
import pay.jh.me.moneysprinkling.util.TokenGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MoneySprinklingServiceTest {

    @Mock
    MoneySprinkleRepository moneySprinkleRepository;
    @Mock
    MoneySprinkleDtlRepository moneySprinkleDtlRepository;
    @Mock
    TokenGenerator tokenGenerator;
    @InjectMocks
    MoneySprinklingServiceImpl moneySprinklingServiceImpl;

    @DisplayName("토큰 발급 테스트")
    @Test
    void sprinkle_success() {
        //given
        Integer amount = 100;
        Integer count = 3;
        MoneySprinkleRequest moneySprinkleRequest = MoneySprinkleRequest.builder()
                .amount(amount)
                .count(count)
                .userId("userid")
                .roomId("roomid")
                .build();
        given(tokenGenerator.generate()).willReturn("asd");

        //when
        String token = moneySprinklingServiceImpl.sprinkle(moneySprinkleRequest);

        //then
        assertEquals(token, "asd");
    }

    @DisplayName("돈 받기 테스트")
    @Test
    void sprinkle_pickup() {
        // given
        MoneySprinkleDtl moneySprinkleDtl = MoneySprinkleDtl.builder()
                                                .receiver("sjh")
                                                .amount(1000)
                                                .build();

        given(moneySprinkleDtlRepository.findTop1ByToken(any())).willReturn(moneySprinkleDtl);

        // when
        moneySprinklingServiceImpl.pickup("token", "user");

        // then
        assertEquals(1000, moneySprinkleDtl.getAmount());
    }
}
