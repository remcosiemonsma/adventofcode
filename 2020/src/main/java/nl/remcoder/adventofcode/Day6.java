package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

public class Day6 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var lines = input.toList();

        var totalYesQuestions = 0;

        var answerKey = new HashSet<>();

        for (var line : lines) {
            if (line.isEmpty()) {
                totalYesQuestions += answerKey.size();
                answerKey = new HashSet<>();
            } else {
                line.chars()
                    .mapToObj(value -> (char) value)
                    .forEach(answerKey::add);
            }
        }

        totalYesQuestions += answerKey.size();

        return totalYesQuestions;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var answerKey = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        var answers = new HashSet<>(Arrays.asList(answerKey));

        var lines = input.toList();

        var totalYesQuestions = 0;

        for (var line : lines) {
            if (line.isEmpty()) {
                totalYesQuestions += answers.size();
                answers = new HashSet<>(Arrays.asList(answerKey));
            } else {
                answers.removeIf(character -> !line.contains(String.valueOf(character)));
            }
        }

        totalYesQuestions += answers.size();

        return totalYesQuestions;
    }
}
