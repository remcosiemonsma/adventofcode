package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Day9 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> inputData) throws Exception {
        var line = inputData.findFirst().orElseThrow(AssertionError::new);

        var opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        var input = new LinkedBlockingQueue<Long>();
        var output = new LinkedBlockingQueue<Long>();

        input.put(1L);

        var intCodeComputer = new IntCodeComputer(opcodes, input, output);

        intCodeComputer.runProgram();

        return output.take();
    }

    @Override
    public Long handlePart2(Stream<String> inputData) throws Exception {
        var line = inputData.findFirst().orElseThrow(AssertionError::new);

        var opcodes = Arrays.stream(line.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();

        var input = new LinkedBlockingQueue<Long>();
        var output = new LinkedBlockingQueue<Long>();

        input.put(2L);

        var intCodeComputer = new IntCodeComputer(opcodes, input, output);

        intCodeComputer.runProgram();

        return output.take();
    }
}
