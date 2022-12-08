package nl.remcoder.adventofcode.library;

import nl.remcoder.adventofcode.library.model.Grid;

import java.util.stream.Stream;

public class GridFactory {
    public static Grid<Boolean> createBooleanGridFromInput(Stream<String> input) {
        return new Grid<>(readBooleanInput(input));
    }

    private static Boolean[][] readBooleanInput(Stream<String> input) {
        return input.map(GridFactory::createLine)
                    .toArray(Boolean[][]::new);
    }

    public static Grid<Integer> createNumberedGridFromInput(Stream<String> input) {
        return new Grid<>(readNumberedInput(input));
    }

    private static Integer[][] readNumberedInput(Stream<String> input) {
        return input.map(s -> s.chars()
                               .map(i -> Character.digit(i, 10))
                               .boxed()
                               .toArray(Integer[]::new))
                    .toArray(Integer[][]::new);
    }

    private static Boolean[] createLine(String s) {
        Boolean[] line = new Boolean[s.length()];
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            line[i] = c == '#';
        }
        return line;
    }
}
