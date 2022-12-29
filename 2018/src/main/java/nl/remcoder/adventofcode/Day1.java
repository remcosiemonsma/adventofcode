package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Day1 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.mapToInt(Integer::parseInt)
                    .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var frequencyShifts = input.map(Integer::parseInt)
                                   .toList();

        int frequency = 0;

        Set<Integer> frequencies = new HashSet<>();

        int frequencyCounter = 0;

        while (frequencies.add(frequency)) {
            frequency += frequencyShifts.get(frequencyCounter);

            frequencyCounter++;
            if (frequencyCounter >= frequencyShifts.size()) {
                frequencyCounter = 0;
            }
        }
        
        return frequency;
    }
}
