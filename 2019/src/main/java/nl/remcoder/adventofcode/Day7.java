package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Day7 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) throws Exception {
        var line = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        var opcodes = Arrays.stream(line.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();

        var highestSignal = Long.MIN_VALUE;

        for (var input1 = 0L; input1 <= 4; input1++) {
            for (var input2 = 0L; input2 <= 4; input2++) {
                if (input2 == input1) {
                    continue;
                }

                for (var input3 = 0L; input3 <= 4; input3++) {
                    if (input3 == input1 || input3 == input2) {
                        continue;
                    }

                    for (var input4 = 0L; input4 <= 4; input4++) {
                        if (input4 == input1 || input4 == input2 || input4 == input3) {
                            continue;
                        }

                        for (var input5 = 0L; input5 <= 4; input5++) {
                            if (input5 == input1 || input5 == input2 || input5 == input3 || input5 == input4) {
                                continue;
                            }

                            var inputState = new LinkedBlockingQueue<Long>();
                            var output1 = new LinkedBlockingQueue<Long>();
                            var output2 = new LinkedBlockingQueue<Long>();
                            var output3 = new LinkedBlockingQueue<Long>();
                            var output4 = new LinkedBlockingQueue<Long>();
                            var output5 = new LinkedBlockingQueue<Long>();

                            inputState.put(input1);
                            output1.put(input2);
                            output2.put(input3);
                            output3.put(input4);
                            output4.put(input5);
                            inputState.put(0L);

                            var cpu1 = new Thread(new IntCodeComputer(opcodes, inputState, output1));
                            var cpu2 = new Thread(new IntCodeComputer(opcodes, output1, output2));
                            var cpu3 = new Thread(new IntCodeComputer(opcodes, output2, output3));
                            var cpu4 = new Thread(new IntCodeComputer(opcodes, output3, output4));
                            var cpu5 = new Thread(new IntCodeComputer(opcodes, output4, output5));

                            cpu1.start();
                            cpu2.start();
                            cpu3.start();
                            cpu4.start();
                            cpu5.start();

                            cpu5.join();

                            long result = output5.take();

                            if (result > highestSignal) {
                                highestSignal = result;
                            }
                        }
                    }
                }
            }
        }
        return highestSignal;
    }

    @Override
    public Long handlePart2(Stream<String> input) throws Exception {
        var line = input.findFirst().orElseThrow(AssertionError::new);

        var opcodes = Arrays.stream(line.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();

        var highestSignal = Long.MIN_VALUE;

        for (var input1 = 5L; input1 <= 9; input1++) {
            for (var input2 = 5L; input2 <= 9; input2++) {
                if (input2 == input1) {
                    continue;
                }

                for (var input3 = 5L; input3 <= 9; input3++) {
                    if (input3 == input1 || input3 == input2) {
                        continue;
                    }

                    for (var input4 = 5L; input4 <= 9; input4++) {
                        if (input4 == input1 || input4 == input2 || input4 == input3) {
                            continue;
                        }

                        for (var input5 = 5L; input5 <= 9; input5++) {
                            if (input5 == input1 || input5 == input2 || input5 == input3 || input5 == input4) {
                                continue;
                            }

                            var output1 = new LinkedBlockingQueue<Long>();
                            var output2 = new LinkedBlockingQueue<Long>();
                            var output3 = new LinkedBlockingQueue<Long>();
                            var output4 = new LinkedBlockingQueue<Long>();
                            var output5 = new LinkedBlockingQueue<Long>();

                            output5.put(input1);
                            output1.put(input2);
                            output2.put(input3);
                            output3.put(input4);
                            output4.put(input5);
                            output5.put(0L);

                            var cpu1 = new Thread(new IntCodeComputer(opcodes, output5, output1));
                            var cpu2 = new Thread(new IntCodeComputer(opcodes, output1, output2));
                            var cpu3 = new Thread(new IntCodeComputer(opcodes, output2, output3));
                            var cpu4 = new Thread(new IntCodeComputer(opcodes, output3, output4));
                            var cpu5 = new Thread(new IntCodeComputer(opcodes, output4, output5));

                            cpu1.start();
                            cpu2.start();
                            cpu3.start();
                            cpu4.start();
                            cpu5.start();

                            cpu5.join();

                            if (output5.isEmpty()) {
                                return -1L;
                            }

                            long result = output5.take();

                            if (result > highestSignal) {
                                highestSignal = result;
                            }
                        }
                    }
                }
            }
        }

        return highestSignal;
    }
}
