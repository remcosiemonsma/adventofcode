package nl.remcoder.adventofcode.day16;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Part2 {
    public static void main(String[] args) throws Exception {
        List<String> data = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day16/input").toURI()));

        Map<Integer, List<Opcode>> possibleOpcodes = new HashMap<>();

        for (int i = 0; i < 16; i++) {
            ArrayList<Opcode> opcodes = new ArrayList<>();
            opcodes.add(addr);
            opcodes.add(addi);
            opcodes.add(mulr);
            opcodes.add(muli);
            opcodes.add(banr);
            opcodes.add(bani);
            opcodes.add(borr);
            opcodes.add(bori);
            opcodes.add(setr);
            opcodes.add(seti);
            opcodes.add(gtir);
            opcodes.add(gtri);
            opcodes.add(gtrr);
            opcodes.add(eqir);
            opcodes.add(eqri);
            opcodes.add(eqrr);
            possibleOpcodes.put(i, opcodes);
        }

        int sampleLength = 0;

        for (; sampleLength < data.size(); sampleLength += 4) {
            if (!data.get(sampleLength).startsWith("Before")) {
                break;
            }
            String beforeString = data.get(sampleLength);
            int[] beforeRegister = parseStringToRegister(beforeString);

            String afterString = data.get(sampleLength + 2);
            int[] afterRegister = parseStringToRegister(afterString);

            String opcodeString = data.get(sampleLength + 1);

            String[] opcodeStrings = opcodeString.split(" ");

            int[] opcodeData = parseStringArrayToIntArray(opcodeStrings);

            int storeRegister = opcodeData[3];

            List<Opcode> opcodes = possibleOpcodes.get(opcodeData[0]);

            List<Opcode> invalidOpcodes = new ArrayList<>();

            for (Opcode opcode : opcodes) {
                int result = opcode.doOp(beforeRegister, opcodeData);

                if (result != afterRegister[storeRegister]) {
                    invalidOpcodes.add(opcode);
                }
            }

            opcodes.removeAll(invalidOpcodes);
        }

        while (possibleOpcodes.values().stream().anyMatch(opcodes -> opcodes.size() > 1)) {
            possibleOpcodes.values()
                           .stream()
                           .filter(opcodes -> opcodes.size() == 1)
                           .forEach(opcodes -> possibleOpcodes.values()
                                                              .stream()
                                                              .filter(opcodes1 -> opcodes != opcodes1)
                                                              .forEach(opcodes1 -> opcodes1.remove(opcodes.get(0))));
        }

        Map<Integer, Opcode> opcodes = possibleOpcodes.entrySet()
                                                      .stream()
                                                      .collect(Collectors.toMap(Map.Entry::getKey,
                                                                                entry -> entry.getValue().get(0)));

        int[] register = new int[4];
        Arrays.fill(register, 0);

        data.stream()
            .skip(sampleLength + 2)
            .map(s -> s.split(" "))
            .map(s -> new int[]{Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3])})
            .forEach(ints -> {
                Opcode opcode = opcodes.get(ints[0]);
                int i = opcode.doOp(register, ints);
                register[ints[3]] = i;
            });

        System.out.println(Arrays.toString(register));
    }

    private static int[] parseStringArrayToIntArray(String[] opcodeStrings) {
        return new int[]{Integer.parseInt(opcodeStrings[0]),
                                             Integer.parseInt(opcodeStrings[1]),
                                             Integer.parseInt(opcodeStrings[2]),
                                             Integer.parseInt(opcodeStrings[3])};
    }

    private static int[] parseStringToRegister(String string) {
        String registerPartOfString = string.substring(9, 19);

        String[] stringRegister = registerPartOfString.split(", ");

        return parseStringArrayToIntArray(stringRegister);
    }

    @FunctionalInterface
    private interface Opcode {
        int doOp(int[] registers, int[] opcode);
    }

    private static Opcode addr = (registers, opcode) -> registers[opcode[1]] + registers[opcode[2]];
    private static Opcode addi = (registers, opcode) -> registers[opcode[1]] + opcode[2];
    private static Opcode mulr = (registers, opcode) -> registers[opcode[1]] * registers[opcode[2]];
    private static Opcode muli = (registers, opcode) -> registers[opcode[1]] * opcode[2];
    private static Opcode banr = (registers, opcode) -> registers[opcode[1]] & registers[opcode[2]];
    private static Opcode bani = (registers, opcode) -> registers[opcode[1]] & opcode[2];
    private static Opcode borr = (registers, opcode) -> registers[opcode[1]] | registers[opcode[2]];
    private static Opcode bori = (registers, opcode) -> registers[opcode[1]] | opcode[2];
    private static Opcode setr = (registers, opcode) -> registers[opcode[1]];
    private static Opcode seti = (registers, opcode) -> opcode[1];
    private static Opcode gtir = (registers, opcode) -> opcode[1] > registers[opcode[2]] ? 1 : 0;
    private static Opcode gtri = (registers, opcode) -> registers[opcode[1]] > opcode[2] ? 1 : 0;
    private static Opcode gtrr = (registers, opcode) -> registers[opcode[1]] > registers[opcode[2]] ? 1 : 0;
    private static Opcode eqir = (registers, opcode) -> opcode[1] == registers[opcode[2]] ? 1 : 0;
    private static Opcode eqri = (registers, opcode) -> registers[opcode[1]] == opcode[2] ? 1 : 0;
    private static Opcode eqrr = (registers, opcode) -> registers[opcode[1]] == registers[opcode[2]] ? 1 : 0;
}
