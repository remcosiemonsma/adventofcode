package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Direction;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Stream;

public class Day24 implements AdventOfCodeSolution<Long> {
    private static final String EMPTY_BUGS = """
                                             .....
                                             .....
                                             .....
                                             .....
                                             .....
                                             """;

    private int minutes;

    @Override
    public Long handlePart1(Stream<String> input) {
        var grid = new Grid<>(input.map(s -> s.chars()
                                              .mapToObj(this::toBug)
                                              .toArray(Bug[]::new))
                                   .toArray(Bug[][]::new));

        var seenStates = new HashSet<>();

        while (seenStates.add(grid.toString())) {
            for (var coordinate : grid.coordinates()) {
                int aliveNeighbors = 0;
                for (var neighborCoordinate : coordinate.getStraightNeighbours()) {
                    var neighbor = grid.get(neighborCoordinate);
                    if (neighbor != null && neighbor.getState() == State.ALIVE) {
                        aliveNeighbors++;
                    }
                }
                Bug bug = grid.get(coordinate);
                if (bug.getState() == State.ALIVE && aliveNeighbors != 1) {
                    bug.setNextState(State.DEAD);
                }
                if (bug.getState() == State.DEAD && (aliveNeighbors == 1 || aliveNeighbors == 2)) {
                    bug.setNextState(State.ALIVE);
                }
            }
            grid.values().forEach(Bug::switchState);
        }

        return getBiodiversityRating(grid);
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var grid = createBugGrid(input);

        var gridMap = new HashMap<Integer, Grid<Bug>>();
        gridMap.put(0, grid);

        int highestLevel = 0;
        int lowestLevel = 0;

        for (var i = 0; i < minutes; i++) {
            for (var entry : gridMap.entrySet()) {
                for (var coordinate : entry.getValue().coordinates()) {
                    var aliveNeighbors = 0;
                    for (var direction : Direction.values()) {
                        var neighborCoordinate = coordinate.getNeighbor(direction);
                        var neighbor = entry.getValue().get(neighborCoordinate);
                        if (neighbor == null) {
                            if (entry.getKey() < highestLevel) {
                                var upperGrid = gridMap.get(entry.getKey() + 1);
                                switch (direction) {
                                    case UP -> {
                                        if (upperGrid.get(2, 1).getState() == State.ALIVE) {
                                            aliveNeighbors++;
                                        }
                                    }
                                    case DOWN -> {
                                        if (upperGrid.get(2, 3).getState() == State.ALIVE) {
                                            aliveNeighbors++;
                                        }
                                    }
                                    case LEFT -> {
                                        if (upperGrid.get(1, 2).getState() == State.ALIVE) {
                                            aliveNeighbors++;
                                        }
                                    }
                                    case RIGHT -> {
                                        if (upperGrid.get(3, 2).getState() == State.ALIVE) {
                                            aliveNeighbors++;
                                        }
                                    }
                                }
                            }
                        } else if (neighbor.getState() == State.CENTER) {
                            if (entry.getKey() > lowestLevel) {
                                aliveNeighbors +=
                                        getBugCountOnOuterEdge(direction.flip(), gridMap.get(entry.getKey() - 1));
                            }
                        } else if (neighbor.getState() == State.ALIVE) {
                            aliveNeighbors++;
                        }
                    }
                    var bug = entry.getValue().get(coordinate);
                    if (bug.getState() == State.ALIVE && aliveNeighbors != 1) {
                        bug.setNextState(State.DEAD);
                    }
                    if (bug.getState() == State.DEAD && (aliveNeighbors == 1 || aliveNeighbors == 2)) {
                        bug.setNextState(State.ALIVE);
                    }
                }
            }
            lowestLevel = generateLowerGrid(gridMap, lowestLevel);
            highestLevel = generateHigherGrid(gridMap, highestLevel);

            gridMap.values().forEach(bugGrid -> bugGrid.values().forEach(Bug::switchState));
        }

        return gridMap.values()
                      .stream()
                      .flatMap(g -> g.values().stream())
                      .filter(bug -> bug.getState() == State.ALIVE)
                      .count();
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    private int generateHigherGrid(HashMap<Integer, Grid<Bug>> gridMap, int highestLevel) {
        var highestGrid = gridMap.get(highestLevel);
        var bugsTopAlive = getBugCountOnOuterEdge(Direction.UP, highestGrid);
        var bugsBottomAlive = getBugCountOnOuterEdge(Direction.DOWN, highestGrid);
        var bugsLeftAlive = getBugCountOnOuterEdge(Direction.LEFT, highestGrid);
        var bugsRightAlive = getBugCountOnOuterEdge(Direction.RIGHT, highestGrid);
        if (bugsTopAlive == 1 || bugsTopAlive == 2 ||
            bugsBottomAlive == 1 || bugsBottomAlive == 2 ||
            bugsLeftAlive == 1 || bugsLeftAlive == 2 ||
            bugsRightAlive == 1 || bugsRightAlive == 2) {
            var newGrid = generateEmptyGrid();
            highestLevel++;
            gridMap.put(highestLevel, newGrid);
            if (bugsTopAlive == 1 || bugsTopAlive == 2) {
                newGrid.get(2, 1).setNextState(State.ALIVE);
            }
            if (bugsBottomAlive == 1 || bugsBottomAlive == 2) {
                newGrid.get(2, 3).setNextState(State.ALIVE);
            }
            if (bugsLeftAlive == 1 || bugsLeftAlive == 2) {
                newGrid.get(1, 2).setNextState(State.ALIVE);
            }
            if (bugsRightAlive == 1 || bugsRightAlive == 2) {
                newGrid.get(3, 2).setNextState(State.ALIVE);
            }
        }
        return highestLevel;
    }

    private int generateLowerGrid(HashMap<Integer, Grid<Bug>> gridMap, int lowestLevel) {
        var lowestGrid = gridMap.get(lowestLevel);
        boolean bug8Alive = lowestGrid.get(2, 1).getState() == State.ALIVE;
        boolean bug12Alive = lowestGrid.get(1, 2).getState() == State.ALIVE;
        boolean bug14Alive = lowestGrid.get(3, 2).getState() == State.ALIVE;
        boolean bug18Alive = lowestGrid.get(2, 3).getState() == State.ALIVE;
        if (bug8Alive || bug12Alive || bug14Alive || bug18Alive) {
            var newGrid = generateEmptyGrid();
            lowestLevel--;
            gridMap.put(lowestLevel, newGrid);
            if (bug8Alive) {
                for (var x = 0; x <= 4; x++) {
                    newGrid.get(x, 0).setNextState(State.ALIVE);
                }
            }
            if (bug12Alive) {
                for (var y = 0; y <= 4; y++) {
                    newGrid.get(0, y).setNextState(State.ALIVE);
                }
            }
            if (bug14Alive) {
                for (var y = 0; y <= 4; y++) {
                    newGrid.get(4, y).setNextState(State.ALIVE);
                }
            }
            if (bug18Alive) {
                for (var x = 0; x <= 4; x++) {
                    newGrid.get(x, 4).setNextState(State.ALIVE);
                }
            }
        }
        return lowestLevel;
    }

    private int getBugCountOnOuterEdge(Direction edge, Grid<Bug> grid) {
        var bugCount = 0;

        switch (edge) {
            case UP -> {
                for (int x = 0; x <= 4; x++) {
                    if (grid.get(x, 0).getState() == State.ALIVE) {
                        bugCount++;
                    }
                }
            }
            case DOWN -> {
                for (int x = 0; x <= 4; x++) {
                    if (grid.get(x, 4).getState() == State.ALIVE) {
                        bugCount++;
                    }
                }
            }
            case LEFT -> {
                for (int y = 0; y <= 4; y++) {
                    if (grid.get(0, y).getState() == State.ALIVE) {
                        bugCount++;
                    }
                }
            }
            case RIGHT -> {
                for (int y = 0; y <= 4; y++) {
                    if (grid.get(4, y).getState() == State.ALIVE) {
                        bugCount++;
                    }
                }
            }
        }

        return bugCount;
    }

    private Grid<Bug> generateEmptyGrid() {
        return createBugGrid(EMPTY_BUGS.lines());
    }

    private Grid<Bug> createBugGrid(Stream<String> input) {
        var grid = new Grid<>(input.map(s -> s.chars()
                                              .mapToObj(this::toBug)
                                              .toArray(Bug[]::new))
                                   .toArray(Bug[][]::new));

        grid.set(2, 2, new Bug(State.CENTER));

        return grid;
    }

    private long getBiodiversityRating(Grid<Bug> grid) {
        long biodiversityRating = 0;
        long biodiversityPoints = 1;

        for (int y = 0; y <= 4; y++) {
            for (int x = 0; x <= 4; x++) {
                if (grid.get(x, y).getState() == State.ALIVE) {
                    biodiversityRating += biodiversityPoints;
                }
                biodiversityPoints *= 2;
            }
        }

        return biodiversityRating;
    }

    private Bug toBug(int c) {
        return new Bug(switch (c) {
            case '.' -> State.DEAD;
            case '#' -> State.ALIVE;
            default -> throw new AssertionError("Eek!");
        });
    }

    private static class Bug {
        private State state;
        private State nextState;

        private Bug(State state) {
            this.state = state;
            nextState = state;
        }

        private State getState() {
            return state;
        }

        private void setNextState(State state) {
            nextState = state;
        }

        private void switchState() {
            state = nextState;
        }

        @Override
        public String toString() {
            return switch (state) {
                case DEAD -> ".";
                case ALIVE -> "#";
                case CENTER -> "?";
            };
        }
    }

    private enum State {
        ALIVE,
        DEAD,
        CENTER
    }
}
