package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

public class Day6 implements AdventOfCodeSolution<Long> {

    @Override
    public Long handlePart1(Stream<String> input) {
        return input.gather(Gatherer.<String, List<List<Integer>>, Long>ofSequential(ArrayList::new,
          (state, element, downstream) -> {
            if (Character.isDigit(element.charAt(0))) {
                var numbers = Arrays.stream(element.split("\\s+"))
                        .map(Integer::parseInt)
                        .toList();
                state.add(numbers);
            } else {
                var operands = element.split("\\s+");
                for (var i = 0; i < operands.length; i++) {
                    switch (operands[i]) {
                        case "*": {
                            var result = 1L;
                            for (var numbers : state) {
                                result *= numbers.get(i);
                            }
                            downstream.push(result);
                            break;
                        }
                        case "+": {
                            var result = 0L;
                            for (var numbers : state) {
                                result += numbers.get(i);
                            }
                            downstream.push(result);
                            break;
                        }
                    }
                }
            }
            return true;
        })).mapToLong(value -> value)
                .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var result = 0L;

        var numbers = new ArrayList<Long>();
        for (var x = grid.getEndx(); x >= 0; x--) {
            var chars = new char[grid.getEndy()];
            for (var y = 0; y < grid.getEndy(); y++) {
                chars[y] = grid.get(x, y);
            }
            var line = new String(chars);
            if (!line.isBlank()) {
                numbers.add(Long.parseLong(line.trim()));
            } else {
                switch (grid.get(x + 1, grid.getEndy())) {
                    case '*': {
                        var answer = 1L;
                        for (var number : numbers) {
                            answer *= number;
                        }
                        result += answer;
                        break;
                    }
                    case '+': {
                        var answer = 0L;
                        for (var number : numbers) {
                            answer += number;
                        }
                        result += answer;
                        break;
                    }
                }
                numbers.clear();
            }
        }

        switch (grid.get(0, grid.getEndy())) {
            case '*': {
                var answer = 1L;
                for (var number : numbers) {
                    answer *= number;
                }
                result += answer;
                break;
            }
            case '+': {
                var answer = 0L;
                for (var number : numbers) {
                    answer += number;
                }
                result += answer;
                break;
            }
        }

        return result;
    }
}
