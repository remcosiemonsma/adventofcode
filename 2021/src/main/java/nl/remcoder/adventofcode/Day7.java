package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 {
    public int handlePart1(Stream<String> input) {
        List<Integer> positions = input.map(s -> s.split(","))
                                       .flatMap(Arrays::stream)
                                       .map(Integer::parseInt)
                                       .sorted()
                                       .collect(Collectors.toList());

        int lowestPosition = positions.get(0);
        int highestPosition = positions.get(positions.size() - 1);

        int step = (highestPosition - lowestPosition) / 2;
        int middle = lowestPosition + step;

        boolean cheapestFound = false;

        while (!cheapestFound) {
            int cost = calculateCostToPosition(positions, middle);
            int costUp = calculateCostToPosition(positions, middle + 1);
            int costDown = calculateCostToPosition(positions, middle - 1);

            step = Math.max(step / 2, 1);

            if (cost < costDown && cost < costUp) {
                cheapestFound = true;
            } else if (cost < costDown) {
                middle += step;
            } else if (cost < costUp) {
                middle -= step;
            }
        }

        return calculateCostToPosition(positions, middle);
    }

    public int handlePart2(Stream<String> input) {
        List<Integer> positions = input.map(s -> s.split(","))
                                       .flatMap(Arrays::stream)
                                       .map(Integer::parseInt)
                                       .sorted()
                                       .collect(Collectors.toList());

        int lowestPosition = positions.get(0);
        int highestPosition = positions.get(positions.size() - 1);

        int step = (highestPosition - lowestPosition) / 2;
        int middle = lowestPosition + step;

        boolean cheapestFound = false;

        while (!cheapestFound) {
            int cost = calculateCostToPositionPart2(positions, middle);
            int costUp = calculateCostToPositionPart2(positions, middle + 1);
            int costDown = calculateCostToPositionPart2(positions, middle - 1);

            step = Math.max(step / 2, 1);

            if (cost < costDown && cost < costUp) {
                cheapestFound = true;
            } else if (cost < costDown) {
                middle += step;
            } else if (cost < costUp) {
                middle -= step;
            }
        }

        return calculateCostToPositionPart2(positions, middle);    }

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
