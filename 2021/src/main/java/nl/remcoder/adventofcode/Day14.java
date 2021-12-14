package nl.remcoder.adventofcode;

import jdk.dynalink.NamedOperation;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day14 {
    public long handlePart1(Stream<String> input) {
        Map<Pair<Character, Character>, List<Pair<Character, Character>>> reactions = new HashMap<>();

        List<Pair<Character, Character>> finalPolymer = new ArrayList<>();

        input.filter(Predicate.not(String::isEmpty))
             .forEach(s -> {
                 String[] split = s.split(" -> ");
                 if (split.length == 1) {
                     char[] chars = split[0].toCharArray();
                     for (int i = 0; i < chars.length - 1; i++) {
                         finalPolymer.add(new Pair<>(chars[i], chars[i + 1]));
                     }
                 } else {
                     reactions.put(new Pair<>(split[0].charAt(0), split[0].charAt(1)),
                                   List.of(new Pair<>(split[0].charAt(0), split[1].charAt(0)),
                                           new Pair<>(split[1].charAt(0), split[0].charAt(1))));
                 }
             });

        List<Pair<Character, Character>> polymer = finalPolymer;

        for (int step = 0; step < 10; step++) {
            polymer = polymer.stream()
                             .map(reactions::get)
                             .flatMap(Collection::stream)
                             .toList();
        }

        String s = polymer.stream()
                          .map(Pair::getLeft)
                          .map(c -> "" + c)
                          .reduce(String::concat)
                          .orElse("");

        s += polymer.get(polymer.size() - 1).right;

        return countMostCommonChar(s) - countLeastCommonChar(s);
    }

    public long handlePart2(Stream<String> input) {
        Map<Pair<Character, Character>, List<Pair<Character, Character>>> reactions = new HashMap<>();

        Map<Pair<Character, Character>, Long> pairCounts = new HashMap<>();

        input.filter(Predicate.not(String::isEmpty))
             .forEach(s -> {
                 String[] split = s.split(" -> ");
                 if (split.length == 1) {
                     char[] chars = split[0].toCharArray();
                     for (int i = 0; i < chars.length - 1; i++) {
                         pairCounts.compute(new Pair<>(chars[i], chars[i + 1]), (key, value) -> {
                             if (value == null) {
                                 return 1L;
                             } else {
                                 return value + 1;
                             }
                         });
                     }
                 } else {
                     reactions.put(new Pair<>(split[0].charAt(0), split[0].charAt(1)),
                                   List.of(new Pair<>(split[0].charAt(0), split[1].charAt(0)),
                                           new Pair<>(split[1].charAt(0), split[0].charAt(1))));
                 }
             });

        for (int step = 0; step < 40; step++) {
            Map<Pair<Character, Character>, Long> copy = Map.copyOf(pairCounts);

            copy.forEach((key1, value1) -> {
                List<Pair<Character, Character>> pairs = reactions.get(key1);

                pairCounts.compute(key1, (key, value) -> value - copy.get(key));

                pairs.forEach(pair -> pairCounts.compute(pair, (key, value) -> {
                    if (value == null) {
                        return pairCounts.get(key1);
                    } else {
                        return value + pairCounts.get(key1);
                    }
                }));
            });
        }

        return pairCounts.values().stream().mapToLong(value -> value).max().getAsLong() -
               pairCounts.values().stream().mapToLong(value -> value).min().getAsLong();
    }

    private long countMostCommonChar(String s) {
        Map<Character, Long> occurrences = new HashMap<>();

        for (char c : s.toCharArray()) {
            occurrences.compute(c, (key, amount) -> {
                if (amount == null) {
                    return 1L;
                } else {
                    return amount + 1;
                }
            });
        }

        return occurrences.values().stream().mapToLong(value -> value).max().getAsLong();
    }

    private long countLeastCommonChar(String s) {
        Map<Character, Long> occurrences = new HashMap<>();

        for (char c : s.toCharArray()) {
            occurrences.compute(c, (key, amount) -> {
                if (amount == null) {
                    return 1L;
                } else {
                    return amount + 1;
                }
            });
        }

        return occurrences.values().stream().mapToLong(value -> value).min().getAsLong();
    }

    private static class Pair<L, R> {
        private final L left;
        private final R right;

        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public L getLeft() {
            return left;
        }

        public R getRight() {
            return right;
        }

        public Stream<Object> stream() {
            return Stream.of(left, right);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(left, pair.left) && Objects.equals(right, pair.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }
    }
}
