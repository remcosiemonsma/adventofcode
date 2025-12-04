package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Day4 implements AdventOfCodeSolution<Long> {

    @Override
    public Long handlePart1(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        return grid.coordinates()
                   .stream()
                   .filter(coordinate -> grid.get(coordinate) == '@')
                   .filter(coordinate -> isRoleAccessible(grid, coordinate))
                   .count();
    }

    private boolean isRoleAccessible(Grid<Character> grid, Coordinate coordinate) {
        return coordinate.getAllNeighbours()
                         .stream()
                         .filter(coordinate1 -> Objects.equals('@', grid.get(coordinate1)))
                         .count() < 4;
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var rolesRemoved = 0L;

        List<Coordinate> rolesToRemove;
        do {
            rolesToRemove = grid.coordinates()
                                .stream()
                                .filter(coordinate -> grid.get(coordinate) == '@')
                                .filter(coordinate -> isRoleAccessible(grid, coordinate))
                                .toList();

            rolesToRemove.forEach(coordinate -> grid.set(coordinate, '.'));

            rolesRemoved += rolesToRemove.size();
        } while (!rolesToRemove.isEmpty());

        return rolesRemoved;
    }
}
