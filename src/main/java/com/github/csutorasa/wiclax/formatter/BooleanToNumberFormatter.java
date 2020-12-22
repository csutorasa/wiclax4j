package com.github.csutorasa.wiclax.formatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Wiclax uses 0 and 1 values instead of false and true. Here you can convert between them.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BooleanToNumberFormatter {

    /**
     * Converts boolean to text.
     *
     * @param value boolean
     * @return text
     */
    public static String format(Boolean value) {
        if (Boolean.TRUE.equals(value)) {
            return "1";
        }
        if (Boolean.FALSE.equals(value)) {
            return "0";
        }
        return null;
    }

    /**
     * Converts text to boolean.
     *
     * @param text text
     * @return boolean
     */
    public static Boolean parse(String text) {
        if ("1".equals(text)) {
            return true;
        }
        if ("0".equals(text)) {
            return false;
        }
        return null;
    }
}
