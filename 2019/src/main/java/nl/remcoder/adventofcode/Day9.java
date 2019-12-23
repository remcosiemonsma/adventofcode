package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Day9 {
    public long handlePart1(Stream<String> inputData) throws Exception {
        String line = inputData.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(1L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, input, output);

        intCodeComputer.runProgram();

        return output.take();
    }

    public long handlePart2(Stream<String> inputData) throws Exception {
        String line = inputData.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(2L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, input, output);

        intCodeComputer.runProgram();

        return output.take();
    }
}
