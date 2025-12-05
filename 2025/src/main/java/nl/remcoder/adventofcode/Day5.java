package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

public class Day5 implements AdventOfCodeSolution<Long> {

    @Override
    public Long handlePart1(Stream<String> input) {
        return input.gather(
                            Gatherer.<String, IDVerifier, Long>ofSequential(IDVerifier::new, (state, element, downstream) -> {
                                if (element.isBlank()) {
                                    state.setLocked(true);
                                    return true;
                                }
                                if (state.isLocked()) {
                                    var id = Long.parseLong(element);
                                    if (state.isIdValid(id)) {
                                        downstream.push(id);
                                    }
                                } else {
                                    state.addRange(createRange(element));
                                }
                                return true;
                            }))
                    .count();
    }

    private FreshIdRange createRange(String element) {
        var edges = element.split("-");
        return new FreshIdRange(Long.parseLong(edges[0]), Long.parseLong(edges[1]));
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var freshIdRanges = input.takeWhile(Predicate.not(String::isBlank))
                                 .map(this::createRange)
                                 .sorted()
                                 .collect(Collectors.toList());

        freshIdRanges = flattenIdRanges(freshIdRanges);

        return freshIdRanges.stream()
                            .mapToLong(FreshIdRange::getFreshIds)
                            .sum();
    }

    private List<FreshIdRange> flattenIdRanges(List<FreshIdRange> ranges) {
        var newRanges = new ArrayList<FreshIdRange>();

        var previousRange = ranges.removeFirst();

        while (!ranges.isEmpty()) {
            var nextRange = ranges.removeFirst();
            if (previousRange.end >= nextRange.start) {
                if (previousRange.end <= nextRange.end) {
                    previousRange = new FreshIdRange(previousRange.start, nextRange.end);
                }
            } else {
                newRanges.add(previousRange);
                previousRange = nextRange;
            }
        }

        newRanges.add(previousRange);

        return newRanges;
    }

    private static final class IDVerifier {

        private final List<FreshIdRange> freshIdRanges = new ArrayList<>();

        private boolean locked = false;

        public void addRange(FreshIdRange freshIdRange) {
            freshIdRanges.add(freshIdRange);
        }

        public boolean isIdValid(long id) {
            return freshIdRanges.stream().anyMatch(freshIdRange -> isIdInRange(id, freshIdRange));
        }

        private boolean isIdInRange(long id, FreshIdRange freshIdRange) {
            return freshIdRange.start <= id && id <= freshIdRange.end;
        }

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }
    }

    private record FreshIdRange(long start, long end) implements Comparable<FreshIdRange> {

        public long getFreshIds() {
            return end - start + 1;
        }

        @Override
        public int compareTo(FreshIdRange o) {
            return Comparator.comparingLong(FreshIdRange::start)
                             .thenComparing(FreshIdRange::end)
                             .compare(this, o);
        }
    }
}
