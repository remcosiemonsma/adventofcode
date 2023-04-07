package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.asembunny.CPU;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day12 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var instructions = input.map(s -> s.split(" ")).toArray(String[][]::new);

        var cpu = new CPU();
        
        cpu.performOperations(instructions);

        return cpu.getRegistera();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var instructions = input.map(s -> s.split(" ")).toArray(String[][]::new);

        var cpu = new CPU();
        
        cpu.setRegisterc(1);

        cpu.performOperations(instructions);

        return cpu.getRegistera();
    }
}
