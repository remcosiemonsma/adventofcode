package nl.remcoder.adventofcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day25 {
    private static final Pattern PATTERN = Pattern.compile("To continue, please consult the code grid in the manual.  Enter the code at row (\\d*), column (\\d*).");

    public long handlePart1(Stream<String> input) {
        String line = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        Matcher matcher = PATTERN.matcher(line);

        if (matcher.matches()) {
            int row = Integer.parseInt(matcher.group(1));
            int column = Integer.parseInt(matcher.group(2));

            int currentrow = 1;
            int currentcolumn = 1;

            long number = 20151125;

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
}
