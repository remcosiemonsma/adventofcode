package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day11 implements AdventOfCodeSolution<Long> {
    private static final Pattern GENERATOR_PATTERN = Pattern.compile(" ([a-z]+) generator");
    private static final Pattern MICROCHIP_PATTERN = Pattern.compile(" ([a-z]+)-compatible microchip");
    private static final Map<State, Step> STEPS = new HashMap<>();

    @Override
    public Long handlePart1(Stream<String> input) {
        var floors = input.map(this::parseFloor)
                          .toArray(Floor[]::new);

        var start = new Step(floors, 0);
        start.setDistance(0);

        var state = new State(floors[0], floors[1], floors[2], floors[3], 0);

        var allGenerators = new HashSet<String>();
        var allMicrochips = new HashSet<String>();

        for (var floor : floors) {
            allGenerators.addAll(floor.generators());
            allMicrochips.addAll(floor.microchips());
        }

        var endFloors = new Floor[] {new Floor(Set.of(), Set.of()),
                                     new Floor(Set.of(), Set.of()),
                                     new Floor(Set.of(), Set.of()),
                                     new Floor(allGenerators, allMicrochips)};
        
        var end = new Step(endFloors, 3);
        var endState = new State(endFloors[0], endFloors[1], endFloors[2], endFloors[3], 3);

        STEPS.put(state, start);
        STEPS.put(endState, end);

        return Dijkstra.findShortestDistance(start, node -> node == end).orElseThrow(() -> new AssertionError("Eek!")).getDistance();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var floors = input.map(this::parseFloor)
                          .toArray(Floor[]::new);
        
        floors[0].microchips().add("elerium");
        floors[0].microchips().add("dilithium");
        floors[0].generators().add("elerium");
        floors[0].generators().add("dilithium");

        var start = new Step(floors, 0);
        start.setDistance(0);

        var state = new State(floors[0], floors[1], floors[2], floors[3], 0);

        var allGenerators = new HashSet<String>();
        var allMicrochips = new HashSet<String>();

        for (var floor : floors) {
            allGenerators.addAll(floor.generators());
            allMicrochips.addAll(floor.microchips());
        }

        var endFloors = new Floor[] {new Floor(Set.of(), Set.of()),
                                     new Floor(Set.of(), Set.of()),
                                     new Floor(Set.of(), Set.of()),
                                     new Floor(allGenerators, allMicrochips)};

        var end = new Step(endFloors, 3);
        var endState = new State(endFloors[0], endFloors[1], endFloors[2], endFloors[3], 3);

        STEPS.put(state, start);
        STEPS.put(endState, end);

        return Dijkstra.findShortestDistance(start, node -> node == end).orElseThrow(() -> new AssertionError("Eek!")).getDistance();
    }

    private Floor parseFloor(String line) {
        var generatorMatcher = GENERATOR_PATTERN.matcher(line);

        var generators = new HashSet<String>();

        while (generatorMatcher.find()) {
            generators.add(generatorMatcher.group(1));
        }

        var microchipMatcher = MICROCHIP_PATTERN.matcher(line);

        var microchips = new HashSet<String>();

        while (microchipMatcher.find()) {
            microchips.add(microchipMatcher.group(1));
        }

        return new Floor(generators, microchips);
    }

    private static class Step extends Node {
        private final Floor[] floors;
        private final int position;

        private Step(Floor[] floors, int position) {
            this.floors = floors;
            this.position = position;
        }

        @Override
        public Map<? extends Node, Long> getNeighbors() {
            var currentFloor = floors[position];

            var neighbors = new HashMap<Step, Long>();

            for (var microchip : currentFloor.microchips()) {
                for (var othermicrochip : currentFloor.microchips()) {
                    if (microchip.equals(othermicrochip)) {
                        continue;
                    }
                    if (position < 3) {
                        var newFloorMicrochips = new HashSet<>(currentFloor.microchips());
                        var newFloorGenerators = new HashSet<>(currentFloor.generators());
                        newFloorMicrochips.remove(microchip);
                        newFloorMicrochips.remove(othermicrochip);
                        var newFloor = new Floor(newFloorGenerators, newFloorMicrochips);
                        var newNextFloorMicrochips = new HashSet<>(floors[position + 1].microchips());
                        var newNextFloorGenerators = new HashSet<>(floors[position + 1].generators());
                        newNextFloorMicrochips.add(microchip);
                        newNextFloorMicrochips.add(othermicrochip);
                        var newNextFloor = new Floor(newNextFloorGenerators, newNextFloorMicrochips);
                        moveFloorUp(neighbors, newFloor, newNextFloor);
                    }
                    if (position > 0) {
                        var newFloorMicrochips = new HashSet<>(currentFloor.microchips());
                        var newFloorGenerators = new HashSet<>(currentFloor.generators());
                        newFloorMicrochips.remove(microchip);
                        newFloorMicrochips.remove(othermicrochip);
                        var newFloor = new Floor(newFloorGenerators, newFloorMicrochips);
                        var newNextFloorMicrochips = new HashSet<>(floors[position - 1].microchips());
                        var newNextFloorGenerators = new HashSet<>(floors[position - 1].generators());
                        newNextFloorMicrochips.add(microchip);
                        newNextFloorMicrochips.add(othermicrochip);
                        var newNextFloor = new Floor(newNextFloorGenerators, newNextFloorMicrochips);
                        moveFloorDown(neighbors, newFloor, newNextFloor);
                    }
                }
                for (var generator : currentFloor.generators) {
                    if (position < 3) {
                        var newFloorMicrochips = new HashSet<>(currentFloor.microchips());
                        var newFloorGenerators = new HashSet<>(currentFloor.generators());
                        newFloorMicrochips.remove(microchip);
                        newFloorGenerators.remove(generator);
                        var newFloor = new Floor(newFloorGenerators, newFloorMicrochips);
                        var newNextFloorMicrochips = new HashSet<>(floors[position + 1].microchips());
                        var newNextFloorGenerators = new HashSet<>(floors[position + 1].generators());
                        newNextFloorMicrochips.add(microchip);
                        newNextFloorGenerators.add(generator);
                        var newNextFloor = new Floor(newNextFloorGenerators, newNextFloorMicrochips);
                        moveFloorUp(neighbors, newFloor, newNextFloor);
                    }
                    if (position > 0) {
                        var newFloorMicrochips = new HashSet<>(currentFloor.microchips());
                        var newFloorGenerators = new HashSet<>(currentFloor.generators());
                        newFloorMicrochips.remove(microchip);
                        newFloorGenerators.remove(generator);
                        var newFloor = new Floor(newFloorGenerators, newFloorMicrochips);
                        var newNextFloorMicrochips = new HashSet<>(floors[position - 1].microchips());
                        var newNextFloorGenerators = new HashSet<>(floors[position - 1].generators());
                        newNextFloorMicrochips.add(microchip);
                        newNextFloorGenerators.add(generator);
                        var newNextFloor = new Floor(newNextFloorGenerators, newNextFloorMicrochips);
                        moveFloorDown(neighbors, newFloor, newNextFloor);
                    }
                }
                if (position < 3) {
                    var newFloorMicrochips = new HashSet<>(currentFloor.microchips());
                    newFloorMicrochips.remove(microchip);
                    var newFloor = new Floor(new HashSet<>(currentFloor.generators()), newFloorMicrochips);
                    var newNextFloorMicrochips = new HashSet<>(floors[position + 1].microchips());
                    var newNextFloorGenerators = new HashSet<>(floors[position + 1].generators());
                    newNextFloorMicrochips.add(microchip);
                    var newNextFloor = new Floor(newNextFloorGenerators, newNextFloorMicrochips);
                    moveFloorUp(neighbors, newFloor, newNextFloor);
                }
                if (position > 0) {
                    var newFloorMicrochips = new HashSet<>(currentFloor.microchips());
                    newFloorMicrochips.remove(microchip);
                    var newFloor = new Floor(new HashSet<>(currentFloor.generators()), newFloorMicrochips);
                    var newNextFloorMicrochips = new HashSet<>(floors[position - 1].microchips());
                    var newNextFloorGenerators = new HashSet<>(floors[position - 1].generators());
                    newNextFloorMicrochips.add(microchip);
                    var newNextFloor = new Floor(newNextFloorGenerators, newNextFloorMicrochips);
                    moveFloorDown(neighbors, newFloor, newNextFloor);
                }
            }
            for (var generator : currentFloor.generators()) {
                for (var otherGenerator : currentFloor.generators) {
                    if (generator.equals(otherGenerator)) {
                        continue;
                    }
                    if (position < 3) {
                        var newFloorGenerators = new HashSet<>(currentFloor.generators());
                        newFloorGenerators.remove(generator);
                        newFloorGenerators.remove(otherGenerator);
                        var newNextFloorGenerators = new HashSet<>(floors[position + 1].generators());
                        var newNextFloorMicrochips = new HashSet<>(floors[position + 1].microchips());
                        newNextFloorGenerators.add(generator);
                        newNextFloorGenerators.add(otherGenerator);
                        var newFloor = new Floor(newFloorGenerators, new HashSet<>(currentFloor.microchips()));
                        var newNextFloor = new Floor(newNextFloorGenerators, newNextFloorMicrochips);
                        moveFloorUp(neighbors, newFloor, newNextFloor);
                    }
                    if (position > 0) {
                        var newFloorGenerators = new HashSet<>(currentFloor.generators());
                        newFloorGenerators.remove(generator);
                        newFloorGenerators.remove(otherGenerator);
                        var newNextFloorGenerators = new HashSet<>(floors[position - 1].generators());
                        var newNextFloorMicrochips = new HashSet<>(floors[position - 1].microchips());
                        newNextFloorGenerators.add(generator);
                        newNextFloorGenerators.add(otherGenerator);
                        var newFloor = new Floor(newFloorGenerators, new HashSet<>(currentFloor.microchips()));
                        var newNextFloor = new Floor(newNextFloorGenerators, newNextFloorMicrochips);
                        moveFloorDown(neighbors, newFloor, newNextFloor);
                    }
                }
                if (position < 3) {
                    var newFloorGenerators = new HashSet<>(currentFloor.generators());
                    newFloorGenerators.remove(generator);
                    var newFloor = new Floor(newFloorGenerators, new HashSet<>(currentFloor.microchips()));
                    var newNextFloorGenerators = new HashSet<>(floors[position + 1].generators());
                    var newNextFloorMicrochips = new HashSet<>(floors[position + 1].microchips());
                    newNextFloorGenerators.add(generator);
                    var newNextFloor = new Floor(newNextFloorGenerators, newNextFloorMicrochips);
                    moveFloorUp(neighbors, newFloor, newNextFloor);
                }
                if (position > 0) {
                    var newFloorGenerators = new HashSet<>(currentFloor.generators());
                    newFloorGenerators.remove(generator);
                    var newFloor = new Floor(newFloorGenerators, new HashSet<>(currentFloor.microchips()));
                    var newNextFloorGenerators = new HashSet<>(floors[position - 1].generators());
                    var newNextFloorMicrochips = new HashSet<>(floors[position - 1].microchips());
                    newNextFloorGenerators.add(generator);
                    var newNextFloor = new Floor(newNextFloorGenerators, newNextFloorMicrochips);
                    moveFloorDown(neighbors, newFloor, newNextFloor);
                }
            }

            return neighbors;
        }

        private void moveFloorDown(HashMap<Step, Long> neighbors, Floor newFloor, Floor newNextFloor) {
            var newFloors = new Floor[] {
                    new Floor(new HashSet<>(floors[0].generators()), new HashSet<>(floors[0].microchips())),
                    new Floor(new HashSet<>(floors[1].generators()), new HashSet<>(floors[1].microchips())),
                    new Floor(new HashSet<>(floors[2].generators()), new HashSet<>(floors[2].microchips())),
                    new Floor(new HashSet<>(floors[3].generators()), new HashSet<>(floors[3].microchips()))
            };
            newFloors[position] = newFloor;
            newFloors[position - 1] = newNextFloor;
            var newState = new State(newFloors[0], newFloors[1], newFloors[2], newFloors[3], position - 1);

            if (newState.isStateValid()) {
                var newStep = STEPS.computeIfAbsent(newState, state -> new Step(newFloors, position - 1));

                neighbors.put(newStep, 1L);
            }
        }

        private void moveFloorUp(HashMap<Step, Long> neighbors, Floor newFloor, Floor newNextFloor) {
            var newFloors = new Floor[] {
                    new Floor(new HashSet<>(floors[0].generators()), new HashSet<>(floors[0].microchips())),
                    new Floor(new HashSet<>(floors[1].generators()), new HashSet<>(floors[1].microchips())),
                    new Floor(new HashSet<>(floors[2].generators()), new HashSet<>(floors[2].microchips())),
                    new Floor(new HashSet<>(floors[3].generators()), new HashSet<>(floors[3].microchips()))
            };
            newFloors[position] = newFloor;
            newFloors[position + 1] = newNextFloor;
            var newState = new State(newFloors[0], newFloors[1], newFloors[2], newFloors[3], position + 1);

            if (newState.isStateValid()) {
                var newStep = STEPS.computeIfAbsent(newState, state -> new Step(newFloors, position + 1));

                neighbors.put(newStep, 1L);
            }
        }

        @Override
        public void printStateInformation() {
            System.out.println(getDistance());
            System.out.println(position);
            System.out.println(floors[0]);
            System.out.println(floors[1]);
            System.out.println(floors[2]);
            System.out.println(floors[3]);
            System.out.println();
            System.out.println();
        }
    }

    private record Floor(Set<String> generators, Set<String> microchips) {
        public boolean isFLoorValid() {
            if (generators.isEmpty()) {
                return true;
            }
            for (var microchip : microchips) {
                if (!generators.contains(microchip)) {
                    return false;
                }
            }
            return true;
        }
    }

    private record State(Floor floor1, Floor floor2, Floor floor3, Floor floor4, int position) {
        public boolean isStateValid() {
            return floor1.isFLoorValid() &&
                   floor2.isFLoorValid() &&
                   floor3.isFLoorValid() &&
                   floor4.isFLoorValid();
        }
    }
}
