package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day21 implements AdventOfCodeSolution<Long> {
    private static final Pattern VALUE_PATTERN = Pattern.compile("(\\w{4}): (\\d+)");
    private static final Pattern OPERATION_PATTERN = Pattern.compile("(\\w{4}): (\\w{4}) ([+\\-*/]) (\\w{4})");

    @Override
    public Long handlePart1(Stream<String> input) {
        var monkeys = input.map(this::parseMonkey)
                           .collect(Collectors.toMap(Monkey::getId, monkey -> monkey));

        monkeys.values().forEach(monkey -> monkey.processMonkeys(monkeys));

        return monkeys.get("root").getValue();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var monkeys = input.map(this::parseMonkey)
                           .collect(Collectors.toMap(Monkey::getId, monkey -> monkey));

        var human = new HumanMonkey();
        monkeys.put("humn", human);
        
        monkeys.values().forEach(monkey -> monkey.processMonkeys(monkeys));
        
        var root = (OperationMonkey) monkeys.get("root");

        var monkey1 = root.monkey1;
        var monkey2 = root.monkey2;
        
        var monkey1Monkeys = new ArrayList<>();
        var monkeysToCheck = new ArrayList<>();
        
        monkeysToCheck.add(monkey1);
        
        while (!monkeysToCheck.isEmpty()) {
            var monkey = monkeysToCheck.remove(0);
            
            if (monkey instanceof OperationMonkey operationMonkey) {
                monkeysToCheck.add(operationMonkey.monkey1);
                monkeysToCheck.add(operationMonkey.monkey2);
                monkey1Monkeys.add(operationMonkey.monkey1);
                monkey1Monkeys.add(operationMonkey.monkey2);
            }
        }

        long value;
        String operation;
        
        if (monkey1Monkeys.contains(human)) {
            value = monkey2.getValue();
            operation = monkey1.printOperation();
        } else {
            value = monkey1.getValue();
            operation = monkey2.printOperation();
        }
        
        return calculateForX(operation.substring(1, operation.length() - 1), value);
    }

    private long calculateForX(String calculation, long value) {
        if ("x".equals(calculation)) {
            return value;
        }
        
        String firstPart;

        var position = 1;
        if (calculation.charAt(0) == '(') {
            var bracketCounter = 1;
            
            while (bracketCounter !=0) {
                if (calculation.charAt(position) == '(') {
                    bracketCounter++;
                } else if (calculation.charAt(position) == ')') {
                    bracketCounter--;
                }
                position++;
            }
            firstPart = calculation.substring(1, position - 1);
        } else {
            position = calculation.indexOf(' ');
            firstPart = calculation.substring(0, position);
        }
        
        var operand = calculation.charAt(++position);
        position += 2;
        
        
        String secondPart;
        if (calculation.charAt(position) == '(') {
            secondPart = calculation.substring(position + 1, calculation.length() - 1);
        } else {
            secondPart = calculation.substring(position);
        }

        switch (operand) {
            case '+' -> {
                if (firstPart.contains("x")) {
                    var second = calculateValue(secondPart);
                    var newValue = value - second;
                    return calculateForX(firstPart, newValue);
                } else {
                    var first = calculateValue(firstPart);
                    var newValue = value - first;
                    return calculateForX(secondPart, newValue);
                }
            }
            case '*' -> {
                if (firstPart.contains("x")) {
                    var second = calculateValue(secondPart);
                    var newValue = value / second;
                    return calculateForX(firstPart, newValue);
                } else {
                    var first = calculateValue(firstPart);
                    var newValue = value / first;
                    return calculateForX(secondPart, newValue);
                }
            }
            case '/' -> {
                if (firstPart.contains("x")) {
                    var second = calculateValue(secondPart);
                    var newValue = value * second;
                    return calculateForX(firstPart, newValue);
                } else {
                    var first = calculateValue(firstPart);
                    var newValue = first * value;
                    return calculateForX(secondPart, newValue);
                }
            }
            case '-' -> {
                if (firstPart.contains("x")) {
                    var second = calculateValue(secondPart);
                    var newValue = value + second;
                    return calculateForX(firstPart, newValue);
                } else {
                    var first = calculateValue(firstPart);
                    var newValue = first - value;
                    return calculateForX(secondPart, newValue);
                }
            }
            default -> throw new AssertionError("Eek!");
        }
    }
    
    private Long calculateValue(String calculation) {
        if (calculation.matches("^\\d+$")) {
            return Long.parseLong(calculation);
        }

        String firstPart;

        var position = 1;
        if (calculation.charAt(0) == '(') {
            var bracketCounter = 1;

            while (bracketCounter !=0) {
                if (calculation.charAt(position) == '(') {
                    bracketCounter++;
                } else if (calculation.charAt(position) == ')') {
                    bracketCounter--;
                }
                position++;
            }
            firstPart = calculation.substring(1, position - 1);
        } else {
            position = calculation.indexOf(' ');
            firstPart = calculation.substring(0, position);
        }

        var operand = calculation.charAt(++position);
        position += 2;

        String secondPart;
        if (calculation.charAt(position) == '(') {
            secondPart = calculation.substring(position + 1, calculation.length() - 1);
        } else {
            secondPart = calculation.substring(position);
        }
        
        switch (operand) {
            case '+' -> {
                var left = calculateValue(firstPart);
                var right = calculateValue(secondPart);
                return left + right;
            }
            case '*' -> {
                var left = calculateValue(firstPart);
                var right = calculateValue(secondPart);
                return left * right;
            }
            case '/' -> {
                var left = calculateValue(firstPart);
                var right = calculateValue(secondPart);
                return left / right;
            }
            case '-' -> {
                var left = calculateValue(firstPart);
                var right = calculateValue(secondPart);
                return left - right;
            }
            default -> throw new AssertionError("Eek!");
        }
    }
    
    private Monkey parseMonkey(String line) {
        var valueMatcher = VALUE_PATTERN.matcher(line);
        if (valueMatcher.matches()) {
            return createValueMonkey(valueMatcher);
        }
        var operationMatcher = OPERATION_PATTERN.matcher(line);
        if (operationMatcher.matches()) {
            return createOperationMonkey(operationMatcher);
        }
        throw new AssertionError("Eek!");
    }

    private Monkey createOperationMonkey(Matcher operationMatcher) {
        BiFunction<Long, Long, Long> function = switch (operationMatcher.group(3).charAt(0)) {
            case '*' -> (aLong, aLong2) -> aLong * aLong2;
            case '+' -> Long::sum;
            case '-' -> (aLong, aLong2) -> aLong - aLong2;
            case '/' -> (aLong, aLong2) -> aLong / aLong2;
            default -> throw new AssertionError("Eek!");
        };
        return new OperationMonkey(operationMatcher.group(1), function, operationMatcher.group(2),
                                   operationMatcher.group(4), operationMatcher.group(3).charAt(0));
    }

    private Monkey createValueMonkey(Matcher valueMatcher) {
        return new Value(valueMatcher.group(1), Long.parseLong(valueMatcher.group(2)));
    }

    private interface Monkey {
        String getId();

        long getValue();

        void processMonkeys(Map<String, Monkey> monkeys);
        
        String printOperation();
    }

    private static class HumanMonkey implements Monkey {
        @Override
        public String getId() {
            return "humn";
        }

        @Override
        public long getValue() {
            return Long.MIN_VALUE;
        }
        
        @Override
        public void processMonkeys(Map<String, Monkey> monkeys) {}

        @Override
        public String printOperation() {
            return "x";
        }
    }

    private static class Value implements Monkey {
        private final String id;
        private final long value;

        private Value(String id, long value) {
            this.id = id;
            this.value = value;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public long getValue() {
            return value;
        }

        @Override
        public void processMonkeys(Map<String, Monkey> monkeys) {
        }

        @Override
        public String printOperation() {
            return String.valueOf(value);
        }
    }

    private static class OperationMonkey implements Monkey {
        private final String id;
        private final BiFunction<Long, Long, Long> operation;
        private final String monkey1Id;
        private final String monkey2Id;
        private final char operand;
        private Monkey monkey1;
        private Monkey monkey2;

        private OperationMonkey(String id, BiFunction<Long, Long, Long> operation, String monkey1Id,
                                String monkey2Id, char operand) {
            this.id = id;
            this.operation = operation;
            this.monkey1Id = monkey1Id;
            this.monkey2Id = monkey2Id;
            this.operand = operand;
        }

        public void processMonkeys(Map<String, Monkey> monkeys) {
            monkey1 = monkeys.get(monkey1Id);
            monkey2 = monkeys.get(monkey2Id);
        }

        @Override
        public String printOperation() {
            return "(%s %s %s)".formatted(monkey1.printOperation(), operand, monkey2.printOperation());
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public long getValue() {
            return operation.apply(monkey1.getValue(), monkey2.getValue());
        }
    }
}
