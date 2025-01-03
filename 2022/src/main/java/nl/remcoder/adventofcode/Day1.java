package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.stream.CombiningCollector;

import java.util.*;
import java.util.stream.Stream;

public class Day1 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.collect(new CombiningCollector<>(Integer::parseInt, String::isBlank, BagImpl::new))
                    .parallel()
                    .map(BagImpl.class::cast)
                    .mapToInt(bag -> bag.stream()
                                        .mapToInt(Integer::intValue)
                                        .sum())
                    .max()
                    .orElseThrow(() -> new AssertionError("Eek!"));
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return input.collect(new CombiningCollector<>(Integer::parseInt, String::isBlank, BagImpl::new))
                    .parallel()
                    .map(BagImpl.class::cast)
                    .map(bag -> bag.stream()
                                   .mapToInt(Integer::intValue)
                                   .sum())
                    .sorted(Comparator.reverseOrder())
                    .limit(3)
                    .mapToInt(Integer::intValue)
                    .sum();
    }

    private static class BagImpl implements CombiningCollector.Bag<Integer> {
        private final List<Integer> list = new ArrayList<>();

        @Override
        public void add(Integer integer) {
            list.add(integer);
        }

        public Stream<Integer> stream() {
            return list.stream();
        }
    }
}