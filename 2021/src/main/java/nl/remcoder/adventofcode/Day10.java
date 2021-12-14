package nl.remcoder.adventofcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Stream;

public class Day10 {
    public int handlePart1(Stream<String> input) {
        return input.mapToInt(this::calculateSyntaxErrorScore)
                    .sum();
    }

    public long handlePart2(Stream<String> input) {
        List<Long> scores = input.map(this::calculateCompletionScore)
                                 .filter(i -> i != -1)
                                 .sorted()
                                 .toList();

        return scores.get(scores.size() / 2);
    }

    private int calculateSyntaxErrorScore(String line) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : line.toCharArray()) {
            switch (c) {
                case '(', '[', '{', '<' -> stack.push(c);
                case ')' -> {
                    char c1 = stack.pop();
                    if (c1 != '(') {
                        return 3;
                    }
                }
                case ']' -> {
                    char c1 = stack.pop();
                    if (c1 != '[') {
                        return 57;
                    }
                }
                case '}' -> {
                    char c1 = stack.pop();
                    if (c1 != '{') {
                        return 1197;
                    }
                }
                case '>' -> {
                    char c1 = stack.pop();
                    if (c1 != '<') {
                        return 25137;
                    }
                }
            }
        }

        return 0;
    }

    private long calculateCompletionScore(String line) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : line.toCharArray()) {
            switch (c) {
                case '(', '[', '{', '<' -> stack.push(c);
                case ')' -> {
                    char c1 = stack.pop();
                    if (c1 != '(') {
                        return -1;
                    }
                }
                case ']' -> {
                    char c1 = stack.pop();
                    if (c1 != '[') {
                        return -1;
                    }
                }
                case '}' -> {
                    char c1 = stack.pop();
                    if (c1 != '{') {
                        return -1;
                    }
                }
                case '>' -> {
                    char c1 = stack.pop();
                    if (c1 != '<') {
                        return -1;
                    }
                }
            }
        }

        long score = 0;

        while (!stack.isEmpty()) {
            char c = stack.pop();

            score = switch (c) {
                case '(' -> (score * 5) + 1;
                case '[' -> (score * 5) + 2;
                case '{' -> (score * 5) + 3;
                case '<' -> (score * 5) + 4;
                default -> throw new AssertionError("Eek!");
            };
        }

        return score;
    }
}
