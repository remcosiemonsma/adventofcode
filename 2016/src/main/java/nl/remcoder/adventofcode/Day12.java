package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day12 {
    private int registera = 0;
    private int registerb = 0;
    private int registerc = 0;
    private int registerd = 0;

    public int handlePart1(Stream<String> input) {
        String[][] instructions = input.map(s -> s.split(" ")).toArray(String[][]::new);

        performOperations(instructions);

        return registera;
    }

    public int handlePart2(Stream<String> input) {
        String[][] instructions = input.map(s -> s.split(" ")).toArray(String[][]::new);

        registerc = 1;

        performOperations(instructions);

        return registera;
    }

    private void performOperations(String[][] instructions) {
        int instructionCounter = 0;

        while (instructionCounter < instructions.length) {
            String[] parts = instructions[instructionCounter];

            switch (parts[0]) {
                case "cpy":
                    performCopyOperation(parts[1], parts[2]);
                    instructionCounter++;
                    break;
                case "inc":
                    performIncrementOperation(parts[1]);
                    instructionCounter++;
                    break;
                case "dec":
                    performDecrementOperation(parts[1]);
                    instructionCounter++;
                    break;
                case "jnz":
                    int value = getValue(parts[1]);

                    if (value != 0) {
                        instructionCounter += Integer.parseInt(parts[2]);
                    } else {
                        instructionCounter++;
                    }
                    break;
            }
        }
    }

    private void performDecrementOperation(String register) {
        switch (register) {
            case "a":
                registera--;
                break;
            case "b":
                registerb--;
                break;
            case "c":
                registerc--;
                break;
            case "d":
                registerd--;
                break;
        }
    }

    private void performIncrementOperation(String register) {
        switch (register) {
            case "a":
                registera++;
                break;
            case "b":
                registerb++;
                break;
            case "c":
                registerc++;
                break;
            case "d":
                registerd++;
                break;
        }
    }

    private void performCopyOperation(String sourceRegister, String targetRegister) {
        int value = getValue(sourceRegister);
        switch (targetRegister) {
            case "a":
                registera = value;
                break;
            case "b":
                registerb = value;
                break;
            case "c":
                registerc = value;
                break;
            case "d":
                registerd = value;
                break;
        }
    }

    private int getValue(String register) {
        int value = 0;

        if (Character.isDigit(register.charAt(0))) {
            value = Integer.parseInt(register);
        } else {
            switch (register) {
                case "a":
                    value = registera;
                    break;
                case "b":
                    value = registerb;
                    break;
                case "c":
                    value = registerc;
                    break;
                case "d":
                    value = registerd;
                    break;
            }
        }
        return value;
    }
}
