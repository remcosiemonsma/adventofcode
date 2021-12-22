package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day22 {
    private static final Pattern REGION_PATTERN =
            Pattern.compile("(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");

    public long handlePart1(Stream<String> input) {
        List<Cube> cubes = input.map(REGION_PATTERN::matcher)
                                .filter(Matcher::matches)
                                .map(this::createRegion)
                                .filter(Cube::isWithin50Range)
                                .toList();

        List<Cube> processedCubes = new ArrayList<>();

        cubes.forEach(region -> processCube(region, processedCubes));

        return processedCubes.stream()
                             .mapToLong(Cube::getRegionSize)
                             .sum();
    }

    public long handlePart2(Stream<String> input) {
        List<Cube> cubes = input.map(REGION_PATTERN::matcher)
                                .filter(Matcher::matches)
                                .map(this::createRegion)
                                .toList();

        List<Cube> mergedRegions = new ArrayList<>();

        cubes.forEach(region -> processCube(region, mergedRegions));

        return mergedRegions.stream()
                            .mapToLong(Cube::getRegionSize)
                            .sum();
    }

    private void processCube(Cube newCube, List<Cube> existingCubes) {
        List<Cube> overlappingCubes = existingCubes.stream()
                                                   .filter(newCube::doCubesOverlap)
                                                   .toList();

        List<Cube> newCubes = new ArrayList<>();
        if (newCube.on) {
            newCubes.add(newCube);
        }
        if (!overlappingCubes.isEmpty()) {
            overlappingCubes.forEach(existingCube -> newCubes.addAll(intersectRegions(existingCube, newCube)));
            existingCubes.removeAll(overlappingCubes);
        }
        existingCubes.addAll(newCubes);
    }

    private List<Cube> intersectRegions(Cube existingCube, Cube newCube) {
        List<Cube> cubes = new ArrayList<>();

        if (existingCube.startx < newCube.startx) {
            cubes.add(new Cube(existingCube.startx, newCube.startx - 1, existingCube.starty, existingCube.endy,
                               existingCube.startz, existingCube.endz, true));
        }
        if (existingCube.endx > newCube.endx) {
            cubes.add(new Cube(newCube.endx + 1, existingCube.endx, existingCube.starty, existingCube.endy,
                               existingCube.startz, existingCube.endz, true));
        }
        if (existingCube.starty < newCube.starty) {
            int startx = Math.max(existingCube.startx, newCube.startx);
            int endx = Math.min(existingCube.endx, newCube.endx);
            cubes.add(new Cube(startx, endx, existingCube.starty, newCube.starty - 1, existingCube.startz,
                               existingCube.endz, true));
        }
        if (existingCube.endy > newCube.endy) {
            int startx = Math.max(existingCube.startx, newCube.startx);
            int endx = Math.min(existingCube.endx, newCube.endx);
            cubes.add(
                    new Cube(startx, endx, newCube.endy + 1, existingCube.endy, existingCube.startz, existingCube.endz,
                             true));
        }
        if (existingCube.startz < newCube.startz) {
            int startx = Math.max(existingCube.startx, newCube.startx);
            int endx = Math.min(existingCube.endx, newCube.endx);
            int starty = Math.max(existingCube.starty, newCube.starty);
            int endy = Math.min(existingCube.endy, newCube.endy);
            cubes.add(new Cube(startx, endx, starty, endy, existingCube.startz, newCube.startz - 1, true));
        }
        if (existingCube.endz > newCube.endz) {
            int startx = Math.max(existingCube.startx, newCube.startx);
            int endx = Math.min(existingCube.endx, newCube.endx);
            int starty = Math.max(existingCube.starty, newCube.starty);
            int endy = Math.min(existingCube.endy, newCube.endy);
            cubes.add(new Cube(startx, endx, starty, endy, newCube.endz + 1, existingCube.endz, true));
        }
        return List.copyOf(cubes);
    }

    private Cube createRegion(Matcher matcher) {
        boolean on = "on".equals(matcher.group(1));
        int startx = Integer.parseInt(matcher.group(2));
        int endx = Integer.parseInt(matcher.group(3));
        int starty = Integer.parseInt(matcher.group(4));
        int endy = Integer.parseInt(matcher.group(5));
        int startz = Integer.parseInt(matcher.group(6));
        int endz = Integer.parseInt(matcher.group(7));

        return new Cube(startx, endx, starty, endy, startz, endz, on);
    }

    private record Cube(int startx, int endx, int starty, int endy, int startz, int endz, boolean on) {
        public boolean isWithin50Range() {
            return startx >= -50 && endx <= 50 && starty >= -50 && endy <= 50 && startz >= -50 && endz <= 50;
        }

        public boolean doCubesOverlap(Cube other) {
            return doesXOverlap(other) &&
                   doesYOverlap(other) &&
                   doesZOverlap(other);
        }

        private boolean doesXOverlap(Cube other) {
            return (startx >= other.startx && startx <= other.endx) ||
                   (endx >= other.startx && endx <= other.endx) ||
                   (startx < other.startx && endx > other.endx);
        }

        private boolean doesYOverlap(Cube other) {
            return (starty >= other.starty && starty <= other.endy) ||
                   (endy >= other.starty && endy <= other.endy) ||
                   (starty < other.starty && endy > other.endy);
        }

        private boolean doesZOverlap(Cube other) {
            return (startz >= other.startz && startz <= other.endz) ||
                   (endz >= other.startz && endz <= other.endz) ||
                   (startz < other.startz && endz > other.endz);
        }

        public long getRegionSize() {
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
