package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day19 implements AdventOfCodeSolution<Integer> {
    private static final Pattern BLUEPRINT_PATTERN = Pattern.compile(
            "Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.");

    @Override
    public Integer handlePart1(Stream<String> input) {
        var blueprints = input.map(BLUEPRINT_PATTERN::matcher)
                              .filter(Matcher::matches)
                              .map(this::parseBlueprint)
                              .toList();

        return blueprints.stream()
                         .mapToInt(this::getResult)
                         .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var blueprints = input.map(BLUEPRINT_PATTERN::matcher)
                              .filter(Matcher::matches)
                              .map(this::parseBlueprint)
                              .limit(3)
                              .toList();

        return blueprints.stream()
                         .mapToInt(blueprint -> findBestResult(blueprint, 32))
                         .reduce((left, right) -> left * right)
                         .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private int getResult(Blueprint blueprint) {
        return findBestResult(blueprint, 24) * blueprint.id();
    }

    private int findBestResult(Blueprint blueprint, int time) {
        var startMinute = new Minute(0, 0, 0, 0, 1, 0, 0, 0);

        var allMinutes = List.of(startMinute);
        var bestMinute = startMinute;

        for (var minute = 0; minute < time; minute++) {
            var newMinutes = new ArrayList<Minute>();

            for (var currentMinute : allMinutes) {
                int newOre = currentMinute.ore() + currentMinute.oreRobots();
                int newClay = currentMinute.clay() + currentMinute.clayRobots();
                int newObsidian = currentMinute.obsidian() + currentMinute.obsidianRobots();
                int newGeode = currentMinute.geode() + currentMinute.geodeRobots();

                //default do nothing minute
                newMinutes.add(new Minute(newOre, newClay, newObsidian, newGeode, currentMinute.oreRobots(),
                                          currentMinute.clayRobots(), currentMinute.obsidianRobots(),
                                          currentMinute.geodeRobots()));

                if (canBuildOreRobot(currentMinute, blueprint)) {
                    newMinutes.add(new Minute(newOre - blueprint.oreRobotCost(), newClay, newObsidian, newGeode,
                                              currentMinute.oreRobots() + 1, currentMinute.clayRobots(),
                                              currentMinute.obsidianRobots(), currentMinute.geodeRobots()));
                }
                if (canBuildClayRobot(currentMinute, blueprint)) {
                    newMinutes.add(new Minute(newOre - blueprint.clayRobotCost(), newClay, newObsidian, newGeode,
                                              currentMinute.oreRobots(), currentMinute.clayRobots() + 1,
                                              currentMinute.obsidianRobots(), currentMinute.geodeRobots()));
                }
                if (canBuildObsidianRobot(currentMinute, blueprint)) {
                    newMinutes.add(new Minute(newOre - blueprint.obsidianRobotOreCost(),
                                              newClay - blueprint.obsidianRobotClayCost(),
                                              newObsidian, newGeode,
                                              currentMinute.oreRobots(), currentMinute.clayRobots(),
                                              currentMinute.obsidianRobots() + 1, currentMinute.geodeRobots()));
                }
                if (canBuildGeodeRobot(currentMinute, blueprint)) {
                    newMinutes.add(new Minute(newOre - blueprint.geodeRobotOreCost(), newClay,
                                              newObsidian - blueprint.geodeRobotObsidianCost(), newGeode,
                                              currentMinute.oreRobots(), currentMinute.clayRobots(),
                                              currentMinute.obsidianRobots(), currentMinute.geodeRobots() + 1));
                }
            }

            allMinutes = newMinutes.stream()
                                   .sorted(Comparator.comparing(Minute::geode).thenComparing(Minute::geodeRobots)
                                                     .thenComparing(Minute::obsidian)
                                                     .thenComparing(Minute::obsidianRobots)
                                                     .thenComparing(Minute::clay).thenComparing(Minute::clayRobots)
                                                     .thenComparing(Minute::ore).thenComparing(Minute::oreRobots)
                                                     .reversed())
                                   .limit(10000)
                                   .toList();

            if (allMinutes.get(0).geode() > bestMinute.geode()) {
                bestMinute = allMinutes.get(0);
            }
        }

        return bestMinute.geode();
    }

    private boolean canBuildGeodeRobot(Minute currentMinute, Blueprint blueprint) {
        return currentMinute.ore() >= blueprint.geodeRobotOreCost() &&
               currentMinute.obsidian() >= blueprint.geodeRobotObsidianCost();
    }

    private boolean canBuildObsidianRobot(Minute currentMinute, Blueprint blueprint) {
        return currentMinute.ore() >= blueprint.obsidianRobotOreCost() &&
               currentMinute.clay() >= blueprint.obsidianRobotClayCost();
    }

    private boolean canBuildClayRobot(Minute currentMinute, Blueprint blueprint) {
        return currentMinute.ore() >= blueprint.clayRobotCost();
    }

    private boolean canBuildOreRobot(Minute currentMinute, Blueprint blueprint) {
        return currentMinute.ore() >= blueprint.oreRobotCost();
    }

    private Blueprint parseBlueprint(Matcher matcher) {
        return new Blueprint(Integer.parseInt(matcher.group(1)),
                             Integer.parseInt(matcher.group(2)),
                             Integer.parseInt(matcher.group(3)),
                             Integer.parseInt(matcher.group(4)),
                             Integer.parseInt(matcher.group(5)),
                             Integer.parseInt(matcher.group(6)),
                             Integer.parseInt(matcher.group(7)));
    }

    private record Minute(int ore, int clay, int obsidian, int geode, int oreRobots, int clayRobots,
                          int obsidianRobots, int geodeRobots) {
    }

    private record Blueprint(int id, int oreRobotCost, int clayRobotCost, int obsidianRobotOreCost,
                             int obsidianRobotClayCost,
                             int geodeRobotOreCost, int geodeRobotObsidianCost) {
    }
}
