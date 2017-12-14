package nl.remcoder.adventofcode.day14;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Part2 {
    public static void main(String[] args) throws Exception {
        String input = "jxqlasbh";

        int[][] grid = new int[128][128];

        for (int i = 0; i < 128; i++) {
            String knotHash = knotHash((input + "-" + i).toCharArray());
            char[] knotHashCharArray = knotHash.toCharArray();

            for (int j = 0; j < knotHash.length(); j++) {
                char c = knotHashCharArray[j];

                StringBuilder binaryStringBuilder = new StringBuilder(Integer.toBinaryString(Integer.parseInt("" + c, 16)).replaceAll("1", "#").replaceAll("0", " "));

                while (binaryStringBuilder.length() < 4) {
                    binaryStringBuilder.insert(0, " ");
                }

                char[] chars = binaryStringBuilder.toString().toCharArray();

                for (int k = 0; k < binaryStringBuilder.length(); k++) {
                    grid[i][(j * 4) + k] = chars[k];
                }
            }
        }

        int groupCount = 0;

        for (int i = 0; i < grid.length; i++) {
            int[] chars = grid[i];
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] == '#') {
                    groupCount++;
                    flagGroup(grid, i, j, groupCount);
                }
            }
        }

        for (int[] ints : grid) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println(groupCount);
    }

    private static void flagGroup(int[][] grid, int i, int j, int groupCount) {
        if (i >= 0 && i < 128 && j >= 0 && j < 128 && grid[i][j] == '#') {
            grid[i][j] = groupCount;
            flagGroup(grid, i + 1, j, groupCount);
            flagGroup(grid, i - 1, j, groupCount);
            flagGroup(grid, i, j + 1, groupCount);
            flagGroup(grid, i, j - 1, groupCount);
        }
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
