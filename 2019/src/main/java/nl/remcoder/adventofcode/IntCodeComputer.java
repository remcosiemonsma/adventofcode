package nl.remcoder.adventofcode;

import java.util.Arrays;

public class IntCodeComputer implements Runnable {
    private final SharedState input;
    private final SharedState output;
    private int[] opcodes;
    private int opcodeCounter;

    public IntCodeComputer(int[] opcodes, SharedState input, SharedState output) {
        this.opcodes = opcodes;
        this.input = input;
        this.output = output;
        opcodeCounter = 0;
    }

    @Override
    public void run() {
        runProgram();
    }

    public void runProgram() {
        while (opcodes[opcodeCounter] != 99) {
            switch (opcodes[opcodeCounter] % 100) {
                case 1:
                    performSummation();
                    break;
                case 2:
                    performMultiplication();
                    break;
                case 3:
                    readInput();
                    break;
                case 4:
                    writeOutput();
                    break;
                case 5:
                    jumpIfTrue();
                    break;
                case 6:
                    jumpIfFalse();
                    break;
                case 7:
                    testLessThan();
                    break;
                case 8:
                    testEquals();
                    break;
                default:
                    throw new AssertionError(String.format("Invalid operation %d!", opcodes[opcodeCounter]));
            }
        }
    }

    private void testEquals() {
        int opcode = opcodes[opcodeCounter++];
        Mode value1Mode = (opcode % 1000) / 100 == 1 ? Mode.PARAMETER : Mode.POSITION;
        Mode value2Mode = (opcode % 10000) / 1000 == 1 ? Mode.PARAMETER : Mode.POSITION;

        int value1 = readValue(value1Mode);
        int value2 = readValue(value2Mode);
        int target = readValue(Mode.PARAMETER);

        if (value1 == value2) {
            opcodes[target] = 1;
        } else {
            opcodes[target] = 0;
        }
    }

    private void testLessThan() {
        int opcode = opcodes[opcodeCounter++];
        Mode value1Mode = (opcode % 1000) / 100 == 1 ? Mode.PARAMETER : Mode.POSITION;
        Mode value2Mode = (opcode % 10000) / 1000 == 1 ? Mode.PARAMETER : Mode.POSITION;

        int value1 = readValue(value1Mode);
        int value2 = readValue(value2Mode);
        int target = readValue(Mode.PARAMETER);

        if (value1 < value2) {
            opcodes[target] = 1;
        } else {
            opcodes[target] = 0;
        }
    }

    private void jumpIfFalse() {
        int opcode = opcodes[opcodeCounter++];
        Mode value1Mode = (opcode % 1000) / 100 == 1 ? Mode.PARAMETER : Mode.POSITION;
        Mode value2Mode = (opcode % 10000) / 1000 == 1 ? Mode.PARAMETER : Mode.POSITION;

        int test = readValue(value1Mode);
        int target = readValue(value2Mode);

        if (test == 0) {
            opcodeCounter = target;
        }
    }

    private void jumpIfTrue() {
        int opcode = opcodes[opcodeCounter++];
        Mode value1Mode = (opcode % 1000) / 100 == 1 ? Mode.PARAMETER : Mode.POSITION;
        Mode value2Mode = (opcode % 10000) / 1000 == 1 ? Mode.PARAMETER : Mode.POSITION;

        int test = readValue(value1Mode);
        int target = readValue(value2Mode);

        if (test != 0) {
            opcodeCounter = target;
        }
    }

    private void writeOutput() {
        int opcode = opcodes[opcodeCounter++];
        Mode value1Mode = (opcode % 1000) / 100 == 1 ? Mode.PARAMETER : Mode.POSITION;

        int value = readValue(value1Mode);

        output.writeValue(value);
    }

    private void readInput() {
        int inputValue = input.readNextValue();
        int target = opcodes[++opcodeCounter];
        opcodes[target] = inputValue;
        opcodeCounter++;
    }

    private void performMultiplication() {
        int opcode = opcodes[opcodeCounter++];
        Mode value1Mode = (opcode % 1000) / 100 == 1 ? Mode.PARAMETER : Mode.POSITION;
        Mode value2Mode = (opcode % 10000) / 1000 == 1 ? Mode.PARAMETER : Mode.POSITION;

        int multiply1 = readValue(value1Mode);
        int multiply2 = readValue(value2Mode);

        performMultiplyOperation(multiply1, multiply2, opcodes[opcodeCounter++]);
    }

    private void performSummation() {
        int opcode = opcodes[opcodeCounter++];
        Mode value1Mode = (opcode % 1000) / 100 == 1 ? Mode.PARAMETER : Mode.POSITION;
        Mode value2Mode = (opcode % 10000) / 1000 == 1 ? Mode.PARAMETER : Mode.POSITION;

        int sum1 = readValue(value1Mode);
        int sum2 = readValue(value2Mode);

        performAddOperation(sum1, sum2, opcodes[opcodeCounter++]);
    }

    private int readValue(Mode value1Mode) {
        int location = 0;

        switch (value1Mode) {
            case POSITION:
                location = opcodes[opcodeCounter++];
                break;
            case PARAMETER:
                location = opcodeCounter++;
                break;
        }

        return opcodes[location];
    }

    private void performAddOperation(int value1, int value2, int targetLocation) {
        opcodes[targetLocation] = value1 + value2;
    }

    private void performMultiplyOperation(int value1, int value2, int targetLocation) {
        opcodes[targetLocation] = value1 * value2;
    }

    public int retrieveValueFromPosition(int position) {
        return opcodes[position];
    }

    private enum Mode {
        PARAMETER,
        POSITION
    }
}
