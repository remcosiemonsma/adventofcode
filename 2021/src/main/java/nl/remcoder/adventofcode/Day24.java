package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Stack;
import java.util.stream.Stream;

public class Day24 implements AdventOfCodeSolution<Long> {

    public static final int DIVISORS_POSITION = 0;
    public static final int FIRST_NUMBER_POSITION = 1;
    public static final int SECOND_NUMBER_POSITION = 2;

    @Override
    public Long handlePart1(Stream<String> input) {
        var lines = input.toList();

        var data = new int[3][14];

        for (var i = 0; i < 14; i++) {
            var position = i * 18;
            data[DIVISORS_POSITION][i] = Integer.parseInt(lines.get(position + 4).substring(6));
            data[FIRST_NUMBER_POSITION][i] = Integer.parseInt(lines.get(position + 5).substring(6));
            data[SECOND_NUMBER_POSITION][i] = Integer.parseInt(lines.get(position + 15).substring(6));
        }

        return solveMax(data);
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var lines = input.toList();

        var data = new int[3][14];

        for (int i = 0; i < 14; i++) {
            int position = i * 18;
            data[DIVISORS_POSITION][i] = Integer.parseInt(lines.get(position + 4).substring(6));
            data[FIRST_NUMBER_POSITION][i] = Integer.parseInt(lines.get(position + 5).substring(6));
            data[SECOND_NUMBER_POSITION][i] = Integer.parseInt(lines.get(position + 15).substring(6));
        }

        return solveMin(data);
    }

    private long solveMax(int[][] data) {
        var chars = new char[14];
        var stack = new Stack<Integer>();
        for (var position = 0; position < 14; position++) {
            if (data[DIVISORS_POSITION][position] == 1) {
                stack.push(position);
            } else {
                findMaxPair(position, stack.pop(), data, chars);
            }
        }
        return Long.parseLong(new String(chars));
    }

    private long solveMin(int[][] data) {
        var chars = new char[14];
        var stack = new Stack<Integer>();
        for (var position = 0; position < 14; position++) {
            if (data[DIVISORS_POSITION][position] == 1) {
                stack.push(position);
            } else {
                findMinPair(position, stack.pop(), data, chars);
            }
        }
        return Long.parseLong(new String(chars));
    }

    private void findMaxPair(int firstIndex, int secondIndex, int[][] data, char[] chars) {
        for (var digit = 9; digit > 0; digit--) {
            var other = digit + data[FIRST_NUMBER_POSITION][firstIndex] + data[SECOND_NUMBER_POSITION][secondIndex];
            if (isNumberValidDigit(other)) {
                chars[secondIndex] = (char) ('0' + digit);
                chars[firstIndex] = (char) ('0' + other);
                return;
            }
        }
    }

    private void findMinPair(int firstIndex, int secondIndex, int[][] data, char[] chars) {
        for (var digit = 1; digit < 10; digit++) {
            var other = digit + data[FIRST_NUMBER_POSITION][firstIndex] + data[SECOND_NUMBER_POSITION][secondIndex];
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
