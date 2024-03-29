package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class Day6 implements AdventOfCodeSolution<Integer> {

    @Override
    public Integer handlePart1(Stream<String> input) {
        var planets = parsePlanets(input);

        var root = planets.get("COM");

        return countOrbits(root, 0);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var planets = parsePlanets(input);

        var root = planets.get("COM");

        var you = planets.get("YOU");

        var santa = planets.get("SAN");

        countOrbits(root, 0);

        return determineDistanceToSanta(you, santa);
    }

    private HashMap<String, Planet> parsePlanets(Stream<String> input) {
        var planets = new HashMap<String, Planet>();

        input.map(s -> s.split("\\)"))
             .forEach(strings -> {
                 var planet1 = planets.computeIfAbsent(strings[0], s -> new Planet(strings[0]));
                 var planet2 = planets.computeIfAbsent(strings[1], s -> new Planet(strings[1]));

                 planet1.orbits.add(planet2);
                 planet2.orbit = planet1;
             });

        return planets;
    }

    private int determineDistanceToSanta(Planet you, Planet santa) {
        var santaOrbit = santa.orbit;

        var pathLength = 0;

        var currentDistance = you.distance;

        var visitedPlanets = new HashSet<Planet>();
        visitedPlanets.add(you);
        visitedPlanets.add(you.orbit);

        while (currentDistance > santa.distance - 1) {
            you.orbit = you.orbit.orbit;
            visitedPlanets.add(you.orbit);
            currentDistance--;
            pathLength++;
        }

        while (!you.orbit.equals(santaOrbit)) {
            var possibleSantaPaths = you.orbit.orbits.stream()
                                                     .filter(planet -> !visitedPlanets.contains(planet))
                                                     .toList();

            var santaFound = false;

            for (var planet : possibleSantaPaths) {
                if (doesPathContainSanta(planet, visitedPlanets)) {
                    santaFound = true;
                }
            }

            if (!santaFound) {
                you.orbit = you.orbit.orbit;
                visitedPlanets.add(you.orbit);
                pathLength++;
            } else {
                while (!you.orbit.equals(santaOrbit)) {
                    you.orbit = you.orbit.orbits.stream()
                                                .filter(planet -> !visitedPlanets.contains(planet))
                                                .findFirst()
                                                .orElseThrow(() -> new AssertionError("Eek!"));

                    pathLength++;
                }
            }
        }

        return pathLength;
    }

    private boolean doesPathContainSanta(Planet planet, Set<Planet> visitedPlanets) {
        var santaFound = false;

        for (var orbit : planet.orbits) {
            if ("SAN".equals(orbit.id)) {
                santaFound = true;
                break;
            } else {
                santaFound = doesPathContainSanta(orbit, visitedPlanets);
                if (santaFound) {
                    break;
                }
            }
        }

        if (!santaFound) {
            visitedPlanets.add(planet);
        }

        return santaFound;
    }

    private int countOrbits(Planet planet, int foundOrbits) {
        planet.distance = foundOrbits;

        if (planet.orbits.isEmpty()) {
            return foundOrbits;
        } else {
            var total = foundOrbits;
            for (var orbit : planet.orbits) {
                total += countOrbits(orbit, foundOrbits + 1);
            }
            return total;
        }
    }

    private static class Planet {
        private final String id;
        private final List<Planet> orbits;
        private Planet orbit;
        private int distance;

        private Planet(String id) {
            this.id = id;
            orbits = new ArrayList<>();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Planet planet = (Planet) o;
            return Objects.equals(id, planet.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "Planet{" +
                   "id='" + id + '\'' +
                   "distance='" + distance + '\'' +
                   '}';
        }
    }
}
