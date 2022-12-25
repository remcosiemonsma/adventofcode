package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.stream.Stream;

public class Day7 implements AdventOfCodeSolution<Long> {
    public Long handlePart1(Stream<String> input) {
        return input.filter(this::doesIpSupportTLS)
                    .count();
    }

    public Long handlePart2(Stream<String> input) {
        return input.filter(this::doesIpSupportSSL)
                    .count();
    }

    private boolean doesIpSupportTLS(String ip) {
        var hypernetSequences = extractHyperNetSequences(ip);
        var nonHypernetSequences = extractNonHyperNetSequences(ip);

        if (hypernetSequences.stream()
                             .anyMatch(this::partContainsABBA)) {
            return false;
        }
        return nonHypernetSequences.stream()
                                   .anyMatch(this::partContainsABBA);
    }

    private boolean doesIpSupportSSL(String ip) {
        var hypernetSequences = extractHyperNetSequences(ip);
        var nonHypernetSequences = extractNonHyperNetSequences(ip);

        var babPatterns = findBABPatterns(nonHypernetSequences);

        return hypernetSequences.stream()
                                .anyMatch(sequence -> this.doesSequenceContainAnyBABPattern(sequence, babPatterns));
    }

    private boolean doesSequenceContainAnyBABPattern(String sequence, Set<String> babPatterns) {
        for (var bab : babPatterns) {
            if (sequence.contains(bab)) {
                return true;
            }
        }
        return false;
    }

    private Set<String> findBABPatterns(List<String> sequences) {
        var patterns = new HashSet<String>();

        for (var sequence : sequences) {
            var chars = sequence.toCharArray();

            for (int position = 0; position <= chars.length - 3; position++) {
                char c1 = chars[position];
                char c2 = chars[position + 1];
                char c3 = chars[position + 2];

                if (c1 == c3 && c1 != c2) {
                    patterns.add(new String(new char[]{c2, c1, c2}));
                }
            }
        }

        return patterns;
    }

    private List<String> extractHyperNetSequences(String ip) {
        return Arrays.asList(ip.replaceAll("(\\w+\\[)|(\\w+$)", "").split("]"));
    }

    private List<String> extractNonHyperNetSequences(String ip) {
        return Arrays.asList(ip.replaceAll("\\[\\w+", "").split("]"));
    }

    private boolean partContainsABBA(String part) {
        var chars = part.toCharArray();

        for (var position = 0; position <= chars.length - 4; position++) {
            var c1 = chars[position];
            var c2 = chars[position + 1];
            var c3 = chars[position + 2];
            var c4 = chars[position + 3];

            if (c1 == c4 && c2 == c3 && c1 != c2) {
                return true;
            }
        }
        return false;
    }
}
