package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.stream.CountingCollector;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

public class Day23 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var elvesList = new ArrayList<Elf>();

        Elf[][] elves = input.map(s -> s.chars()
                                        .mapToObj(value -> value == '#' ? new Elf() : null)
                                        .toArray(Elf[]::new))
                             .toArray(Elf[][]::new);

        var elvesGrid = new Grid<>(elves);

        for (int y = elvesGrid.getStarty(); y <= elvesGrid.getEndy(); y++) {
            for (int x = elvesGrid.getStartx(); x <= elvesGrid.getEndx(); x++) {
                Coordinate coordinate = new Coordinate(x, y);
                var elf = elvesGrid.get(coordinate);
                if (elf != null) {
                    elvesList.add(elf);
                    elf.setPosition(coordinate);
                }
            }
        }

        for (int round = 0; round < 10; round++) {
            Map<Coordinate, Integer> coordinateCount = elvesList.stream()
                                                                .map(elf -> elf.determineNextPosition(elvesGrid))
                                                                .collect(new CountingCollector<>());
            
            elvesList.forEach(elf -> elf.move(elvesGrid, coordinateCount));
        }

        elvesGrid.calculateSize();
        
        int count = 0;
        for (int y = elvesGrid.getStarty(); y <= elvesGrid.getEndy(); y++) {
            for (int x = elvesGrid.getStartx(); x <= elvesGrid.getEndx(); x++) {
                if (elvesGrid.get(new Coordinate(x, y)) == null) {
                    count++;
                }
            }
        }
        
        return count;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var elvesList = new ArrayList<Elf>();

        Elf[][] elves = input.map(s -> s.chars()
                                        .mapToObj(value -> value == '#' ? new Elf() : null)
                                        .toArray(Elf[]::new))
                             .toArray(Elf[][]::new);

        var elvesGrid = new Grid<>(elves);

        for (int y = elvesGrid.getStarty(); y <= elvesGrid.getEndy(); y++) {
            for (int x = elvesGrid.getStartx(); x <= elvesGrid.getEndx(); x++) {
                Coordinate coordinate = new Coordinate(x, y);
                var elf = elvesGrid.get(coordinate);
                if (elf != null) {
                    elvesList.add(elf);
                    elf.setPosition(coordinate);
                }
            }
        }
        
        int round = 0;
        
        boolean elvesMoved = true;
        
        while (elvesMoved) {
            Map<Coordinate, Integer> coordinateCount = elvesList.stream()
                                                                .map(elf -> elf.determineNextPosition(elvesGrid))
                                                                .collect(new CountingCollector<>());
            
            var moveCount = elvesList.stream()
                                     .map(elf -> elf.move(elvesGrid, coordinateCount))
                                     .filter(Boolean::booleanValue)
                                     .count();
            
            elvesMoved = moveCount != 0;
            round++;
        }
        
        return round;
    }

    private static class Elf {
        private static final Direction[] DIRECTIONS =
                new Direction[] {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        private int firstDirection = 0;
        private Coordinate position;
        private Coordinate nextPosition;

        public Elf() {
        }

        public void setPosition(Coordinate position) {
            this.position = position;
        }

        public Coordinate determineNextPosition(Grid<Elf> grid) {
            if (isMovementPossible(grid)) {
                for (int i = 0; i < 4; i++) {
                    var direction = DIRECTIONS[(firstDirection + i) % 4];
                    switch (direction) {
                        case UP -> {
                            if (isUpPossible(grid)) {
                                incrementDirection();
                                nextPosition = position.above();
                                return nextPosition;
                            }
                        }
                        case DOWN -> {
                            if (isDownPossible(grid)) {
                                incrementDirection();
                                nextPosition = position.below();
                                return nextPosition;
                            }
                        }
                        case LEFT -> {
                            if (isLeftPossible(grid)) {
                                incrementDirection();
                                nextPosition = position.left();
                                return nextPosition;
                            }
                        }
                        case RIGHT -> {
                            if (isRightPossible(grid)) {
                                incrementDirection();
                                nextPosition = position.right();
                                return nextPosition;
                            }
                        }
                    }
                }
            }
            incrementDirection();
            nextPosition = position;
            return position;
        }

        public boolean move(Grid<Elf> grid, Map<Coordinate, Integer> coordinateCount) {
            if (position.equals(nextPosition)) {
                nextPosition = null;
                return false;
            }
            if (coordinateCount.get(nextPosition) == 1) {
                grid.set(position, null);
                grid.set(nextPosition, this);
                position = nextPosition;
                nextPosition = null;
                return true;
            } else {
                nextPosition = null;
                return false;
            }
        }

        private void incrementDirection() {
            firstDirection++;
            if (firstDirection == 4) {
                firstDirection = 0;
            }
        }
        
        private boolean isMovementPossible(Grid<Elf> grid) {
            return position.getAllNeighbours().stream().anyMatch(coordinate -> grid.get(coordinate) != null);
        }

        private boolean isUpPossible(Grid<Elf> grid) {
            return position.getTopNeighbours().stream().noneMatch(coordinate -> grid.get(coordinate) != null);
        }

        private boolean isDownPossible(Grid<Elf> grid) {
            return position.getBottomNeighbours().stream().noneMatch(coordinate -> grid.get(coordinate) != null);
        }

        private boolean isLeftPossible(Grid<Elf> grid) {
            return position.getLeftNeighbours().stream().noneMatch(coordinate -> grid.get(coordinate) != null);
        }

        private boolean isRightPossible(Grid<Elf> grid) {
            return position.getRightNeighbours().stream().noneMatch(coordinate -> grid.get(coordinate) != null);
        }

        @Override
        public String toString() {
            return "#";
        }
    }
}
