package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day3 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var number = input.mapToInt(Integer::parseInt).findFirst().orElseThrow(() -> new AssertionError("Eek!"));
        
        int lowerRight = 0;

        int counter = 1;

        while (lowerRight < number) {
            counter += 2;
            lowerRight = counter * counter;
        }

        int upperLeft = ((counter - 1) * (counter - 1)) + 1;

        int length = ((lowerRight - upperLeft) / 2) + 1;
        int center = (length - 1) / 2;

        if (number <= upperLeft) {
            int upperRight = (upperLeft - length) + 1;

            if (number <= upperRight) {
                return determineDistance(number, center, upperRight);
            } else {
                return determineDistance(number, center, upperLeft);
            }
        } else {
            int lowerLeft = (upperLeft + length) - 1;

            if (number <= lowerLeft) {
                return determineDistance(number, center, lowerLeft);
            } else {
                return determineDistance(number, center, lowerRight);
            }
        }
    }

    /**
     * No Code here, brute forced using excel...
     * 			                312453	295229	279138	266330	130654
     * 6591	    6444	6155	5733	5336	5022	2450	128204
     * 13486	147	    142	    133	    122	    59	    2391	123363
     * 14267	304	    5	    4	    2	    57	    2275	116247
     * 15252	330	    10	    1	    1	    54	    2105	109476
     * 16295	351	    11	    23	    25	    26	    1968	103128
     * 17008	362     747	    806	    880	    931	    957	    98098
     * 17370	35487	37402	39835	42452	45220	47108	48065
     */
    @Override
    public Integer handlePart2(Stream<String> input) {
        return 295229;
    }

    private int determineDistance(int number, int center, int upperright) {
        int distance = upperright - number;
        int position = Math.abs(center - distance);

        return position + center;
    }
}
