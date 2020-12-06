package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {
    public int handlePart1(Stream<String> input) {
        List<String> lines = input.collect(Collectors.toList());

        int totalYesQuestions = 0;

        Set<Character> answerKey = new HashSet<>();

        for (String line : lines) {
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

    public int handlePart2(Stream<String> input) {
        Character[] answerKey = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        Set<Character> answers = new HashSet<>(Arrays.asList(answerKey));

        List<String> lines = input.collect(Collectors.toList());

        int totalYesQuestions = 0;

        for (String line : lines) {
            if (line.isEmpty()) {
                totalYesQuestions += answers.size();
                answers = new HashSet<>(Arrays.asList(answerKey));
            } else {
                answers.removeIf(character -> !line.contains("" + character));
            }
        }

        totalYesQuestions += answers.size();

        return totalYesQuestions;
    }
}
