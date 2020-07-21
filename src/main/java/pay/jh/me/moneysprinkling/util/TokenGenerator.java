package pay.jh.me.moneysprinkling.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

@UtilityClass
public class TokenGenerator {
    public int TOKEN_LENGTH = 3;

    public String generate() {
        return RandomStringUtils.randomAlphabetic(TOKEN_LENGTH);
    }
}
