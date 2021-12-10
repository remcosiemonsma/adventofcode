package nl.remcoder.adventofcode;

import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
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
                                 .collect(Collectors.toList());

        return scores.get(scores.size() / 2);
    }

    private int calculateSyntaxErrorScore(String line) {
        Stack<Character> stack = new Stack<>();

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
        Stack<Character> stack = new Stack<>();

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

            switch (c) {
                case '(' -> score = (score * 5) + 1;
                case '[' -> score = (score * 5) + 2;
                case '{' -> score = (score * 5) + 3;
                case '<' -> score = (score * 5) + 4;
            }
        }

        return score;
    }
}
