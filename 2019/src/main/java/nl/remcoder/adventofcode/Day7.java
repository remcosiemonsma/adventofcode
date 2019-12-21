package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Day7 {
    public long handlePart1(Stream<String> input) throws Exception {
        String line = input.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        long highestSignal = Integer.MIN_VALUE;

        for (long input1 = 0; input1 <= 4; input1++) {
            for (long input2 = 0; input2 <= 4; input2++) {
                if (input2 == input1) {
                    continue;
                }

                for (long input3 = 0; input3 <= 4; input3++) {
                    if (input3 == input1 || input3 == input2) {
                        continue;
                    }

                    for (long input4 = 0; input4 <= 4; input4++) {
                        if (input4 == input1 || input4 == input2 || input4 == input3) {
                            continue;
                        }

                        for (long input5 = 0; input5 <= 4; input5++) {
                            if (input5 == input1 || input5 == input2 || input5 == input3 || input5 == input4) {
                                continue;
                            }

                            BlockingQueue<Long> inputState = new LinkedBlockingQueue<>();
                            BlockingQueue<Long> output1 = new LinkedBlockingQueue<>();
                            BlockingQueue<Long> output2 = new LinkedBlockingQueue<>();
                            BlockingQueue<Long> output3 = new LinkedBlockingQueue<>();
                            BlockingQueue<Long> output4 = new LinkedBlockingQueue<>();
                            BlockingQueue<Long> output5 = new LinkedBlockingQueue<>();

                            inputState.put(input1);
                            output1.put(input2);
                            output2.put(input3);
                            output3.put(input4);
                            output4.put(input5);
                            inputState.put(0L);

                            Thread cpu1 = new Thread(new IntCodeComputer(opcodes, inputState, output1));
                            Thread cpu2 = new Thread(new IntCodeComputer(opcodes, output1, output2));
                            Thread cpu3 = new Thread(new IntCodeComputer(opcodes, output2, output3));
                            Thread cpu4 = new Thread(new IntCodeComputer(opcodes, output3, output4));
                            Thread cpu5 = new Thread(new IntCodeComputer(opcodes, output4, output5));

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

    public long handlePart2(Stream<String> input) throws Exception {
        String line = input.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        long highestSignal = Integer.MIN_VALUE;

        for (long input1 = 5; input1 <= 9; input1++) {
            for (long input2 = 5; input2 <= 9; input2++) {
                if (input2 == input1) {
                    continue;
                }

                for (long input3 = 5; input3 <= 9; input3++) {
                    if (input3 == input1 || input3 == input2) {
                        continue;
                    }

                    for (long input4 = 5; input4 <= 9; input4++) {
                        if (input4 == input1 || input4 == input2 || input4 == input3) {
                            continue;
                        }

                        for (long input5 = 5; input5 <= 9; input5++) {
                            if (input5 == input1 || input5 == input2 || input5 == input3 || input5 == input4) {
                                continue;
                            }

                            BlockingQueue<Long> output1 = new LinkedBlockingQueue<>();
                            BlockingQueue<Long> output2 = new LinkedBlockingQueue<>();
                            BlockingQueue<Long> output3 = new LinkedBlockingQueue<>();
                            BlockingQueue<Long> output4 = new LinkedBlockingQueue<>();
                            BlockingQueue<Long> output5 = new LinkedBlockingQueue<>();

                            output5.put(input1);
                            output1.put(input2);
                            output2.put(input3);
                            output3.put(input4);
                            output4.put(input5);
                            output5.put(0L);

                            Thread cpu1 = new Thread(new IntCodeComputer(opcodes, output5, output1));
                            Thread cpu2 = new Thread(new IntCodeComputer(opcodes, output1, output2));
                            Thread cpu3 = new Thread(new IntCodeComputer(opcodes, output2, output3));
                            Thread cpu4 = new Thread(new IntCodeComputer(opcodes, output3, output4));
                            Thread cpu5 = new Thread(new IntCodeComputer(opcodes, output4, output5));

                            cpu1.start();
                            cpu2.start();
                            cpu3.start();
                            cpu4.start();
                            cpu5.start();

                            cpu5.join();

                            if (output5.isEmpty()) {
                                return -1;
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
