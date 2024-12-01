package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Stream;

public class Day1 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var leftList = new ArrayList<Integer>();
        var rightList = new ArrayList<Integer>();

        input.map(this::mapToIntegerPair)
             .forEach(pair -> {
                 leftList.add(pair.left());
                 rightList.add(pair.right());
             });

        leftList.sort(Comparator.naturalOrder());
        rightList.sort(Comparator.naturalOrder());

        var result = 0;

        for (int i = 0; i < leftList.size(); i++) {
            var left = leftList.get(i);
            var right = rightList.get(i);

            result += Math.abs(left - right);
        }

        return result;
    }

    private Pair<Integer, Integer> mapToIntegerPair(String line) {
        var split = line.split(" {3}");

        var left = Integer.parseInt(split[0]);
        var right = Integer.parseInt(split[1]);

        return new Pair<>(left, right);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var leftList = new ArrayList<Integer>();
        var rightMap = new HashMap<Integer, Integer>();

        input.map(this::mapToIntegerPair)
             .forEach(pair -> {
                 leftList.add(pair.left());
                 rightMap.compute(pair.right(), (right, amount) -> amount == null ? 1 : amount + 1);
             });

        return leftList.stream()
                       .mapToInt(left -> left * rightMap.getOrDefault(left, 0))
                       .sum();
    }
}
