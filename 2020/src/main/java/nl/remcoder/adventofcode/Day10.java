package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.*;
import java.util.stream.Stream;

public class Day10 implements BiAdventOfCodeSolution<Integer, Long> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var jolts = new ArrayList<Integer>();

        jolts.add(0);

        jolts.addAll(input.map(Integer::parseInt)
                          .sorted()
                          .toList());

        jolts.add(jolts.get(jolts.size() - 1) + 3);

        return countDifferences(jolts);
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var jolts = new ArrayList<Integer>();

        jolts.add(0);

        jolts.addAll(input.map(Integer::parseInt)
                          .sorted()
                          .toList());

        jolts.add(jolts.get(jolts.size() - 1) + 3);

        var cache = new HashMap<Integer, Long>();

        return countCombinations(jolts, cache);
    }

    private long countCombinations(List<Integer> jolts, Map<Integer, Long> cache) {
        if (jolts.size() <= 2) {
            return 1;
        }

        var lastJolt = jolts.get(jolts.size() - 1);

        if (cache.containsKey(lastJolt)) {
            return cache.get(lastJolt);
        }

        var combinations = 0L;

        for (var position = jolts.size() - 2; position > jolts.size() - 5 && position >= 0; position--) {
            var jolt = jolts.get(position);

            if (lastJolt - jolt <= 3) {
                combinations += countCombinations(jolts.subList(0, position + 1), cache);
            }
        }

        cache.put(lastJolt, combinations);

        return combinations;
    }

    private int countDifferences(List<Integer> jolts) {
        var differences1 = 0;
        var differences3 = 0;

        for (var position = 0; position < jolts.size() - 1; position++) {
            var jolt1 = jolts.get(position);
            var jolt2 = jolts.get(position + 1);

            if (jolt2 - jolt1 == 1) {
                differences1++;
            }
            if (jolt2 - jolt1 == 3) {
                differences3++;
            }
        }

        return differences1 * differences3;
    }
}
