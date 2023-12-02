package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.map(this::parseGame)
                    .filter(this::isGamePossible)
                    .mapToInt(Game::id)
                    .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return input.map(this::parseGame)
                    .mapToInt(this::calculateGamePower)
                    .sum();
    }

    private boolean isGamePossible(Game game) {
        return game.rounds.stream().allMatch(this::isRoundPossible);
    }

    private boolean isRoundPossible(Round round) {
        if (round.cubes.getOrDefault("red", 0) > 12) {
            return false;
        }
        if (round.cubes.getOrDefault("green", 0) > 13) {
            return false;
        }
        if (round.cubes.getOrDefault("blue", 0) > 14) {
            return false;
        }
        return true;
    }

    private int calculateGamePower(Game game) {
        return getMinimumCubesForColor(game, "red") *
               getMinimumCubesForColor(game, "green") *
               getMinimumCubesForColor(game, "blue");
    }

    private int getMinimumCubesForColor(Game game, String color) {
        return game.rounds.stream()
                          .mapToInt(round -> round.cubes.getOrDefault(color, 0))
                          .max()
                          .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private Game parseGame(String line) {
        var gameSplit = line.split(": ");

        var id = Integer.parseInt(gameSplit[0].substring(5));

        return new Game(id, Arrays.stream(gameSplit[1].split("; "))
                                  .map(this::parseRound)
                                  .toList());
    }

    private Round parseRound(String part) {
        return new Round(Arrays.stream(part.split(", "))
                               .map(s -> s.split(" "))
                               .collect(Collectors.toMap(strings -> strings[1],
                                                         strings -> Integer.parseInt(strings[0]))));
    }

    private record Game(int id, List<Round> rounds) {

    }

    private record Round(Map<String, Integer> cubes) {

    }
}
