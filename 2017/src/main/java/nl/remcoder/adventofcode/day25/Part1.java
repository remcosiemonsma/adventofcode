package nl.remcoder.adventofcode.day25;

public class Part1 {
    public static void main(String[] args) {
        boolean[] tape = new boolean[12656374];

        State state = State.A;

        int position = 12656374/2;

        for (int counter = 0; counter < 12656374; counter++) {
            switch (state) {
                case A:
                    if (!tape[position]) {
                        tape[position] = true;
                        position++;
                        state = State.B;
                    } else {
                        tape[position] = false;
                        position--;
                        state = State.C;
                    }
                    break;
                case B:
                    if (!tape[position]) {
                        tape[position] = true;
                        position--;
                        state = State.A;
                    } else {
                        tape[position] = true;
                        position--;
                        state = State.D;
                    }
                    break;
                case C:
                    if (!tape[position]) {
                        tape[position] = true;
                        position++;
                        state = State.D;
                    } else {
                        tape[position] = false;
                        position++;
                        state = State.C;
                    }
                    break;
                case D:
                    if (!tape[position]) {
                        tape[position] = false;
                        position--;
                        state = State.B;
                    } else {
                        tape[position] = false;
                        position++;
                        state = State.E;
                    }
                    break;
                case E:
                    if (!tape[position]) {
                        tape[position] = true;
                        position++;
                        state = State.C;
                    } else {
                        tape[position] = true;
                        position--;
                        state = State.F;
                    }
                    break;
                case F:
                    if (!tape[position]) {
                        tape[position] = true;
                        position--;
                        state = State.E;
                    } else {
                        tape[position] = true;
                        position++;
                        state = State.A;
                    }
                    break;
            }

//            System.out.println(position);

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

        System.out.println(counter);
    }

    enum State {
        A,
        B,
        C,
        D,
        E,
        F
    }
}
