package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day5 {
    public int handlePart1(Stream<String> input) {
        String line = input.findFirst().orElseThrow(AssertionError::new);

        int[] opcodes = Arrays.stream(line.split(","))
                              .mapToInt(Integer::parseInt)
                              .toArray();

        SharedState inputState = new SharedState();
        SharedState outputState = new SharedState();

        inputState.writeValue(1);

        IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, inputState, outputState);

        intCodeComputer.runProgram();

        int output = 0;

        while (!outputState.isEmpty()) {
            output = outputState.readNextValue();
        }

        return output;
    }

    public int handlePart2(Stream<String> input) {
        String line = input.findFirst().orElseThrow(AssertionError::new);

        int[] opcodes = Arrays.stream(line.split(","))
                              .mapToInt(Integer::parseInt)
                              .toArray();

        SharedState inputState = new SharedState();
        SharedState outputState = new SharedState();

        inputState.writeValue(5);

        IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, inputState, outputState);

        intCodeComputer.runProgram();

        int output = 0;

        while (!outputState.isEmpty()) {
            output = outputState.readNextValue();
        }

        return output;
    }
}