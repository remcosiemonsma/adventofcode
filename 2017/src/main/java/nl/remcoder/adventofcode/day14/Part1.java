package nl.remcoder.adventofcode.day14;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Part1 {
    public static void main(String[] args) throws Exception {
        String input = "jxqlasbh";

        int squaresUsed = 0;

        for (int i = 0; i < 128; i++) {
            String knotHash = knotHash((input + "-" + i).toCharArray());

            for (char c : knotHash.toCharArray()) {
                String binaryString = Integer.toBinaryString(Integer.parseInt("" + c, 16));
                for (char b : binaryString.toCharArray()) {
                    if (b == '1') {
                        squaresUsed++;
                    }
                }
            }
        }

        System.out.println(squaresUsed);
    }


    private static String knotHash(char[] valuesFromFile) {
        int skipSize = 0;
        int position = 0;

        int[] hashList = IntStream.range(0, 256).toArray();

        char[] values = Arrays.copyOf(valuesFromFile, valuesFromFile.length + 5);

        values[valuesFromFile.length + 0] = 17;
        values[valuesFromFile.length + 1] = 31;
        values[valuesFromFile.length + 2] = 73;
        values[valuesFromFile.length + 3] = 47;
        values[valuesFromFile.length + 4] = 23;

        for (int counter = 0; counter < 64; counter++) {
            for (int value : values) {
                if (position + value < hashList.length) {
                    for (int i = 0; i < value / 2; i++) {
                        int temp = hashList[position + i];
                        hashList[position + i] = hashList[position + (value - i) - 1];
                        hashList[position + (value - i) - 1] = temp;
                    }
                } else {
                    for (int i = 0; i < value / 2; i++) {
                        int positioni = position + i;
                        if (positioni >= hashList.length) {
                            positioni -= hashList.length;
                        }
                        int positionj = position + (value - i) - 1;
                        if (positionj >= hashList.length) {
                            positionj -= hashList.length;
                        }
                        int temp = hashList[positioni];
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
            if(hexString.length() % 2 != 0) {
                hexString = "0" + hexString;
            }
            result.append(hexString);
        }
        return result.toString();
    }
}
