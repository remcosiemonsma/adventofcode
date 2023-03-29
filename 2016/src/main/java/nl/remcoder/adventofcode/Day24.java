package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Day24 implements AdventOfCodeSolution<Integer> {
    private int expectedSize;
    
    @Override
    public Integer handlePart1(Stream<String> input) {
        var data = input.map(string -> string.chars()
                                             .mapToObj(i -> (char) i)
                                             .toArray(Character[]::new))
                        .toArray(Character[][]::new);
        
        var grid = new Grid<>(data);
        
        var start = grid.findValue('0').orElseThrow(() -> new AssertionError("Eek!"));
        
        var steps = 0;
        
        var startState = new State(start, steps, Set.of((int) '0')); 
        
        var statesToCheck = new HashSet<State>();
        statesToCheck.add(startState);
        
        outer: while (true) {
            steps++;
            var newStates = new HashSet<State>();
            for (var state : statesToCheck) {
                for (var nextCoordinate : state.position().getStraightNeighbours()) {
                    if (grid.get(nextCoordinate) != '#') {
                        var newFound = new HashSet<>(state.foundWires);
                        if (Character.isDigit(grid.get(nextCoordinate))) {
                            newFound.add((int) grid.get(nextCoordinate));
                            if (newFound.size() == expectedSize) {
                                break outer;
                            }
                        }
                        newStates.add(new State(nextCoordinate, steps, newFound));
                    }
                }
            }
            statesToCheck = newStates;
        }

        return steps;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var data = input.map(string -> string.chars()
                                             .mapToObj(i -> (char) i)
                                             .toArray(Character[]::new))
                        .toArray(Character[][]::new);

        var grid = new Grid<>(data);

        var start = grid.findValue('0').orElseThrow(() -> new AssertionError("Eek!"));

        var steps = 0;

        var startState = new State(start, steps, Set.of((int) '0'));

        var statesToCheck = new HashSet<State>();
        statesToCheck.add(startState);

        outer: while (true) {
            steps++;
            var newStates = new HashSet<State>();
            for (var state : statesToCheck) {
                for (var nextCoordinate : state.position().getStraightNeighbours()) {
                    if (grid.get(nextCoordinate) != '#') {
                        var newFound = new HashSet<>(state.foundWires);
                        if (Character.isDigit(grid.get(nextCoordinate))) {
                            newFound.add((int) grid.get(nextCoordinate));
                            if (newFound.size() == 8 && nextCoordinate.equals(start)) {
                                break outer;
                            }
                        }
                        newStates.add(new State(nextCoordinate, steps, newFound));
                    }
                }
            }
            statesToCheck = newStates;
        }

        return steps;    
    }

    public void setExpectedSize(int expectedSize) {
        this.expectedSize = expectedSize;
    }

    private record State(Coordinate position, int steps, Set<Integer> foundWires) {}
}
