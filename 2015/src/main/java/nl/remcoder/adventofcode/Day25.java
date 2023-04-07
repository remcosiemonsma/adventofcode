package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day25 implements AdventOfCodeSolution<Long> {
    private static final Pattern PATTERN = Pattern.compile("To continue, please consult the code grid in the manual. {2}Enter the code at row (\\d*), column (\\d*).");

    @Override
    public Long handlePart1(Stream<String> input) {
        var line = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        var matcher = PATTERN.matcher(line);

        if (matcher.matches()) {
            var row = Integer.parseInt(matcher.group(1));
            var column = Integer.parseInt(matcher.group(2));

            var currentrow = 1;
            var currentcolumn = 1;

            var number = 20151125L;

            while (true) {
                currentrow--;
                currentcolumn++;
                number = (number * 252533) % 33554393;
                if (currentrow < 1) {
                    currentrow = currentcolumn;
                    currentcolumn = 1;
                }
                if (currentrow == row && currentcolumn == column) {
                    return number;
                }
            }
        }

        return 0L;
    }

    @Override
    public Long handlePart2(Stream<String> input) throws Exception {
        System.out.println("Merry Christmas!");
        return null;
    }
}
