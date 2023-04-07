package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.ArrayDeque;
import java.util.stream.Stream;

public class Day10 implements BiAdventOfCodeSolution<Integer, Long> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.mapToInt(this::calculateSyntaxErrorScore)
                    .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var scores = input.map(this::calculateCompletionScore)
                          .filter(i -> i != -1)
                          .sorted()
                          .toList();

        return scores.get(scores.size() / 2);
    }

    private int calculateSyntaxErrorScore(String line) {
        var stack = new ArrayDeque<Character>();

        for (var c : line.toCharArray()) {
            switch (c) {
                case '(', '[', '{', '<' -> stack.push(c);
                case ')' -> {
                    var c1 = stack.pop();
                    if (c1 != '(') {
                        return 3;
                    }
                }
                case ']' -> {
                    var c1 = stack.pop();
                    if (c1 != '[') {
                        return 57;
                    }
                }
                case '}' -> {
                    var c1 = stack.pop();
                    if (c1 != '{') {
                        return 1197;
                    }
                }
                case '>' -> {
                    var c1 = stack.pop();
                    if (c1 != '<') {
                        return 25137;
                    }
                }
            }
        }

        return 0;
    }

    private long calculateCompletionScore(String line) {
        var stack = new ArrayDeque<Character>();

        for (var c : line.toCharArray()) {
            switch (c) {
                case '(', '[', '{', '<' -> stack.push(c);
                case ')' -> {
                    var c1 = stack.pop();
                    if (c1 != '(') {
                        return -1;
                    }
                }
                case ']' -> {
                    var c1 = stack.pop();
                    if (c1 != '[') {
                        return -1;
                    }
                }
                case '}' -> {
                    var c1 = stack.pop();
                    if (c1 != '{') {
                        return -1;
                    }
                }
                case '>' -> {
                    var c1 = stack.pop();
                    if (c1 != '<') {
                        return -1;
                    }
                }
            }
        }

        var score = 0L;

        while (!stack.isEmpty()) {
            var c = stack.pop();

            score *= 5;
            score += switch (c) {
                case '(' -> 1;
                case '[' -> 2;
                case '{' -> 3;
                case '<' -> 4;
                default -> throw new AssertionError("Eek!");
            };
        }

        return score;
    }
}
