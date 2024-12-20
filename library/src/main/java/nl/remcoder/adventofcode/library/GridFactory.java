package nl.remcoder.adventofcode.library;

import nl.remcoder.adventofcode.library.model.Grid;

import java.util.List;
import java.util.function.IntFunction;
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

    public static Grid<Character> createCharacterGridFromInput(Stream<String> input) {
        return new Grid<>(readCharacterInput(input));
    }

    public static <T> Grid<T> createTypedGridFromInput(Stream<String> input, IntFunction<T> mappingFunction) {
        return new Grid<>(readObjectInput(input, mappingFunction));
    }

    private static <T> List<List<T>> readObjectInput(Stream<String> input, IntFunction<T> mappingFunction) {
        return input.parallel()
                .map(line -> createObjectLine(line, mappingFunction))
                .toList();
    }

    private static Character[][] readCharacterInput(Stream<String> input) {
        return input.parallel()
                    .map(GridFactory::createCharacterLine)
                    .toArray(Character[][]::new);
    }

    private static Character[] createCharacterLine(String s) {
        return s.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
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

    private static <T> List<T> createObjectLine(String s, IntFunction<T> mappingFunction) {
        return s.chars()
                .parallel()
                .mapToObj(mappingFunction)
                .toList();
    }
}
