package pay.jh.me.moneysprinkling.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {
    public static int TOKEN_LENGTH = 3;
    public String generate() {
        return RandomStringUtils.randomAlphabetic(TOKEN_LENGTH);
    }
}
