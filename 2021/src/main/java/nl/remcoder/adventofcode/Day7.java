package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 {
    public int handlePart1(Stream<String> input) {
        List<Integer> positions = parseInput(input);

        return calculateCost(positions, this::calculateCostToPosition);
    }

    public int handlePart2(Stream<String> input) {
        List<Integer> positions = parseInput(input);

        return calculateCost(positions, this::calculateCostToPositionPart2);
    }

    private int calculateCost(List<Integer> positions, BiFunction<List<Integer>, Integer, Integer> costCalculator) {
        int lowestPosition = positions.get(0);
        int highestPosition = positions.get(positions.size() - 1);

        int step = (highestPosition - lowestPosition) / 2;
        int middle = lowestPosition + step;

        boolean cheapestFound = false;

        int cost = -1;
        while (!cheapestFound) {
            cost = costCalculator.apply(positions, middle);
            int costUp = costCalculator.apply(positions, middle + 1);
            int costDown = costCalculator.apply(positions, middle - 1);

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

    private List<Integer> parseInput(Stream<String> input) {
        return input.map(s -> s.split(","))
                    .flatMap(Arrays::stream)
                    .map(Integer::parseInt)
                    .sorted()
                    .toList();
    }

    private int calculateCostToPosition(List<Integer> positions, int requestedPosition) {
        int cost = 0;

        for (int position : positions) {
            cost += Math.abs(position - requestedPosition);
        }

        return cost;
    }

    private int calculateCostToPositionPart2(List<Integer> positions, int requestedPosition) {
        int cost = 0;

        for (int position : positions) {
            cost += sumNumbers(Math.abs(position - requestedPosition));
        }

        return cost;
    }

    private int sumNumbers(int number) {
        return (number * (number + 1)) / 2;
    }
}
