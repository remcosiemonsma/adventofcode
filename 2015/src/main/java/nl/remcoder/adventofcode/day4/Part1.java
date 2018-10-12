package nl.remcoder.adventofcode.day4;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class Part1 {
    public static void main(String[] args) throws Exception {
        String line = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day4/input").toURI())).get(0);

        boolean found = false;

        int counter = 0;

        while(!found) {
            counter++;

            String newline = line + counter;

            System.out.println(newline);

            byte[] data = newline.getBytes();

            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte[] md5data = md5.digest(data);

            String md5line = byteArrayToHex(md5data);

            if (md5line.startsWith("00000")) {
                found = true;
            }
        }

        System.out.println(counter);
    }

    private static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
