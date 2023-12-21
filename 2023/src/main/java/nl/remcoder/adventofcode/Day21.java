package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day21 implements AdventOfCodeSolution<Long> {
    private int steps = 64;

    public void setSteps(int steps) {
        this.steps = steps;
    }

    @Override
    public Long handlePart1(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var start = grid.findValue('S')
                        .orElseThrow(() -> new AssertionError("Eek!"));

        var spotsToVisit = new HashSet<Coordinate>();

        spotsToVisit.add(start);

        for (int step = 0; step < steps; step++) {
            var nextSpotsToVisit = new HashSet<Coordinate>();
            for (var spotToVisit : spotsToVisit) {
                for (var nextPossibleSpotToVisit : spotToVisit.getStraightNeighbours()) {
                    if (grid.isCoordinateInGrid(nextPossibleSpotToVisit) && grid.get(nextPossibleSpotToVisit) != '#') {
                        nextSpotsToVisit.add(nextPossibleSpotToVisit);
                    }
                }
            }
            spotsToVisit = nextSpotsToVisit;
        }

        return (long) spotsToVisit.size();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var start = grid.findValue('S')
                        .orElseThrow(() -> new AssertionError("Eek!"));

        var width = grid.getEndx() + 1L;

        var count = steps;
        var cycles = count / width;
        var remainder = count % width;

        Set<Coordinate> toExplore = new HashSet<>();

        toExplore.add(start);

        var regressionPoints = new ArrayList<Coordinate>();

        int steps = 0;
        for (int i = 0; i < 3; i++) {
            while (steps < i * width + remainder) {
                toExplore = toExplore.stream()
                                     .flatMap(p -> p.getStraightNeighbours().stream())
                                     .filter(p -> isOpenSpot(grid, p))
                                     .collect(Collectors.toSet());
                steps++;
            }
            regressionPoints.add(new Coordinate((i), toExplore.size()));
        }

        return quadraticCurve(cycles, regressionPoints);
    }

    private boolean isOpenSpot(Grid<Character> grid, Coordinate p) {
        var width = grid.getEndx() + 1;
        var height = grid.getEndy() + 1;

        int x = ((p.x() % width) + width) % width;
        int y = ((p.y() % height) + height) % height;
        return grid.get(x, y) != '#';
    }

    private long quadraticCurve(long cycles, List<Coordinate> regressionPoints) {
        double x1 = regressionPoints.get(0).x();
        double y1 = regressionPoints.get(0).y();
        double x2 = regressionPoints.get(1).x();
        double y2 = regressionPoints.get(1).y();
        double x3 = regressionPoints.get(2).x();
        double y3 = regressionPoints.get(2).y();
        return (long) (((cycles - x2) * (cycles - x3)) / ((x1 - x2) * (x1 - x3)) * y1 +
                       ((cycles - x1) * (cycles - x3)) / ((x2 - x1) * (x2 - x3)) * y2 +
                       ((cycles - x1) * (cycles - x2)) / ((x3 - x1) * (x3 - x2)) * y3);
    }
}
