package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;

import java.util.*;
import java.util.stream.Stream;

public class Day10 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = GridFactory.createNumberedGridFromInput(input);

        var startingPoints = grid.findValues(0);

        var totalScore = 0;

        for (var startingPoint : startingPoints) {
            var currentStep = 0;
            var currentPoints = Set.of(startingPoint);
            while (currentStep < 9) {
                currentStep++;
                var nextPoints = new HashSet<Coordinate>();
                for (var currentPoint : currentPoints) {
                    int finalCurrentStep = currentStep;
                    currentPoint.getStraightNeighbours()
                                .stream()
                                .filter(grid::isCoordinateInGrid)
                                .filter(point -> grid.get(point) == finalCurrentStep)
                                .forEach(nextPoints::add);
                }
                currentPoints = nextPoints;
            }
            totalScore += currentPoints.size();
        }

        return totalScore;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = GridFactory.createNumberedGridFromInput(input);

        var startingPoints = grid.findValues(0);

        var totalScore = 0;

        for (var startingPoint : startingPoints) {
            var currentStep = 0;
            var currentPoints = List.of(startingPoint);
            while (currentStep < 9) {
                currentStep++;
                var nextPoints = new ArrayList<Coordinate>();
                for (var currentPoint : currentPoints) {
                    int finalCurrentStep = currentStep;
                    currentPoint.getStraightNeighbours()
                                .stream()
                                .filter(grid::isCoordinateInGrid)
                                .filter(point -> grid.get(point) == finalCurrentStep)
                                .forEach(nextPoints::add);
                }
                currentPoints = nextPoints;
            }
            totalScore += currentPoints.size();
        }

        return totalScore;
    }
}
