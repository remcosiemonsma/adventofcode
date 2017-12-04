package nl.remcoder.adventofcode.day3;

public class Part1 {
    public static void main(String[] args) throws Exception {
        int input = 289326;

        int lowerright = 0;

        int counter = 1;

        while (lowerright < input) {
            counter += 2;
            lowerright = counter * counter;
        }

        int upperleft = ((counter - 1) * (counter - 1)) + 1;

        int length = ((lowerright - upperleft) / 2) + 1;
        int center = (length - 1) / 2;

        if (input <= upperleft) {
            int upperright = (upperleft - length) + 1;

            if (input <= upperright) {
                determineDistance(input, center, upperright);
            } else {
                determineDistance(input, center, upperleft);
            }
        } else {
            int lowerleft = (upperleft + length) - 1;

            if (input <= lowerleft) {
                determineDistance(input, center, lowerleft);
            } else {
                determineDistance(input, center, lowerright);
            }
        }
    }

    private static void determineDistance(int input, int center, int upperright) {
        int distance = upperright - input;
        int position = Math.abs(center - distance);

        System.out.println(position + center);
    }
}
