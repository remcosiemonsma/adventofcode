package nl.remcoder.adventofcode;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day23 {
    private static final Pattern FIRST_LINE = Pattern.compile("###(.)#(.)#(.)#(.)###");
    private static final Pattern SECOND_LINE = Pattern.compile("  #(.)#(.)#(.)#(.)#");

    public int handlePart1(Stream<String> input) {
        List<String> lines = input.skip(2)
                                  .limit(2)
                                  .toList();

        Matcher firstMatcher = FIRST_LINE.matcher(lines.get(0));

        Set<Amphipod> amphipods = new HashSet<>();

        if (firstMatcher.matches()) {
            Amphipod first = new Amphipod(firstMatcher.group(1).charAt(0), new Point(2, 1));
            Amphipod third = new Amphipod(firstMatcher.group(2).charAt(0), new Point(4, 1));
            Amphipod fifth = new Amphipod(firstMatcher.group(3).charAt(0), new Point(6, 1));
            Amphipod seventh = new Amphipod(firstMatcher.group(4).charAt(0), new Point(8, 1));
            amphipods.add(first);
            amphipods.add(third);
            amphipods.add(fifth);
            amphipods.add(seventh);
        } else {
            return -1;
        }

        Matcher secondMatcher = SECOND_LINE.matcher(lines.get(1));

        if (secondMatcher.matches()) {
            Amphipod second = new Amphipod(secondMatcher.group(1).charAt(0), new Point(2, 2));
            Amphipod fourth = new Amphipod(secondMatcher.group(2).charAt(0), new Point(4, 2));
            Amphipod sixth = new Amphipod(secondMatcher.group(3).charAt(0), new Point(6, 2));
            Amphipod eighth = new Amphipod(secondMatcher.group(4).charAt(0), new Point(8, 2));
            amphipods.add(second);
            amphipods.add(fourth);
            amphipods.add(sixth);
            amphipods.add(eighth);
        } else {
            return -2;
        }

        GameState start = new GameState(amphipods, 0);
        start.setTotalCost(0);

        Set<Amphipod> endAmphipods = new HashSet<>();

        endAmphipods.add(new Amphipod('A', new Point(2, 1)));
        endAmphipods.add(new Amphipod('A', new Point(2, 2)));
        endAmphipods.add(new Amphipod('B', new Point(4, 1)));
        endAmphipods.add(new Amphipod('B', new Point(4, 2)));
        endAmphipods.add(new Amphipod('C', new Point(6, 1)));
        endAmphipods.add(new Amphipod('C', new Point(6, 2)));
        endAmphipods.add(new Amphipod('D', new Point(8, 1)));
        endAmphipods.add(new Amphipod('D', new Point(8, 2)));

        GameState end = new GameState(endAmphipods, 0);

        int lowestScore = findShortestDistance(start, end);

        return lowestScore;
    }

    public int handlePart2(Stream<String> input) {
        return 0;
    }

    public int findShortestDistance(GameState from, GameState to) {
        List<GameState> children = from.getAllChildren();

        return from.getAllChildren()
                   .stream()
                   .filter(gameState -> gameState.equals(to))
                   .mapToInt(GameState::getTotalCost)
                   .min()
                   .getAsInt();

//        Queue<GameState> toVisit = new PriorityQueue<>();
//        toVisit.add(from);
//
//        while (!toVisit.isEmpty()) {
//            GameState min = toVisit.remove();
//            if (min.equals(to)) {
//                return min.getTotalCost();
//            }
//            if (min.isVisited()) {
//                continue;
//            }
//            min.setVisited(true);
//            for (Map.Entry<GameState, Integer> neighborEntry : min.getNextPossibleGameStates().entrySet()) {
//                int adjacentDistance = min.getTotalCost() + neighborEntry.getValue();
//                GameState neighbor = neighborEntry.getKey();
//                if (neighbor.getTotalCost() > adjacentDistance && !neighbor.isVisited()) {
//                    neighbor.setTotalCost(adjacentDistance);
//                    toVisit.add(neighbor);
//                }
//            }
//        }
//
//        throw new RuntimeException("'to' node unreachable");
    }

    private class GameState implements Comparable<GameState> {
        private static final List<Point> POSSIBLE_POINTS = new ArrayList<>();

        static {
            POSSIBLE_POINTS.add(new Point(0, 0));
            POSSIBLE_POINTS.add(new Point(1, 0));
            POSSIBLE_POINTS.add(new Point(3, 0));
            POSSIBLE_POINTS.add(new Point(5, 0));
            POSSIBLE_POINTS.add(new Point(7, 0));
            POSSIBLE_POINTS.add(new Point(9, 0));
            POSSIBLE_POINTS.add(new Point(10, 0));

            for (int i = 1; i <= 2; i++) {
                POSSIBLE_POINTS.add(new Point(2, i));
                POSSIBLE_POINTS.add(new Point(4, i));
                POSSIBLE_POINTS.add(new Point(6, i));
                POSSIBLE_POINTS.add(new Point(8, i));
            }
        }

        private final Set<Amphipod> amphipods;
        private final List<Point> validPoints;
        private int totalCost;
        private boolean visited;
        private List<GameState> nextPossibleGameStates;

        public GameState(Set<Amphipod> amphipods, int cost) {
            this.amphipods = Set.copyOf(amphipods);

            validPoints = new ArrayList<>(POSSIBLE_POINTS);

            validPoints.removeIf(point -> amphipods.stream()
                                                   .map(Amphipod::position)
                                                   .anyMatch(Predicate.isEqual(point)));

            nextPossibleGameStates = calculateNextPossibleGameStates();

            totalCost = cost;
        }

        public int getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(int totalCost) {
            this.totalCost = totalCost;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }


        public boolean isMovePossible(Amphipod amphipod, Point target) {
            if (!validPoints.contains(target)) {
                return false;
            }

            if (amphipod.position.isInHallway() && target.isInHallway()) {
                return false;
            }

            if (!amphipod.position.isInHallway() && target.isInHallway()) {
                List<Point> points = getPointsBetween(amphipod.position, target);

                return validPoints.containsAll(points);
            }

            if (target.x == amphipod.getTargetX()) {
                List<Point> points = getPointsBetween(amphipod.position, target);

                return validPoints.containsAll(points);
            }

            return false;
        }

        public List<Point> determinePossibleMovesForAmphipod(Amphipod amphipod) {
            List<Point> possibleMoves = new ArrayList<>();

            for (Point position : validPoints) {
                if (isMovePossible(amphipod, position)) {
                    possibleMoves.add(position);
                }
            }

            return possibleMoves;
        }

        public Map<Amphipod, List<Point>> getPossibleMoves() {
            return amphipods.stream()
                            .collect(Collectors.toMap(Function.identity(), this::determinePossibleMovesForAmphipod));
        }

        public List<Point> getPointsBetween(Point start, Point end) {
            List<Point> points = new ArrayList<>();
            if (!start.isInHallway()) {
                for (int y = start.y - 1; y > 0; y--) {
                    points.add(new Point(start.x, y));
                }
            }

            int startx = Math.min(start.x, end.x);
            int endx = Math.max(start.x, end.x);

            for (int x = startx; x <= endx; ) {
                points.add(new Point(x, 0));
                if (x == 0 || x == 9) {
                    x++;
                } else {
                    x += 2;
                }
            }

            if (!end.isInHallway()) {
                for (int y = end.y; y > 0; y--) {
                    points.add(new Point(end.x, y));
                }
            }

            return points;
        }

        public List<GameState> calculateNextPossibleGameStates() {
            List<GameState> gameStates = new ArrayList<>();

            Map<Amphipod, List<Point>> possibleMoves = getPossibleMoves();

            for (Map.Entry<Amphipod, List<Point>> entry : possibleMoves.entrySet()) {
                for (Point point : entry.getValue()) {
                    Set<Amphipod> newAmphipods = new HashSet<>(amphipods);

                    newAmphipods.remove(entry.getKey());

                    newAmphipods.add(new Amphipod(entry.getKey().type(), point));

                    int costToMove = entry.getKey().calculateCostToMove(point);
                    GameState gameState = new GameState(newAmphipods, totalCost + costToMove);

                    gameStates.add(gameState);
                }
            }

            return gameStates;
        }

        public List<GameState> getAllChildren() {
            List<GameState> children = new ArrayList<>(nextPossibleGameStates);

            nextPossibleGameStates.forEach(gameState -> children.addAll(gameState.getAllChildren()));

            return children;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            GameState gameState = (GameState) o;
            return Objects.equals(amphipods, gameState.amphipods);
        }

        @Override
        public int hashCode() {
            return Objects.hash(amphipods, validPoints);
        }

        @Override
        public int compareTo(GameState o) {
            return Integer.compare(this.totalCost, o.totalCost);
        }
    }

    private record Amphipod(char type, Point position) {
        public int calculateCostToMove(Point moveTo) {
            int cost = switch (type) {
                case 'A' -> 1;
                case 'B' -> 10;
                case 'C' -> 100;
                case 'D' -> 1000;
                default -> throw new AssertionError("Eek!");
            };

            return position.calculateDistance(moveTo) * cost;
        }

        public int getTargetX() {
            return switch (type) {
                case 'A' -> 2;
                case 'B' -> 4;
                case 'C' -> 6;
                case 'D' -> 8;
                default -> throw new AssertionError("Eek!");
            };
        }
    }

    private record Point(int x, int y) {
        private int calculateDistance(Point target) {
            int xdist = Math.abs(x - target.x);
            int ydist = Math.abs(y - target.y);

            return xdist + ydist;
        }

        public boolean isInHallway() {
            return y == 0;
        }
    }
}
