package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day19 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        List<String> lines = input.toList();

        String instructionPointerString = lines.get(0);

        int instructionPointer = Integer.parseInt(instructionPointerString.substring(4));

        List<Operation> operations = lines.stream()
                                          .skip(1)
                                          .map(s -> s.split(" "))
                                          .map(this::parseStringArrayToOperation)
                                          .toList();

        int[] registers = new int[6];

        Arrays.fill(registers, 0);

        while (registers[instructionPointer] >= 0 && registers[instructionPointer] < operations.size()) {
            operations.get(registers[instructionPointer]).doOp(registers);

            registers[instructionPointer]++;
        }

        return registers[0];
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        int number = 10551296;

        int result = 0;

        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                result += i;
            }
        }
        return result;
    }

    private static final Map<String, Opcode> opcodes = new HashMap<>();

    private static final Opcode addr =
            (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] + registers[opcode[1]];
    private static final Opcode addi = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] + opcode[1];
    private static final Opcode mulr =
            (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] * registers[opcode[1]];
    private static final Opcode muli = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] * opcode[1];
    private static final Opcode banr =
            (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] & registers[opcode[1]];
    private static final Opcode bani = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] & opcode[1];
    private static final Opcode borr =
            (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] | registers[opcode[1]];
    private static final Opcode bori = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] | opcode[1];
    private static final Opcode setr = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]];
    private static final Opcode seti = (registers, opcode) -> registers[opcode[2]] = opcode[0];
    private static final Opcode
            gtir = (registers, opcode) -> registers[opcode[2]] = opcode[0] > registers[opcode[1]] ? 1 : 0;
    private static final Opcode
            gtri = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] > opcode[1] ? 1 : 0;
    private static final Opcode
            gtrr = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] > registers[opcode[1]] ? 1 : 0;
    private static final Opcode
            eqir = (registers, opcode) -> registers[opcode[2]] = opcode[0] == registers[opcode[1]] ? 1 : 0;
    private static final Opcode
            eqri = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] == opcode[1] ? 1 : 0;
    private static final Opcode
            eqrr = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] == registers[opcode[1]] ? 1 : 0;

    static {
        opcodes.put("addr", addr);
        opcodes.put("addi", addi);
        opcodes.put("mulr", mulr);
        opcodes.put("muli", muli);
        opcodes.put("banr", banr);
        opcodes.put("bani", bani);
        opcodes.put("borr", borr);
        opcodes.put("bori", bori);
        opcodes.put("setr", setr);
        opcodes.put("seti", seti);
        opcodes.put("gtir", gtir);
        opcodes.put("gtri", gtri);
        opcodes.put("gtrr", gtrr);
        opcodes.put("eqir", eqir);
        opcodes.put("eqri", eqri);
        opcodes.put("eqrr", eqrr);
    }

    private Operation parseStringArrayToOperation(String[] opcodeStrings) {
        return new Operation(opcodes.get(opcodeStrings[0]), new int[] {Integer.parseInt(opcodeStrings[1]),
                                                                       Integer.parseInt(opcodeStrings[2]),
                                                                       Integer.parseInt(opcodeStrings[3])});
    }

    @FunctionalInterface
    private interface Opcode {
        void doOp(int[] registers, int[] opcode);

    }

    private static class Operation {
        final Opcode opcode;
        final int[] parameters;

        private Operation(Opcode opcode, int[] parameters) {
            this.opcode = opcode;
            this.parameters = parameters;
        }

        private void doOp(int[] registers) {
            opcode.doOp(registers, parameters);
        }
    }
}
