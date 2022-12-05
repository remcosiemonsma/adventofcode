package nl.remcoder.adventofcode;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day7 {
    private static final Pattern NUMERIC = Pattern.compile("^\\d+$");
    private final Map<String, Wire> wires = new HashMap<>();

    public int handlePart1(Stream<String> input) {

        input.forEach(line -> {
            var split = line.split(" -> ");
            var left = split[0].split(" ");

            switch (left.length) {
                case 1 -> parseDirect(split);
                case 2 -> parseNot(split, left);
                default -> parseRest(split, left);
            }
        });

        return wires.get("a").getValue();
    }

    public int handlePart2(Stream<String> input) {
        int valueA = handlePart1(input);

        wires.values().forEach(Wire::reset);

        wires.put(Integer.toString(valueA), new Value(Integer.toString(valueA), valueA));
        wires.put("b", new Direct("b", Integer.toString(valueA)));

        return wires.get("a").getValue();
    }

    private void parseDirect(String[] split) {
        var numericMatcher = NUMERIC.matcher(split[0]);

        if (numericMatcher.matches()) {
            var value = new Value(split[0], Integer.parseInt(split[0]));
            wires.put(split[0], value);
        }

        var direct = new Direct(split[1], split[0]);
        wires.put(split[1], direct);
    }

    private void parseNot(String[] split, String[] left) {
        var numericMatcher = NUMERIC.matcher(left[1]);

        if (numericMatcher.matches()) {
            var value = new Value(left[1], Integer.parseInt(left[1]));
            wires.put(left[1], value);
        }

        var not = new Not(split[1], left[1]);
        wires.put(split[1], not);
    }

    private void parseRest(String[] split, String[] left) {
        switch (left[1]) {
            case "AND" -> parseAnd(split, left);
            case "OR" -> parseOr(split, left);
            case "LSHIFT" -> parseLShift(split, left);
            case "RSHIFT" -> parseRShift(split, left);
        }
    }

    private void parseAnd(String[] split, String[] left) {
        parseValues(left);
        var and = new And(split[0], left[0], left[2]);
        wires.put(split[1], and);
    }

    private void parseOr(String[] split, String[] left) {
        parseValues(left);
        var or = new Or(split[0], left[0], left[2]);
        wires.put(split[1], or);
    }

    private void parseLShift(String[] split, String[] left) {
        parseValues(left);
        var leftShift = new LeftShift(split[0], left[0], left[2]);
        wires.put(split[1], leftShift);
    }

    private void parseRShift(String[] split, String[] left) {
        parseValues(left);
        var rightShift = new RightShift(split[0], left[0], left[2]);
        wires.put(split[1], rightShift);
    }

    private void parseValues(String[] left) {
        Matcher numericMatcher;
        numericMatcher = NUMERIC.matcher(left[0]);
        if (numericMatcher.matches()) {
            var value = new Value(left[0], Integer.parseInt(left[0]));
            wires.put(left[0], value);
        }
        numericMatcher = NUMERIC.matcher(left[2]);
        if (numericMatcher.matches()) {
            var value = new Value(left[2], Integer.parseInt(left[2]));
            wires.put(left[2], value);
        }
    }

    private interface Wire {
        String getId();
        int getValue();
        void reset();
    }

    private static class Value implements Wire {
        private final String id;
        private final int value;

        private Value(String id, int value) {
            this.id = id;
            this.value = value;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public int getValue() {
            return value;
        }

        @Override
        public void reset() {}
    }

    private class Direct implements Wire {
        private final String id;
        private final String source;
        private int result = -1;

        private Direct(String id, String source) {
            this.id = id;
            this.source = source;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public int getValue() {
            if (result == -1) {
                result = wires.get(source).getValue();
            }
            return result;
        }

        @Override
        public void reset() {
            result = -1;
        }
    }

    private class And implements Wire {
        private final String id;
        private final String left;
        private final String right;
        private int result = -1;

        private And(String id, String left, String right) {
            this.id = id;
            this.left = left;
            this.right = right;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public int getValue() {
            if (result == -1) {
                result = (wires.get(left).getValue() & wires.get(right).getValue()) & 0xffff;
            }
            return result;
        }

        @Override
        public void reset() {
            result = -1;
        }
    }

    private class Or implements Wire {
        private final String id;
        private final String left;
        private final String right;
        private int result = -1;

        private Or(String id, String left, String right) {
            this.id = id;
            this.left = left;
            this.right = right;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public int getValue() {
            if (result == -1) {
                result = (wires.get(left).getValue() | wires.get(right).getValue()) & 0xffff;
            }
            return result;
        }

        @Override
        public void reset() {
            result = -1;
        }
    }

    private class LeftShift implements Wire {
        private final String id;
        private final String left;
        private final String right;
        private int result = -1;

        private LeftShift(String id, String left, String right) {
            this.id = id;
            this.left = left;
            this.right = right;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public int getValue() {
            if (result == -1) {
                result = (wires.get(left).getValue() << wires.get(right).getValue()) & 0xffff;
            }
            return result;
        }

        @Override
        public void reset() {
            result = -1;
        }
    }

    private class RightShift implements Wire {
        private final String id;
        private final String left;
        private final String right;
        private int result = -1;

        private RightShift(String id, String left, String right) {
            this.id = id;
            this.left = left;
            this.right = right;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public int getValue() {
            if (result == -1) {
                result = (wires.get(left).getValue() >> wires.get(right).getValue()) & 0xffff;
            }
            return result;
        }

        @Override
        public void reset() {
            result = -1;
        }
    }

    private class Not implements Wire {
        private final String id;
        private final String source;
        private int result = -1;

        private Not(String id, String source) {
            this.id = id;
            this.source = source;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public int getValue() {
            if (result == -1) {
                result = (~wires.get(source).getValue()) & 0xffff;
            }
            return result;
        }

        @Override
        public void reset() {
            result = -1;
        }
    }
}
