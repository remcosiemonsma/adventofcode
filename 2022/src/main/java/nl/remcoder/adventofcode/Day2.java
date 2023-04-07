package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day2 implements AdventOfCodeSolution<Integer> {

    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.mapToInt(this::mapToScorePart1)
                    .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return input.mapToInt(this::mapToScorePart2)
                    .sum();
    }

    private int mapToScorePart1(String line) {
        var elf = switch (line.charAt(0)) {
            case 'A' -> RockPaperScissors.ROCK;
            case 'B' -> RockPaperScissors.PAPER;
            case 'C' -> RockPaperScissors.SCISSORS;
            default -> throw new AssertionError("Eek!");
        };
        var player = switch (line.charAt(2)) {
            case 'X' -> RockPaperScissors.ROCK;
            case 'Y' -> RockPaperScissors.PAPER;
            case 'Z' -> RockPaperScissors.SCISSORS;
            default -> throw new AssertionError("Eek!");
        };
        return switch (player) {
            case ROCK -> switch (elf) {
                case ROCK -> 4;
                case PAPER -> 1;
                case SCISSORS -> 7;
            };
            case PAPER -> switch (elf) {
                case ROCK -> 8;
                case PAPER -> 5;
                case SCISSORS -> 2;
            };
            case SCISSORS -> switch (elf) {
                case ROCK -> 3;
                case PAPER -> 9;
                case SCISSORS -> 6;
            };
        };
    }

    private int mapToScorePart2(String line) {
        var elf = switch (line.charAt(0)) {
            case 'A' -> RockPaperScissors.ROCK;
            case 'B' -> RockPaperScissors.PAPER;
            case 'C' -> RockPaperScissors.SCISSORS;
            default -> throw new AssertionError("Eek!");
        };
        var result = switch (line.charAt(2)) {
            case 'X' -> Result.LOSS;
            case 'Y' -> Result.DRAW;
            case 'Z' -> Result.WIN;
            default -> throw new AssertionError("Eek!");
        };
        return switch (result) {
            case WIN -> switch (elf) {
                case ROCK -> 8;
                case SCISSORS -> 7;
                case PAPER -> 9;
            };
            case DRAW -> switch (elf) {
                case ROCK -> 4;
                case SCISSORS -> 6;
                case PAPER -> 5;
            };
            case LOSS -> switch (elf) {
                case ROCK -> 3;
                case SCISSORS -> 2;
                case PAPER -> 1;
            };
        };
    }

    private enum RockPaperScissors {
        ROCK,
        PAPER,
        SCISSORS
    }

    private enum Result {
        LOSS,
        WIN,
        DRAW
    }
}
