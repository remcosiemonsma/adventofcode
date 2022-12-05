package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day10 {
    public int handlePart1(Stream<String> input) {
        var sequence = input.findFirst()
                            .orElseThrow(() -> new AssertionError("Eek!"));

        for (var round = 0; round < 40; round++) {
            sequence = playRound(sequence);
        }

        return sequence.length();
    }

    public int handlePart2(Stream<String> input) {
        var sequence = input.findFirst()
                            .orElseThrow(() -> new AssertionError("Eek!"));

        for (var round = 0; round < 50; round++) {
            sequence = playRound(sequence);
        }

        return sequence.length();
    }

    private String playRound(String sequence) {
        var sequenceBuilder = new StringBuilder();

        char previouschar = sequence.charAt(0);
        int amountOfChars = 1;

        for (var position = 1; position < sequence.length(); position++) {
            var nextchar = sequence.charAt(position);

            if (nextchar == previouschar) {
                amountOfChars++;
            } else {
                sequenceBuilder.append(amountOfChars);
                sequenceBuilder.append(previouschar);
                amountOfChars = 1;
            }

            previouschar = nextchar;
        }

        sequenceBuilder.append(amountOfChars);
        sequenceBuilder.append(previouschar);

        sequence = sequenceBuilder.toString();
        return sequence;
    }
}
