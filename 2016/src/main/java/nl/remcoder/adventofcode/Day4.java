package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.stream.CountingCollector;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day4 implements AdventOfCodeSolution<Integer> {
    private static final Pattern ROOM_REGEX = Pattern.compile("^(.+)-(\\d+)\\[(\\w+)]$");
    private static final char[] NORTH_POLE_STORAGE = "northpole object storage".toCharArray();

    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.map(ROOM_REGEX::matcher)
                    .filter(Matcher::matches)
                    .filter(this::isValidRoom)
                    .map(matcher -> matcher.group(2))
                    .mapToInt(Integer::parseInt)
                    .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return input.map(ROOM_REGEX::matcher)
                    .filter(Matcher::matches)
                    .filter(this::isValidRoom)
                    .filter(this::isRoomNorthPoleStorage)
                    .findFirst()
                    .map(matcher -> matcher.group(2))
                    .map(Integer::parseInt)
                    .orElse(0);
    }

    private boolean isValidRoom(Matcher matcher) {
        var roomName = matcher.group(1);
        var checksum = matcher.group(3);

        var roomchecksum = createChecksum(roomName.replace("-", ""));

        return roomchecksum.equals(checksum);
    }

    private String createChecksum(String roomName) {
        var characterAmounts = roomName.chars()
                                       .mapToObj(value -> (char) value)
                                       .collect(new CountingCollector<>());

        var characters = characterAmounts.entrySet()
                                         .stream()
                                         .sorted(Comparator.comparing(Map.Entry<Character, Integer>::getValue)
                                                           .reversed()
                                                           .thenComparing(Map.Entry::getKey))
                                         .limit(5)
                                         .map(Map.Entry::getKey)
                                         .toArray(Character[]::new);

        var chars = new char[5];

        chars[0] = characters[0];
        chars[1] = characters[1];
        chars[2] = characters[2];
        chars[3] = characters[3];
        chars[4] = characters[4];

        return new String(chars);
    }

    private boolean isRoomNorthPoleStorage(Matcher matcher) {
        var roomName = matcher.group(1);
        var cipher = Integer.parseInt(matcher.group(2)) % 26;

        var chars = roomName.toCharArray();

        for (var i = 0; i < chars.length; i++) {
            if (chars[i] == '-') {
                chars[i] = ' ';
                continue;
            }
            var newchar = (char) (chars[i] + cipher);
            if (newchar > 'z') {
                newchar -= 26;
            }
            chars[i] = newchar;
        }

        return Arrays.equals(NORTH_POLE_STORAGE, chars);
    }
}
