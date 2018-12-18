package pl.manufacturer.object.util;

import java.util.Random;

public class BasicTypeValueGeneratorUtil {

    private static Random random = new Random();

    private BasicTypeValueGeneratorUtil() {
        // this should be empty
    }

    public static String generateString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public static Boolean generateBoolean() {
        return random.nextBoolean();
    }

    public static Integer generateInteger() {
        return random.nextInt();
    }

    public static Long generateLong() {
        return random.nextLong();
    }

    public static Double generateDouble() {
        return random.nextDouble();
    }

    public static Float generateFloat() {
        return random.nextFloat();
    }

    public static Character generateCharacter() {
        return (char) (random.nextInt('z' - 'a') + 'a');
    }

    public static Byte generateByte() {
        byte[] oneByte = new byte[1];
        random.nextBytes(oneByte);
        return oneByte[0];
    }

    public static Short generateShort() {
        return (short) random.nextInt(Short.MAX_VALUE + 1);
    }
}
