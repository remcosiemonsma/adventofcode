package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day5 {
    public long handlePart1(Stream<String> input) {
        return input.filter(line -> !(line.contains("ab") || line.contains("cd") || line.contains("pq") ||
                                      line.contains("xy")))
                    .filter(line -> line.chars()
                                        .filter(value -> value == 'a' || value == 'e' || value == 'o' || value == 'i' ||
                                                         value == 'u')
                                        .count() >= 3)
                    .map(String::toCharArray)
                    .filter(chars -> {
                        char previouschar = chars[0];
                        boolean niceString = false;

                        for (int i = 1; i < chars.length; i++) {
                            if (chars[i] == previouschar) {
                                niceString = true;
                                break;
                            }
                            previouschar = chars[i];
                        }

                        return niceString;
                    })
                    .count();
    }

    public long handlePart2(Stream<String> input) {
        return input.filter(this::doesStringFulfillFirstRule)
                    .filter(this::doesStringFulfillSecondRule)
                    .count();
    }

    private boolean doesStringFulfillFirstRule(String string) {
        char[] chars = string.toCharArray();

        for (int i = 0; i < chars.length - 2; i++) {
            String currentchars = new String(new char[]{chars[i], chars[i + 1]});

            for (int j = i + 2; j < chars.length - 1; j++) {
                String otherchars = new String(new char[]{chars[j], chars[j + 1]});

                if (currentchars.equals(otherchars)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean doesStringFulfillSecondRule(String string) {
        char[] chars = string.toCharArray();

        for (int i = 0; i < chars.length - 2; i++) {
            if (chars[i] == chars[i + 2]) {
                return true;
            }
        }
        return false;
    }
}
