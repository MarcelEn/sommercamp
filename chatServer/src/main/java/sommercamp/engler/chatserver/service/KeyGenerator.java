package sommercamp.engler.chatserver.service;

import org.apache.commons.lang3.RandomStringUtils;

public class KeyGenerator {
    private static char[] allowedCharacters = { //
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', //
            'a', 'b', 'c', 'd', 'r', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', //
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    };

    public static String generateKey() {
        return RandomStringUtils.random(60, allowedCharacters);
    }
}