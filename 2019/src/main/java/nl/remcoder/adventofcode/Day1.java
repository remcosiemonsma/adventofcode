package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day1 {

    public int handlePart1(Stream<String> input) {
        return input.mapToInt(Integer::parseInt)
                    .map(this::calculateFuelRequirements)
                    .sum();
    }

    public int handlePart2(Stream<String> input) {
        return input.mapToInt(Integer::parseInt)
                    .map(this::calculateFuelRequirementsWithAddedMass)
                    .sum();
    }

    private int calculateFuelRequirements(int mass) {
        return (int) Math.floor(mass / 3d) - 2;
    }

    private int calculateFuelRequirementsWithAddedMass(int mass) {
        int totalFuel = 0;

        int fuelRequired = mass;

        while ((fuelRequired = (int) Math.floor(fuelRequired / 3d) - 2) > 0) {
            totalFuel += fuelRequired;
        }

        return totalFuel;
    }
}