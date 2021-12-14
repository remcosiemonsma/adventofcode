package nl.remcoder.adventofcode;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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

        return countMostCommonElement(s) - countLeastCommonElement(s);
    }

    public long handlePart2(Stream<String> input) {
        Map<Pair<Character, Character>, List<Pair<Character, Character>>> reactions = new HashMap<>();

        Map<Pair<Character, Character>, Long> pairCounts = new HashMap<>();

        AtomicReference<Character> tail = new AtomicReference<>();

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
                     tail.set(chars[chars.length - 1]);
                 } else {
                     reactions.put(new Pair<>(split[0].charAt(0), split[0].charAt(1)),
                                   List.of(new Pair<>(split[0].charAt(0), split[1].charAt(0)),
                                           new Pair<>(split[1].charAt(0), split[0].charAt(1))));
                 }
             });

        for (int step = 0; step < 40; step++) {
            Map<Pair<Character, Character>, Long> copy = Map.copyOf(pairCounts);

            copy.forEach((copyPair, copyValue) -> {
                List<Pair<Character, Character>> pairs = reactions.get(copyPair);

                pairCounts.compute(copyPair, (key, value) -> value - copyValue);

                pairs.forEach(pair -> pairCounts.compute(pair, (key, value) -> {
                    if (value == null) {
                        return copyValue;
                    } else {
                        return value + copyValue;
                    }
                }));
            });
        }

        return countMostCommonElement(pairCounts, tail.get()) -
               countLeastCommonElement(pairCounts, tail.get());
    }

    private long countMostCommonElement(String s) {
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

    private long countLeastCommonElement(String s) {
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

    private long countMostCommonElement(Map<Pair<Character, Character>, Long> polymer, Character tail) {
        Map<Character, Long> occurrences = countElements(polymer, tail);

        return occurrences.values().stream().mapToLong(value -> value).max().getAsLong();
    }

    private long countLeastCommonElement(Map<Pair<Character, Character>, Long> polymer, Character tail) {
        Map<Character, Long> occurrences = countElements(polymer, tail);

        return occurrences.values().stream().mapToLong(value -> value).min().getAsLong();
    }

    private Map<Character, Long> countElements(Map<Pair<Character, Character>, Long> polymer, Character tail) {
        Map<Character, Long> occurrences = new HashMap<>();

        occurrences.put(tail, 1L);

        for (Pair<Character, Character> pair : polymer.keySet()) {
            occurrences.compute(pair.left, (key, value) -> {
                if (value == null) {
                    return polymer.get(pair);
                } else {
                    return value + polymer.get(pair);
                }
            });
        }
        return occurrences;
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
