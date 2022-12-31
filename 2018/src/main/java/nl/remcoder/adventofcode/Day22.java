package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.stream.Stream;

public class Day22 implements AdventOfCodeSolution<Integer> {
    private static final Map<State, Step> STEP_MAP = new HashMap<>();

    @Override
    public Integer handlePart1(Stream<String> input) {
        var lines = input.toList();

        var depth = Integer.parseInt(lines.get(0).substring(7));

        var targetCoords = lines.get(1).substring(8).split(",");

        var target = new Coordinate(Integer.parseInt(targetCoords[0]),
                                    Integer.parseInt(targetCoords[1]));

        var grid = new Grid<Region>(0, 0, target.x(), target.y());

        var riskLevel = 0;

        for (var y = 0; y <= target.y(); y++) {
            for (var x = 0; x <= target.x(); x++) {
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
                    case 1 -> {
                        riskLevel++;
                        yield RegionType.WET;
                    }
                    case 2 -> {
                        riskLevel += 2;
                        yield RegionType.NARROW;
                    }
                    default -> throw new AssertionError("Impossible!");
                };

                var region = new Region(geologicIndex, erosionLevel, regionType);
                grid.set(new Coordinate(x, y), region);
            }
        }

        return riskLevel;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var lines = input.toList();

        var depth = Integer.parseInt(lines.get(0).substring(7));

        var targetCoords = lines.get(1).substring(8).split(",");

        var target = new Coordinate(Integer.parseInt(targetCoords[0]),
                                    Integer.parseInt(targetCoords[1]));

        var grid = new Grid<Region>(0, 0, target.x() * 2, target.y() * 2);

