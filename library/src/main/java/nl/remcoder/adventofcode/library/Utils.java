package nl.remcoder.adventofcode.library;

public class Utils {
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public static String byteArrayToHex(byte[] hash) {
        char[] hexChars = new char[hash.length * 2];
        for (int i = 0; i < hash.length; i++) {
            int v = hash[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
