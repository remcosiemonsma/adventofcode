package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day14 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var reactions = new HashMap<Pair<Character, Character>, List<Pair<Character, Character>>>();

        var pairCounts = new HashMap<Pair<Character, Character>, Long>();

        var tail = new AtomicReference<Character>();

        parseInput(input, reactions, pairCounts, tail);

        performPolymerReactions(reactions, pairCounts, 10);

        return countMostCommonElement(pairCounts, tail.get()) -
               countLeastCommonElement(pairCounts, tail.get());
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var reactions = new HashMap<Pair<Character, Character>, List<Pair<Character, Character>>>();

        var pairCounts = new HashMap<Pair<Character, Character>, Long>();

        var tail = new AtomicReference<Character>();

        parseInput(input, reactions, pairCounts, tail);

        performPolymerReactions(reactions, pairCounts, 40);

        return countMostCommonElement(pairCounts, tail.get()) -
               countLeastCommonElement(pairCounts, tail.get());
    }

    private void performPolymerReactions(Map<Pair<Character, Character>, List<Pair<Character, Character>>> reactions,
                                         Map<Pair<Character, Character>, Long> pairCounts,
                                         int amountOfSteps) {
        for (var step = 0; step < amountOfSteps; step++) {
            var copy = Map.copyOf(pairCounts);

            copy.forEach((copyPair, copyValue) -> {
                var pairs = reactions.get(copyPair);

                pairCounts.computeIfPresent(copyPair, (key, value) -> value - copyValue);

                pairs.forEach(pair -> pairCounts.compute(pair, (key, value) -> {
                    if (value == null) {
                        return copyValue;
                    } else {
                        return value + copyValue;
                    }
                }));
            });
        }
    }

    private void parseInput(Stream<String> input,
                            Map<Pair<Character, Character>, List<Pair<Character, Character>>> reactions,
                            Map<Pair<Character, Character>, Long> pairCounts, AtomicReference<Character> tail) {
        input.filter(Predicate.not(String::isEmpty))
             .forEach(s -> {
                 var split = s.split(" -> ");
                 if (split.length == 1) {
                     var chars = split[0].toCharArray();
                     for (var i = 0; i < chars.length - 1; i++) {
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
    }

    private long countMostCommonElement(Map<Pair<Character, Character>, Long> polymer, Character tail) {
        var occurrences = countElements(polymer, tail);

        return occurrences.values()
                          .stream()
                          .mapToLong(value -> value)
                          .max()
                          .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private long countLeastCommonElement(Map<Pair<Character, Character>, Long> polymer, Character tail) {
        Map<Character, Long> occurrences = countElements(polymer, tail);

        return occurrences.values()
                          .stream()
                          .mapToLong(value -> value)
                          .min()
                          .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private Map<Character, Long> countElements(Map<Pair<Character, Character>, Long> polymer, Character tail) {
        Map<Character, Long> occurrences = new HashMap<>();

        occurrences.put(tail, 1L);

        for (Map.Entry<Pair<Character, Character>, Long> entry : polymer.entrySet()) {
            occurrences.compute(entry.getKey().left(), (key, value) -> {
                if (value == null) {
                    return entry.getValue();
                } else {
                    return value + entry.getValue();
                }
            });
        }
        return occurrences;
    }
}
