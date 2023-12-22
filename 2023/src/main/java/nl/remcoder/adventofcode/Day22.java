package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate3D;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day22 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var cubes = input.map(this::mapCube)
                         .sorted()
                         .collect(Collectors.toList());

        var occupiedSpots = dropCubes(cubes);

        var removedCount = 0;

        for (int i = 0; i < cubes.size(); i++) {
            var cube = cubes.get(i);
            var occupiedSpotsCopy = new HashSet<>(occupiedSpots);

            occupiedSpotsCopy.removeAll(cube.getAllOccupiedCubes());

            var height = cube.height();

            var canBeRemoved = true;

            for (var j = i + 1; j < cubes.size(); j++) {
                var nextCube = cubes.get(j);
                //next cube is on same height as removed, cannot fall by removing
                if (nextCube.start.z() == cube.start.z()) {
                    continue;
                }
                //all next cubes are supported by other cubes, cannot fall any further
                if (nextCube.start.z() > cube.start.z() + height + 1) {
                    break;
                }
                //Next cube can fall, so we cannot remove cube, further searching is pointless
                if (nextCube.canFall(occupiedSpotsCopy)) {
                    canBeRemoved = false;
                    break;
                }
            }

            if (canBeRemoved) {
                removedCount++;
            }
        }

        return removedCount;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var cubes = input.map(this::mapCube)
                         .sorted()
                         .collect(Collectors.toList());

        var occupiedSpots = dropCubes(cubes);

        var fallCount = 0;

        for (int i = 0; i < cubes.size(); i++) {
            var cube = cubes.get(i);
            var occupiedSpotsCopy = new HashSet<>(occupiedSpots);

            occupiedSpotsCopy.removeAll(cube.getAllOccupiedCubes());

            for (var j = i + 1; j < cubes.size(); j++) {
                var nextCube = cubes.get(j);
                //next cube is on same height as removed, cannot fall by removing
                if (nextCube.start.z() == cube.start.z()) {
                    continue;
                }
                if (nextCube.canFall(occupiedSpotsCopy)) {
                    fallCount++;
                    var nextCubeCopy = new Cube(nextCube.start(), nextCube.end());
                    occupiedSpotsCopy.removeAll(nextCubeCopy.getAllOccupiedCubes());
                    dropCube(nextCubeCopy, occupiedSpotsCopy);
                    occupiedSpotsCopy.addAll(nextCubeCopy.getAllOccupiedCubes());
                }
            }
        }

        return fallCount;
    }

    private Set<Coordinate3D> dropCubes(List<Cube> cubes) {
        var occupiedSpots = new HashSet<Coordinate3D>();

        for (var cube : cubes) {
            dropCube(cube, occupiedSpots);
        }

        cubes.sort(Comparator.naturalOrder());
        return occupiedSpots;
    }

    private void dropCube(Cube cube, HashSet<Coordinate3D> occupiedSpots) {
        while (cube.canFall(occupiedSpots)) {
            cube.fall();
        }
        occupiedSpots.addAll(cube.getAllOccupiedCubes());
    }

    private Cube mapCube(String line) {
        var firstSplit = line.split("~");

        var startSplit = firstSplit[0].split(",");
        var endSplit = firstSplit[1].split(",");

        var start = new Coordinate3D(Integer.parseInt(startSplit[0]),
                                     Integer.parseInt(startSplit[1]),
                                     Integer.parseInt(startSplit[2]));
        var end = new Coordinate3D(Integer.parseInt(endSplit[0]),
                                   Integer.parseInt(endSplit[1]),
                                   Integer.parseInt(endSplit[2]));

        return new Cube(start, end);
    }

    private static final class Cube implements Comparable<Cube> {
        private Coordinate3D start;
        private Coordinate3D end;
        private final int height;

        private Cube(Coordinate3D start, Coordinate3D end) {
            this.start = start;
            this.end = end;
            if (start.x() == end.x() && start.y() == end.y()) {
                height = start.getAllBetween(end).size();
            } else {
                height = 1;
            }
        }

        @Override
        public int compareTo(Cube o) {
            return Comparator.comparingInt(Coordinate3D::z)
                             .thenComparingInt(Coordinate3D::y)
                             .thenComparingInt(Coordinate3D::x)
                             .compare(this.start, o.start);
        }

        public Coordinate3D start() {
            return start;
        }

        public Coordinate3D end() {
            return end;
        }

        public int height() {
            return height;
        }

        public List<Coordinate3D> getAllOccupiedCubes() {
            return start.getAllBetween(end);
        }

        public boolean canFall(Set<Coordinate3D> occupiedSpots) {
            if (start.z() == 1) {
                return false;
            }
            if (start.x() == end.x() && start.y() == end.y()) {
                var cubeBelow = new Coordinate3D(start.x(), start.y(), start.z() - 1);
                return !occupiedSpots.contains(cubeBelow);
            } else {
                for (var cube : getAllOccupiedCubes()) {
                    var cubeBelow = new Coordinate3D(cube.x(), cube.y(), cube.z() - 1);
                    if (occupiedSpots.contains(cubeBelow)) {
                        return false;
                    }
                }
            }
            return true;
        }

        public void fall() {
            start = new Coordinate3D(start.x(), start.y(), start.z() - 1);
            end = new Coordinate3D(end.x(), end.y(), end.z() - 1);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            var that = (Cube) obj;
            return Objects.equals(this.start, that.start) &&
                   Objects.equals(this.end, that.end);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }

        @Override
        public String toString() {
            return "Cube{" +
                   "start=" + start +
                   ", end=" + end +
                   '}';
        }
    }
}
