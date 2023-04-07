package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.stream.Stream;

public class Day22 implements BiAdventOfCodeSolution<Integer, Long> {
    private static final Map<State, Step> STEP_MAP = new HashMap<>();

    @Override
    public Integer handlePart1(Stream<String> input) {
        var lines = input.toList();

        var depth = Integer.parseInt(lines.get(0).substring(7));

        var targetCoords = lines.get(1).substring(8).split(",");

        var target = new Coordinate(Integer.parseInt(targetCoords[0]),
                                    Integer.parseInt(targetCoords[1]));

        var grid = generateGrid(depth, target, 1);

        var riskLevel = 0;

        for (var y = 0; y <= target.y(); y++) {
            for (var x = 0; x <= target.x(); x++) {
                switch (grid.get(x, y).regionType) {
                    case WET -> riskLevel++;
                    case NARROW -> riskLevel += 2;
                }
            }
        }

        return riskLevel;
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        STEP_MAP.clear();

        var lines = input.toList();

        var depth = Integer.parseInt(lines.get(0).substring(7));

        var targetCoords = lines.get(1).substring(8).split(",");

        var target = new Coordinate(Integer.parseInt(targetCoords[0]),
                                    Integer.parseInt(targetCoords[1]));

        var grid = generateGrid(depth, target, 2);

        var start = new Step(new Coordinate(0, 0), target, Equipment.TORCH, grid,
                             List.of(new State(new Coordinate(0, 0), Equipment.TORCH)));
        start.setDistance(0);

        STEP_MAP.put(new State(new Coordinate(0, 0), Equipment.TORCH), start);

        return Dijkstra.findShortestDistance(start, node -> {
                           var step = (Step) node;
                           return step.currentPosition.equals(target) &&
                                  step.currentEquipment == Equipment.TORCH;
                       })
                       .map(Node::getDistance)
                       .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private Grid<Region> generateGrid(int depth, Coordinate target, int growthFactor) {
        var grid = new Grid<Region>(0, 0, target.x() * growthFactor, target.y() * growthFactor);

        for (var y = 0; y <= target.y() * growthFactor; y++) {
            for (var x = 0; x <= target.x() * growthFactor; x++) {
                int geologicIndex;
                if ((x == 0 && y == 0) || (x == target.x() && y == target.y())) {
                    geologicIndex = 0;
                } else if (x == 0) {
                    geologicIndex = y * 48271;
                } else if (y == 0) {
                    geologicIndex = x * 16807;
                } else {
                    geologicIndex = (grid.get(x - 1, y).erosionLevel() * grid.get(x, y - 1).erosionLevel());
                }
                int erosionLevel = (geologicIndex + depth) % 20183;

                var regionType = switch (erosionLevel % 3) {
                    case 0 -> RegionType.ROCKY;
                    case 1 -> RegionType.WET;
                    case 2 -> RegionType.NARROW;
                    default -> throw new AssertionError("Impossible!");
                };

                var region = new Region(geologicIndex, erosionLevel, regionType);
                grid.set(new Coordinate(x, y), region);
            }
        }
        return grid;
    }

    private static class Step extends Node<Step> {
        private final Coordinate currentPosition;
        private final Coordinate target;
        private final Equipment currentEquipment;
        private final Grid<Region> grid;
        private final List<State> visitedStates;

        private Step(Coordinate currentPosition, Coordinate target, Equipment currentEquipment, Grid<Region> grid,
                     List<State> visitedStates) {
            this.currentPosition = currentPosition;
            this.target = target;
            this.currentEquipment = currentEquipment;
            this.grid = grid;
            this.visitedStates = visitedStates;
        }

        @Override
        public Map<Step, Long> getNeighbors() {
            var neighbors = new HashMap<Step, Long>();

            for (var newPosition : currentPosition.getStraightNeighbours()) {
                if (!grid.isCoordinateInGrid(newPosition)) {
                    continue;
                }
                var newRegion = grid.get(newPosition);
                switch (currentEquipment) {
                    case TORCH -> {
                        if (newRegion.regionType == RegionType.ROCKY ||
                            newRegion.regionType == RegionType.NARROW) {
                            var step = createStep(newPosition, Equipment.TORCH);
                            neighbors.put(step, 1L);
                        }
                    }
                    case NEITHER -> {
                        if (newRegion.regionType == RegionType.WET ||
                            newRegion.regionType == RegionType.NARROW) {
                            var step = createStep(newPosition, Equipment.NEITHER);
                            neighbors.put(step, 1L);
                        }
                    }
                    case CLIMBING_GEAR -> {
                        if (newRegion.regionType == RegionType.ROCKY ||
                            newRegion.regionType == RegionType.WET) {
                            var step = createStep(newPosition, Equipment.CLIMBING_GEAR);
                            neighbors.put(step, 1L);
                        }
                    }
                }
            }
            var currentRegion = grid.get(currentPosition);
            switch (currentRegion.regionType) {
                case ROCKY -> {
                    Step step;
                    if (currentEquipment == Equipment.TORCH) {
                        step = createStep(currentPosition, Equipment.CLIMBING_GEAR);
                    } else {
                        step = createStep(currentPosition, Equipment.TORCH);
                    }
                    neighbors.put(step, 7L);
                }
                case WET -> {
                    Step step;
                    if (currentEquipment == Equipment.CLIMBING_GEAR) {
                        step = createStep(currentPosition, Equipment.NEITHER);
                    } else {
                        step = createStep(currentPosition, Equipment.CLIMBING_GEAR);
                    }
                    neighbors.put(step, 7L);
                }
                case NARROW -> {
                    Step step;
                    if (currentEquipment == Equipment.TORCH) {
                        step = createStep(currentPosition, Equipment.NEITHER);
                    } else {
                        step = createStep(currentPosition, Equipment.TORCH);
                    }
                    neighbors.put(step, 7L);
                }
            }

            return neighbors;
        }

        private Step createStep(Coordinate newPosition, Equipment equipment) {
            var newVisitedStates = new ArrayList<>(visitedStates);
            newVisitedStates.add(new State(newPosition, equipment));
            return STEP_MAP.computeIfAbsent(new State(newPosition, equipment),
                                            state -> new Step(newPosition, target,
                                                              equipment, grid,
                                                              newVisitedStates));
        }

        @Override
        public void printStateInformation() {
            for (var state : visitedStates) {
                System.out.println(state);
            }
        }
    }

    private record State(Coordinate position, Equipment equipment) {
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            State state = (State) o;
            return Objects.equals(position, state.position) && equipment == state.equipment;
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, equipment);
        }
    }

    private record Region(int geologicIndex, int erosionLevel, RegionType regionType) {
        @Override
        public String toString() {
            return switch (regionType) {
                case ROCKY -> ".";
                case NARROW -> "|";
                case WET -> "=";
            };
        }
    }

    private enum RegionType {
        ROCKY,
        NARROW,
        WET
    }

    private enum Equipment {
        TORCH,
        CLIMBING_GEAR,
        NEITHER
    }
}
