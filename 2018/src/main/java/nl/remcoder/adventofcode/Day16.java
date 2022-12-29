package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var data = input.toList();

        var amountOfThreeOpcodeSamples = 0;

        for (var i = 0; i < data.size(); i += 4) {
            if (!data.get(i).startsWith("Before")) {
                break;
            }
            var beforeString = data.get(i);
            var beforeRegisterString = beforeString.substring(9, 19);

            var beforeRegisterStrings = beforeRegisterString.split(", ");

            var beforeRegister = new int[] {Integer.parseInt(beforeRegisterStrings[0]),
                                            Integer.parseInt(beforeRegisterStrings[1]),
                                            Integer.parseInt(beforeRegisterStrings[2]),
                                            Integer.parseInt(beforeRegisterStrings[3])};

            var afterString = data.get(i + 2);
            var afterRegisterString = afterString.substring(9, 19);

            var afterRegisterStrings = afterRegisterString.split(", ");

            var afterRegister = new int[] {Integer.parseInt(afterRegisterStrings[0]),
                                           Integer.parseInt(afterRegisterStrings[1]),
                                           Integer.parseInt(afterRegisterStrings[2]),
                                           Integer.parseInt(afterRegisterStrings[3])};

            var opcodeString = data.get(i + 1);

            var opcodeStrings = opcodeString.split(" ");

            var opcodeData = new int[] {Integer.parseInt(opcodeStrings[0]),
                                        Integer.parseInt(opcodeStrings[1]),
                                        Integer.parseInt(opcodeStrings[2]),
                                        Integer.parseInt(opcodeStrings[3])};

            var amountOfPossibleOpCodes = 0;

            var storeRegister = opcodeData[3];

            var addr = beforeRegister[opcodeData[1]] + beforeRegister[opcodeData[2]];
            var addi = beforeRegister[opcodeData[1]] + opcodeData[2];
            var mulr = beforeRegister[opcodeData[1]] * beforeRegister[opcodeData[2]];
            var muli = beforeRegister[opcodeData[1]] * opcodeData[2];
            var banr = beforeRegister[opcodeData[1]] & beforeRegister[opcodeData[2]];
            var bani = beforeRegister[opcodeData[1]] & opcodeData[2];
            var borr = beforeRegister[opcodeData[1]] | beforeRegister[opcodeData[2]];
            var bori = beforeRegister[opcodeData[1]] | opcodeData[2];
            var setr = beforeRegister[opcodeData[1]];
            var seti = opcodeData[1];
            var gtir = opcodeData[1] > beforeRegister[opcodeData[2]] ? 1 : 0;
            var gtri = beforeRegister[opcodeData[1]] > opcodeData[2] ? 1 : 0;
            var gtrr = beforeRegister[opcodeData[1]] > beforeRegister[opcodeData[2]] ? 1 : 0;
            var eqir = opcodeData[1] == beforeRegister[opcodeData[2]] ? 1 : 0;
            var eqri = beforeRegister[opcodeData[1]] == opcodeData[2] ? 1 : 0;
            var eqrr = beforeRegister[opcodeData[1]] == beforeRegister[opcodeData[2]] ? 1 : 0;

            var opcodeResults = new int[] {addr, addi, mulr, muli, banr, bani, borr, bori, setr, seti, gtir, gtri,
                                           gtrr, eqir, eqri, eqrr};

            for (var opcodeResult : opcodeResults) {
                if (afterRegister[storeRegister] == opcodeResult) {
                    amountOfPossibleOpCodes++;
                }
            }

            if (amountOfPossibleOpCodes >= 3) {
                amountOfThreeOpcodeSamples++;
            }
        }

        return amountOfThreeOpcodeSamples;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var data = input.toList();

        var possibleOpcodes = new HashMap<Integer, List<Opcode>>();

        for (var i = 0; i < 16; i++) {
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

        var sampleLength = 0;

        for (; sampleLength < data.size(); sampleLength += 4) {
            if (!data.get(sampleLength).startsWith("Before")) {
                break;
            }
            var beforeString = data.get(sampleLength);
            var beforeRegister = parseStringToRegister(beforeString);

            var afterString = data.get(sampleLength + 2);
            var afterRegister = parseStringToRegister(afterString);

            var opcodeString = data.get(sampleLength + 1);

            var opcodeStrings = opcodeString.split(" ");

            var opcodeData = parseStringArrayToIntArray(opcodeStrings);

            var storeRegister = opcodeData[3];

            var opcodes = possibleOpcodes.get(opcodeData[0]);

            var invalidOpcodes = new ArrayList<Opcode>();

            for (var opcode : opcodes) {
                var result = opcode.doOp(beforeRegister, opcodeData);

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

        var opcodes = possibleOpcodes.entrySet()
                                     .stream()
                                     .collect(Collectors.toMap(Map.Entry::getKey,
                                                               entry -> entry.getValue().get(0)));

        var register = new int[4];
        Arrays.fill(register, 0);

        data.stream()
            .skip(sampleLength + 2)
            .map(s -> s.split(" "))
            .map(s -> new int[] {Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]),
                                 Integer.parseInt(s[3])})
            .forEach(ints -> {
                Opcode opcode = opcodes.get(ints[0]);
                int i = opcode.doOp(register, ints);
                register[ints[3]] = i;
            });

        return register[0];
    }

    private static int[] parseStringArrayToIntArray(String[] opcodeStrings) {
        return new int[] {Integer.parseInt(opcodeStrings[0]),
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

    private static final Opcode addr = (registers, opcode) -> registers[opcode[1]] + registers[opcode[2]];
    private static final Opcode addi = (registers, opcode) -> registers[opcode[1]] + opcode[2];
    private static final Opcode mulr = (registers, opcode) -> registers[opcode[1]] * registers[opcode[2]];
    private static final Opcode muli = (registers, opcode) -> registers[opcode[1]] * opcode[2];
    private static final Opcode banr = (registers, opcode) -> registers[opcode[1]] & registers[opcode[2]];
    private static final Opcode bani = (registers, opcode) -> registers[opcode[1]] & opcode[2];
    private static final Opcode borr = (registers, opcode) -> registers[opcode[1]] | registers[opcode[2]];
    private static final Opcode bori = (registers, opcode) -> registers[opcode[1]] | opcode[2];
    private static final Opcode setr = (registers, opcode) -> registers[opcode[1]];
    private static final Opcode seti = (registers, opcode) -> opcode[1];
    private static final Opcode gtir = (registers, opcode) -> opcode[1] > registers[opcode[2]] ? 1 : 0;
    private static final Opcode gtri = (registers, opcode) -> registers[opcode[1]] > opcode[2] ? 1 : 0;
    private static final Opcode gtrr = (registers, opcode) -> registers[opcode[1]] > registers[opcode[2]] ? 1 : 0;
    private static final Opcode eqir = (registers, opcode) -> opcode[1] == registers[opcode[2]] ? 1 : 0;
    private static final Opcode eqri = (registers, opcode) -> registers[opcode[1]] == opcode[2] ? 1 : 0;
    private static final Opcode eqrr = (registers, opcode) -> registers[opcode[1]] == registers[opcode[2]] ? 1 : 0;
}
