package nl.remcoder.adventofcode;

import java.util.*;
import java.util.stream.Stream;

public class Day6 {
    public String handlePart1(Stream<String> input) {
        var messages = input.map(String::toCharArray)
                            .toList();

        var characterAmounts = new ArrayList<Map<Character, Integer>>();

        for (char ignored : messages.get(0)) {
            characterAmounts.add(new HashMap<>());
        }

        for (char[] message : messages) {
            for (var position = 0; position < message.length; position++) {
                characterAmounts.get(position)
                                .compute(message[position], (key, value) -> value == null ? 1 : value + 1);
            }
        }

        var message = new char[characterAmounts.size()];

        for (var position = 0; position < characterAmounts.size(); position++) {
            message[position] = characterAmounts.get(position)
                                                .entrySet()
                                                .stream()
                                                .max(Map.Entry.comparingByValue())
                                                .orElseThrow(() -> new AssertionError("Eek!"))
                                                .getKey();
        }

        return new String(message);
    }

    public String handlePart2(Stream<String> input) {
        var messages = input.map(String::toCharArray)
                            .toList();

        var characterAmounts = new ArrayList<Map<Character, Integer>>();

        for (var ignored : messages.get(0)) {
            characterAmounts.add(new HashMap<>());
        }

        for (var message : messages) {
            for (var position = 0; position < message.length; position++) {
                characterAmounts.get(position)
                                .compute(message[position], (key, value) -> value == null ? 1 : value + 1);
            }
        }

        var message = new char[characterAmounts.size()];

        for (var position = 0; position < characterAmounts.size(); position++) {
            message[position] = characterAmounts.get(position)
                                                .entrySet()
                                                .stream()
                                                .min(Map.Entry.comparingByValue())
                                                .orElseThrow(() -> new AssertionError("Eek!"))
                                                .getKey();
        }

        return new String(message);
    }
}
