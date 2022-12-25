package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day12 implements AdventOfCodeSolution<Integer> {
    private int registera = 0;
    private int registerb = 0;
    private int registerc = 0;
    private int registerd = 0;

    @Override
    public Integer handlePart1(Stream<String> input) {
        var instructions = input.map(s -> s.split(" ")).toArray(String[][]::new);

        performOperations(instructions);

        return registera;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var instructions = input.map(s -> s.split(" ")).toArray(String[][]::new);

        registerc = 1;

        performOperations(instructions);

        return registera;
    }

    private void performOperations(String[][] instructions) {
        var instructionCounter = 0;

        while (instructionCounter < instructions.length) {
            var parts = instructions[instructionCounter];

            switch (parts[0]) {
                case "cpy" -> {
                    performCopyOperation(parts[1], parts[2]);
                    instructionCounter++;
                }
                case "inc" -> {
                    performIncrementOperation(parts[1]);
                    instructionCounter++;
                }
                case "dec" -> {
                    performDecrementOperation(parts[1]);
                    instructionCounter++;
                }
                case "jnz" -> {
                    int value = getValue(parts[1]);
                    if (value != 0) {
                        instructionCounter += Integer.parseInt(parts[2]);
                    } else {
                        instructionCounter++;
                    }
                }
            }
        }
    }

    private void performDecrementOperation(String register) {
        switch (register) {
            case "a" -> registera--;
            case "b" -> registerb--;
            case "c" -> registerc--;
            case "d" -> registerd--;
        }
    }

    private void performIncrementOperation(String register) {
        switch (register) {
            case "a" -> registera++;
            case "b" -> registerb++;
            case "c" -> registerc++;
            case "d" -> registerd++;
        }
    }

    private void performCopyOperation(String sourceRegister, String targetRegister) {
        var value = getValue(sourceRegister);
        switch (targetRegister) {
            case "a" -> registera = value;
            case "b" -> registerb = value;
            case "c" -> registerc = value;
            case "d" -> registerd = value;
        }
    }

    private int getValue(String register) {
        var value = 0;

        if (Character.isDigit(register.charAt(0))) {
            value = Integer.parseInt(register);
        } else {
            value = switch (register) {
                case "a" -> registera;
                case "b" -> registerb;
                case "c" -> registerc;
                case "d" -> registerd;
                default -> value;
            };
        }
        return value;
    }
}
