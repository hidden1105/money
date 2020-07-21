package pay.jh.me.moneysprinkling.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pay.jh.me.moneysprinkling.dao.MoneySprinkleDtlRepository;
import pay.jh.me.moneysprinkling.dao.MoneySprinkleRepository;
import pay.jh.me.moneysprinkling.dto.MoneySprinkleRequestMock;
import pay.jh.me.moneysprinkling.entity.MoneySprinkle;
import pay.jh.me.moneysprinkling.entity.MoneySprinkleDtl;
import pay.jh.me.moneysprinkling.exception.DuplicateTokenException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Snow
 */
@ExtendWith(MockitoExtension.class)
class MoneySprinklingServiceTest {

    @Mock
    MoneySprinkleRepository moneySprinkleRepository;
    @Mock
    MoneySprinkleDtlRepository moneySprinkleDtlRepository;
    @Mock
    MoneySprinkle moneySprinkle;
    @InjectMocks
    MoneySprinklingServiceImpl moneySprinklingServiceImpl;

    // retry는 SpringJUnit4ClassRunner로 별도 테스트 필요
    @Test
    void sprinkle_duplicate() {
        //given
        given(moneySprinkleRepository.findByToken(anyString()))
                .willReturn(Optional.of(moneySprinkle));
        //when


        //then
        assertThrows(DuplicateTokenException.class,
                () -> moneySprinklingServiceImpl.sprinkle(
                        "1", "100",
                        MoneySprinkleRequestMock.create(100, 3)
                ));
    }

    @Test
    void sprinkle_success() {
        //given
        given(moneySprinkleRepository.findByToken(anyString()))
                .willReturn(Optional.empty());
        Integer amount = 100;
        Integer count = 3;

        //when
        String token = moneySprinklingServiceImpl
                .sprinkle("1", "100",
                        MoneySprinkleRequestMock.create(amount, count));

        //then
        assertNotNull(token);
        verify(moneySprinkleRepository, times(1))
                .save(any(MoneySprinkle.class));
        verify(moneySprinkleDtlRepository, times(count))
                .save(any(MoneySprinkleDtl.class));
    }
}
