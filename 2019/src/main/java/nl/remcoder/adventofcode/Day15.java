package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Day15 implements AdventOfCodeSolution<Integer> {
    private Coordinate oxygenSystem;
    private Grid<Square> grid;

    @Override
    public Integer handlePart1(Stream<String> input) throws Exception {
        generateGrid(input);

        var start = new Step(new Coordinate(0, 0), grid, List.of(), new HashMap<>());
        start.setDistance(0);

        return (int) Dijkstra.findShortestDistance(start, node -> {
            var step = (Step) node;
            return step.current.equals(oxygenSystem);
        }).getDistance();
    }

    @Override
    public Integer handlePart2(Stream<String> input) throws Exception {
        generateGrid(input);
        
        var filledNodes = new HashSet<Coordinate>();
        var nodesToProcess = new ArrayList<Coordinate>();
        nodesToProcess.add(oxygenSystem);
        
        var minutes = 0;
        
        while (!nodesToProcess.isEmpty()) {
            var newNodesToProcess = new ArrayList<Coordinate>();
            for (var node : nodesToProcess) {
                for (var neighbor : node.getStraightNeighbours()) {
                    if (!filledNodes.contains(neighbor)) {
                        filledNodes.add(neighbor);
                        if (grid.get(neighbor).getState() == State.OPEN) {
                            newNodesToProcess.add(neighbor);
                        }
                    }
                }
            }
            nodesToProcess = newNodesToProcess;
            minutes++;
        }
        
        return minutes - 1;
    }

    private void generateGrid(Stream<String> input) throws InterruptedException {
        var line = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        var opcodes = Arrays.stream(line.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();

        var outputState = new LinkedBlockingQueue<Long>();
        var inputState = new LinkedBlockingQueue<Long>();

        var intcodeComputer = new IntCodeComputer(opcodes, inputState, outputState);

        var thread = new Thread(intcodeComputer);

        thread.start();

        grid = new Grid<>(0, 0, 0, 0);

        var current = new Coordinate(0, 0);

        var startNode = new Square(State.START);
        startNode.setVisited(true);
        grid.set(current, startNode);

        var mapCompleted = false;

        var nodesToInvestigate = new Stack<Coordinate>();

        while (!mapCompleted) {
            var openSides = determineOpenSides(current, inputState, outputState);
            if (openSides.size() > 2) {
                if (!nodesToInvestigate.contains(current) && !allSidesVisited(current)) {
                    nodesToInvestigate.push(current);
                }
            }
            if (openSides.size() == 1 && !current.equals(new Coordinate(0, 0))) {
                if (nodesToInvestigate.isEmpty()) {
                    mapCompleted = true;
                } else {
                    var lastNode = nodesToInvestigate.pop();
                    var start = new Step(current, grid, List.of(), new HashMap<>());
                    start.setDistance(0);
                    var path = (Step) Dijkstra.findShortestDistance(start, node -> {
                        var step = (Step) node;
                        return step.current.equals(lastNode);
                    });
                    for (var step : path.steps) {
                        inputState.put(step);
                        outputState.take();
                    }
                    current = lastNode;
                }
            }
            //move to next unvisited side
            openSides = determineOpenSides(current, inputState, outputState);
            boolean allSidesVisited = true;
            for (var openSide : openSides) {
                Coordinate next = current.getNeighbor(openSide);
                var status = grid.get(next);
                if (!status.isVisited()) {
                    current = next;
                    status.setVisited(true);
                    inputState.put(switch (openSide) {
                        case UP -> 1L;
                        case DOWN -> 2L;
                        case LEFT -> 3L;
                        case RIGHT -> 4L;
                    });
                    outputState.take();
                    allSidesVisited = false;
                    break;
                }
            }
            if (allSidesVisited) {
                if (nodesToInvestigate.isEmpty()) {
                    mapCompleted = true;
                } else {
                    var lastNode = nodesToInvestigate.pop();
                    var start = new Step(current, grid, List.of(), new HashMap<>());
                    start.setDistance(0);
                    var path = (Step) Dijkstra.findShortestDistance(start, node -> {
                        var step = (Step) node;
                        return step.current.equals(lastNode);
                    });
                    for (var step : path.steps) {
                        inputState.put(step);
                        outputState.take();
                    }
                    current = lastNode;
                }
            }
        }
    }

    private boolean allSidesVisited(Coordinate current) {
        for (var neighbor : current.getStraightNeighbours()) {
            var other = grid.get(neighbor);
            if (other == null || !other.isVisited()) {
                return false;
            }
        }
        return true;
    }

    private Set<Direction> determineOpenSides(Coordinate current, LinkedBlockingQueue<Long> inputState,
                                              LinkedBlockingQueue<Long> outputState)
            throws InterruptedException {
        Set<Direction> openSides = new HashSet<>();
        checkAbove(current, inputState, outputState, openSides);
        checkBelow(current, inputState, outputState, openSides);
        checkRight(current, inputState, outputState, openSides);
        checkLeft(current, inputState, outputState, openSides);
        return openSides;
    }

    private void checkAbove(Coordinate current, LinkedBlockingQueue<Long> inputState,
                            LinkedBlockingQueue<Long> outputState, Set<Direction> openSides)
            throws InterruptedException {
        inputState.put(1L);
        long result = outputState.take();
        if (result > 0) {
            openSides.add(Direction.UP);
            inputState.put(2L);
            outputState.take();
            if (grid.get(current.above()) == null) {
                grid.set(current.above(), new Square(State.OPEN));
            }
        } else {
            grid.set(current.above(), new Square(State.WALL));
        }
        if (result == 2) {
            oxygenSystem = current.above();
        }
    }

    private void checkBelow(Coordinate current, LinkedBlockingQueue<Long> inputState,
                            LinkedBlockingQueue<Long> outputState, Set<Direction> openSides)
            throws InterruptedException {
        inputState.put(2L);
        long result = outputState.take();
        if (result > 0) {
            openSides.add(Direction.DOWN);
            inputState.put(1L);
            outputState.take();
            if (grid.get(current.below()) == null) {
                grid.set(current.below(), new Square(State.OPEN));
            }
        } else {
            grid.set(current.below(), new Square(State.WALL));
        }
        if (result == 2) {
            oxygenSystem = current.below();
        }
    }

    private void checkRight(Coordinate current, LinkedBlockingQueue<Long> inputState,
                            LinkedBlockingQueue<Long> outputState, Set<Direction> openSides)
            throws InterruptedException {
        inputState.put(4L);
        long result = outputState.take();
        if (result > 0) {
            openSides.add(Direction.RIGHT);
            inputState.put(3L);
            outputState.take();
            if (grid.get(current.right()) == null) {
                grid.set(current.right(), new Square(State.OPEN));
            }
        } else {
            grid.set(current.right(), new Square(State.WALL));
        }
        if (result == 2) {
            oxygenSystem = current.right();
        }
    }

    private void checkLeft(Coordinate current, LinkedBlockingQueue<Long> inputState,
                           LinkedBlockingQueue<Long> outputState, Set<Direction> openSides)
            throws InterruptedException {
        inputState.put(3L);
        long result = outputState.take();
        if (result > 0) {
            openSides.add(Direction.LEFT);
            inputState.put(4L);
            outputState.take();
            if (grid.get(current.left()) == null) {
                grid.set(current.left(), new Square(State.OPEN));
            }
        } else {
            grid.set(current.left(), new Square(State.WALL));
        }
        if (result == 2) {
            oxygenSystem = current.left();
        }
    }

    private static class Step extends Node {
        private final Coordinate current;
        private final Grid<Square> grid;
        private final List<Long> steps;
        private final Map<Coordinate, Step> foundSteps;

        private Step(Coordinate current, Grid<Square> grid, List<Long> steps,
                     Map<Coordinate, Step> foundSteps) {
            this.current = current;
            this.grid = grid;
            this.steps = steps;
            this.foundSteps = foundSteps;
        }

        @Override
        public Map<? extends Node, Long> getNeighbors() {
            var neighbors = new HashMap<Step, Long>();

            getStep(current.above(), 1L, neighbors);
            getStep(current.below(), 2L, neighbors);
            getStep(current.left(), 3L, neighbors);
            getStep(current.right(), 4L, neighbors);

            return neighbors;
        }

        private void getStep(Coordinate coordinate, long step, HashMap<Step, Long> neighbors) {
            if (foundSteps.containsKey(coordinate)) {
                neighbors.put(foundSteps.get(coordinate), 1L);
            } else {
                var next = grid.get(coordinate);
                if (next != null && next.getState() == State.OPEN) {
                    var newSteps = new ArrayList<>(steps);
                    newSteps.add(step);
                    var newStep = new Step(coordinate, grid, newSteps, foundSteps);
                    neighbors.put(newStep, 1L);
                    foundSteps.put(coordinate, newStep);
                }
            }
        }

        @Override
        public void printStateInformation() {

        }
    }

    private static class Square {
        private final State state;
        private boolean visited;

        private Square(State state) {
            this.state = state;
            if (state == State.WALL) {
                visited = true;
            }
        }

        public State getState() {
            return state;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        @Override
        public String toString() {
            return switch (state) {
                case OPEN -> " ";
                case WALL -> "#";
                case START -> "D";
            };
        }
    }

    private enum State {
        WALL,
        OPEN,
        START
    }
}
