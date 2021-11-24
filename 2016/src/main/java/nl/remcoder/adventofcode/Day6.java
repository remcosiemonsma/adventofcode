package nl.remcoder.adventofcode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {
    public String handlePart1(Stream<String> input) {
        List<char[]> messages = input.map(String::toCharArray)
                                     .collect(Collectors.toList());

        List<Map<Character, Integer>> characterAmounts = new ArrayList<>();

        for (char ignored : messages.get(0)) {
            characterAmounts.add(new HashMap<>());
        }

        for (char[] message : messages) {
            for (int position = 0; position < message.length; position++) {
                characterAmounts.get(position)
                                .compute(message[position], (key, value) -> value == null ? 1 : value + 1);
            }
        }

        char[] message = new char[characterAmounts.size()];

        for (int position = 0; position < characterAmounts.size(); position++) {
            message[position] = characterAmounts.get(position)
                                                .entrySet()
                                                .stream()
                                                .max(Map.Entry.comparingByValue())
                                                .get()
                                                .getKey();
        }

        return new String(message);
    }

    public String handlePart2(Stream<String> input) {
        List<char[]> messages = input.map(String::toCharArray)
                                     .collect(Collectors.toList());

        List<Map<Character, Integer>> characterAmounts = new ArrayList<>();

        for (char ignored : messages.get(0)) {
            characterAmounts.add(new HashMap<>());
        }

        for (char[] message : messages) {
            for (int position = 0; position < message.length; position++) {
                characterAmounts.get(position)
                                .compute(message[position], (key, value) -> value == null ? 1 : value + 1);
            }
        }

        char[] message = new char[characterAmounts.size()];

        for (int position = 0; position < characterAmounts.size(); position++) {
            message[position] = characterAmounts.get(position)
                                                .entrySet()
                                                .stream()
                                                .min(Map.Entry.comparingByValue())
                                                .get()
                                                .getKey();
        }

        return new String(message);
    }
}
