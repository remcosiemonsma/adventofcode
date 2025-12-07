package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

public class Day7 implements BiAdventOfCodeSolution<Integer, Long> {

    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.gather(Gatherer.<String, Set<Integer>, Integer>ofSequential(
                            HashSet::new,
                            (beams, line, downstream) -> {
                                if (line.indexOf('S') > 0) {
                                    beams.add(line.indexOf('S'));
                                    return true;
                                }
                                if (line.indexOf('^') < 0) {
                                    return true;
                                }

                                var splitterPosition = line.indexOf('^');
                                var splits = 0;
                                while (splitterPosition > 0) {
                                    if (beams.remove(splitterPosition)) {
                                        beams.add(splitterPosition - 1);
                                        beams.add(splitterPosition + 1);
                                        splits++;
                                    }
                                    splitterPosition = line.indexOf('^', splitterPosition + 1);
                                }
                                downstream.push(splits);
                                return true;
                            }))
                    .mapToInt(value -> value)
                    .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var start = grid.findValue('S').orElseThrow(() -> new AssertionError("Eek!"));
        var splitters = grid.findValues('^');

        var beams = new ArrayList<Coordinate>();

        for (var x = 0; x <= grid.getEndx(); x++) {
            beams.add(new Coordinate(x, grid.getEndy()));
        }

        return beams.stream()
                       .mapToLong(beam -> countWaysToPoint(beam, splitters, start, new HashMap<>()))
                       .sum();
    }

    private long countWaysToPoint(Coordinate point, List<Coordinate> splitters, Coordinate start, Map<Coordinate, Long> memo) {
        if (memo.containsKey(point)) {
            return memo.get(point);
        }
        var newPoint = point.above();
        if (splitters.contains(newPoint)) {
            return 0L;
        }
        if (newPoint.y() == 0) {
            if (newPoint.equals(start)) {
                return 1L;
            } else {
                return 0L;
            }
        }
        var ways = 0L;
        if (splitters.contains(newPoint.left())) {
            ways += countWaysToPoint(newPoint.left(), splitters, start, memo);
        }
        if (splitters.contains(newPoint.right())) {
            ways += countWaysToPoint(newPoint.right(), splitters, start, memo);
        }
        ways += countWaysToPoint(newPoint, splitters, start, memo);
        memo.put(point, ways);
        return ways;
    }
}
