package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day18 {
    public long handlePart1(Stream<String> input) {
        return input.mapToLong(this::calculateExpressionPart1)
                    .sum();
    }

    public long handlePart2(Stream<String> input) {
        return input.mapToLong(this::calculateExpressionPart2)
                    .sum();
    }

    private long calculateExpressionPart2(String expression) {
        long result = 0;

        StringBuilder parenthesesStringBuilder = new StringBuilder();
        int parenthesesCount = 0;

        char[] charArray = expression.toCharArray();
        for (int position = 0; position < charArray.length; position++) {
            char character = charArray[position];
            if (parenthesesCount > 0) {
                if (character == '(') {
                    parenthesesCount++;
                }
                if (character == ')') {
                    parenthesesCount--;
                }
                if (parenthesesCount == 0) {
                    result += calculateExpressionPart2(parenthesesStringBuilder.toString());
                    parenthesesStringBuilder = new StringBuilder();
                } else {
                    parenthesesStringBuilder.append(character);
                }
                continue;
            }

            if (character == ' ' || character == '+') {
                continue;
            }

            if (character >= '0' && character <= '9') {
                result += character - '0';
            }

            if (character == '*') {
                result *= calculateExpressionPart2(expression.substring(position + 1));
                break;
            }

            if (character == '(') {
                parenthesesCount++;
            }
        }

        return result;
    }

    private long calculateExpressionPart1(String expression) {
        long result = 0;

        Mode mode = Mode.ADDITION;

        StringBuilder parenthesesStringBuilder = new StringBuilder();
        int parenthesesCount = 0;

        for (char character : expression.toCharArray()) {
            if (parenthesesCount > 0) {
                if (character == '(') {
                    parenthesesCount++;
                }
                if (character == ')') {
                    parenthesesCount--;
                }
                if (parenthesesCount == 0) {
                    switch (mode) {
                        case ADDITION -> result += calculateExpressionPart1(parenthesesStringBuilder.toString());
                        case MULTIPLICATION -> result *= calculateExpressionPart1(parenthesesStringBuilder.toString());
                    }
                    parenthesesStringBuilder = new StringBuilder();
                } else {
                    parenthesesStringBuilder.append(character);
                }
                continue;
            }

            if (character == ' ') {
                continue;
            }

            if (character >= '0' && character <= '9') {
                switch (mode) {
                    case ADDITION -> result += character - '0';
                    case MULTIPLICATION -> result *= character - '0';
                }
            }

            if (character == '*') {
                mode = Mode.MULTIPLICATION;
            }
            if (character == '+') {
                mode = Mode.ADDITION;
            }
            if (character == '(') {
                parenthesesCount++;
            }
        }

        return result;
    }

    private enum Mode {
        MULTIPLICATION,
        ADDITION
    }
}
