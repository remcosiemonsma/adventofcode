package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day22 implements AdventOfCodeSolution<Long> {
    private static final Pattern CUBOID_PATTERN =
            Pattern.compile("(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");

    @Override
    public Long handlePart1(Stream<String> input) {
        var cuboids = input.map(CUBOID_PATTERN::matcher)
                           .filter(Matcher::matches)
                           .map(this::createCuboid)
                           .filter(Cuboid::isWithin50Range)
                           .toList();

        var processedCuboids = new ArrayList<Cuboid>();

        cuboids.forEach(cuboid -> processCuboid(cuboid, processedCuboids));

        return processedCuboids.stream()
                               .mapToLong(Cuboid::getCuboidSize)
                               .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var cuboids = input.map(CUBOID_PATTERN::matcher)
                           .filter(Matcher::matches)
                           .map(this::createCuboid)
                           .toList();

        var processedCuboids = new ArrayList<Cuboid>();

        cuboids.forEach(cuboid -> processCuboid(cuboid, processedCuboids));

        return processedCuboids.stream()
                               .mapToLong(Cuboid::getCuboidSize)
                               .sum();
    }

    private void processCuboid(Cuboid newCuboid, List<Cuboid> existingCuboids) {
        var overlappingCuboids = existingCuboids.stream()
                                                .filter(newCuboid::doCuboidsOverlap)
                                                .toList();

        var newCuboids = new ArrayList<Cuboid>();
        if (newCuboid.on) {
            newCuboids.add(newCuboid);
        }
        if (!overlappingCuboids.isEmpty()) {
            overlappingCuboids.forEach(
                    existingCuboid -> newCuboids.addAll(intersectCuboids(existingCuboid, newCuboid)));
            existingCuboids.removeAll(overlappingCuboids);
        }
        existingCuboids.addAll(newCuboids);
    }

    private List<Cuboid> intersectCuboids(Cuboid existingCuboid, Cuboid newCuboid) {
        var cuboids = new ArrayList<Cuboid>();

        if (existingCuboid.startx < newCuboid.startx) {
            cuboids.add(
                    new Cuboid(existingCuboid.startx, newCuboid.startx - 1, existingCuboid.starty, existingCuboid.endy,
                               existingCuboid.startz, existingCuboid.endz, true));
        }
        if (existingCuboid.endx > newCuboid.endx) {
            cuboids.add(new Cuboid(newCuboid.endx + 1, existingCuboid.endx, existingCuboid.starty, existingCuboid.endy,
                                   existingCuboid.startz, existingCuboid.endz, true));
        }
        if (existingCuboid.starty < newCuboid.starty) {
            var startx = Math.max(existingCuboid.startx, newCuboid.startx);
            var endx = Math.min(existingCuboid.endx, newCuboid.endx);
            cuboids.add(new Cuboid(startx, endx, existingCuboid.starty, newCuboid.starty - 1, existingCuboid.startz,
                                   existingCuboid.endz, true));
        }
        if (existingCuboid.endy > newCuboid.endy) {
            var startx = Math.max(existingCuboid.startx, newCuboid.startx);
            var endx = Math.min(existingCuboid.endx, newCuboid.endx);
            cuboids.add(
                    new Cuboid(startx, endx, newCuboid.endy + 1, existingCuboid.endy, existingCuboid.startz,
                               existingCuboid.endz,
                               true));
        }
        if (existingCuboid.startz < newCuboid.startz) {
            var startx = Math.max(existingCuboid.startx, newCuboid.startx);
            var endx = Math.min(existingCuboid.endx, newCuboid.endx);
            var starty = Math.max(existingCuboid.starty, newCuboid.starty);
            var endy = Math.min(existingCuboid.endy, newCuboid.endy);
            cuboids.add(new Cuboid(startx, endx, starty, endy, existingCuboid.startz, newCuboid.startz - 1, true));
        }
        if (existingCuboid.endz > newCuboid.endz) {
            var startx = Math.max(existingCuboid.startx, newCuboid.startx);
            var endx = Math.min(existingCuboid.endx, newCuboid.endx);
            var starty = Math.max(existingCuboid.starty, newCuboid.starty);
            var endy = Math.min(existingCuboid.endy, newCuboid.endy);
            cuboids.add(new Cuboid(startx, endx, starty, endy, newCuboid.endz + 1, existingCuboid.endz, true));
        }
        return List.copyOf(cuboids);
    }

    private Cuboid createCuboid(Matcher matcher) {
        var on = "on".equals(matcher.group(1));
        var startx = Integer.parseInt(matcher.group(2));
        var endx = Integer.parseInt(matcher.group(3));
        var starty = Integer.parseInt(matcher.group(4));
        var endy = Integer.parseInt(matcher.group(5));
        var startz = Integer.parseInt(matcher.group(6));
        var endz = Integer.parseInt(matcher.group(7));

        return new Cuboid(startx, endx, starty, endy, startz, endz, on);
    }

    private record Cuboid(int startx, int endx, int starty, int endy, int startz, int endz, boolean on) {
        public boolean isWithin50Range() {
            return startx >= -50 && endx <= 50 && starty >= -50 && endy <= 50 && startz >= -50 && endz <= 50;
        }

        public boolean doCuboidsOverlap(Cuboid other) {
            return doesXOverlap(other) &&
                   doesYOverlap(other) &&
                   doesZOverlap(other);
        }

        private boolean doesXOverlap(Cuboid other) {
            return (startx >= other.startx && startx <= other.endx) ||
                   (endx >= other.startx && endx <= other.endx) ||
                   (startx < other.startx && endx > other.endx);
        }

        private boolean doesYOverlap(Cuboid other) {
            return (starty >= other.starty && starty <= other.endy) ||
                   (endy >= other.starty && endy <= other.endy) ||
                   (starty < other.starty && endy > other.endy);
        }

        private boolean doesZOverlap(Cuboid other) {
            return (startz >= other.startz && startz <= other.endz) ||
                   (endz >= other.startz && endz <= other.endz) ||
                   (startz < other.startz && endz > other.endz);
        }

        public long getCuboidSize() {
            long xsize = (endx - startx) + 1;
            long ysize = (endy - starty) + 1;
            long zsize = (endz - startz) + 1;

            return xsize * ysize * zsize;
        }

        @Override
        public String toString() {
            return "x=" + startx + ".." + endx +
                   ",y=" + starty + ".." + endy +
                   ",z=" + startz + ".." + endz;
        }
    }
}
