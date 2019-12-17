package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day7 {
    public int handlePart1(Stream<String> input) {
        String line = input.findFirst().orElseThrow(AssertionError::new);

        int[] opcodes = Arrays.stream(line.split(","))
                              .mapToInt(Integer::parseInt)
                              .toArray();

        int highestSignal = Integer.MIN_VALUE;

        for (int input1 = 0; input1 <= 4; input1++) {
            for (int input2 = 0; input2 <= 4; input2++) {
                if (input2 == input1) {
                    continue;
                }

                for (int input3 = 0; input3 <= 4; input3++) {
                    if (input3 == input1 || input3 == input2) {
                        continue;
                    }

                    for (int input4 = 0; input4 <= 4; input4++) {
                        if (input4 == input1 || input4 == input2 || input4 == input3) {
                            continue;
                        }

                        for (int input5 = 0; input5 <= 4; input5++) {
                            if (input5 == input1 || input5 == input2 || input5 == input3 || input5 == input4) {
                                continue;
                            }

                            SharedState inputState = new SharedState();
                            SharedState output1 = new SharedState();
                            SharedState output2 = new SharedState();
                            SharedState output3 = new SharedState();
                            SharedState output4 = new SharedState();
                            SharedState output5 = new SharedState();

                            inputState.writeValue(input1);
                            output1.writeValue(input2);
                            output2.writeValue(input3);
                            output3.writeValue(input4);
                            output4.writeValue(input5);
                            inputState.writeValue(0);

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

                            int result = output5.readNextValue();

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
