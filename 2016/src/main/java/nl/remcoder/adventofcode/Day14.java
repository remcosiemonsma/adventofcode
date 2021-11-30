package nl.remcoder.adventofcode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Stream;

public class Day14 {
    private static final MessageDigest MD5;
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    static {
        try {
            MD5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public int handlePart1(Stream<String> input) {
        String salt = input.findFirst().get();

        Deque<String> hashes = new ArrayDeque<>(1001);

        for (int i = 0; i < 1001; i++) {
            String hash = createHash(salt, i);
            hashes.add(hash);
        }

        int counter = 1001;

        int foundPasswords = 0;

        while (foundPasswords < 64) {
            String hash = hashes.remove();

            StringBuilder sequenceBuilder = new StringBuilder();

            for (int i = 0; i < hash.length() - 2; i++) {
                if (hash.charAt(i) == hash.charAt(i + 1) && hash.charAt(i) == hash.charAt(i + 2)) {
                    char tripletchar = hash.charAt(i);

                    sequenceBuilder.append(tripletchar).append(tripletchar)
                                   .append(tripletchar).append(tripletchar).append(tripletchar);

                    break;
                }
            }

            String sequence = sequenceBuilder.toString();

            if (sequence.length() != 0 && hashes.stream()
                                                .anyMatch(s -> s.contains(sequence))) {
                foundPasswords++;
            }

            hashes.add(createHash(salt, counter++));
        }

        return counter - 1002;
    }

    public int handlePart2(Stream<String> input) {
        String salt = input.findFirst().get();

        Deque<String> hashes = new ArrayDeque<>(1001);

        for (int i = 0; i < 1001; i++) {
            String hash = createStretchedHash(salt, i);
            hashes.add(hash);
        }

        int counter = 1001;

        int foundPasswords = 0;

        while (foundPasswords < 64) {
            String hash = hashes.remove();

            StringBuilder sequenceBuilder = new StringBuilder();

            for (int i = 0; i < hash.length() - 2; i++) {
                if (hash.charAt(i) == hash.charAt(i + 1) && hash.charAt(i) == hash.charAt(i + 2)) {
                    char tripletchar = hash.charAt(i);

                    sequenceBuilder.append(tripletchar).append(tripletchar)
                                   .append(tripletchar).append(tripletchar).append(tripletchar);

                    break;
                }
            }

            String sequence = sequenceBuilder.toString();

            if (sequence.length() != 0 && hashes.stream()
                                                .anyMatch(s -> s.contains(sequence))) {
                foundPasswords++;
            }

            hashes.add(createStretchedHash(salt, counter++));
        }

        return counter - 1002;
    }

    private String createHash(String salt, int counter) {
        String newstring = salt + counter;
        byte[] digest = MD5.digest(newstring.getBytes());

        return bytesToHex(digest);
    }

    private String createStretchedHash(String salt, int counter) {
        String newstring = salt + counter;
        byte[] digest = MD5.digest(newstring.getBytes());

        for (int i = 0; i < 2016; i++) {
            String s = bytesToHex(digest);
            digest = MD5.digest(s.getBytes());
        }

        return bytesToHex(digest);
    }

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
