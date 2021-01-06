package nl.remcoder.adventofcode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day4 {
    public int handlePart1(Stream<String> input) {
        String line = input.findFirst().get();

        return IntStream.iterate(0, value -> value + 1)
                        .parallel()
                        .filter(value -> {
                            MessageDigest md5;
                            try {
                                md5 = MessageDigest.getInstance("MD5");
                            } catch (NoSuchAlgorithmException e) {
                                throw new RuntimeException(e);
                            }

                            String newline = line + value;

                            byte[] data = newline.getBytes();

                            byte[] md5data = md5.digest(data);

                            String md5line = byteArrayToHex(md5data);

                            return md5line.startsWith("00000");
                        })
                        .findFirst()
                        .getAsInt();
    }

    public int handlePart2(Stream<String> input) {
        String line = input.findFirst().get();

        return IntStream.iterate(0, value -> value + 1)
                        .parallel()
                        .filter(value -> {
                            MessageDigest md5;
                            try {
                                md5 = MessageDigest.getInstance("MD5");
                            } catch (NoSuchAlgorithmException e) {
                                throw new RuntimeException(e);
                            }

                            String newline = line + value;

                            byte[] data = newline.getBytes();

                            byte[] md5data = md5.digest(data);

                            String md5line = byteArrayToHex(md5data);

                            return md5line.startsWith("000000");
                        })
                        .findFirst()
                        .getAsInt();
    }

    private String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
