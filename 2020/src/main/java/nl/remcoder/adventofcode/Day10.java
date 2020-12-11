package nl.remcoder.adventofcode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 {
    public int handlePart1(Stream<String> input) {
        List<Integer> jolts = input.map(Integer::parseInt)
                                   .sorted()
                                   .collect(Collectors.toList());

        jolts.add(0, 0);
        jolts.add(jolts.get(jolts.size() - 1) + 3);

        return countDifferences(jolts);
    }

    public long handlePart2(Stream<String> input) {
        List<Integer> jolts = input.map(Integer::parseInt)
                                   .sorted()
                                   .collect(Collectors.toList());

        jolts.add(0, 0);
        jolts.add(jolts.get(jolts.size() - 1) + 3);

        Map<Integer, Long> cache = new HashMap<>();

        return countCombinations(jolts, cache);
    }

    private long countCombinations(List<Integer> jolts, Map<Integer, Long> cache) {
        if (jolts.size() <= 2) {
            return 1;
        }

        Integer lastJolt = jolts.get(jolts.size() - 1);

        if (cache.containsKey(lastJolt)) {
            return cache.get(lastJolt);
        }

        long combinations = 0;

        for (int position = jolts.size() - 2; position > jolts.size() - 5 && position >= 0; position--) {
            Integer jolt = jolts.get(position);

            if (lastJolt - jolt <= 3) {
                combinations += countCombinations(jolts.subList(0, position + 1), cache);
            }
        }

        cache.put(lastJolt, combinations);

        return combinations;
    }

    private int countDifferences(List<Integer> jolts) {
        int differences1 = 0;
        int differences3 = 0;

        for (int position = 0; position < jolts.size() - 1; position++) {
            Integer jolt1 = jolts.get(position);
            Integer jolt2 = jolts.get(position + 1);

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
