package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Day5 {
    public long handlePart1(Stream<String> input) throws Exception {
        String line = input.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        BlockingQueue<Long> inputState = new LinkedBlockingQueue<>();
        BlockingQueue<Long> outputState = new LinkedBlockingQueue<>();

        inputState.put(1L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, inputState, outputState);

        intCodeComputer.runProgram();

        long output = 0;

        while (!outputState.isEmpty()) {
            output = outputState.take();
        }

        return output;
    }

    public long handlePart2(Stream<String> input) throws Exception {
        String line = input.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                              .mapToLong(Long::parseLong)
                              .toArray();

        BlockingQueue<Long> inputState = new LinkedBlockingQueue<>();
        BlockingQueue<Long> outputState = new LinkedBlockingQueue<>();

        inputState.put(5L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, inputState, outputState);

        intCodeComputer.runProgram();

        long output = 0;

        while (!outputState.isEmpty()) {
            output = outputState.take();
        }

        return output;
    }
}