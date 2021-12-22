package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day22 {
    private static final Pattern CUBOID_PATTERN =
            Pattern.compile("(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");

    public long handlePart1(Stream<String> input) {
        List<Cuboid> cuboids = input.map(CUBOID_PATTERN::matcher)
                                    .filter(Matcher::matches)
                                    .map(this::createCuboid)
                                    .filter(Cuboid::isWithin50Range)
                                    .toList();

        List<Cuboid> processedCuboids = new ArrayList<>();

        cuboids.forEach(cuboid -> processCuboid(cuboid, processedCuboids));

        return processedCuboids.stream()
                               .mapToLong(Cuboid::getCuboidSize)
                               .sum();
    }

    public long handlePart2(Stream<String> input) {
        List<Cuboid> cuboids = input.map(CUBOID_PATTERN::matcher)
                                    .filter(Matcher::matches)
                                    .map(this::createCuboid)
                                    .toList();

        List<Cuboid> processedCuboids = new ArrayList<>();

        cuboids.forEach(cuboid -> processCuboid(cuboid, processedCuboids));

        return processedCuboids.stream()
                               .mapToLong(Cuboid::getCuboidSize)
                               .sum();
    }

    private void processCuboid(Cuboid newCuboid, List<Cuboid> existingCuboids) {
        List<Cuboid> overlappingCuboids = existingCuboids.stream()
                                                         .filter(newCuboid::doCuboidsOverlap)
                                                         .toList();

        List<Cuboid> newCuboids = new ArrayList<>();
        if (newCuboid.on) {
            newCuboids.add(newCuboid);
        }
        if (!overlappingCuboids.isEmpty()) {
            overlappingCuboids.forEach(existingCuboid -> newCuboids.addAll(intersectCuboids(existingCuboid, newCuboid)));
            existingCuboids.removeAll(overlappingCuboids);
        }
        existingCuboids.addAll(newCuboids);
    }

    private List<Cuboid> intersectCuboids(Cuboid existingCuboid, Cuboid newCuboid) {
        List<Cuboid> cuboids = new ArrayList<>();

        if (existingCuboid.startx < newCuboid.startx) {
            cuboids.add(new Cuboid(existingCuboid.startx, newCuboid.startx - 1, existingCuboid.starty, existingCuboid.endy,
                                   existingCuboid.startz, existingCuboid.endz, true));
        }
        if (existingCuboid.endx > newCuboid.endx) {
            cuboids.add(new Cuboid(newCuboid.endx + 1, existingCuboid.endx, existingCuboid.starty, existingCuboid.endy,
                                   existingCuboid.startz, existingCuboid.endz, true));
        }
        if (existingCuboid.starty < newCuboid.starty) {
            int startx = Math.max(existingCuboid.startx, newCuboid.startx);
            int endx = Math.min(existingCuboid.endx, newCuboid.endx);
            cuboids.add(new Cuboid(startx, endx, existingCuboid.starty, newCuboid.starty - 1, existingCuboid.startz,
                                   existingCuboid.endz, true));
        }
        if (existingCuboid.endy > newCuboid.endy) {
            int startx = Math.max(existingCuboid.startx, newCuboid.startx);
            int endx = Math.min(existingCuboid.endx, newCuboid.endx);
            cuboids.add(
                    new Cuboid(startx, endx, newCuboid.endy + 1, existingCuboid.endy, existingCuboid.startz, existingCuboid.endz,
                               true));
        }
        if (existingCuboid.startz < newCuboid.startz) {
            int startx = Math.max(existingCuboid.startx, newCuboid.startx);
            int endx = Math.min(existingCuboid.endx, newCuboid.endx);
            int starty = Math.max(existingCuboid.starty, newCuboid.starty);
            int endy = Math.min(existingCuboid.endy, newCuboid.endy);
            cuboids.add(new Cuboid(startx, endx, starty, endy, existingCuboid.startz, newCuboid.startz - 1, true));
        }
        if (existingCuboid.endz > newCuboid.endz) {
            int startx = Math.max(existingCuboid.startx, newCuboid.startx);
            int endx = Math.min(existingCuboid.endx, newCuboid.endx);
            int starty = Math.max(existingCuboid.starty, newCuboid.starty);
            int endy = Math.min(existingCuboid.endy, newCuboid.endy);
            cuboids.add(new Cuboid(startx, endx, starty, endy, newCuboid.endz + 1, existingCuboid.endz, true));
        }
        return List.copyOf(cuboids);
    }

    private Cuboid createCuboid(Matcher matcher) {
        boolean on = "on".equals(matcher.group(1));
        int startx = Integer.parseInt(matcher.group(2));
        int endx = Integer.parseInt(matcher.group(3));
        int starty = Integer.parseInt(matcher.group(4));
        int endy = Integer.parseInt(matcher.group(5));
        int startz = Integer.parseInt(matcher.group(6));
        int endz = Integer.parseInt(matcher.group(7));

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
