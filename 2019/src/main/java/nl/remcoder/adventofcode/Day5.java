package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.ConsumingQueue;
import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;
import nl.remcoder.adventofcode.intcodecomputer.ProducingQueue;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day5 implements AdventOfCodeSolution<Long> {
    Long output;
    Long input;

    @Override
    public Long handlePart1(Stream<String> input) {
        var line = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        var opcodes = Arrays.stream(line.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();

        var inputState = new ProducingQueue(this::provideInput);
        var outputState = new ConsumingQueue(this::procesOutput);

        this.input = 1L;

        var intCodeComputer = new IntCodeComputer(opcodes, inputState, outputState);

        intCodeComputer.runProgram();

        return output;
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var line = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        var opcodes = Arrays.stream(line.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();

        var inputState = new ProducingQueue(this::provideInput);
        var outputState = new ConsumingQueue(this::procesOutput);

        this.input = 5L;

        var intCodeComputer = new IntCodeComputer(opcodes, inputState, outputState);

        intCodeComputer.runProgram();

        return output;
    }

    private Long provideInput() {
        return input;
    }

    private void procesOutput(Long output) {
        this.output = output;
    }
}