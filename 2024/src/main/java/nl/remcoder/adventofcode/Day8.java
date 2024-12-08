package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;

import java.util.HashSet;
import java.util.stream.Stream;

public class Day8 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var antennas = grid.findValuesCombined(character -> character != '.');

        var antinodes = new HashSet<>();

        for (var antennatype : antennas.keySet()) {
            var locations = antennas.get(antennatype);
            for (var location : locations) {
                for (var otherLocation : locations) {
                    if (location.equals(otherLocation)) {
                        continue;
                    }
                    var distance = location.getDistanceTo(otherLocation);
                    var circle1 = location.getAllAtDistance(distance);
                    for (var possibleAntinode : circle1) {
                        if (grid.isCoordinateInGrid(possibleAntinode)) {
                            var directions = possibleAntinode.getDirectionsTo(location);
                            var expectedAntenna = location.getAtDirections(directions);
                            if (otherLocation.equals(expectedAntenna)) {
                                antinodes.add(possibleAntinode);
                            }
                        }
                    }
                }
            }
        }

        return antinodes.size();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var antennas = grid.findValuesCombined(character -> character != '.');

        var antinodes = new HashSet<>();

        for (var antennatype : antennas.keySet()) {
            var locations = antennas.get(antennatype);
            for (var location : locations) {
                for (var otherLocation : locations) {
                    if (location.equals(otherLocation)) {
                        continue;
                    }
                    var directions = location.getDirectionsTo(otherLocation);
                    var antinode = otherLocation;
                    while (grid.isCoordinateInGrid(antinode)) {
                        antinodes.add(antinode);
                        antinode = antinode.getAtDirections(directions);
                    }
                }
            }
        }

        return antinodes.size();
    }
}
