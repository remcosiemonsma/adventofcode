package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.*;
import java.util.stream.Stream;

public class Day14 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var roundRocks = grid.findValues('O');

        return roundRocks.stream()
                         .sorted(Comparator.comparing(Coordinate::y).thenComparing(Coordinate::x))
                         .map(rock -> rollNorth(rock, grid))
                         .mapToInt(rock -> calculateWeight(rock, grid.getEndy()))
                         .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var roundRocks = grid.findValues('O');

        var states = new ArrayList<Set<Coordinate>>();

        var cycleFound = false;

        while (!cycleFound) {
            roundRocks = roundRocks.stream()
                                   .sorted(Comparator.comparing(Coordinate::y).thenComparing(Coordinate::x))
                                   .map(rock -> rollNorth(rock, grid))
                                   .toList();

            roundRocks = roundRocks.stream()
                                   .sorted(Comparator.comparing(Coordinate::x).thenComparing(Coordinate::y))
                                   .map(rock -> rollWest(rock, grid))
                                   .toList();

            roundRocks = roundRocks.stream()
                                   .sorted(Comparator.comparing(Coordinate::y).reversed().thenComparing(Coordinate::x))
                                   .map(rock -> rollSouth(rock, grid))
                                   .toList();

            roundRocks = roundRocks.stream()
                                   .sorted(Comparator.comparing(Coordinate::x).reversed().thenComparing(Coordinate::y))
                                   .map(rock -> rollEast(rock, grid))
                                   .toList();

            var state = new HashSet<>(roundRocks);
            if (states.contains(state)) {
                cycleFound = true;
            }
            states.add(state);
        }

        var lastState = states.removeLast();

        var cycleStartIndex = states.indexOf(lastState);

        var cycleSize = states.size() - cycleStartIndex;

        var remainingCycles = (1000000000 - (cycleStartIndex)) % cycleSize;

        var finalState = states.get((cycleStartIndex + remainingCycles) - 1);

        return finalState.stream()
                         .mapToInt(rock -> calculateWeight(rock, grid.getEndy()))
                         .sum();
    }

    private Coordinate rollNorth(Coordinate rock, Grid<Character> grid) {
        grid.set(rock, '.');

        while (grid.isCoordinateInGrid(rock.above()) && grid.get(rock.above()) == '.') {
            rock = rock.above();
        }

        grid.set(rock, 'O');

        return rock;
    }

    private Coordinate rollSouth(Coordinate rock, Grid<Character> grid) {
        grid.set(rock, '.');

        while (grid.isCoordinateInGrid(rock.below()) && grid.get(rock.below()) == '.') {
            rock = rock.below();
        }

        grid.set(rock, 'O');

        return rock;
    }

    private Coordinate rollWest(Coordinate rock, Grid<Character> grid) {
        grid.set(rock, '.');

        while (grid.isCoordinateInGrid(rock.left()) && grid.get(rock.left()) == '.') {
            rock = rock.left();
        }

        grid.set(rock, 'O');

        return rock;
    }

    private Coordinate rollEast(Coordinate rock, Grid<Character> grid) {
        grid.set(rock, '.');

        while (grid.isCoordinateInGrid(rock.right()) && grid.get(rock.right()) == '.') {
            rock = rock.right();
        }

        grid.set(rock, 'O');

        return rock;
    }

    private int calculateWeight(Coordinate rock, int endY) {
        return endY - rock.y() + 1;
    }
}
