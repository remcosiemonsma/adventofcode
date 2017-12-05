package nl.remcoder.adventofcode.day5;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day5/input").toURI()));

        int[] instructions = new int[lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            instructions[i] = Integer.parseInt(lines.get(i));
        }

        int iterations = 0;
        int pc = 0;

        while (0 <= pc && pc < instructions.length) {
            int instruction = instructions[pc];
            instructions[pc]++;
            pc += instruction;
            iterations++;
        }

        System.out.println(iterations);
    }
}
