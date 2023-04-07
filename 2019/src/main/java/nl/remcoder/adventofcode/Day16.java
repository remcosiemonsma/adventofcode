package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day16 implements AdventOfCodeSolution<List<Integer>> {
    private static final List<Integer> pattern = List.of(0, 1, 0, -1);

    private int steps;

    @Override
    public List<Integer> handlePart1(Stream<String> input) {
        var numbers = input.flatMapToInt(String::chars)
                           .mapToObj(Character::getNumericValue)
                           .toList();

        for (var step = 0; step < steps; step++) {
            numbers = applyFFT(numbers);
        }

        return numbers.subList(0, 8);
    }

    @Override
    public List<Integer> handlePart2(Stream<String> input) {
        var numbers = input.flatMapToInt(String::chars)
                           .mapToObj(Character::getNumericValue)
                           .toList();

        var offsetBuilder = new StringBuilder();

        numbers.stream()
               .limit(7)
               .forEach(offsetBuilder::append);
        
        var offset = Integer.parseInt(offsetBuilder.toString());

        var expandedList = expandList(numbers);

        var array = expandedList.toArray(new Integer[0]);
        for (var i = 0; i < 100; i++) {
            for (var j = array.length - 1; j > offset - 1; j--) {
                array[j - 1] = (array[j - 1] + array[j]) % 10;
            }
        }

        var result = new Integer[8];
        
        System.arraycopy(array, offset, result, 0, 8);
        
        return Arrays.asList(result);
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    private List<Integer> applyFFT(List<Integer> numbers) {
        var newNumbers = new ArrayList<Integer>(numbers.size());

        for (var i = 0; i < numbers.size(); i++) {
            var result = 0;

            var index = 0;
            var repeat = 1;
            if (i == 0) {
                repeat = 0;
                index = 1;
            }
            for (var number : numbers) {
                result += number * pattern.get(index);
                if (repeat == i) {
                    repeat = 0;
                    index++;
                } else {
                    repeat++;
                }
                if (index == 4) {
                    index = 0;
                }
            }

            newNumbers.add(Math.abs(result % 10));
        }

        return newNumbers;
    }

    private List<Integer> expandList(List<Integer> numbers) {
        var expandedList = new ArrayList<Integer>(numbers.size() * 10000);

        for (int i = 0; i < 10000; i++) {
            expandedList.addAll(numbers);
        }

        return expandedList;
    }
}
