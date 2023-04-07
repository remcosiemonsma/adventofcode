package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.stream.Stream;

public class Day17 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var buckets = new HashSet<Bucket>();

        var sizes = input.map(Integer::parseInt)
                         .toList();

        for (int i = 0; i < sizes.size(); i++) {
            buckets.add(new Bucket(i, sizes.get(i)));
        }

        var memo = new HashMap<Set<Bucket>, Integer>();

        return findPossibleCombinations(new HashSet<>(), buckets, memo);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var buckets = new HashSet<Bucket>();

        var sizes = input.map(Integer::parseInt)
                         .toList();

        for (var i = 0; i < sizes.size(); i++) {
            buckets.add(new Bucket(i, sizes.get(i)));
        }

        var memo = new HashMap<Set<Bucket>, Integer>();

        findPossibleCombinations(new HashSet<>(), buckets, memo);

        return memo.keySet()
                   .stream()
                   .filter(buckets1 -> calculateBucketsSize(buckets1) == 150)
                   .mapToInt(Set::size)
                   .min()
                   .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private int findPossibleCombinations(Set<Bucket> bucketsUsed, Set<Bucket> remainingBuckets,
                                         Map<Set<Bucket>, Integer> processedBuckets) {
        var amount = 0;
        for (var bucket : remainingBuckets) {
            var newBuckets = new HashSet<>(bucketsUsed);
            newBuckets.add(bucket);
            if (!processedBuckets.containsKey(newBuckets)) {
                var size = calculateBucketsSize(newBuckets);
                if (size == 150) {
                    amount++;
                } else if (size < 150) {
                    var newRemaining = new HashSet<>(remainingBuckets);
                    newRemaining.remove(bucket);

                    amount += findPossibleCombinations(newBuckets, newRemaining, processedBuckets);
                }
                processedBuckets.put(newBuckets, 1);
            }
        }

        return amount;
    }

    private int calculateBucketsSize(Set<Bucket> buckets) {
        return buckets.stream()
                      .mapToInt(Bucket::size)
                      .sum();
    }

    private record Bucket(int id, int size) {
    }
}
