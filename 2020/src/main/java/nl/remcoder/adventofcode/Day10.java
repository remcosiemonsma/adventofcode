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

        Set<List<Integer>> cache = new HashSet<>();

        return countCombinations(jolts, cache);
    }

    private long countCombinations(List<Integer> jolts, Set<List<Integer>> cache) {
        long combinations = 1;

        for (int position = 1; position < jolts.size() - 1; position++) {
            List<Integer> newJolts = new ArrayList<>(jolts);
            newJolts.remove(position);

            if (!cache.contains(newJolts)) {
                cache.add(newJolts);

                if (isCombinationValid(newJolts)) {
                    long combinationsOfSubList = countCombinations(newJolts, cache);

                    combinations += combinationsOfSubList;
                }
            }
        }

        return combinations;
    }

    private boolean isCombinationValid(List<Integer> jolts) {
        for (int position = 0; position < jolts.size() - 1; position++) {
            Integer jolt1 = jolts.get(position);
            Integer jolt2 = jolts.get(position + 1);

            if (jolt2 - jolt1 > 3) {
                return false;
            }
        }

        return true;
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

    private long countPossibleChanges(List<Integer> differences) {
        long changes = 0;

        for (int position = 0; position < differences.size() - 1; position++) {
            Integer difference1 = differences.get(position);
            Integer difference2 = differences.get(position + 1);

            if (difference1 == 1 && difference2 == 1) {
                changes++;
            }
        }

        return changes;
    }

    private List<Integer> createDifferences(List<Integer> jolts) {
        List<Integer> differences = new ArrayList<>();

        for (int position = 0; position < jolts.size() - 1; position++) {
            Integer jolt1 = jolts.get(position);
            Integer jolt2 = jolts.get(position + 1);

            if (jolt2 - jolt1 == 1) {
                differences.add(1);
            }
            if (jolt2 - jolt1 == 3) {
                differences.add(3);
            }
        }

        return differences;
    }
}
