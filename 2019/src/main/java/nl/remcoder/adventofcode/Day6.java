package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {
    public int handlePart1(Stream<String> input) {
        Map<String, Planet> planets = new HashMap<>();

        input.map(s -> s.split("\\)"))
             .forEach(strings -> {
                 Planet planet1 = planets.computeIfAbsent(strings[0], s -> new Planet(strings[0]));
                 Planet planet2 = planets.computeIfAbsent(strings[1], s -> new Planet(strings[1]));

                 planet1.orbits.add(planet2);
                 planet2.orbit = planet1;
             });

        Planet root = planets.values()
                             .stream()
                             .filter(planet -> planet.orbit == null)
                             .findFirst()
                             .orElseThrow(AssertionError::new);

        return countOrbits(root, 0);
    }

    public int handlePart2(Stream<String> input) {
        Map<String, Planet> planets = new HashMap<>();

        input.map(s -> s.split("\\)"))
             .forEach(strings -> {
                 Planet planet1 = planets.computeIfAbsent(strings[0], s -> new Planet(strings[0]));
                 Planet planet2 = planets.computeIfAbsent(strings[1], s -> new Planet(strings[1]));

                 planet1.orbits.add(planet2);
                 planet2.orbit = planet1;
             });

        Planet root = planets.values()
                             .stream()
                             .filter(planet -> planet.orbit == null)
                             .findFirst()
                             .orElseThrow(AssertionError::new);

        Planet you = planets.values()
                            .stream()
                            .filter(planet -> "YOU".equals(planet.id))
                            .findFirst()
                            .orElseThrow(AssertionError::new);

        Planet santa = planets.values()
                              .stream()
                              .filter(planet -> "SAN".equals(planet.id))
                              .findFirst()
                              .orElseThrow(AssertionError::new);

        countOrbits(root, 0);

        return determineDistanceToSanta(you, santa);
    }

    private int determineDistanceToSanta(Planet you, Planet santa) {
        Planet santaOrbit = santa.orbit;

        int pathLength = 0;

        int currentDistance = you.distance;

        Set<Planet> visitedPlanets = new HashSet<>();
        visitedPlanets.add(you);
        visitedPlanets.add(you.orbit);

        while (currentDistance > santa.distance - 1) {
            you.orbit = you.orbit.orbit;
            visitedPlanets.add(you.orbit);
            currentDistance--;
            pathLength++;
        }

        while (!you.orbit.equals(santaOrbit)) {
            List<Planet> possibleSantaPaths = you.orbit.orbits.stream()
                                                              .filter(planet -> !visitedPlanets.contains(planet))
                                                              .collect(Collectors.toList());

            boolean santaFound = false;

            for (Planet planet : possibleSantaPaths) {
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
                                                .orElseThrow(AssertionError::new);

                    pathLength++;
                }
            }
        }

        return pathLength;
    }

    private boolean doesPathContainSanta(Planet planet, Set<Planet> visitedPlanets) {
        boolean santaFound = false;

        for (Planet orbit : planet.orbits) {
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
            int total = foundOrbits;
            for (Planet orbit : planet.orbits) {
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
