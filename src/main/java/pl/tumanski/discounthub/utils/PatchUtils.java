package pl.tumanski.discounthub.utils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class PatchUtils {

    public static boolean shouldUpdateValue(String value) {
        return value != null && !value.isBlank();
    }

    public static boolean shouldUpdateValue(Long value) {
        return value != null;
    }

    public static boolean shouldUpdateValue(BigDecimal value) {
        return value != null;
    }

    public static boolean shouldUpdateValue(OffsetDateTime value) {
        return value != null;
    }
}
