package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day15 implements AdventOfCodeSolution<Integer> {
    private static final Pattern DISC_PATTERN =
            Pattern.compile("Disc #\\d has (\\d+) positions; at time=0, it is at position (\\d+).");

    @Override
    public Integer handlePart1(Stream<String> input) {
        var discs = input.map(DISC_PATTERN::matcher)
                         .filter(Matcher::matches)
                         .map(this::parseDisc)
                         .toList();

        return rotate(discs);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var discs = input.map(DISC_PATTERN::matcher)
                         .filter(Matcher::matches)
                         .map(this::parseDisc)
                         .toList();
        
        discs = new ArrayList<>(discs);
        
        discs.add(new Disc(11, 0));

        return rotate(discs);
    }

    private static int rotate(List<Disc> discs) {
        var rotations = 1;

        var totalRotations = 0;

        for (var i = 0; i < discs.size(); i++) {
            var disc = discs.get(i);

            var targetPosition = disc.positions - i - 1;

            int iterations = 0;

            while ((disc.startingPosition + totalRotations + (rotations * iterations)) % disc.positions != targetPosition) {
                iterations++;
            }

            totalRotations += iterations * rotations;
            rotations *= disc.positions;
        }

        return totalRotations;
    }

    private Disc parseDisc(Matcher matcher) {
        return new Disc(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
    }

    private record Disc(int positions, int startingPosition) {
    }
}
