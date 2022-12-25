package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day25 implements AdventOfCodeSolution<String> {
    @Override
    public String handlePart1(Stream<String> input) {
        return parseSnafuNumber(input.mapToLong(this::parseSnafuNumber).sum());
    }

    @Override
    public String handlePart2(Stream<String> input) {
        return null;
    }
    
    private long parseSnafuNumber(String line) {
        var snafuLength = line.length() - 1;
        
        var result = 0L;
        for (int i = 0; i < line.length(); i++) {
            result += Math.pow(5, snafuLength - i) * switch (line.charAt(i)) {
                case '2' -> 2;
                case '1' -> 1;
                case '0' -> 0;
                case '-' -> -1;
                case '=' -> -2;
                default -> throw new AssertionError("Eek!");
            };
        }
        
        return result;
    }
    
    private String parseSnafuNumber(long number) {
        var resultBuilder = new StringBuilder();
        
        while (number > 0) {
            var remainder = number % 5;

            if (remainder == 0) {
                resultBuilder.insert(0, '0');
            } else if (remainder == 1) {
                resultBuilder.insert(0, '1');
                number--;
            } else if (remainder == 2) {
                resultBuilder.insert(0, '2');
                number -= 2;
            } else if (remainder == 3) {
                resultBuilder.insert(0, '=');
                number += 2;
            } else {
                resultBuilder.insert(0, '-');
                number++;
            }
            
            number /= 5; 
        }
        
        return resultBuilder.toString();
    }
}
