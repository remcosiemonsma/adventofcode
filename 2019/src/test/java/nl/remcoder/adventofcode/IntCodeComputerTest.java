package nl.remcoder.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

class IntCodeComputerTest {

    @Test
    public void testDay2Part1Case1() throws Exception {
        int[] program = new int[]{1, 0, 0, 0, 99};

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, null);

        intCodeComputer.runProgram();

        assertEquals(2, intCodeComputer.retrieveValueFromPosition(0));
    }

    @Test
    public void testDay2Part1Case2() throws Exception {
        int[] program = new int[]{2, 3, 0, 3, 99};

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, null);

        intCodeComputer.runProgram();

        assertEquals(6, intCodeComputer.retrieveValueFromPosition(3));
    }

    @Test
    public void testDay2Part1Case3() throws Exception {
        int[] program = new int[]{2, 4, 4, 5, 99, 0};

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, null);

        intCodeComputer.runProgram();

        assertEquals(9801, intCodeComputer.retrieveValueFromPosition(5));
    }

    @Test
    public void testDay2Part1Case4() throws Exception {
        int[] program = new int[]{1, 1, 1, 4, 99, 5, 6, 0, 99};

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, null);

        intCodeComputer.runProgram();

        assertEquals(30, intCodeComputer.retrieveValueFromPosition(0));
        assertEquals(2, intCodeComputer.retrieveValueFromPosition(4));
    }

    @Test
    public void testDay5Part1Case1() throws Exception {
        int[] program = new int[]{3, 0, 4, 0, 99};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(42);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(42, output.take());
    }

    @Test
    public void testDay5Part1Case2() throws Exception {
        int[] program = new int[]{1002, 4, 3, 4, 33};

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, null);

        intCodeComputer.runProgram();
    }

    @Test
    public void testDay5Part1Case3() throws Exception {
        int[] program = new int[]{1101, 100, -1, 4, 0};

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, null);

        intCodeComputer.runProgram();
    }

    @Test
    public void testDay5Part2Case1_1() throws Exception {
        int[] program = new int[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(7);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    public void testDay5Part2Case1_2() throws Exception {
        int[] program = new int[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(8);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    public void testDay5Part2Case1_3() throws Exception {
        int[] program = new int[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(9);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    public void testDay5Part2Case2_1() throws Exception {
        int[] program = new int[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(7);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    public void testDay5Part2Case2_2() throws Exception {
        int[] program = new int[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(8);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    public void testDay5Part2Case2_3() throws Exception {
        int[] program = new int[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(9);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    public void testDay5Part2Case3_1() throws Exception {
        int[] program = new int[]{3, 3, 1108, -1, 8, 3, 4, 3, 99};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(7);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    public void testDay5Part2Case3_2() throws Exception {
        int[] program = new int[]{3, 3, 1108, -1, 8, 3, 4, 3, 99};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(8);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    public void testDay5Part2Case3_3() throws Exception {
        int[] program = new int[]{3, 3, 1108, -1, 8, 3, 4, 3, 99};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(9);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    public void testDay5Part2Case4_1() throws Exception {
        int[] program = new int[]{3, 3, 1107, -1, 8, 3, 4, 3, 99};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(7);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    public void testDay5Part2Case4_2() throws Exception {
        int[] program = new int[]{3, 3, 1107, -1, 8, 3, 4, 3, 99};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(8);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    public void testDay5Part2Case4_3() throws Exception {
        int[] program = new int[]{3, 3, 1107, -1, 8, 3, 4, 3, 99};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(9);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    public void testDay5Part2Case5_1() throws Exception {
        int[] program = new int[]{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(-1);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    public void testDay5Part2Case5_2() throws Exception {
        int[] program = new int[]{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(0);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    public void testDay5Part2Case5_3() throws Exception {
        int[] program = new int[]{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(1);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    public void testDay5Part2Case6_1() throws Exception {
        int[] program = new int[]{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(-1);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    public void testDay5Part2Case6_2() throws Exception {
        int[] program = new int[]{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(0);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    public void testDay5Part2Case6_3() throws Exception {
        int[] program = new int[]{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(1);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    public void testDay5Part2Case7_1() throws Exception {
        int[] program = new int[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                                  1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                                  999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(7);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(999, output.take());
    }

    @Test
    public void testDay5Part2Case7_2() throws Exception {
        int[] program = new int[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                                  1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                                  999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(8);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1000, output.take());
    }

    @Test
    public void testDay5Part2Case7_3() throws Exception {
        int[] program = new int[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                                  1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                                  999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99};

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(100);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(100);

        input.put(9);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1001, output.take());
    }
}