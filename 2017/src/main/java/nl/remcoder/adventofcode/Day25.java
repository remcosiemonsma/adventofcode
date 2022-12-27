package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day25 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        boolean[] tape = new boolean[12656374];

        State state = State.A;

        int position = 12656374/2;

        for (int counter = 0; counter < 12656374; counter++) {
            switch (state) {
                case A -> {
                    if (!tape[position]) {
                        tape[position] = true;
                        position++;
                        state = State.B;
                    } else {
                        tape[position] = false;
                        position--;
                        state = State.C;
                    }
                }
                case B -> {
                    if (!tape[position]) {
                        tape[position] = true;
                        position--;
                        state = State.A;
                    } else {
                        tape[position] = true;
                        position--;
                        state = State.D;
                    }
                }
                case C -> {
                    if (!tape[position]) {
                        tape[position] = true;
                        position++;
                        state = State.D;
                    } else {
                        tape[position] = false;
                        position++;
                    }
                }
                case D -> {
                    if (!tape[position]) {
                        tape[position] = false;
                        position--;
                        state = State.B;
                    } else {
                        tape[position] = false;
                        position++;
                        state = State.E;
                    }
                }
                case E -> {
                    if (!tape[position]) {
                        tape[position] = true;
                        position++;
                        state = State.C;
                    } else {
                        tape[position] = true;
                        position--;
                        state = State.F;
                    }
                }
                case F -> {
                    if (!tape[position]) {
                        tape[position] = true;
                        position--;
                        state = State.E;
                    } else {
                        tape[position] = true;
                        position++;
                        state = State.A;
                    }
                }
            }
            
            while (position < 0) {
                position += 12656374;
            }
            while (position >= 12656374) {
                position -= 12656374;
            }
        }

        int counter = 0;
        for (boolean value : tape) {
            if (value) {
                counter++;
            }
        }
        
        return counter;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return null;
    }

    private enum State {
        A,
        B,
        C,
        D,
        E,
        F
    }
}
