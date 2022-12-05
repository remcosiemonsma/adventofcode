package nl.remcoder.adventofcode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day4 {
    public int handlePart1(Stream<String> input) {
        var line = input.findFirst()
                           .orElseThrow(() -> new AssertionError("Eek!"));;

        return IntStream.iterate(0, value -> value + 1)
                        .parallel()
                        .filter(value -> isValid(line, value, "00000"))
                        .findFirst()
                        .orElseThrow(() -> new AssertionError("Ook!"));
    }

    public int handlePart2(Stream<String> input) {
        var line = input.findFirst()
                           .orElseThrow(() -> new AssertionError("Eek!"));

        return IntStream.iterate(0, value -> value + 1)
                        .parallel()
                        .filter(value -> isValid(line, value, "000000"))
                        .findFirst()
                        .orElseThrow(() -> new AssertionError("Ook!"));
    }

    private boolean isValid(String line, int value, String prefix) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        var newline = line + value;

        var data = newline.getBytes();

        var md5data = md5.digest(data);

        var md5line = byteArrayToHex(md5data);

        return md5line.startsWith(prefix);
    }

    private String byteArrayToHex(byte[] a) {
        var sb = new StringBuilder(a.length * 2);
        for (var b : a) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
