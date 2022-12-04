package nl.remcoder.adventofcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day4 {
    private final static Pattern ASSIGNMENT = Pattern.compile("(\\d*)-(\\d*),(\\d*)-(\\d*)");

    public long handlePart1(Stream<String> input) {
        return input.map(ASSIGNMENT::matcher)
                    .filter(Matcher::matches)
                    .map(this::mapToPair)
                    .filter(this::isOverlapPart1)
                    .count();
    }

    public long handlePart2(Stream<String> input) {
        return input.map(ASSIGNMENT::matcher)
                    .filter(Matcher::matches)
                    .map(this::mapToPair)
                    .filter(this::isOverlapPart2)
                    .count();
    }

    private boolean isOverlapPart1(AssignmentPair assignmentPair) {
        return assignmentPair.start1() >= assignmentPair.start2() && assignmentPair.end1() <= assignmentPair.end2() ||
               assignmentPair.start2() >= assignmentPair.start1() && assignmentPair.end2() <= assignmentPair.end1();
    }

    private boolean isOverlapPart2(AssignmentPair assignmentPair) {
        return (assignmentPair.start1 >= assignmentPair.start2 && assignmentPair.start1 <= assignmentPair.end2) ||
               (assignmentPair.end1 >= assignmentPair.start2 && assignmentPair.end1 <= assignmentPair.end2) ||
               (assignmentPair.start2 >= assignmentPair.start1 && assignmentPair.start2 <= assignmentPair.end1) ||
               (assignmentPair.end2 >= assignmentPair.start1 && assignmentPair.end2 <= assignmentPair.end1);
    }

    private AssignmentPair mapToPair(Matcher matcher) {
        return new AssignmentPair(Integer.parseInt(matcher.group(1)),
                                  Integer.parseInt(matcher.group(2)),
                                  Integer.parseInt(matcher.group(3)),
                                  Integer.parseInt(matcher.group(4)));
    }

    private record AssignmentPair(int start1, int end1, int start2, int end2) {
    }
}
