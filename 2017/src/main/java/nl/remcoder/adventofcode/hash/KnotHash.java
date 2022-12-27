package nl.remcoder.adventofcode.hash;

import java.util.Arrays;
import java.util.stream.IntStream;

public class KnotHash {
    public static String getHash(int[] values) {
        var hashList = IntStream.range(0, 256).toArray();

        values = Arrays.copyOf(values, values.length + 5);

        values[values.length - 5] = 17;
        values[values.length - 4] = 31;
        values[values.length - 3] = 73;
        values[values.length - 2] = 47;
        values[values.length - 1] = 23;

        performHash(values, hashList, 64);

        int[] results = new int[16];

        for (int i = 0; i < hashList.length; i += 16) {
            int result = hashList[i];
            for (int j = 1; j < 16; j++) {
                result ^= hashList[j + i];
            }
            results[i / 16] = result;
        }

        StringBuilder result = new StringBuilder();

        for (int i : results) {
            String hexString = Integer.toHexString(i);
            if (hexString.length() % 2 != 0) {
                hexString = "0" + hexString;
            }
            result.append(hexString);
        }
        
        return result.toString();
    }
    
    public static void performHash(int[] values, int[] hashList, int iterations) {
        var skipSize = 0;
        var position = 0;

        for (int counter = 0; counter < iterations; counter++) {
            for (var value : values) {
                if (position + value < hashList.length) {
                    for (var i = 0; i < value / 2; i++) {
                        var temp = hashList[position + i];
                        hashList[position + i] = hashList[position + (value - i) - 1];
                        hashList[position + (value - i) - 1] = temp;
                    }
                } else {
                    for (var i = 0; i < value / 2; i++) {
                        var positioni = position + i;
                        if (positioni >= hashList.length) {
                            positioni -= hashList.length;
                        }
                        var positionj = position + (value - i) - 1;
                        if (positionj >= hashList.length) {
                            positionj -= hashList.length;
                        }
                        var temp = hashList[positioni];
                        hashList[positioni] = hashList[positionj];
                        hashList[positionj] = temp;
                    }
                }

                position += value + skipSize;
                while (position >= hashList.length) {
                    position -= hashList.length;
                }
                skipSize++;
            }
        }
    }
}
