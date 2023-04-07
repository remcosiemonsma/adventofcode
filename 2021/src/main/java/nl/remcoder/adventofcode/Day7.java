package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Day7 {
    public int handlePart1(Stream<String> input) {
        var positions = input.map(s -> s.split(","))
                             .flatMap(Arrays::stream)
                             .map(Integer::parseInt)
                             .sorted()
                             .toList();

        return calculateCost(positions, this::calculateCostToPosition);
    }

    public int handlePart2(Stream<String> input) {
        var positions = input.map(s -> s.split(","))
                             .flatMap(Arrays::stream)
                             .map(Integer::parseInt)
                             .sorted()
                             .toList();

        return calculateCost(positions, this::calculateCostToPositionPart2);
    }

    private int calculateCost(List<Integer> positions, BiFunction<List<Integer>, Integer, Integer> costCalculator) {
        var lowestPosition = positions.get(0);
        var highestPosition = positions.get(positions.size() - 1);

        var step = (highestPosition - lowestPosition) / 2;
        var middle = lowestPosition + step;

        var cheapestFound = false;

        var cost = -1;
        while (!cheapestFound) {
            cost = costCalculator.apply(positions, middle);
            var costUp = costCalculator.apply(positions, middle + 1);
            var costDown = costCalculator.apply(positions, middle - 1);

            step = Math.max(step / 2, 1);

            if (cost < costDown && cost < costUp) {
                cheapestFound = true;
            } else if (cost < costDown) {
                middle += step;
            } else if (cost < costUp) {
                middle -= step;
            }
        }
        return cost;
    }

    private int calculateCostToPosition(List<Integer> positions, int requestedPosition) {
        var cost = 0;

        for (var position : positions) {
            cost += Math.abs(position - requestedPosition);
        }

        return cost;
    }

    private int calculateCostToPositionPart2(List<Integer> positions, int requestedPosition) {
        int cost = 0;

        for (var position : positions) {
            cost += sumNumbers(Math.abs(position - requestedPosition));
        }

        return cost;
    }

    private int sumNumbers(int number) {
        return (number * (number + 1)) / 2;
    }
}
