package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day4 {
    private static final Pattern ROOM_REGEX = Pattern.compile("^(.+)-(\\d+)\\[(\\w+)]$");
    private static final char[] NORTH_POLE_STORAGE = "northpole object storage".toCharArray();

    public int handlePart1(Stream<String> input) {
        return input.map(ROOM_REGEX::matcher)
                    .filter(Matcher::matches)
                    .filter(this::isValidRoom)
                    .map(matcher -> matcher.group(2))
                    .mapToInt(Integer::parseInt)
                    .sum();
    }

    private boolean isValidRoom(Matcher matcher) {
        var roomName = matcher.group(1);
        var checksum = matcher.group(3);

        var roomchecksum = createChecksum(roomName.replace("-", ""));

        return roomchecksum.equals(checksum);
    }

    private String createChecksum(String roomName) {
        var characterAmounts = new HashMap<Character, Integer>();

        roomName.chars()
                .forEach(c -> characterAmounts.compute((char) c, (k, v) -> v == null ? 1 : v + 1));

        var characters = characterAmounts.entrySet()
                                         .stream()
                                         .map(entry -> new Pair(entry.getKey(), entry.getValue()))
                                         .sorted(Comparator.comparing(Pair::getAmount)
                                                           .reversed()
                                                           .thenComparing(Pair::getKey))
                                         .limit(5)
                                         .map(Pair::getKey)
                                         .toArray(Character[]::new);

        var chars = new char[5];

        chars[0] = characters[0];
        chars[1] = characters[1];
        chars[2] = characters[2];
        chars[3] = characters[3];
        chars[4] = characters[4];

        return new String(chars);
    }

    public int handlePart2(Stream<String> input) {
        return input.map(ROOM_REGEX::matcher)
                    .filter(Matcher::matches)
                    .filter(this::isValidRoom)
                    .filter(this::isRoomNorthPoleStorage)
                    .findFirst()
                    .map(matcher -> matcher.group(2))
                    .map(Integer::parseInt)
                    .orElse(0);
    }

    private boolean isRoomNorthPoleStorage(Matcher matcher) {
        String roomName = matcher.group(1);
        int cipher = Integer.parseInt(matcher.group(2)) % 26;

        char[] chars = roomName.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '-') {
                chars[i] = ' ';
                continue;
            }
            char newchar = (char) (chars[i] + cipher);
            if (newchar > 'z') {
                newchar -= 26;
            }
            chars[i] = newchar;
        }

        return Arrays.equals(NORTH_POLE_STORAGE, chars);
    }

    private static class Pair {
        private final Character key;
        private final Integer amount;

        private Pair(Character key, Integer amount) {
            this.key = key;
            this.amount = amount;
        }

        public Character getKey() {
            return key;
        }

        public Integer getAmount() {
            return amount;
        }
    }
}
