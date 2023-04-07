package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day2 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.mapToInt(this::calculateChecksum).sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return input.mapToInt(this::calculateBitwiseChecksum).sum();
    }

    private int calculateChecksum(String line) {
        var largest = Integer.MIN_VALUE;
        var smallest = Integer.MAX_VALUE;

        var numbers = line.split("\\t");

        for (var numberstring : numbers) {
            var number = Integer.parseInt(numberstring);

            if (number > largest) {
                largest = number;
            }
            if (number < smallest) {
                smallest = number;
            }
        }

        return largest - smallest;
    }

    private int calculateBitwiseChecksum(String line) {
        var numbers = line.split("\\t");

        var result = 0;
        
        outer: for (var i = 0; i < numbers.length; i++) {
            var numberi = Integer.parseInt(numbers[i]);
            for (var j = 0; j < numbers.length; j++) {
                if (i == j) {
                    continue;
                }
                var numberj = Integer.parseInt(numbers[j]);

                if (numberi % numberj == 0) {
                    result += numberi / numberj;
                    continue outer;
                }
            }
        }
        
        return result;
    }
}
