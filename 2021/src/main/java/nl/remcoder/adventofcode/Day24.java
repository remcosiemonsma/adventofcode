package nl.remcoder.adventofcode;

import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

public class Day24 {

    public static final int DIVISORS_POSITION = 0;
    public static final int FIRST_NUMBER_POSITION = 1;
    public static final int SECOND_NUMBER_POSITION = 2;

    public long handlePart1(Stream<String> input) {
        List<String> lines = input.toList();

        int[][] data = new int[3][14];

        for (int i = 0; i < 14; i++) {
            int position = i * 18;
            data[DIVISORS_POSITION][i] = Integer.parseInt(lines.get(position + 4).substring(6));
            data[FIRST_NUMBER_POSITION][i] = Integer.parseInt(lines.get(position + 5).substring(6));
            data[SECOND_NUMBER_POSITION][i] = Integer.parseInt(lines.get(position + 15).substring(6));
        }

        return solveMax(data);
    }

    public long handlePart2(Stream<String> input) {
        List<String> lines = input.toList();

        int[][] data = new int[3][14];

        for (int i = 0; i < 14; i++) {
            int position = i * 18;
            data[DIVISORS_POSITION][i] = Integer.parseInt(lines.get(position + 4).substring(6));
            data[FIRST_NUMBER_POSITION][i] = Integer.parseInt(lines.get(position + 5).substring(6));
            data[SECOND_NUMBER_POSITION][i] = Integer.parseInt(lines.get(position + 15).substring(6));
        }

        return solveMin(data);
    }

    private long solveMax(int[][] data) {
        char[] chars = new char[14];
        Stack<Integer> stack = new Stack<>();
        for (int position = 0; position < 14; position++) {
            if (data[DIVISORS_POSITION][position] == 1) {
                stack.push(position);
            } else {
                findMaxPair(position, stack.pop(), data, chars);
            }
        }
        return Long.parseLong(new String(chars));
    }

    private long solveMin(int[][] data) {
        char[] chars = new char[14];
        Stack<Integer> stack = new Stack<>();
        for (int position = 0; position < 14; position++) {
            if (data[DIVISORS_POSITION][position] == 1) {
                stack.push(position);
            } else {
                findMinPair(position, stack.pop(), data, chars);
            }
        }
        return Long.parseLong(new String(chars));
    }

    private void findMaxPair(int firstIndex, int secondIndex, int[][] data, char[] chars) {
        for (int digit = 9; digit > 0; digit--) {
            int other = digit + data[FIRST_NUMBER_POSITION][firstIndex] + data[SECOND_NUMBER_POSITION][secondIndex];
            if (isNumberValidDigit(other)) {
                chars[secondIndex] = (char) ('0' + digit);
                chars[firstIndex] = (char) ('0' + other);
                return;
            }
        }
    }

    private void findMinPair(int firstIndex, int secondIndex, int[][] data, char[] chars) {
        for (int digit = 1; digit < 10; digit++) {
            int other = digit + data[FIRST_NUMBER_POSITION][firstIndex] + data[SECOND_NUMBER_POSITION][secondIndex];
            if (isNumberValidDigit(other)) {
                chars[secondIndex] = (char) ('0' + digit);
                chars[firstIndex] = (char) ('0' + other);
                return;
            }
        }
    }

    private boolean isNumberValidDigit(int digit) {
        return 10 > digit && digit > 0;
    }
}
