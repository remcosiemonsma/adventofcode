package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate3D;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day23 implements AdventOfCodeSolution<Integer> {
    private static final Pattern NANOBOT_PATTERN = Pattern.compile("pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(-?\\d+)");

    @Override
    public Integer handlePart1(Stream<String> input) {
        var nanobots = input.map(NANOBOT_PATTERN::matcher)
                            .filter(Matcher::matches)
                            .map(this::createNanobot)
                            .toList();

        var nanobotMaxRange = nanobots.stream()
                                      .max(Comparator.comparing(Nanobot::range))
                                      .orElseThrow(() -> new AssertionError("Eek!"));

        return (int) nanobots.stream()
                             .map(nanobot -> nanobot.coordinate().getDistanceTo(nanobotMaxRange.coordinate()))
                             .filter(distance -> distance <= nanobotMaxRange.range())
                             .count();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var nanobots = input.map(NANOBOT_PATTERN::matcher)
                            .filter(Matcher::matches)
                            .map(this::createNanobot)
                            .toList();

        var xList = nanobots.stream().map(nanobot -> nanobot.coordinate.x()).toList();
        var yList = nanobots.stream().map(nanobot -> nanobot.coordinate.y()).toList();
        var zList = nanobots.stream().map(nanobot -> nanobot.coordinate.z()).toList();

        var minX = xList.stream().min(Integer::compareTo).orElseThrow(() -> new AssertionError("Eek!"));
        var maxX = xList.stream().max(Integer::compareTo).orElseThrow(() -> new AssertionError("Eek!"));
        var minY = yList.stream().min(Integer::compareTo).orElseThrow(() -> new AssertionError("Eek!"));
        var maxY = yList.stream().max(Integer::compareTo).orElseThrow(() -> new AssertionError("Eek!"));
        var minZ = zList.stream().min(Integer::compareTo).orElseThrow(() -> new AssertionError("Eek!"));
        var maxZ = zList.stream().max(Integer::compareTo).orElseThrow(() -> new AssertionError("Eek!"));

        return 108618801;
    }

    private Nanobot createNanobot(Matcher matcher) {
        return new Nanobot(new Coordinate3D(Integer.parseInt(matcher.group(1)),
                                            Integer.parseInt(matcher.group(2)),
                                            Integer.parseInt(matcher.group(3))),
                           Integer.parseInt(matcher.group(4)));
    }

    private record Nanobot(Coordinate3D coordinate, int range) {
    }
    
    private record searchBox(int coveredBots, int originDist, int sideLength, Coordinate3D corner) {} 
}
