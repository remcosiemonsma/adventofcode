package nl.remcoder.adventofcode.day8;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
    private static Pattern pattern = Pattern.compile("(\\w+) (\\w{3}) (-?\\d+) if (\\w+) ([!<>=]{1,2}) (-?\\d+)");

    public static void main(String[] args) throws Exception {
        Map<String, Integer> registers = new HashMap<>();

        Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI())).forEach(s -> {
            Matcher matcher = pattern.matcher(s);
            if(matcher.find()) {
                String register1 = matcher.group(1);
                String instruction = matcher.group(2);
                int increment = Integer.parseInt(matcher.group(3));
                String register2 = matcher.group(4);
                String expression = matcher.group(5);
                int verification = Integer.parseInt(matcher.group(6));

                Integer register1Value = registers.get(register1);
                if (register1Value == null) {
                    register1Value = 0;
                }

                Integer register2Value = registers.get(register2);

                if(register2Value == null) {
                    register2Value = 0;
                }

                boolean performoperation = false;
                switch (expression) {
                    case "<":
                        performoperation = register2Value < verification;
                        break;
                    case ">":
                        performoperation = register2Value > verification;
                        break;
                    case "==":
                        performoperation = register2Value == verification;
                        break;
                    case "!=":
                        performoperation = register2Value != verification;
                        break;
                    case "<=":
                        performoperation = register2Value <= verification;
                        break;
                    case ">=":
                        performoperation = register2Value >= verification;
                        break;
                }

                if (performoperation) {
                    switch (instruction) {
                        case "inc":
                            register1Value += increment;
                            break;
                        case "dec":
                            register1Value -= increment;
                            break;
                    }
                }
                registers.put(register1, register1Value);
            }
        });

        int largestValue = Integer.MIN_VALUE;

        for (String register : registers.keySet()) {
            if (largestValue < registers.get(register)) {
                largestValue = registers.get(register);
            }
        }
        System.out.println(largestValue);
    }
}
