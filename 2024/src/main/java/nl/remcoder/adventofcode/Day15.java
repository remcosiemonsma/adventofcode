package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.stream.CombiningCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Day15 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var bag = input.collect(new CombiningCollector<>(Function.identity(), String::isBlank, WarehouseBag::new))
                       .map(WarehouseBag.class::cast)
                       .toList();

        var grid = bag.getFirst().toGrid();

        var moves = bag.get(1).toRobotMoves();

        var robot = new Robot(grid.findValue('@').orElseThrow(() -> new AssertionError("Eek!")));

        moves.map(this::moveToDirection)
             .forEach(direction -> moveRobot(grid, robot, direction));

        return grid.findValues('O')
                   .stream()
                   .mapToLong(this::toGpsCoordinate)
                   .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var bag = input.map(s -> s.replaceAll("#", "##")
                                  .replaceAll("O", "[]")
                                  .replaceAll("\\.", "..")
                                  .replaceAll("@", "@."))
                       .collect(new CombiningCollector<>(Function.identity(), String::isBlank, WarehouseBag::new))
                       .map(WarehouseBag.class::cast)
                       .toList();

        var grid = bag.getFirst().toGrid();

        var moves = bag.get(1).toRobotMoves();

        var robot = new Robot(grid.findValue('@').orElseThrow(() -> new AssertionError("Eek!")));

        moves.map(this::moveToDirection)
             .forEach(direction -> moveRobotWide(grid, robot, direction));


        return grid.findValues('[')
                   .stream()
                   .mapToLong(this::toGpsCoordinate)
                   .sum();
    }

    private Long toGpsCoordinate(Coordinate coordinate) {
        return (coordinate.y() * 100L) + coordinate.x();
    }

    private Direction moveToDirection(Character move) {
        return switch (move) {
            case '^' -> Direction.UP;
            case '>' -> Direction.RIGHT;
            case 'v' -> Direction.DOWN;
            case '<' -> Direction.LEFT;
            default -> throw new AssertionError("Ook!");
        };
    }

    private void moveRobot(Grid<Character> grid, Robot robot, Direction direction) {
        var robotPoint = robot.position();

        var nextRobotPosition = robotPoint.getNeighbor(direction);

        if (grid.get(nextRobotPosition) == 'O') {
            var box = nextRobotPosition;
            while (grid.get(box) == 'O') {
                box = box.getNeighbor(direction);
            }
            if (grid.get(box) == '.') {
                grid.set(box, 'O');
                grid.set(robotPoint, '.');
                grid.set(nextRobotPosition, '@');
                robot.move(nextRobotPosition);
            }
        } else if (grid.get(nextRobotPosition) == '.') {
            grid.set(robotPoint, '.');
            grid.set(nextRobotPosition, '@');
            robot.move(nextRobotPosition);
        }
    }

    private void moveRobotWide(Grid<Character> grid, Robot robot, Direction direction) {
        var nextPosition = robot.position().getNeighbor(direction);
        if (grid.get(nextPosition) == '.') {
            grid.set(robot.position(), '.');
            grid.set(nextPosition, '@');
            robot.move(nextPosition);
            return;
        }
        switch (direction) {
            case UP -> moveRobotUp(grid, robot);
            case RIGHT -> moveRobotRight(grid, robot);
            case DOWN -> moveRobotDown(grid, robot);
            case LEFT -> moveRobotLeft(grid, robot);
        }
    }

    private void moveRobotUp(Grid<Character> grid, Robot robot) {
        var nextRobotPosition = robot.position().above();
        Coordinate box;
        if (grid.get(nextRobotPosition) == '#') {
            return;
        } else if (grid.get(nextRobotPosition) == ']') {
            box = nextRobotPosition.left();
        } else {
            box = nextRobotPosition;
        }
        var canMove = true;
        var boxesToMove = new ArrayList<Coordinate>();
        var boxesToInspect = List.of(box);
        outer:
        while (!boxesToInspect.isEmpty()) {
            var nextBoxesToInspect = new ArrayList<Coordinate>();

            for (var boxToInspect : boxesToInspect) {
                var leftPosition = boxToInspect.above();
                var rightPosition = boxToInspect.topRight();
                var leftItem = grid.get(leftPosition);
                var rightItem = grid.get(rightPosition);
                if (leftItem == '[') {
                    nextBoxesToInspect.add(leftPosition);
                    boxesToMove.add(boxToInspect);
                    continue;
                }
                if (leftItem == ']') {
                    nextBoxesToInspect.add(leftPosition.left());
                    if (rightItem == '.') {
                        boxesToMove.add(boxToInspect);
                        continue;
                    }
                    if (rightItem == '[') {
                        nextBoxesToInspect.add(rightPosition);
                        boxesToMove.add(boxToInspect);
                        continue;
                    }
                    if (rightItem == '#') {
                        canMove = false;
                        break outer;
                    }
                }
                if (leftItem == '#') {
                    canMove = false;
                    break outer;
                }
                if (rightItem == '[') {
                    nextBoxesToInspect.add(rightPosition);
                    boxesToMove.add(boxToInspect);
                    continue;
                }
                if (rightItem == '#') {
                    canMove = false;
                    break outer;
                }
                boxesToMove.add(boxToInspect);
            }

            boxesToInspect = nextBoxesToInspect;
        }
        if (canMove) {
            boxesToMove.forEach(boxToMove -> {
                grid.set(boxToMove, '.');
                grid.set(boxToMove.right(), '.');
            });
            boxesToMove.forEach(boxToMove -> {
                grid.set(boxToMove.above(), '[');
                grid.set(boxToMove.topRight(), ']');
            });
            grid.set(nextRobotPosition, '@');
            grid.set(robot.position(), '.');
            robot.move(nextRobotPosition);
        }
    }

    private void moveRobotDown(Grid<Character> grid, Robot robot) {
        var nextRobotPosition = robot.position().below();
        Coordinate box;
        if (grid.get(nextRobotPosition) == '#') {
            return;
        } else if (grid.get(nextRobotPosition) == ']') {
            box = nextRobotPosition.left();
        } else {
            box = nextRobotPosition;
        }
        var canMove = true;
        var boxesToMove = new ArrayList<Coordinate>();
        var boxesToInspect = List.of(box);
        outer:
        while (!boxesToInspect.isEmpty()) {
            var nextBoxesToInspect = new ArrayList<Coordinate>();

            for (var boxToInspect : boxesToInspect) {
                var leftPosition = boxToInspect.below();
                var rightPosition = boxToInspect.bottomRight();
                var leftItem = grid.get(leftPosition);
                var rightItem = grid.get(rightPosition);
                if (leftItem == '[') {
                    nextBoxesToInspect.add(leftPosition);
                    boxesToMove.add(boxToInspect);
                    continue;
                }
                if (leftItem == ']') {
                    nextBoxesToInspect.add(leftPosition.left());
                    if (rightItem == '.') {
                        boxesToMove.add(boxToInspect);
                        continue;
                    }
                    if (rightItem == '[') {
                        nextBoxesToInspect.add(rightPosition);
                        boxesToMove.add(boxToInspect);
                        continue;
                    }
                    if (rightItem == '#') {
                        canMove = false;
                        break outer;
                    }
                }
                if (leftItem == '#') {
                    canMove = false;
                    break outer;
                }
                if (rightItem == '[') {
                    nextBoxesToInspect.add(rightPosition);
                    boxesToMove.add(boxToInspect);
                    continue;
                }
                if (rightItem == '#') {
                    canMove = false;
                    break outer;
                }
                boxesToMove.add(boxToInspect);
            }

            boxesToInspect = nextBoxesToInspect;
        }
        if (canMove) {
            boxesToMove.forEach(boxToMove -> {
                grid.set(boxToMove, '.');
                grid.set(boxToMove.right(), '.');
            });
            boxesToMove.forEach(boxToMove -> {
                grid.set(boxToMove.below(), '[');
                grid.set(boxToMove.bottomRight(), ']');
            });
            grid.set(nextRobotPosition, '@');
            grid.set(robot.position(), '.');
            robot.move(nextRobotPosition);
        }
    }

    private void moveRobotLeft(Grid<Character> grid, Robot robot) {
        var nextRobotPosition = robot.position().left();
        var box = nextRobotPosition;
        var boxesToMove = new ArrayList<Coordinate>();
        while (grid.get(box) == ']') {
            boxesToMove.add(box);
            box = box.left().left();
        }
        if (grid.get(box) == '.') {
            for (var boxToMove : boxesToMove) {
                grid.set(boxToMove.left(), ']');
                grid.set(boxToMove.left().left(), '[');
            }
            grid.set(nextRobotPosition, '@');
            grid.set(robot.position(), '.');
            robot.move(nextRobotPosition);
        }
    }

    private void moveRobotRight(Grid<Character> grid, Robot robot) {
        var nextRobotPosition = robot.position().right();
        var box = nextRobotPosition;
        var boxesToMove = new ArrayList<Coordinate>();
        while (grid.get(box) == '[') {
            boxesToMove.add(box);
            box = box.right().right();
        }
        if (grid.get(box) == '.') {
            for (var boxToMove : boxesToMove) {
                grid.set(boxToMove.right(), '[');
                grid.set(boxToMove.right().right(), ']');
            }
            grid.set(nextRobotPosition, '@');
            grid.set(robot.position(), '.');
            robot.move(nextRobotPosition);
        }
    }

    private static class WarehouseBag implements CombiningCollector.Bag<String> {
        private final List<String> bag = new ArrayList<>();

        @Override
        public void add(String s) {
            bag.add(s);
        }

        public Grid<Character> toGrid() {
            return GridFactory.createCharacterGridFromInput(bag.stream());
        }

        public Stream<Character> toRobotMoves() {
            return bag.stream()
                      .flatMap(s -> s.chars().boxed())
                      .map(i -> (char) i.intValue());
        }
    }

    private static class Robot {
        private Coordinate position;

        public Robot(Coordinate position) {
            this.position = position;
        }

        private void move(Coordinate newPosition) {
            position = newPosition;
        }

        public Coordinate position() {
            return position;
        }
    }
}
