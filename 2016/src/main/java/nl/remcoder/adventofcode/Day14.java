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
        var salt = input.findFirst()
                .orElseThrow(() -> new AssertionError("Eek!"));

        var hashes = new ArrayDeque<String>(1001);

        for (var i = 0; i < 1001; i++) {
            var hash = createHash(salt, i);
            hashes.add(hash);
        }

        int counter = 1001;

        int foundPasswords = 0;

        while (foundPasswords < 64) {
            var hash = hashes.remove();

            var sequenceBuilder = new StringBuilder();

            for (var i = 0; i < hash.length() - 2; i++) {
                if (hash.charAt(i) == hash.charAt(i + 1) && hash.charAt(i) == hash.charAt(i + 2)) {
                    var tripletchar = hash.charAt(i);

                    sequenceBuilder.append(tripletchar).append(tripletchar)
                                   .append(tripletchar).append(tripletchar).append(tripletchar);

                    break;
                }
            }

            var sequence = sequenceBuilder.toString();

            if (sequence.length() != 0 && hashes.stream()
                                                .anyMatch(s -> s.contains(sequence))) {
                foundPasswords++;
            }

            hashes.add(createHash(salt, counter++));
        }

        return counter - 1002;
    }

    public int handlePart2(Stream<String> input) {
        var salt = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        Deque<String> hashes = new ArrayDeque<>(1001);

        for (var i = 0; i < 1001; i++) {
            var hash = createStretchedHash(salt, i);
            hashes.add(hash);
        }

        var counter = 1001;

        var foundPasswords = 0;

        while (foundPasswords < 64) {
            var hash = hashes.remove();

            var sequenceBuilder = new StringBuilder();

            for (var i = 0; i < hash.length() - 2; i++) {
                if (hash.charAt(i) == hash.charAt(i + 1) && hash.charAt(i) == hash.charAt(i + 2)) {
                    var tripletchar = hash.charAt(i);

                    sequenceBuilder.append(tripletchar).append(tripletchar)
                                   .append(tripletchar).append(tripletchar).append(tripletchar);

                    break;
                }
            }

            var sequence = sequenceBuilder.toString();

            if (sequence.length() != 0 && hashes.stream()
                                                .anyMatch(s -> s.contains(sequence))) {
                foundPasswords++;
            }

            hashes.add(createStretchedHash(salt, counter++));
        }

        return counter - 1002;
    }

    private String createHash(String salt, int counter) {
        var newstring = salt + counter;
        var digest = MD5.digest(newstring.getBytes());

        return bytesToHex(digest);
    }

    private String createStretchedHash(String salt, int counter) {
        var newstring = salt + counter;
        var digest = MD5.digest(newstring.getBytes());

        for (var i = 0; i < 2016; i++) {
            var s = bytesToHex(digest);
            digest = MD5.digest(s.getBytes());
        }

        return bytesToHex(digest);
    }

    private String bytesToHex(byte[] bytes) {
        var hexChars = new char[bytes.length * 2];
        for (var j = 0; j < bytes.length; j++) {
            var v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
