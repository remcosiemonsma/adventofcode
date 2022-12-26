package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day21 implements AdventOfCodeSolution<String> {
    private final Pattern SWAP_POSITION_PATTERN = Pattern.compile("swap position (\\d) with position (\\d)");
    private final Pattern SWAP_LETTER_PATTERN = Pattern.compile("swap letter (\\w) with letter (\\w)");
    private final Pattern ROTATE_LEFT_RIGHT_PATTERN = Pattern.compile("rotate (left|right) (\\d) steps?");
    private final Pattern ROTATE_POSITION_PATTERN = Pattern.compile("rotate based on position of letter (\\w)");
    private final Pattern REVERSE_PATTERN = Pattern.compile("reverse positions (\\w) through (\\w)");
    private final Pattern MOVE_PATTERN = Pattern.compile("move position (\\d) to position (\\d)");

    private String password;

    @Override
    public String handlePart1(Stream<String> input) {
        var chars = password.toCharArray();

        input.forEach(operation -> processPassword(chars, operation));

        return String.valueOf(chars);
    }

    @Override
    public String handlePart2(Stream<String> input) {
        var chars = password.toCharArray();

        var operations = new ArrayList<>(input.toList());
        Collections.reverse(operations);

        operations.forEach(operation -> revertPassword(chars, operation));
        
        return String.valueOf(chars);
    }

    public void processPassword(char[] chars, String operation) {
        var matcher = SWAP_POSITION_PATTERN.matcher(operation);
        if (matcher.matches()) {
            swapPositions(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), chars);
            return;
        }
        matcher = SWAP_LETTER_PATTERN.matcher(operation);
        if (matcher.matches()) {
            swapLetters(matcher.group(1).charAt(0), matcher.group(2).charAt(0), chars);
            return;
        }
        matcher = ROTATE_LEFT_RIGHT_PATTERN.matcher(operation);
        if (matcher.matches()) {
            switch (matcher.group(1)) {
                case "left" -> rotateLeft(Integer.parseInt(matcher.group(2)), chars);
                case "right" -> rotateRight(Integer.parseInt(matcher.group(2)), chars);
            }
            return;
        }
        matcher = ROTATE_POSITION_PATTERN.matcher(operation);
        if (matcher.matches()) {
            rotatePosition(matcher.group(1).charAt(0), chars);
            return;
        }
        matcher = REVERSE_PATTERN.matcher(operation);
        if (matcher.matches()) {
            reversePositions(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), chars);
            return;
        }
        matcher = MOVE_PATTERN.matcher(operation);
        if (matcher.matches()) {
            move(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), chars);
        }
    }
    
    public void revertPassword(char[] chars, String operation) {
        var matcher = SWAP_POSITION_PATTERN.matcher(operation);
        if (matcher.matches()) {
            swapPositions(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(1)), chars);
            return;
        }
        matcher = SWAP_LETTER_PATTERN.matcher(operation);
        if (matcher.matches()) {
            swapLetters(matcher.group(1).charAt(0), matcher.group(2).charAt(0), chars);
            return;
        }
        matcher = ROTATE_LEFT_RIGHT_PATTERN.matcher(operation);
        if (matcher.matches()) {
            switch (matcher.group(1)) {
                case "left" -> rotateRight(Integer.parseInt(matcher.group(2)), chars);
                case "right" -> rotateLeft(Integer.parseInt(matcher.group(2)), chars);
            }
            return;
        }
        matcher = ROTATE_POSITION_PATTERN.matcher(operation);
        if (matcher.matches()) {
            var c = matcher.group(1).charAt(0);
            for (int i = 1; i <= chars.length; i++) {
                var temp = Arrays.copyOf(chars, chars.length);
                rotateLeft(i, temp);
                rotatePosition(c, temp);
                if (Arrays.equals(temp, chars)) {
                    rotateLeft(i, chars);
                    return;
                }
            }
            throw new AssertionError("Eek!");
        }
        matcher = REVERSE_PATTERN.matcher(operation);
        if (matcher.matches()) {
            reversePositions(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), chars);
            return;
        }
        matcher = MOVE_PATTERN.matcher(operation);
        if (matcher.matches()) {
            move(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(1)), chars);
        }
    }

    private void swapPositions(int first, int second, char[] chars) {
        char temp = chars[first];
        chars[first] = chars[second];
        chars[second] = temp;
    }

    private void swapLetters(char c1, char c2, char[] chars) {
        for (var i = 0; i < chars.length; i++) {
            if (chars[i] == c1) {
                chars[i] = c2;
            } else if (chars[i] == c2) {
                chars[i] = c1;
            }
        }
    }

    private void rotateLeft(int steps, char[] chars) {
        char[] temp = Arrays.copyOf(chars, chars.length);
        
        for (var i = 0; i < chars.length; i++) {
            var newIndex = Math.floorMod(i - steps, chars.length);
            chars[newIndex] = temp[i];
        }
    }

    private void rotateRight(int steps, char[] chars) {
        char[] temp = Arrays.copyOf(chars, chars.length);

        for (var i = 0; i < chars.length; i++) {
            var newIndex = (i + steps) % chars.length;
            chars[newIndex] = temp[i];
        }
    }

    private void rotatePosition(char c, char[] chars) {
        for (var i = 0; i < chars.length; i++) {
            if (chars[i] == c) {
                var steps = i + 1;
                if (i >= 4) {
                    steps++;
                }
                rotateRight(steps, chars);
                return;
            }
        }
    }

    private void reversePositions(int firstIndex, int secondIndex, char[] chars) {
        var start = Math.min(firstIndex, secondIndex);
        var end = Math.max(firstIndex, secondIndex);

        char[] temp = Arrays.copyOf(chars, chars.length);
        
        for (var i = start; i <= end; i++) {
            chars[i] = temp[end - (i - start)];
        }
    }

    private void move(int index1, int index2, char[] chars) {
        char c = chars[index1];
        
        if (index1 < index2) {
            for (var i = index1; i < index2; i++) {
                chars[i] = chars[i + 1];
            }
        } else {
            for (var i = index1; i > index2; i--) {
                chars[i] = chars[i - 1];
            }
        }
        chars[index2] = c;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
