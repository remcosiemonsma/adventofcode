package nl.remcoder.adventofcode;

import java.util.*;
import java.util.stream.Stream;

public class Day7 {
    public int handlePart1(Stream<String> input) {
        return (int) input.filter(this::doesIpSupportTLS)
                          .count();
    }

    public int handlePart2(Stream<String> input) {
        return (int) input.filter(this::doesIpSupportSSL)
                          .count();
    }

    private boolean doesIpSupportTLS(String ip) {
        List<String> hypernetSequences = extractHyperNetSequences(ip);
        List<String> nonHypernetSequences = extractNonHyperNetSequences(ip);

        if (hypernetSequences.stream()
                             .anyMatch(this::partContainsABBA)) {
            return false;
        }
        return nonHypernetSequences.stream()
                                   .anyMatch(this::partContainsABBA);
    }

    private boolean doesIpSupportSSL(String ip) {
        List<String> hypernetSequences = extractHyperNetSequences(ip);
        List<String> nonHypernetSequences = extractNonHyperNetSequences(ip);

        Set<String> babPatterns = findBABPatterns(nonHypernetSequences);

        return hypernetSequences.stream()
                                .anyMatch(sequence -> this.doesSequenceContainAnyBABPattern(sequence, babPatterns));
    }

    private boolean doesSequenceContainAnyBABPattern(String sequence, Set<String> babPatterns) {
        for (String bab : babPatterns) {
            if (sequence.contains(bab)) {
                return true;
            }
        }
        return false;
    }

    private Set<String> findBABPatterns(List<String> sequences) {
        Set<String> patterns = new HashSet<>();

        for (String sequence : sequences) {
            char[] chars = sequence.toCharArray();

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
        char[] chars = part.toCharArray();

        for (int position = 0; position <= chars.length - 4; position++) {
            char c1 = chars[position];
            char c2 = chars[position + 1];
            char c3 = chars[position + 2];
            char c4 = chars[position + 3];

            if (c1 == c4 && c2 == c3 && c1 != c2) {
                return true;
            }
        }
        return false;
    }
}
