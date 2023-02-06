package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;
import nl.remcoder.adventofcode.intcodecomputer.ProducingQueue;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Day21 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) throws Exception {
        var opcodes = input.flatMap(s -> Arrays.stream(s.split(",")))
                           .mapToLong(Long::parseLong)
                           .toArray();
        
        String program = """
                         OR A J
                         AND C J
                         NOT J J
                         AND D J
                         WALK
                         """;

        Queue<Long> chars = new ArrayDeque<>();

        for (char c : program.toCharArray()) {
            chars.add((long) c);
        }

        var outputState = new LinkedBlockingQueue<Long>();
        var inputState = new ProducingQueue(chars::remove);
        
        var intcodeComputer = new IntCodeComputer(opcodes, inputState, outputState);
        
        intcodeComputer.runProgram();

        long damage = 0;
        while (!outputState.isEmpty()) {
            damage = outputState.take();
        }

        return damage;
    }

    @Override
    public Long handlePart2(Stream<String> input) throws Exception {
        var opcodes = input.flatMap(s -> Arrays.stream(s.split(",")))
                           .mapToLong(Long::parseLong)
                           .toArray();

        String program = """
                         OR B J
                         AND C J
                         NOT J J
                         AND D J
                         AND H J
                         NOT A T
                         OR T J
                         RUN
                         """;

        Queue<Long> chars = new ArrayDeque<>();

        for (char c : program.toCharArray()) {
            chars.add((long) c);
        }

        var outputState = new LinkedBlockingQueue<Long>();
        var inputState = new ProducingQueue(chars::remove);

        var intcodeComputer = new IntCodeComputer(opcodes, inputState, outputState);

        intcodeComputer.runProgram();

        long damage = 0;
        while (!outputState.isEmpty()) {
            damage = outputState.take();
        }

        return damage;
    }
}
