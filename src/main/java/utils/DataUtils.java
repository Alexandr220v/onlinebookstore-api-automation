package utils;

import java.util.concurrent.ThreadLocalRandom;

public class DataUtils {

    public static int uniqueId() {
        return (int) (System.currentTimeMillis() % 1_000_000)
                + ThreadLocalRandom.current().nextInt(1000);
    }

    public static int negativeId() {
        return ThreadLocalRandom.current().nextInt(-9999, -1);
    }

    public static int maxIntId() {
        return Integer.MAX_VALUE;
    }
}
