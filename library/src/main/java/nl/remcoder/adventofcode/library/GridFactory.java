package nl.remcoder.adventofcode.library;

import nl.remcoder.adventofcode.library.model.Grid;

import java.util.stream.Stream;

public class GridFactory {
    public static Grid<Boolean> createBooleanGridFromInput(Stream<String> input) {
        return new Grid<>(readBooleanInput(input));
    }

    private static Boolean[][] readBooleanInput(Stream<String> input) {
        return input.parallel()
                    .map(GridFactory::createBooleanLine)
                    .toArray(Boolean[][]::new);
    }

    public static Grid<Integer> createNumberedGridFromInput(Stream<String> input) {
        return new Grid<>(readNumberedInput(input));
    }

    private static Integer[][] readNumberedInput(Stream<String> input) {
        return input.parallel()
                    .map(GridFactory::createNumberedLine)
                    .toArray(Integer[][]::new);
    }

    private static Integer[] createNumberedLine(String s) {
        return s.chars()
                .parallel()
                .map(i -> Character.digit(i, 10))
                .boxed()
                .toArray(Integer[]::new);
    }

    private static Boolean[] createBooleanLine(String s) {
        return s.chars()
                .parallel()
                .mapToObj(c -> c == '#')
                .toArray(Boolean[]::new);
    }
}
