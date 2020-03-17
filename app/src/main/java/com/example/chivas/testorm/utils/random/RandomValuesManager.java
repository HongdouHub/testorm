package com.example.chivas.testorm.utils.random;

import java.util.Random;

public class RandomValuesManager {
    // Fixed seed so we generate the same set of strings every time.
    private static final long SEED = -2662502316022774L;
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 500;

    private Random mRandom;

    // limit to a fixed set of chars
    private static final char[] CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private RandomValuesManager() {
        mRandom = new Random(SEED);
    }

    public static RandomValuesManager getInstance() {
        return RandomValuesManagerHolder.instance;
    }

    private static class RandomValuesManagerHolder {
        private static RandomValuesManager instance = new RandomValuesManager();
    }

    /**
     * Creates the same random sequence of strings.
     */
    public String[] createFixedRandomStrings(int count) {
        return createFixedRandomStrings(count, MIN_LENGTH, MAX_LENGTH);
    }

    public String[] createFixedRandomStrings(int count, int minLength, int maxLength) {
        String[] strings = new String[count];
        for (int i = 0; i < count; i++) {
            strings[i] = createRandomString(minLength, maxLength);
        }
        return strings;
    }

    public String createRandomString(int minLength, int maxLength) {
        int length = minLength + mRandom.nextInt(maxLength - minLength);
        return createRandomString(length);
    }

    public String createRandomString(int length) {
        char[] chars = new char[length + 4];
        for (int i = 0; i < length; ) {
            int intVal = mRandom.nextInt();
            chars[i++] = CHARS[((intVal & 0xff) % CHARS.length)];
            chars[i++] = CHARS[(((intVal >> 8) & 0xff) % CHARS.length)];
            chars[i++] = CHARS[(((intVal >> 16) & 0xff) % CHARS.length)];
            chars[i++] = CHARS[(((intVal >> 24) & 0xff) % CHARS.length)];
        }
        return new String(chars, 0, length);
    }

    /**
     * Creates the same random sequence of indexes. To be used to select strings by {@link
     * #createFixedRandomStrings(int)}.
     */
    public int[] createFixedRandomInts(int count, int maxInt) {
        int[] ints = new int[count];
        for (int i = 0; i < count; i++) {
            ints[i] = mRandom.nextInt(maxInt + 1);
        }
        return ints;
    }

    public boolean createRandomBoolean() {
        return mRandom.nextBoolean();
    }

    public byte createRandomByte() {
        return (byte) mRandom.nextInt();
    }

    public short createRandomShort() {
        return (short) mRandom.nextInt();
    }

    public int createRandomInt() {
        return mRandom.nextInt();
    }

    public int createRandomInt(int maxValue) {
        return mRandom.nextInt(maxValue);
    }

    public long createRandomLong() {
        return mRandom.nextLong();
    }

    public double createRandomDouble() {
        return mRandom.nextDouble();
    }

    public float createRandomFloat() {
        return mRandom.nextFloat();
    }

    public String createRandomString() {
        return createRandomString(0, 100);
    }

    public byte[] createRandomByteArrays() {
        int length = mRandom.nextInt(100);
        byte[] bytes = new byte[length];
        mRandom.nextBytes(bytes);
        return bytes;
    }
}