package nl.remcoder.adventofcode.day16;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws Exception {
        List<String> data = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day16/input").toURI()));

        int amountOfThreeOpcodeSamples = 0;

        for (int i = 0; i < data.size(); i += 4) {
            if (!data.get(i).startsWith("Before")) {
                break;
            }
            String beforeString = data.get(i);
            String beforeRegisterString = beforeString.substring(9, 19);

            String[] beforeRegisterStrings = beforeRegisterString.split(", ");

            int[] beforeRegister = new int[]{Integer.parseInt(beforeRegisterStrings[0]),
                                             Integer.parseInt(beforeRegisterStrings[1]),
                                             Integer.parseInt(beforeRegisterStrings[2]),
                                             Integer.parseInt(beforeRegisterStrings[3])};

            String afterString = data.get(i + 2);
            String afterRegisterString = afterString.substring(9, 19);

            String[] afterRegisterStrings = afterRegisterString.split(", ");

            int[] afterRegister = new int[]{Integer.parseInt(afterRegisterStrings[0]),
                                            Integer.parseInt(afterRegisterStrings[1]),
                                            Integer.parseInt(afterRegisterStrings[2]),
                                            Integer.parseInt(afterRegisterStrings[3])};

            String opcodeString = data.get(i + 1);

            String[] opcodeStrings = opcodeString.split(" ");

            int[] opcodeData = new int[]{Integer.parseInt(opcodeStrings[0]),
                                         Integer.parseInt(opcodeStrings[1]),
                                         Integer.parseInt(opcodeStrings[2]),
                                         Integer.parseInt(opcodeStrings[3])};

            int amountOfPossibleOpCodes = 0;

            int storeRegister = opcodeData[3];

            int addr = beforeRegister[opcodeData[1]] + beforeRegister[opcodeData[2]];
            int addi = beforeRegister[opcodeData[1]] + opcodeData[2];
            int mulr = beforeRegister[opcodeData[1]] * beforeRegister[opcodeData[2]];
            int muli = beforeRegister[opcodeData[1]] * opcodeData[2];
            int banr = beforeRegister[opcodeData[1]] & beforeRegister[opcodeData[2]];
            int bani = beforeRegister[opcodeData[1]] & opcodeData[2];
            int borr = beforeRegister[opcodeData[1]] | beforeRegister[opcodeData[2]];
            int bori = beforeRegister[opcodeData[1]] | opcodeData[2];
            int setr = beforeRegister[opcodeData[1]];
            int seti = opcodeData[1];
            int gtir = opcodeData[1] > beforeRegister[opcodeData[2]] ? 1 : 0;
            int gtri = beforeRegister[opcodeData[1]] > opcodeData[2] ? 1 : 0;
            int gtrr = beforeRegister[opcodeData[1]] > beforeRegister[opcodeData[2]] ? 1 : 0;
            int eqir = opcodeData[1] == beforeRegister[opcodeData[2]] ? 1 : 0;
            int eqri = beforeRegister[opcodeData[1]] == opcodeData[2] ? 1 : 0;
            int eqrr = beforeRegister[opcodeData[1]] == beforeRegister[opcodeData[2]] ? 1 : 0;

            int[] opcodeResults = new int[]{addr, addi, mulr, muli, banr, bani, borr, bori, setr, seti, gtir, gtri,
                                            gtrr, eqir, eqri, eqrr};

            for (int opcodeResult : opcodeResults) {
                if (afterRegister[storeRegister] == opcodeResult) {
                    amountOfPossibleOpCodes++;
                }
            }

            if (amountOfPossibleOpCodes >= 3) {
                amountOfThreeOpcodeSamples++;
            }
        }

        System.out.println(amountOfThreeOpcodeSamples);
    }
}
