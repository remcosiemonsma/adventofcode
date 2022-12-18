package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate3D;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day18 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var coordinates = input.map(this::parseToCoordinate)
                               .toList();

        return coordinates.stream()
                          .map(Coordinate3D::getNeighbors)
                          .flatMap(Collection::stream)
                          .filter(coordinate3D -> !coordinates.contains(coordinate3D))
                          .count();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var coordinates = input.map(this::parseToCoordinate)
                               .collect(Collectors.toSet());

        var waterArea = generateWater(coordinates);

        return coordinates.stream()
                          .map(Coordinate3D::getNeighbors)
                          .flatMap(Collection::stream)
                          .filter(Predicate.not(coordinates::contains))
                          .filter(waterArea::contains)
                          .count();
    }

    private Set<Coordinate3D> generateWater(Set<Coordinate3D> lava) {
        int minX = lava.stream().mapToInt(Coordinate3D::x).min().orElseThrow(() -> new AssertionError("Eek!"));
        int maxX = lava.stream().mapToInt(Coordinate3D::x).max().orElseThrow(() -> new AssertionError("Eek!"));
        int minY = lava.stream().mapToInt(Coordinate3D::y).min().orElseThrow(() -> new AssertionError("Eek!"));
        int maxY = lava.stream().mapToInt(Coordinate3D::y).max().orElseThrow(() -> new AssertionError("Eek!"));
        int minZ = lava.stream().mapToInt(Coordinate3D::z).min().orElseThrow(() -> new AssertionError("Eek!"));
        int maxZ = lava.stream().mapToInt(Coordinate3D::z).max().orElseThrow(() -> new AssertionError("Eek!"));

        var min = new Coordinate3D(minX - 1, minY - 1, minZ - 1);
        var max = new Coordinate3D(maxX + 1, maxY + 1, maxZ + 1);

        var waterArea = new HashSet<Coordinate3D>();
        var toCheck = new ArrayList<Coordinate3D>();

        toCheck.add(min);
        waterArea.add(min);

        while (!toCheck.isEmpty()) {
            var currentCube = toCheck.remove(0);
            var newPossibleWater = currentCube.getNeighbors();

            for (var water : newPossibleWater) {
                if (waterArea.contains(water)) {
                    continue;
                }
                if (lava.contains(water)) {
                    continue;
                }
                if (water.x() < min.x() || water.x() > max.x() ||
                    water.y() < min.y() || water.y() > max.y() ||
                    water.z() < min.z() || water.z() > max.z()) {
                    continue;
                }
                waterArea.add(water);
                toCheck.add(water);
            }
        }

        return waterArea;
    }

    private Coordinate3D parseToCoordinate(String line) {
        var parts = line.split(",");
        return new Coordinate3D(Integer.parseInt(parts[0]),
                                Integer.parseInt(parts[1]),
                                Integer.parseInt(parts[2]));
    }
}
