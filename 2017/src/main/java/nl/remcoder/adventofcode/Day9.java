package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day9 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var line = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));
        
        var nesting = 0;
        var skipNext = false;
        var garbage = false;
        var totalScore = 0;

        for (var c : line.toCharArray()) {
            if (!skipNext) {
                switch (c) {
                    case '{' -> {
                        if (!garbage) {
                            nesting++;
                        }
                    }
                    case '}' -> {
                        if (!garbage) {
                            totalScore += nesting;
                            nesting--;
                        }
                    }
                    case '<' -> garbage = true;
                    case '>' -> garbage = false;
                    case '!' -> skipNext = true;
                }
            } else {
                skipNext = false;
            }
        }
        return totalScore;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var line = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));
        
        var skipNext = false;
        var garbage = false;
        var nonCanceledGarbageChars = 0;

        for (var c : line.toCharArray()) {
            if (!skipNext) {
                switch (c) {
                    case '<' -> {
                        if (!garbage) {
                            garbage = true;
                        } else {
                            nonCanceledGarbageChars++;
                        }
                    }
                    case '>' -> garbage = false;
                    case '!' -> skipNext = true;
                    default -> {
                        if (garbage) {
                            nonCanceledGarbageChars++;
                        }
                    }
                }
            } else {
                skipNext = false;
            }
        }
        return nonCanceledGarbageChars;
    }
}
