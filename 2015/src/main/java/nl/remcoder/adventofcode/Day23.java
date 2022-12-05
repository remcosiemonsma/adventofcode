package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day23 {
    private final long[] registers = {0, 0};
    private int counter;
    private Instruction[] instructions;

    public long handlePart1(Stream<String> input) {
        instructions = input.map(this::parseInstruction)
                            .toArray(Instruction[]::new);

        while (counter < instructions.length) {
            performOperation();
            counter++;
            if (registers[0] < 0 || registers[1] < 0) {
                throw new AssertionError("Negative register value!");
            }
        }

        return registers[1];
    }

    public long handlePart2(Stream<String> input) {
        registers[0] = 1;

        return handlePart1(input);
    }

    private Instruction parseInstruction(String line) {
        var parts = line.split(" ");
        if (parts.length == 2) {
            if (parts[0].startsWith("j")) {
                return new Instruction(fromString(parts[0]), '\0', Integer.parseInt(parts[1]));
            } else {
                return new Instruction(fromString(parts[0]), parts[1].charAt(0), 0);
            }
        } else {
            return new Instruction(fromString(parts[0]), parts[1].charAt(0), Integer.parseInt(parts[2]));
        }
    }

    private void performOperation() {
        var instruction = instructions[counter];
        switch (instruction.type) {
            case HALF -> registers[instruction.target - 'a'] = registers[instruction.target - 'a'] / 2;
            case TRIPLE -> registers[instruction.target - 'a'] = registers[instruction.target - 'a'] * 3;
            case INCREMENT -> registers[instruction.target - 'a'] = registers[instruction.target - 'a'] + 1;
            case JUMP -> counter += instruction.value - 1;
            case JUMP_IF_EVEN -> {
                if (registers[instruction.target - 'a'] % 2 == 0) {
                    counter += instruction.value - 1;
                }
            }
            case JUMP_IF_ONE -> {
                if (registers[instruction.target - 'a'] == 1) {
                    counter += instruction.value - 1;
                }
            }
        }
    }

    private record Instruction(Type type, char target, int value) {
    }

    public Type fromString(String value) {
        return switch (value) {
            case "hlf" -> Type.HALF;
            case "tpl" -> Type.TRIPLE;
            case "inc" -> Type.INCREMENT;
            case "jmp" -> Type.JUMP;
            case "jie" -> Type.JUMP_IF_EVEN;
            case "jio" -> Type.JUMP_IF_ONE;
            default -> throw new AssertionError(value + " is not recognized as an operation");
        };
    }

    private enum Type {
        HALF,
        TRIPLE,
        INCREMENT,
        JUMP,
        JUMP_IF_EVEN,
        JUMP_IF_ONE
    }
}
