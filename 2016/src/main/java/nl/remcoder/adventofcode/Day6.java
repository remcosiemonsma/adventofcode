package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.stream.Stream;

public class Day6 implements AdventOfCodeSolution<String> {
    @Override
    public String handlePart1(Stream<String> input) {
        var characterAmounts = getCharacterAmounts(input);

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

    @Override
    public String handlePart2(Stream<String> input) {
        var characterAmounts = getCharacterAmounts(input);

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

    private List<Map<Character, Integer>> getCharacterAmounts(Stream<String> input) {
        var messages = input.map(String::toCharArray)
                            .toList();

        var characterAmounts = new ArrayList<Map<Character, Integer>>();

        for (var i = 0; i < messages.get(0).length; i++) {
            characterAmounts.add(new HashMap<>());
        }

        for (var message : messages) {
            for (var position = 0; position < message.length; position++) {
                characterAmounts.get(position)
                                .compute(message[position], (key, value) -> value == null ? 1 : value + 1);
            }
        }
        return characterAmounts;
    }
}
