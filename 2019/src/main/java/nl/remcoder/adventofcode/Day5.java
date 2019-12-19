package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Day5 {
    public int handlePart1(Stream<String> input) throws Exception {
        String line = input.findFirst().orElseThrow(AssertionError::new);

        int[] opcodes = Arrays.stream(line.split(","))
                              .mapToInt(Integer::parseInt)
                              .toArray();

        BlockingQueue<Integer> inputState = new LinkedBlockingQueue<>();
        BlockingQueue<Integer> outputState = new LinkedBlockingQueue<>();

        inputState.put(1);

        IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, inputState, outputState);

        intCodeComputer.runProgram();

        int output = 0;

        while (!outputState.isEmpty()) {
            output = outputState.take();
        }

        return output;
    }

    public int handlePart2(Stream<String> input) throws Exception {
        String line = input.findFirst().orElseThrow(AssertionError::new);

        int[] opcodes = Arrays.stream(line.split(","))
                              .mapToInt(Integer::parseInt)
                              .toArray();

        BlockingQueue<Integer> inputState = new LinkedBlockingQueue<>();
        BlockingQueue<Integer> outputState = new LinkedBlockingQueue<>();

        inputState.put(5);

        IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, inputState, outputState);

        intCodeComputer.runProgram();

        int output = 0;

        while (!outputState.isEmpty()) {
            output = outputState.take();
        }

        return output;
    }
}