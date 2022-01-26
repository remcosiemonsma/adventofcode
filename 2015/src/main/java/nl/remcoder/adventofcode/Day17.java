package nl.remcoder.adventofcode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day17 {
    public int handlePart1(Stream<String> input) {
        Set<Bucket> buckets = new HashSet<>();

        List<Integer> sizes = input.map(Integer::parseInt)
                                   .collect(Collectors.toList());

        for (int i = 0; i < sizes.size(); i++) {
            buckets.add(new Bucket(i, sizes.get(i)));
        }

        Map<Set<Bucket>, Integer> memo = new HashMap<>();

        return findPossibleCombinations(new HashSet<>(), buckets, memo);
    }

    public int handlePart2(Stream<String> input) {
        Set<Bucket> buckets = new HashSet<>();

        List<Integer> sizes = input.map(Integer::parseInt)
                                   .collect(Collectors.toList());

        for (int i = 0; i < sizes.size(); i++) {
            buckets.add(new Bucket(i, sizes.get(i)));
        }

        Map<Set<Bucket>, Integer> memo = new HashMap<>();

        findPossibleCombinations(new HashSet<>(), buckets, memo);

        return memo.keySet()
                   .stream()
                   .filter(buckets1 -> calculateBucketsSize(buckets1) == 150)
                   .mapToInt(Set::size)
                   .min()
                   .getAsInt();
    }

    private int findPossibleCombinations(Set<Bucket> bucketsUsed, Set<Bucket> remainingBuckets,
                                         Map<Set<Bucket>, Integer> processedBuckets) {
        int amount = 0;
        for (Bucket bucket : remainingBuckets) {
            Set<Bucket> newBuckets = new HashSet<>(bucketsUsed);
            newBuckets.add(bucket);
            if (!processedBuckets.containsKey(newBuckets)) {
                int size = calculateBucketsSize(newBuckets);
                if (size == 150) {
                    amount++;
                } else if (size < 150) {
                    Set<Bucket> newRemaining = new HashSet<>(remainingBuckets);
                    newRemaining.remove(bucket);

                    amount += findPossibleCombinations(newBuckets, newRemaining, processedBuckets);
                }
                processedBuckets.put(newBuckets, 1);
            }
        }

        return amount;
    }

    private int calculateBucketsSize(Set<Bucket> buckets) {
        return buckets.stream().mapToInt(Bucket::getSize).sum();
    }

    private static class Bucket {
        private final int id;
        private final int size;

        public Bucket(int id, int size) {
            this.id = id;
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        @Override
        public String toString() {
            return "Bucket{" +
                   "id=" + id +
                   ", size=" + size +
                   '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Bucket bucket = (Bucket) o;
            return id == bucket.id && size == bucket.size;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, size);
        }
    }
}
