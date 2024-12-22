package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.HashMap;
import java.util.stream.Stream;

public class Day22 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        return input.mapToLong(Long::parseLong)
                    .map(this::determineSecretNumber)
                    .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var sequenceResults = new HashMap<Sequence, Long>();

        input.mapToLong(Long::parseLong)
             .forEach(secret -> calculateSequences(secret, sequenceResults));

        return sequenceResults.values()
                              .stream()
                              .mapToLong(Long::longValue)
                              .max()
                              .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private void calculateSequences(long secret, HashMap<Sequence, Long> sequenceResults) {
        var monkeySequenceResults = new HashMap<Sequence, Long>();

        var firstSecret = generateNextSecret(secret);
        var secondSecret = generateNextSecret(firstSecret);
        var thirdSecret = generateNextSecret(secondSecret);
        var fourthSecret = generateNextSecret(thirdSecret);

        var change1 = determineChange(firstSecret, secret);
        var change2 = determineChange(secondSecret, firstSecret);
        var change3 = determineChange(thirdSecret, secondSecret);
        var change4 = determineChange(fourthSecret, thirdSecret);

        var sequence = new Sequence(change1, change2, change3, change4);

        var bananaPrice = fourthSecret % 10;

        monkeySequenceResults.putIfAbsent(sequence, bananaPrice);

        var previousSecret = fourthSecret;

        for (var i = 4; i < 2000; i++) {
            var currentSecret = generateNextSecret(previousSecret);

            var change = determineChange(currentSecret, previousSecret);

            sequence = new Sequence(sequence.change2(), sequence.change3(), sequence.change4(), change);
            bananaPrice = currentSecret % 10;

            monkeySequenceResults.putIfAbsent(sequence, bananaPrice);

            previousSecret = currentSecret;
        }

        for (var seq : monkeySequenceResults.keySet()) {
            sequenceResults.compute(seq, (key, bananas) -> bananas == null ? monkeySequenceResults.get(key) :
                                                           bananas + monkeySequenceResults.get(key));
        }
    }

    private long determineSecretNumber(long secret) {
        for (int i = 0; i < 2000; i++) {
            secret = generateNextSecret(secret);
        }

        return secret;
    }

    private long generateNextSecret(long secret) {
        var result = secret * 64;
        secret ^= result;
        secret %= 16777216;
        result = secret / 32;
        secret ^= result;
        secret %= 16777216;
        result = secret * 2048;
        secret ^= result;
        secret %= 16777216;

        return secret;
    }

    private int determineChange(long currentSecret, long previousSecret) {
        var previousDigit = (int) (previousSecret % 10);
        var currentDigit = (int) (currentSecret % 10);

        return currentDigit - previousDigit;
    }

    private record Sequence(int change1, int change2, int change3, int change4) {
    }
}
