package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;

import java.util.*;
import java.util.stream.Stream;

public class Day24 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var lines = input.toList();

        var blizzards = parseBlizzards(lines);

        int valleyWidth = lines.getFirst().length() - 3;
        int valleyHeight = lines.size() - 3;

        var blizzardStates = generateBlizzardStates(blizzards, valleyWidth, valleyHeight);

        var startPosition = new Coordinate(0, -1);
        var exitPosition = new Coordinate(lines.getFirst().length() - 3, lines.size() - 2);

        var nextBlizzardStateIndex = 0;
        
        var minute = 0;
        
        var positions = new HashSet<Coordinate>();
        positions.add(startPosition);
        
        while (!positions.contains(exitPosition)) {
            minute++;
            nextBlizzardStateIndex = (nextBlizzardStateIndex + 1) % blizzardStates.size();
            var newPositions = new HashSet<Coordinate>();
            for (var position : positions) {
                newPositions.addAll(getNextPossiblePositions(position, blizzardStates.get(nextBlizzardStateIndex), valleyHeight, valleyWidth));
            }
            positions = newPositions;
        }
        
        return minute;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var lines = input.toList();

        var blizzards = parseBlizzards(lines);

        int valleyWidth = lines.getFirst().length() - 3;
        int valleyHeight = lines.size() - 3;

        var blizzardStates = generateBlizzardStates(blizzards, valleyWidth, valleyHeight);

        var startPosition = new Coordinate(0, -1);
        var exitPosition = new Coordinate(lines.getFirst().length() - 3, lines.size() - 2);

        var nextBlizzardStateIndex = 0;

        var minute = 0;

        var positions = new HashSet<Coordinate>();
        positions.add(startPosition);

        while (!positions.contains(exitPosition)) {
            minute++;
            nextBlizzardStateIndex = (nextBlizzardStateIndex + 1) % blizzardStates.size();
            var newPositions = new HashSet<Coordinate>();
            for (var position : positions) {
                newPositions.addAll(getNextPossiblePositions(position, blizzardStates.get(nextBlizzardStateIndex), valleyHeight, valleyWidth));
            }
            positions = newPositions;
        }

        positions.clear();
        positions.add(exitPosition);
        
        while (!positions.contains(startPosition)) {
            minute++;
            nextBlizzardStateIndex = (nextBlizzardStateIndex + 1) % blizzardStates.size();
            var newPositions = new HashSet<Coordinate>();
            for (var position : positions) {
                newPositions.addAll(getNextPossiblePositions(position, blizzardStates.get(nextBlizzardStateIndex), valleyHeight, valleyWidth));
            }
            positions = newPositions;
        }

        positions.clear();
        positions.add(startPosition);
        
        while (!positions.contains(exitPosition)) {
            minute++;
            nextBlizzardStateIndex = (nextBlizzardStateIndex + 1) % blizzardStates.size();
            var newPositions = new HashSet<Coordinate>();
            for (var position : positions) {
                newPositions.addAll(getNextPossiblePositions(position, blizzardStates.get(nextBlizzardStateIndex), valleyHeight, valleyWidth));
            }
            positions = newPositions;
        }

        return minute;
    }

    private List<List<Blizzard>> generateBlizzardStates(List<Blizzard> blizzards, int valleyWidth, int valleyHeight) {
        var blizzardStates = new ArrayList<List<Blizzard>>();
        blizzardStates.add(blizzards);

        var nextBlizzardStates = blizzards.stream().map(blizzard -> blizzard.getNextBlizzard(valleyWidth, valleyHeight)).toList();

        while (!blizzardStates.contains(nextBlizzardStates)) {
            blizzardStates.add(nextBlizzardStates);
            nextBlizzardStates = nextBlizzardStates.stream().map(blizzard -> blizzard.getNextBlizzard(valleyWidth,
                                                                                                      valleyHeight)).toList();
        }
        return blizzardStates;
    }

    private List<Blizzard> parseBlizzards(List<String> lines) {
        var blizzards = new ArrayList<Blizzard>();

        for (var y = 1; y < lines.size() - 1; y++) {
            var line = lines.get(y);
            for (var x = 1; x < line.length() - 1; x++) {
                switch (line.charAt(x)) {
                    case '>' -> blizzards.add(new Blizzard(new Coordinate(x - 1, y - 1), Direction.RIGHT));
                    case 'v' -> blizzards.add(new Blizzard(new Coordinate(x - 1, y - 1), Direction.DOWN));
                    case '<' -> blizzards.add(new Blizzard(new Coordinate(x - 1, y - 1), Direction.LEFT));
                    case '^' -> blizzards.add(new Blizzard(new Coordinate(x - 1, y - 1), Direction.UP));
                }
            }
        }
        return blizzards;
    }

    private Set<Coordinate> getNextPossiblePositions(Coordinate position, List<Blizzard> blizzards,
                                                            int valleyHeight, int valleyWidth) {
        var positions = new HashSet<Coordinate>();
        for (var nextPosition : position.getStraightNeighbours()) {
            if (nextPosition.x() == valleyWidth && nextPosition.y() == valleyHeight + 1) {
                positions.add(nextPosition);
            }
            if (nextPosition.x() == 0 && nextPosition.y() == -1) {
                positions.add(nextPosition);
            }
            if (nextPosition.y() >= 0 && nextPosition.y() <= valleyHeight &&
                nextPosition.x() >= 0 && nextPosition.x() <= valleyWidth &&
                blizzards.stream().noneMatch(blizzard -> blizzard.position().equals(nextPosition))) {
                positions.add(nextPosition);
            }
        }
        if (blizzards.stream().noneMatch(blizzard -> blizzard.position.equals(position))) {
            positions.add(position);
        }
        
        return positions;
    }

    private record Blizzard(Coordinate position, Direction direction) {
        public Blizzard getNextBlizzard(int valleyWidth, int valleyHeight) {
            return switch (direction) {
                case RIGHT -> {
                    var nextPosition = position.right();
                    if (nextPosition.x() > valleyWidth) {
                        nextPosition = new Coordinate(0, position.y());
                    }
                    yield new Blizzard(nextPosition, direction);
                }
                case DOWN -> {
                    var nextPosition = position.below();
                    if (nextPosition.y() > valleyHeight) {
                        nextPosition = new Coordinate(position.x(), 0);
                    }
                    yield new Blizzard(nextPosition, direction);
                }
                case LEFT -> {
                    var nextPosition = position.left();
                    if (nextPosition.x() < 0) {
                        nextPosition = new Coordinate(valleyWidth, position.y());
                    }
                    yield new Blizzard(nextPosition, direction);
                }
                case UP -> {
                    var nextPosition = position.above();
                    if (nextPosition.y() < 0) {
                        nextPosition = new Coordinate(position.x(), valleyHeight);
                    }
                    yield new Blizzard(nextPosition, direction);
                }
                case UPLEFT, UPRIGHT, DOWNRIGHT, DOWNLEFT -> throw new AssertionError("Not supposed to happen!");
            };
        }
    }
}
