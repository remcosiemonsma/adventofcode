package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day5 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var instructions = input.mapToInt(Integer::parseInt).toArray();

        var iterations = 0;
        var pc = 0;

        while (0 <= pc && pc < instructions.length) {
            var instruction = instructions[pc];
            instructions[pc]++;
            pc += instruction;
            iterations++;
        }

        return iterations;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var instructions = input.mapToInt(Integer::parseInt).toArray();

        var iterations = 0;
        var pc = 0;

        while (0 <= pc && pc < instructions.length) {
            var instruction = instructions[pc];

            if (instruction >= 3) {
                instructions[pc]--;
            } else {
                instructions[pc]++;
            }
            pc += instruction;
            iterations++;
        }

        return iterations;
    }
}
