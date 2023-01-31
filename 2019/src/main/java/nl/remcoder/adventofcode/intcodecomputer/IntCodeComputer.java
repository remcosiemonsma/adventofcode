package nl.remcoder.adventofcode.intcodecomputer;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

public class IntCodeComputer implements Runnable {
    private final BlockingQueue<Long> input;
    private final BlockingQueue<Long> output;
    private long[] opcodes;
    private int opcodeCounter;
    private int relativeBase;
    private boolean running = true;

    public IntCodeComputer(long[] opcodes, BlockingQueue<Long> input, BlockingQueue<Long> output) {
        this.opcodes = Arrays.copyOf(opcodes, 1024 * 64);
        this.input = input;
        this.output = output;
        opcodeCounter = 0;
        relativeBase = 0;
    }

    @Override
    public void run() {
        runProgram();
    }

    public void runProgram() {
        while (opcodes[opcodeCounter] != 99 && running) {
            switch ((int) (opcodes[opcodeCounter] % 100)) {
                case 1 -> performSummation();
                case 2 -> performMultiplication();
                case 3 -> readInput();
                case 4 -> writeOutput();
                case 5 -> jumpIfTrue();
                case 6 -> jumpIfFalse();
                case 7 -> testLessThan();
                case 8 -> testEquals();
                case 9 -> adjustRelativeBase();
                default -> throw new AssertionError(String.format("Invalid operation %d!", opcodes[opcodeCounter]));
            }
        }
    }

    private void adjustRelativeBase() {
        int opcode = (int) opcodes[opcodeCounter++];
        Mode value1Mode = determineMode((opcode % 1000) / 100);

        long adjustValue = readValue(value1Mode);

        relativeBase += adjustValue;
    }

    private void testEquals() {
        int opcode = (int) opcodes[opcodeCounter++];
        Mode value1Mode = determineMode((opcode % 1000) / 100);
        Mode value2Mode = determineMode((opcode % 10000) / 1000);
        Mode targetMode = determineMode((opcode % 100000) / 10000);

        long value1 = readValue(value1Mode);
        long value2 = readValue(value2Mode);

        int target = switch (targetMode) {
            case POSITION -> (int) opcodes[opcodeCounter++];
            case RELATIVE -> relativeBase + (int) opcodes[opcodeCounter++];
            case IMMEDIATE -> throw new AssertionError("Immediate Mode not allowed!");
        };

        if (value1 == value2) {
            opcodes[target] = 1;
        } else {
            opcodes[target] = 0;
        }
    }

    private void testLessThan() {
        int opcode = (int) opcodes[opcodeCounter++];
        Mode value1Mode = determineMode((opcode % 1000) / 100);
        Mode value2Mode = determineMode((opcode % 10000) / 1000);
        Mode targetMode = determineMode((opcode % 100000) / 10000);

        long value1 = readValue(value1Mode);
        long value2 = readValue(value2Mode);

        int target = switch (targetMode) {
            case POSITION -> (int) opcodes[opcodeCounter++];
            case RELATIVE -> relativeBase + (int) opcodes[opcodeCounter++];
            case IMMEDIATE -> throw new AssertionError("Immediate Mode not allowed!");
        };

        if (value1 < value2) {
            opcodes[target] = 1;
        } else {
            opcodes[target] = 0;
        }
    }

    private void jumpIfFalse() {
        int opcode = (int) opcodes[opcodeCounter++];
        Mode value1Mode = determineMode((opcode % 1000) / 100);
        Mode value2Mode = determineMode((opcode % 10000) / 1000);

        long test = readValue(value1Mode);
        int target = (int) readValue(value2Mode);

        if (test == 0) {
            opcodeCounter = target;
        }
    }

    private void jumpIfTrue() {
        int opcode = (int) opcodes[opcodeCounter++];
        Mode value1Mode = determineMode((opcode % 1000) / 100);
        Mode value2Mode = determineMode((opcode % 10000) / 1000);

        long test = readValue(value1Mode);
        int target = (int) readValue(value2Mode);

        if (test != 0) {
            opcodeCounter = target;
        }
    }

    private void writeOutput() {
        int opcode = (int) opcodes[opcodeCounter++];
        Mode value1Mode = determineMode((opcode % 1000) / 100);

        long value = readValue(value1Mode);

        try {
            output.put(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void readInput() {
        try {
            long inputValue = input.take();

            int opcode = (int) opcodes[opcodeCounter++];
            Mode targetMode = determineMode((opcode % 1000) / 100);

            int target = switch (targetMode) {
                case POSITION -> (int) opcodes[opcodeCounter++];
                case RELATIVE -> relativeBase + (int) opcodes[opcodeCounter++];
                case IMMEDIATE -> throw new AssertionError("Immediate Mode not allowed!");
            };

            opcodes[target] = inputValue;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void performMultiplication() {
        int opcode = (int) opcodes[opcodeCounter++];
        Mode value1Mode = determineMode((opcode % 1000) / 100);
        Mode value2Mode = determineMode((opcode % 10000) / 1000);
        Mode targetMode = determineMode((opcode % 100000) / 10000);

        long multiply1 = readValue(value1Mode);
        long multiply2 = readValue(value2Mode);

        int target = switch (targetMode) {
            case POSITION -> (int) opcodes[opcodeCounter++];
            case RELATIVE -> relativeBase + (int) opcodes[opcodeCounter++];
            case IMMEDIATE -> throw new AssertionError("Immediate Mode not allowed!");
        };

        performMultiplyOperation(multiply1, multiply2, target);
    }

    private void performSummation() {
        int opcode = (int) opcodes[opcodeCounter++];
        Mode value1Mode = determineMode((opcode % 1000) / 100);
        Mode value2Mode = determineMode((opcode % 10000) / 1000);
        Mode targetMode = determineMode((opcode % 100000) / 10000);

        long sum1 = readValue(value1Mode);
        long sum2 = readValue(value2Mode);

        int target = switch (targetMode) {
            case POSITION -> (int) opcodes[opcodeCounter++];
            case RELATIVE -> relativeBase + (int) opcodes[opcodeCounter++];
            case IMMEDIATE -> throw new AssertionError("Immediate Mode not allowed!");
        };

        performAddOperation(sum1, sum2, target);
    }

    private long readValue(Mode value1Mode) {
        int location = switch (value1Mode) {
            case POSITION -> (int) opcodes[opcodeCounter++];
            case IMMEDIATE -> opcodeCounter++;
            case RELATIVE -> (int) opcodes[opcodeCounter++] + relativeBase;
        };

        return opcodes[location];
    }

    private void performAddOperation(long value1, long value2, int targetLocation) {
        opcodes[targetLocation] = value1 + value2;
    }

    private void performMultiplyOperation(long value1, long value2, int targetLocation) {
        opcodes[targetLocation] = value1 * value2;
    }

    public long retrieveValueFromPosition(int position) {
        return opcodes[position];
    }

    public void stop() {
        running = false;
    }
    
    private Mode determineMode(int parameterMode) {
        return switch (parameterMode) {
            case 0 -> Mode.POSITION;
            case 1 -> Mode.IMMEDIATE;
            case 2 -> Mode.RELATIVE;
            default -> throw new AssertionError(String.format("Invalid parameter mode %d received!", parameterMode));
        };
    }

    private enum Mode {
        POSITION,
        IMMEDIATE,
        RELATIVE
    }
}
