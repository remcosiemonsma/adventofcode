package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day1 implements AdventOfCodeSolution<Integer> {

    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.mapToInt(Integer::parseInt)
                    .map(this::calculateFuelRequirements)
                    .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return input.mapToInt(Integer::parseInt)
                    .map(this::calculateFuelRequirementsWithAddedMass)
                    .sum();
    }

    private int calculateFuelRequirements(int mass) {
        return (int) Math.floor(mass / 3d) - 2;
    }

    private int calculateFuelRequirementsWithAddedMass(int mass) {
        var totalFuel = 0;

        var fuelRequired = mass;

        while ((fuelRequired = (int) Math.floor(fuelRequired / 3d) - 2) > 0) {
            totalFuel += fuelRequired;
        }

        return totalFuel;
    }
}