package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day12 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        return input.map(this::mapSpring)
                    .mapToLong(spring -> countPossibleArrangements(new HashMap<>(), spring, 0, 0))
                    .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        return input.map(this::mapUnfoldedSpring)
                    .mapToLong(spring -> countPossibleArrangements(new HashMap<>(), spring, 0, 0))
                    .sum();
    }

    private long countPossibleArrangements(Map<Key, Long> memo, Spring spring, int patternIndex, int arrangementIndex) {
        if (memo.containsKey(new Key(patternIndex, arrangementIndex))) {
            return memo.get(new Key(patternIndex, arrangementIndex));
        }

        if (arrangementIndex >= spring.arrangement().length) {
            if (patternIndex >= spring.pattern().length()) {
                return 1;
            } else if (spring.pattern().substring(patternIndex).contains("#")) {
                return 0;
            } else {
                return 1;
            }
        }

        var currentSpringSize = spring.arrangement()[arrangementIndex];

        var possibleArrangements = 0L;

        for (int i = patternIndex; i <= spring.pattern().length() - currentSpringSize; i++) {
            if (spring.pattern().charAt(i) != '.') {
                var possibleSpring = spring.pattern().substring(i, i + currentSpringSize);
                if (possibleSpring.contains(".")) {
                    if (possibleSpring.charAt(0) == '?') {
                        continue;
                    }
                    //invalid state, further looping is pointless.
                    break;
                } else {
                    //possible valid spring found, should have either . or ? after spring, if so, consume spring and find rest
                    if ((i + currentSpringSize == spring.pattern().length() || spring.pattern().charAt(i + currentSpringSize) != '#')) {
                        possibleArrangements +=
                                countPossibleArrangements(memo, spring, i + currentSpringSize + 1, arrangementIndex + 1);
                    }
                    if (possibleSpring.charAt(0) != '?') {
                        //if spring does not start with a ? we cannot make a . and move to next, so will end up invalid, and we can exit the loop
                        break;
                    }
                }
            }
        }

        memo.put(new Key(patternIndex, arrangementIndex), possibleArrangements);

        return possibleArrangements;
    }

    private Spring mapUnfoldedSpring(String line) {
        var split = line.split(" ");
        var newPattern = new StringBuilder(split[0]);
        var newArrangement = new StringBuilder(split[1]);

        for (int i = 0; i < 4; i++) {
            newPattern.append("?").append(split[0]);
            newArrangement.append(",").append(split[1]);
        }

        var arrangement = Arrays.stream(newArrangement.toString().split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray();

        return new Spring(newPattern.toString(), arrangement);
    }

    private Spring mapSpring(String line) {
        var split = line.split(" ");

        var arrangement = Arrays.stream(split[1].split(","))
                                .mapToInt(Integer::parseInt)
                                .toArray();

        return new Spring(split[0], arrangement);
    }

    private record Spring(String pattern, int[] arrangement) {
        @Override
        public String toString() {
            return "Spring{" +
                   "pattern='" + pattern + '\'' +
                   ", arrangement=" + Arrays.toString(arrangement) +
                   '}';
        }
    }

    private record Key(int patternIndex, int arrangementIndex) {
    }
}
