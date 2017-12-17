package nl.remcoder.adventofcode.day17;

public class Part2 {
    public static void main(String[] args) {
        int steps = 329;

        int[] buffer = new int[50000001];
        buffer[0] = 0;
        buffer[1] = 1;

        int insertionValue = 2;

        int previousPosition = 1;

        for (int i = 1; i < 50000000; i++) {
            int position = previousPosition + steps;

            if (position >= i + 1) {
                position = position % (i + 1);
            }

            if (position == 0) {
                int[] newBuffer = new int[50000001];

                System.arraycopy(buffer, 0, newBuffer, 0, position + 1);
                System.arraycopy(buffer, position + 1, newBuffer, position + 2, (i + 1) - (position + 1));

                newBuffer[position + 1] = insertionValue;

                buffer = newBuffer;
            }

            previousPosition = position + 1;

            insertionValue++;
        }

        System.out.println(buffer[1]);
    }
}
