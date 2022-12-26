package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.asembunny.CPU;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day23 implements AdventOfCodeSolution<Integer> {

    @Override
    public Integer handlePart1(Stream<String> input) {
        var instructions = input.map(s -> s.split(" ")).toArray(String[][]::new);
        
        CPU cpu = new CPU();
        
        cpu.setRegistera(7);

        cpu.performOperations(instructions);

        return cpu.getRegistera();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var instructions = input.map(s -> s.split(" ")).toArray(String[][]::new);

        CPU cpu = new CPU();

        cpu.setRegistera(12);

        cpu.performOperations(instructions);

        return cpu.getRegistera();
    }
}