        for (var y = 0; y <= target.y() * 2; y++) {
            for (var x = 0; x <= target.x() * 2; x++) {
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

        var stateMap = new HashMap<State, Integer>();
        var steps = new HashMap<State, List<State>>();

        var start = new State(new Coordinate(0, 0), Equipment.TORCH);
        stateMap.put(start, 0);
        steps.put(start, List.of(start));

        var statesToCheck = List.of(start);

        while (!statesToCheck.isEmpty()) {
            var newStates = new ArrayList<State>();

            for (var state : statesToCheck) {
                var currentDistance = stateMap.get(state);
                for (var coordinate : state.position().getStraightNeighbours()) {
                    if (grid.isCoordinateInGrid(coordinate)) {
                        var region = grid.get(coordinate);
                        switch (region.regionType) {
                            case ROCKY -> {
                                processState(stateMap, newStates, state, currentDistance, coordinate, Equipment.TORCH,
                                             steps);
                                processState(stateMap, newStates, state, currentDistance, coordinate,
                                             Equipment.CLIMBING_GEAR, steps);
                            }
                            case NARROW -> {
                                processState(stateMap, newStates, state, currentDistance, coordinate,
                                             Equipment.NEITHER, steps);
                                processState(stateMap, newStates, state, currentDistance, coordinate, Equipment.TORCH,
                                             steps);
                            }
                            case WET -> {
                                processState(stateMap, newStates, state, currentDistance, coordinate,
                                             Equipment.NEITHER, steps);
                                processState(stateMap, newStates, state, currentDistance, coordinate,
                                             Equipment.CLIMBING_GEAR, steps);
                            }
                        }
                    }
                }
            }

            statesToCheck = newStates;
        }


//        var start = new Step(new Coordinate(0, 0), target, Equipment.TORCH, grid,
//                             List.of(new State(new Coordinate(0, 0), Equipment.TORCH)));
//        start.setDistance(0);
//
//        STEP_MAP.put(new State(new Coordinate(0, 0), Equipment.TORCH), start);
//
//        Node end = Dijkstra.findShortestDistance(start, node -> {
//            var step = (Step) node;
//            return step.currentPosition.equals(target);
//        });
//
//        end.printStateInformation();
//        
//        return (int) end.getDistance();

        var end = new State(target, Equipment.TORCH);
        
        for (var step : steps.get(end)) {
            System.out.println(step);
            System.out.println(stateMap.get(step));
        }
        
        return stateMap.get(end);
    }

    private void processState(HashMap<State, Integer> stateMap, ArrayList<State> newStates, State state,
                              Integer currentDistance, Coordinate coordinate, Equipment equipment,
                              Map<State, List<State>> steps) {
        var newState = new State(coordinate, equipment);
        int distanceToTorchState;
        if (state.equipment() == equipment) {
            distanceToTorchState = currentDistance + 1;
        } else {
            distanceToTorchState = currentDistance + 8;
        }
        if (distanceToTorchState < stateMap.getOrDefault(newState, Integer.MAX_VALUE)) {
            var newSteps = new ArrayList<>(steps.get(state));
            newSteps.add(newState);
            steps.put(newState, newSteps);
            stateMap.put(newState, distanceToTorchState);
            newStates.add(newState);
        }
    }

    private static class Step extends Node {
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
        public Map<? extends Node, Long> getNeighbors() {
            var neighbors = new HashMap<Step, Long>();

            for (var newPosition : currentPosition.getStraightNeighbours()) {
                if (newPosition.equals(target)) {
                    var newVisitedStates = new ArrayList<>(visitedStates);
                    newVisitedStates.add(new State(newPosition, Equipment.TORCH));
                    if (currentEquipment == Equipment.TORCH) {
                        neighbors.put(new Step(newPosition, target, currentEquipment, grid, newVisitedStates), 1L);
                    } else {
                        neighbors.put(new Step(newPosition, target, Equipment.TORCH, grid, newVisitedStates), 8L);
                    }
                    break;
                }

                if (!grid.isCoordinateInGrid(newPosition)) {
                    continue;
                }
                var newRegion = grid.get(newPosition);
                switch (newRegion.regionType()) {
                    case ROCKY -> {
                        var step = createStep(newPosition, Equipment.TORCH);
                        if (currentEquipment == Equipment.TORCH) {
                            neighbors.put(step, 1L);
                        } else {
                            neighbors.put(step, 8L);
                        }
                        step = createStep(newPosition, Equipment.CLIMBING_GEAR);
                        if (currentEquipment == Equipment.CLIMBING_GEAR) {
                            neighbors.put(step, 1L);
                        } else {
                            neighbors.put(step, 8L);
                        }
                    }
                    case NARROW -> {
                        var step = createStep(newPosition, Equipment.NEITHER);
                        if (currentEquipment == Equipment.NEITHER) {
                            neighbors.put(step, 1L);
                        } else {
                            neighbors.put(step, 8L);
                        }
                        step = createStep(newPosition, Equipment.TORCH);
                        if (currentEquipment == Equipment.TORCH) {
                            neighbors.put(step, 1L);
                        } else {
                            neighbors.put(step, 8L);
                        }
                    }
                    case WET -> {
                        var step = createStep(newPosition, Equipment.NEITHER);
                        if (currentEquipment == Equipment.NEITHER) {
                            neighbors.put(step, 1L);
                        } else {
                            neighbors.put(step, 8L);
                        }
                        step = createStep(newPosition, Equipment.CLIMBING_GEAR);
                        if (currentEquipment == Equipment.CLIMBING_GEAR) {
                            neighbors.put(step, 1L);
                        } else {
                            neighbors.put(step, 8L);
                        }
                    }
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
//                var step = STEP_MAP.get(state);
//                System.out.println(step.getDistance());
                System.out.println(state);
            }
        }

//        @Override
//        public int compareTo(Node o) {
//            if (o instanceof Step step) {
//                return Comparator.comparing(Step::getDistance)
//                                 .thenComparing(step1 -> step1.currentPosition.getDistanceTo(target))
//                                 .compare(this, step);
//            } else {
//                return super.compareTo(o);
//            }
//        }
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
