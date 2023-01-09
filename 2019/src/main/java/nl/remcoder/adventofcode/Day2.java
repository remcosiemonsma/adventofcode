package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day2 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var line = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        var opcodes = Arrays.stream(line.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();

        opcodes[1] = 12;
        opcodes[2] = 2;

        var intCodeComputer = new IntCodeComputer(opcodes, null, null);

        intCodeComputer.runProgram();

        return intCodeComputer.retrieveValueFromPosition(0);
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var line = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        var opcodesInput = Arrays.stream(line.split(","))
                                 .mapToLong(Long::parseLong)
                                 .toArray();

        for (var noun = 0L; noun < 100; noun++) {
            for (var verb = 0L; verb < 100; verb++) {
                long[] opcodes = Arrays.copyOf(opcodesInput, opcodesInput.length);

                opcodes[1] = noun;
                opcodes[2] = verb;

                IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, null, null);

                intCodeComputer.runProgram();

                if (intCodeComputer.retrieveValueFromPosition(0) == 19690720) {
                    return (100 * noun) + verb;
                }
            }
        }

        throw new AssertionError();
    }
}