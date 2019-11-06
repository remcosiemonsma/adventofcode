package nl.remcoder.adventofcode.day19;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Part1 {
    private static Map<String, Opcode> opcodes = new HashMap<>();

    private static Opcode addr = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] + registers[opcode[1]];
    private static Opcode addi = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] + opcode[1];
    private static Opcode mulr = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] * registers[opcode[1]];
    private static Opcode muli = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] * opcode[1];
    private static Opcode banr = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] & registers[opcode[1]];
    private static Opcode bani = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] & opcode[1];
    private static Opcode borr = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] | registers[opcode[1]];
    private static Opcode bori = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] | opcode[1];
    private static Opcode setr = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]];
    private static Opcode seti = (registers, opcode) -> registers[opcode[2]] = opcode[0];
    private static Opcode gtir = (registers, opcode) -> registers[opcode[2]] = opcode[0] > registers[opcode[1]] ? 1 : 0;
    private static Opcode gtri = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] > opcode[1] ? 1 : 0;
    private static Opcode gtrr = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] > registers[opcode[1]] ? 1 : 0;
    private static Opcode eqir = (registers, opcode) -> registers[opcode[2]] = opcode[0] == registers[opcode[1]] ? 1 : 0;
    private static Opcode eqri = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] == opcode[1] ? 1 : 0;
    private static Opcode eqrr = (registers, opcode) -> registers[opcode[2]] = registers[opcode[0]] == registers[opcode[1]] ? 1 : 0;

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

    public static void main(String[] args) throws Exception {
        String instructionPointerString = Files.lines(Paths.get(ClassLoader.getSystemResource("day19/input").toURI()))
                                               .findFirst()
                                               .orElseThrow(AssertionError::new);

        int instructionPointer = Integer.parseInt(instructionPointerString.substring(4));

        List<Operation> operations = Files.lines(Paths.get(ClassLoader.getSystemResource("day19/input").toURI()))
                                      .skip(1)
                                      .map(s -> s.split(" "))
                                      .map(Part1::parseStringArrayToOperation)
                                      .collect(Collectors.toList());

        int[] registers = new int[6];

        Arrays.fill(registers, 0);

        while(registers[instructionPointer] >= 0 && registers[instructionPointer] < operations.size()) {
            operations.get(registers[instructionPointer]).doOp(registers);

            registers[instructionPointer]++;
        }

        System.out.println(registers[0]);
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

    @FunctionalInterface
    private interface Opcode {
        void doOp(int[] registers, int[] opcode);
    }

    private static Operation parseStringArrayToOperation(String[] opcodeStrings) {
        return new Operation(opcodes.get(opcodeStrings[0]), new int[]{Integer.parseInt(opcodeStrings[1]),
                                                                      Integer.parseInt(opcodeStrings[2]),
                                                                      Integer.parseInt(opcodeStrings[3])});
    }
}
