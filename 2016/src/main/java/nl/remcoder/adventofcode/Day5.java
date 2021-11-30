package nl.remcoder.adventofcode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

public class Day5 {
    private static final MessageDigest MD5;

    static {
        try {
            MD5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String handlePart1(Stream<String> input) {
        String doorId = input.findFirst().get();

        StringBuilder passwordBuilder = new StringBuilder();

        int counter = 0;

        while (passwordBuilder.length() < 8) {
            String indexedId = doorId + counter;

            String hash = byteArrayToHex(MD5.digest(indexedId.getBytes()));

            if (hash.startsWith("00000")) {
                passwordBuilder.append(hash.charAt(5));
            }

            counter++;
        }

        return passwordBuilder.toString();
    }

    public String handlePart2(Stream<String> input) {
        String doorId = input.findFirst().get();

        char[] password = new char[8];

        int counter = 0;

        while (password[0] == 0 || password[1] == 0 || password[2] == 0 || password[3] == 0 ||
               password[4] == 0 || password[5] == 0 || password[6] == 0 || password[7] == 0) {
            String indexedId = doorId + counter;

            String hash = byteArrayToHex(MD5.digest(indexedId.getBytes()));

            if (hash.startsWith("00000")) {
                int position = Character.digit(hash.charAt(5), 10);

                if (position >= 0 && position < 8 && password[position] == 0) {
                    password[position] = hash.charAt(6);
                }
            }

            counter++;
        }

        return new String(password);
    }

    private String byteArrayToHex(byte[] hash) {
        StringBuilder sb = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
