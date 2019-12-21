package nl.remcoder.adventofcode;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

class IntCodeComputerTest {

    @Test
    void testDay2Part1Case1() {
        long[] program = new long[]{1, 0, 0, 0, 99};

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, null);

        intCodeComputer.runProgram();

        assertEquals(2, intCodeComputer.retrieveValueFromPosition(0));
    }

    @Test
    void testDay2Part1Case2() {
        long[] program = new long[]{2, 3, 0, 3, 99};

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, null);

        intCodeComputer.runProgram();

        assertEquals(6, intCodeComputer.retrieveValueFromPosition(3));
    }

    @Test
    void testDay2Part1Case3() {
        long[] program = new long[]{2, 4, 4, 5, 99, 0};

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, null);

        intCodeComputer.runProgram();

        assertEquals(9801, intCodeComputer.retrieveValueFromPosition(5));
    }

    @Test
    void testDay2Part1Case4() {
        long[] program = new long[]{1, 1, 1, 4, 99, 5, 6, 0, 99};

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, null);

        intCodeComputer.runProgram();

        assertEquals(30, intCodeComputer.retrieveValueFromPosition(0));
        assertEquals(2, intCodeComputer.retrieveValueFromPosition(4));
    }

    @Test
    void testDay5Part1Case1() throws Exception {
        long[] program = new long[]{3, 0, 4, 0, 99};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(42L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(42, output.take());
    }

    @Test
    void testDay5Part1Case2() {
        long[] program = new long[]{1002, 4, 3, 4, 33};

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, null);

        intCodeComputer.runProgram();
    }

    @Test
    void testDay5Part1Case3() {
        long[] program = new long[]{1101, 100, -1, 4, 0};

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, null);

        intCodeComputer.runProgram();
    }

    @Test
    void testDay5Part2Case1_1() throws Exception {
        long[] program = new long[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(7L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    void testDay5Part2Case1_2() throws Exception {
        long[] program = new long[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(8L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    void testDay5Part2Case1_3() throws Exception {
        long[] program = new long[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(9L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    void testDay5Part2Case2_1() throws Exception {
        long[] program = new long[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(7L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    void testDay5Part2Case2_2() throws Exception {
        long[] program = new long[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(8L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    void testDay5Part2Case2_3() throws Exception {
        long[] program = new long[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(9L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    void testDay5Part2Case3_1() throws Exception {
        long[] program = new long[]{3, 3, 1108, -1, 8, 3, 4, 3, 99};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(7L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    void testDay5Part2Case3_2() throws Exception {
        long[] program = new long[]{3, 3, 1108, -1, 8, 3, 4, 3, 99};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(8L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    void testDay5Part2Case3_3() throws Exception {
        long[] program = new long[]{3, 3, 1108, -1, 8, 3, 4, 3, 99};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(9L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    void testDay5Part2Case4_1() throws Exception {
        long[] program = new long[]{3, 3, 1107, -1, 8, 3, 4, 3, 99};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(7L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    void testDay5Part2Case4_2() throws Exception {
        long[] program = new long[]{3, 3, 1107, -1, 8, 3, 4, 3, 99};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(8L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    void testDay5Part2Case4_3() throws Exception {
        long[] program = new long[]{3, 3, 1107, -1, 8, 3, 4, 3, 99};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(9L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    void testDay5Part2Case5_1() throws Exception {
        long[] program = new long[]{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(-1L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    void testDay5Part2Case5_2() throws Exception {
        long[] program = new long[]{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(0L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    void testDay5Part2Case5_3() throws Exception {
        long[] program = new long[]{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(1L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    void testDay5Part2Case6_1() throws Exception {
        long[] program = new long[]{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(-1L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    void testDay5Part2Case6_2() throws Exception {
        long[] program = new long[]{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(0L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(0, output.take());
    }

    @Test
    void testDay5Part2Case6_3() throws Exception {
        long[] program = new long[]{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(1L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1, output.take());
    }

    @Test
    void testDay5Part2Case7_1() throws Exception {
        long[] program = new long[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                                    1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                                    999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(7L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(999, output.take());
    }

    @Test
    void testDay5Part2Case7_2() throws Exception {
        long[] program = new long[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                                    1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                                    999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(8L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1000, output.take());
    }

    @Test
    void testDay5Part2Case7_3() throws Exception {
        long[] program = new long[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                                    1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                                    999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99};

        BlockingQueue<Long> input = new LinkedBlockingQueue<>();
        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        input.put(9L);

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, input, output);

        intCodeComputer.runProgram();

        assertEquals(1001, output.take());
    }

    @Test
    void testDay9Part1Case1() {
        long[] program = new long[]{109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99};

        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, output);

        intCodeComputer.runProgram();

        assertArrayEquals(program, output.stream().mapToLong(l -> l).toArray());
    }

    @Test
    void testDay9Part1Case2() throws Exception {
        long[] program = new long[]{1102, 34915192, 34915192, 7, 4, 7, 99, 0};

        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, output);

        intCodeComputer.runProgram();

        assertEquals(1219070632396864L, output.take());
    }

    @Test
    void testDay9Part1Case3() throws Exception {
        long[] program = new long[]{104, 1125899906842624L, 99};

        BlockingQueue<Long> output = new LinkedBlockingQueue<>();

        IntCodeComputer intCodeComputer = new IntCodeComputer(program, null, output);

        intCodeComputer.runProgram();

        assertEquals(1125899906842624L, output.take());
    }
}