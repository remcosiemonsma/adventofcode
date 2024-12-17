package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day17 implements BiAdventOfCodeSolution<String, Long> {
    @Override
    public String handlePart1(Stream<String> input) {
        var lines = input.toList();

        var registerA = Integer.parseInt(lines.getFirst().substring(12));
        var registerB = Integer.parseInt(lines.get(1).substring(12));
        var registerC = Integer.parseInt(lines.get(2).substring(12));

        var program = Arrays.stream(lines.get(4).substring(9).split(",")).mapToInt(Integer::parseInt).toArray();

        var cpu = new CPU(program, registerA, registerB, registerC);

        cpu.runProgram();

        return cpu.getOutput()
                  .stream()
                  .map(Object::toString)
                  .collect(Collectors.joining(","));
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var lines = input.toList();

        var program = Arrays.stream(lines.get(4).substring(9).split(",")).mapToInt(Integer::parseInt).toArray();

        var current = program.length - 1;

        var possibleDigits = new HashMap<Integer, Set<Integer>>();

        for (var i = 0; i < program.length; i++) {
            possibleDigits.put(i, new HashSet<>());
        }

        while (current >= program.length / 2) {
            for (long i = 0; i < 8; i++) {
                var possibleAttempts = generatePossibleAttempts(possibleDigits);

                for (var possibleAttempt : possibleAttempts) {
                    possibleAttempt += i << current * 3;

                    if (possibleAttempt == 0) {
                        continue;
                    }

                    var cpu = new CPU(program, possibleAttempt, 0, 0);

                    cpu.runProgram();

                    var output = cpu.getOutput();
                    if (output.get(current) == program[current]) {
                        possibleDigits.get(current).add((int)i);
                    }
                }
            }

            current--;
        }

        List<Long> attempts = generatePossibleAttempts(possibleDigits);

        var expected = new int[program.length / 2];
        System.arraycopy(program, program.length / 2, expected, 0, program.length / 2);

        var possibleValues = new ArrayList<Long>();

        for (var possibleAttempt : attempts) {
            var cpu = new CPU(program, possibleAttempt, 0, 0);

            cpu.runProgram();

            var output = cpu.getOutput();

            if (Arrays.equals(output.stream().skip(program.length / 2).mapToInt(Integer::intValue).toArray(), expected)) {
                possibleValues.add(possibleAttempt);
            }
        }

        Collections.sort(possibleValues);

        for (long value = possibleValues.getFirst(); value < possibleValues.getLast(); value++) {
            var cpu = new CPU(program, value, 0, 0);

            cpu.runProgram();

            var output = cpu.getOutput();

            if (Arrays.equals(output.stream().mapToInt(Integer::intValue).toArray(), program)) {
                return value;
            }
        }

        return 0L;
    }

    private List<Long> generatePossibleAttempts(Map<Integer, Set<Integer>> knownValues) {
        var possibleAttempts = new ArrayList<Long>();
        possibleAttempts.add(0L);
        for (var index : knownValues.keySet()) {
            var values = knownValues.get(index);
            if (!values.isEmpty()) {
                var newPossibleAttempts = new ArrayList<Long>();
                for (var value : values) {
                    for (var possibleAttempt : possibleAttempts) {
                        newPossibleAttempts.add(possibleAttempt + ((long) value << index * 3));
                    }
                }
                possibleAttempts = newPossibleAttempts;
            }
        }
        Collections.sort(possibleAttempts);
        return possibleAttempts;
    }

    private static class CPU {
        private long registerA;
        private long registerB;
        private long registerC;
        private final int[] program;
        private int counter = 0;
        private final List<Integer> output = new ArrayList<>();

        public CPU(int[] program, long registerA, long registerB, long registerC) {
            this.program = program;
            this.registerA = registerA;
            this.registerB = registerB;
            this.registerC = registerC;
        }

        private void runProgram() {
            while (counter < program.length) {
                var instruction = program[counter];

                switch (instruction) {
                    case 0 -> adv();
                    case 1 -> bxl();
                    case 2 -> bst();
                    case 3 -> jnz();
                    case 4 -> bxc();
                    case 5 -> out();
                    case 6 -> bdv();
                    case 7 -> cdv();
                }

                counter += 2;
            }
        }

        private void adv() {
            registerA = registerA >> getComboOperand(program[counter + 1]);
        }

        private void bxl() {
            registerB ^= program[counter + 1];
        }

        private void bst() {
            registerB = getComboOperand(program[counter + 1]) % 8;
        }

        private void jnz() {
            if (registerA != 0) {
                counter = program[counter + 1] - 2;
            }
        }

        private void bxc() {
            registerB ^= registerC;
        }

        private void out() {
            output.add((int) (getComboOperand(program[counter + 1]) % 8));
        }

        private void bdv() {
            registerC = registerA >> getComboOperand(program[counter + 1]);
        }

        private void cdv() {
            registerC = registerA >> getComboOperand(program[counter + 1]);
        }

        private long getComboOperand(int operand) {
            if (operand <= 3) {
                return operand;
            } else if (operand == 4) {
                return registerA;
            } else if (operand == 5) {
                return registerB;
            } else if (operand == 6) {
                return registerC;
            } else {
                throw new AssertionError("Invalid operand: " + operand);
            }
        }

        public List<Integer> getOutput() {
            return output;
        }
    }
}
