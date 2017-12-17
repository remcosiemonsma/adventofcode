package nl.remcoder.adventofcode.day17;

public class Part1 {
    public static void main(String[] args) {
        int steps = 329;

        int[] buffer = new int[] {0, 1};

        int insertionValue = 2;

        int previousPosition = 1;

        for (int i = 0; i < 2016; i++) {
            int position = previousPosition + steps;

            if (position >= buffer.length) {
                position = position % buffer.length;
            }

            int[] newBuffer = new int[buffer.length + 1];

            System.arraycopy(buffer, 0, newBuffer, 0, position + 1);
            System.arraycopy(buffer, position + 1, newBuffer, position + 2, buffer.length - (position + 1));

            newBuffer[position + 1] = insertionValue;

            buffer = newBuffer;

            previousPosition = position + 1;

            insertionValue++;
        }

        System.out.println(buffer[previousPosition + 1]);
    }
}
