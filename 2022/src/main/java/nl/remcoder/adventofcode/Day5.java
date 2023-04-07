package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day5 implements AdventOfCodeSolution<String> {
    private static final Pattern STACK_PATTERN = Pattern.compile("(\\[[A-Z]] ?| {3} ?)");
    private static final Pattern MOVE_PATTERN = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
    private Stack<Character>[] stacks;
    private Mode mode = Mode.STACKING;

    @Override
    public String handlePart1(Stream<String> input) {
        input.forEach(this::processLine);

        var stringBuilder = new StringBuilder();

        for (var stack : stacks) {
            stringBuilder.append(stack.peek());
        }

        return stringBuilder.toString();
    }

    @Override
    public String handlePart2(Stream<String> input) {
        input.forEach(this::processLine2);

        var stringBuilder = new StringBuilder();

        for (var stack : stacks) {
            stringBuilder.append(stack.peek());
        }

        return stringBuilder.toString();
    }

    private void processLine(String line) {
        switch (mode) {
            case STACKING -> appendStack(line);
            case MOVING -> move(line);
        }
    }

    private void processLine2(String line) {
        switch (mode) {
            case STACKING -> appendStack(line);
            case MOVING -> move2(line);
        }
    }

    private void move(String line) {
        var matcher = MOVE_PATTERN.matcher(line);

        if (matcher.matches()) {
            var amount = Integer.parseInt(matcher.group(1));
            var from = Integer.parseInt(matcher.group(2)) - 1;
            var to = Integer.parseInt(matcher.group(3)) - 1;

            var source = stacks[from];
            var target = stacks[to];

            for (var i = 0; i < amount; i++) {
                target.push(source.pop());
            }
        }
    }

    private void move2(String line) {
        var matcher = MOVE_PATTERN.matcher(line);

        if (matcher.matches()) {
            var amount = Integer.parseInt(matcher.group(1));
            var from = Integer.parseInt(matcher.group(2)) - 1;
            var to = Integer.parseInt(matcher.group(3)) - 1;

            var source = stacks[from];
            var target = stacks[to];
            var temp = new Stack<Character>();

            for (int i = 0; i < amount; i++) {
                temp.push(source.pop());
            }

            while (!temp.isEmpty()) {
                target.push(temp.pop());
            }
        }
    }

    private void appendStack(String line) {
        if (line.isBlank()) {
            reverseStacks();
            mode = Mode.MOVING;
            return;
        }

        var matcher = STACK_PATTERN.matcher(line);

        var crates = processCrates(matcher);

        if (stacks == null) {
            stacks = new Stack[crates.size()];
            for (int i = 0; i < stacks.length; i++) {
                stacks[i] = new Stack<>();
            }
        }

        if (crates.size() < stacks.length) {
            return;
        }

        for (int i = 0; i < crates.size(); i++) {
            stacks[i].push(crates.get(i));
        }
    }

    private static List<Character> processCrates(Matcher matcher) {
        List<Character> crates = new ArrayList<>();

        while (matcher.find()) {
            String crate = matcher.group(1);
            if (crate.isBlank()) {
                crates.add(null);
            } else {
                crates.add(crate.charAt(1));
            }
        }
        return crates;
    }

    private void reverseStacks() {
        for (int i = 0; i < stacks.length; i++) {
            Stack<Character> temp = new Stack<>();
            Stack<Character> current = stacks[i];
            Character c;
            while (!current.isEmpty() && (c = current.pop()) != null) {
                temp.push(c);
            }
            stacks[i] = temp;
        }
    }

    private enum Mode {
        STACKING,
        MOVING
    }
}
