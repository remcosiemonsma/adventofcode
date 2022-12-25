package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.model.Vector;

import java.util.stream.Stream;

public class Day22 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var lines = input.toList();

        var path = lines.get(lines.size() - 1);

        var gridArray = new Character[lines.size() - 2][];

        for (var i = 0; i < lines.size() - 2; i++) {
            String line = lines.get(i);
            gridArray[i] = new Character[line.length()];
            for (var j = 0; j < line.length(); j++) {
                gridArray[i][j] = line.charAt(j);
            }
        }

        var grid = new Grid<>(gridArray);

        var start = new Coordinate(lines.get(0).indexOf('.'), 0);

        return walk(grid, start, path);
    }


    @Override
    public Integer handlePart2(Stream<String> input) {
        var lines = input.toList();

        var path = lines.get(lines.size() - 1);

        var gridArray = new Character[lines.size() - 2][];

        for (var i = 0; i < lines.size() - 2; i++) {
            String line = lines.get(i);
            gridArray[i] = new Character[line.length()];
            for (var j = 0; j < line.length(); j++) {
                gridArray[i][j] = line.charAt(j);
            }
        }

        var grid = new Grid<>(gridArray);

        var start = new Coordinate(lines.get(0).indexOf('.'), 0);

        return walkCube(grid, start, path);
    }

    private int walk(Grid<Character> grid, Coordinate start, String path) {
        var current = start;

        var direction = Direction.RIGHT;

        var stringBuilder = new StringBuilder();

        for (char c : path.toCharArray()) {
            if (Character.isDigit(c)) {
                stringBuilder.append(c);
            } else {
                grid.set(current, switch (direction) {
                    case RIGHT -> '>';
                    case DOWN -> 'v';
                    case LEFT -> '<';
                    case UP -> '^';
                });

                var steps = Integer.parseInt(stringBuilder.toString());
                stringBuilder = new StringBuilder();

                current = walk(grid, current, direction, steps);

                direction = switch (c) {
                    case 'R' -> switch (direction) {
                        case RIGHT -> Direction.DOWN;
                        case DOWN -> Direction.LEFT;
                        case LEFT -> Direction.UP;
                        case UP -> Direction.RIGHT;
                    };
                    case 'L' -> switch (direction) {
                        case RIGHT -> Direction.UP;
                        case UP -> Direction.LEFT;
                        case LEFT -> Direction.DOWN;
                        case DOWN -> Direction.RIGHT;
                    };
                    default -> throw new AssertionError("Eek!");
                };
            }
        }

        var steps = Integer.parseInt(stringBuilder.toString());
        current = walk(grid, current, direction, steps);

        return ((current.y() + 1) * 1000) + (current.x() + 1) * 4 +
               switch (direction) {
                   case RIGHT -> 0;
                   case DOWN -> 1;
                   case LEFT -> 2;
                   case UP -> 3;
               };
    }

    private static Coordinate walk(Grid<Character> grid, Coordinate current, Direction direction, int steps) {
        for (var i = 0; i < steps; i++) {
            var next = switch (direction) {
                case UP -> current.above();
                case LEFT -> current.left();
                case DOWN -> current.below();
                case RIGHT -> current.right();
            };

            if (grid.get(next) == null || grid.get(next) == ' ') {
                next = switch (direction) {
                    case UP -> new Coordinate(next.x(), grid.getEndy());
                    case LEFT -> new Coordinate(grid.getEndx(), next.y());
                    case DOWN -> new Coordinate(next.x(), grid.getStarty());
                    case RIGHT -> new Coordinate(grid.getStartx(), next.y());
                };

                while (grid.get(next) == null || grid.get(next) == ' ') {
                    next = switch (direction) {
                        case UP -> next.above();
                        case LEFT -> next.left();
                        case DOWN -> next.below();
                        case RIGHT -> next.right();
                    };
                }
            }

            if (grid.get(next) == '#') {
                break;
            }
            current = next;
            grid.set(current, switch (direction) {
                case RIGHT -> '>';
                case DOWN -> 'v';
                case LEFT -> '<';
                case UP -> '^';
            });
        }
        return current;
    }

    private int walkCube(Grid<Character> grid, Coordinate start, String path) {
        var vector = new Vector(start, Direction.RIGHT);

        var stringBuilder = new StringBuilder();

        for (char c : path.toCharArray()) {
            if (Character.isDigit(c)) {
                stringBuilder.append(c);
            } else {
                var steps = Integer.parseInt(stringBuilder.toString());
                stringBuilder = new StringBuilder();

                vector = walkCube(grid, vector, steps);
                var direction = vector.direction();
                direction = switch (c) {
                    case 'R' -> switch (direction) {
                        case RIGHT -> Direction.DOWN;
                        case DOWN -> Direction.LEFT;
                        case LEFT -> Direction.UP;
                        case UP -> Direction.RIGHT;
                    };
                    case 'L' -> switch (direction) {
                        case RIGHT -> Direction.UP;
                        case UP -> Direction.LEFT;
                        case LEFT -> Direction.DOWN;
                        case DOWN -> Direction.RIGHT;
                    };
                    default -> throw new AssertionError("Eek!");
                };
                vector = new Vector(vector.coordinate(), direction);
            }
        }

        var steps = Integer.parseInt(stringBuilder.toString());

        vector = walkCube(grid, vector, steps);

        var current = vector.coordinate();
        var direction = vector.direction();
        
        return ((current.y() + 1) * 1000) + (current.x() + 1) * 4 +
               switch (direction) {
                   case RIGHT -> 0;
                   case DOWN -> 1;
                   case LEFT -> 2;
                   case UP -> 3;
               };
    }

    private Vector walkCube(Grid<Character> grid, Vector vector, int steps) {
        for (var i = 0; i < steps; i++) {
            var current = vector.coordinate();
            var direction = vector.direction();
            var next = switch (direction) {
                case UP -> current.above();
                case LEFT -> current.left();
                case DOWN -> current.below();
                case RIGHT -> current.right();
            };

            if (grid.get(next) == null || grid.get(next) == ' ') {
                char edge = determineEdge(vector);

                next = switch (edge) {
                    case 'A' -> new Coordinate(current.x() + 100, 0);
                    case 'B' -> new Coordinate(current.y() - 100, 149);
                    case 'C' -> new Coordinate(49, current.x() + 100);
                    case 'D' -> new Coordinate(149, 149 - current.y());
                    case 'E' -> new Coordinate(current.y() + 50, 49);
                    case 'F' -> new Coordinate(99, current.x() - 50);
                    case 'G' -> new Coordinate(99, 149 - current.y());
                    case 'H' -> new Coordinate(current.x() - 100, 199);
                    case 'J' -> new Coordinate(0, current.x() + 100);
                    case 'K' -> new Coordinate(0, 149 - current.y());
                    case 'L' -> new Coordinate(current.y() - 50, 100);
                    case 'M' -> new Coordinate(50, current.x() + 50);
                    case 'N' -> new Coordinate(50, 149 - current.y());
                    case 'P' -> new Coordinate(current.y() - 100, 0);
                    default -> throw new AssertionError("Eek!");
                };

                if (grid.get(next) != '#') {
                    direction = switch (edge) {
                        case 'A', 'L', 'P' -> Direction.DOWN;
                        case 'B', 'E', 'H' -> Direction.UP;
                        case 'C', 'D', 'F', 'G' -> Direction.LEFT;
                        case 'J', 'K', 'M', 'N' -> Direction.RIGHT;
                        default -> throw new AssertionError("Eek!");
                    };
                }
            }
            if (grid.get(next) == '#') {
                break;
            }

            vector = new Vector(next, direction);
        }
        return vector;
    }

    private char determineEdge(Vector vector) {
        var position = vector.coordinate();
        if (position.y() == 199 && position.x() >= 0 && position.x() < 50 && vector.direction() == Direction.DOWN) {
            return 'A';
        }
        if (position.x() == 49 && position.y() >= 150 && position.y() < 200 && vector.direction() == Direction.RIGHT) {
            return 'B';
        }
        if (position.y() == 149 && position.x() >= 50 && position.x() < 100 && vector.direction() == Direction.DOWN) {
            return 'C';
        }
        if (position.x() == 99 && position.y() >= 100 && position.y() < 150 && vector.direction() == Direction.RIGHT) {
            return 'D';
        }
        if (position.x() == 99 && position.y() >= 50 && position.y() < 100 && vector.direction() == Direction.RIGHT) {
            return 'E';
        }
        if (position.y() == 49 && position.x() >= 100 && position.x() < 150 && vector.direction() == Direction.DOWN) {
            return 'F';
        }
        if (position.x() == 149 && position.y() >= 0 && position.y() < 50 && vector.direction() == Direction.RIGHT) {
            return 'G';
        }
        if (position.y() == 0 && position.x() >= 100 && position.x() < 150 && vector.direction() == Direction.UP) {
            return 'H';
        }
        if (position.y() == 0 && position.x() >= 50 && position.x() < 100 && vector.direction() == Direction.UP) {
            return 'J';
        }
        if (position.x() == 50 && position.y() >= 0 && position.y() < 50 && vector.direction() == Direction.LEFT) {
            return 'K';
        }
        if (position.x() == 50 && position.y() >= 50 && position.y() < 100 && vector.direction() == Direction.LEFT) {
            return 'L';
        }
        if (position.y() == 100 && position.x() >= 0 && position.x() < 50 && vector.direction() == Direction.UP) {
            return 'M';
        }
        if (position.x() == 0 && position.y() >= 100 && position.y() < 150 && vector.direction() == Direction.LEFT) {
            return 'N';
        }
        if (position.x() == 0 && position.y() >= 150 && position.y() < 200 && vector.direction() == Direction.LEFT) {
            return 'P';
        }
        System.out.println(position);
        throw new AssertionError("Eek!");
    }
}
