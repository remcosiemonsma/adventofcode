package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day8 implements AdventOfCodeSolution<Integer> {
    private static final Pattern PATTERN = Pattern.compile("(\\w+) (\\w{3}) (-?\\d+) if (\\w+) ([!<>=]{1,2}) (-?\\d+)");
    private final Map<String, Integer> registers = new HashMap<>();
    private int largestValue = Integer.MIN_VALUE;

    @Override
    public Integer handlePart1(Stream<String> input) {
        input.forEach(this::processOperation);

        var largestRegisterValue = Integer.MIN_VALUE;

        for (String register : registers.keySet()) {
            if (largestRegisterValue < registers.get(register)) {
                largestRegisterValue = registers.get(register);
            }
        }
        return largestRegisterValue;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        input.forEach(this::processOperation);

        return largestValue;
    }

    private void processOperation(String operation) {
        var matcher = PATTERN.matcher(operation);
        if (matcher.matches()) {
            var register1 = matcher.group(1);
            var instruction = matcher.group(2);
            var increment = Integer.parseInt(matcher.group(3));
            var register2 = matcher.group(4);
            var expression = matcher.group(5);
            var verification = Integer.parseInt(matcher.group(6));

            var register1Value = registers.get(register1);
            if (register1Value == null) {
                register1Value = 0;
            }

            var register2Value = registers.get(register2);

            if (register2Value == null) {
                register2Value = 0;
            }

            var performOperation = switch (expression) {
                case "<" -> register2Value < verification;
                case ">" -> register2Value > verification;
                case "==" -> register2Value == verification;
                case "!=" -> register2Value != verification;
                case "<=" -> register2Value <= verification;
                case ">=" -> register2Value >= verification;
                default -> false;
            };

            if (performOperation) {
                switch (instruction) {
                    case "inc" -> register1Value += increment;
                    case "dec" -> register1Value -= increment;
                }
            }
            if (largestValue < register1Value) {
                largestValue = register1Value;
            }

            registers.put(register1, register1Value);
        }
    }
}
