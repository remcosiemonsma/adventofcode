package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day23 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var operations = input.toArray(String[]::new);

        var registers = new HashMap<String, Long>();

        registers.put("a", 0L);
        registers.put("b", 0L);
        registers.put("c", 0L);
        registers.put("d", 0L);
        registers.put("e", 0L);
        registers.put("f", 0L);
        registers.put("g", 0L);
        registers.put("h", 0L);

        var pc = 0;

        var timesmul = 0;

        while (0 <= pc && pc < operations.length) {
            String[] opparts = operations[pc].split(" ");

            switch (opparts[0]) {
                case "set" -> {
                    registers.put(opparts[1], getValue(opparts[2], registers));
                    pc++;
                }
                case "sub" -> {
                    registers.put(opparts[1], registers.get(opparts[1]) - getValue(opparts[2], registers));
                    pc++;
                }
                case "mul" -> {
                    registers.put(opparts[1], registers.get(opparts[1]) * getValue(opparts[2], registers));
                    pc++;
                    timesmul++;
                }
                case "jnz" -> {
                    if (getValue(opparts[1], registers) != 0) {
                        pc += getValue(opparts[2], registers);
                    } else {
                        pc++;
                    }
                }
            }
        }
        
        return timesmul;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var h = 0;

        for (var i = 109300; i <= 126300; i += 17) {
            if (isComposite(i)) {
                h++;
            }
        }
        
        return h;
    }

    private boolean isComposite(int i) {
        for (int j = 2; j < i; j++) {
            if (i % j == 0) {
                return true;
            }
        }
        return false;
    }

    private static long getValue(String register, Map<String, Long> registers) {
        long value;
        try {
            value = Long.parseLong(register);
        } catch (NumberFormatException e) {
            value = registers.get(register);
        }
        return value;
    }
}
