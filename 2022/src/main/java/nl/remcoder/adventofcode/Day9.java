package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Day9 implements AdventOfCodeSolution<Integer> {
    private final Map<Integer, Coordinate> knots = new HashMap<>();
    private final Set<Coordinate> tailPositions = new HashSet<>();
    @Override
    public Integer handlePart1(Stream<String> input) {
        knots.put(0, new Coordinate(0, 0));
        knots.put(1, new Coordinate(0, 0));
        
        tailPositions.add(knots.get(1));
        
        input.forEach(this::move);
        
        return tailPositions.size();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        for (int i = 0; i < 10; i++) {
            knots.put(i, new Coordinate(0, 0));
        }
        
        tailPositions.add(knots.get(9));

        input.forEach(this::move);
        
        return tailPositions.size();
    }

    private void move(String line) {
        String[] split = line.split(" ");

        switch (split[0]) {
            case "U" -> moveUp(Integer.parseInt(split[1]));
            case "L" -> moveLeft(Integer.parseInt(split[1]));
            case "D" -> moveDown(Integer.parseInt(split[1]));
            case "R" -> moveRight(Integer.parseInt(split[1]));
            default -> throw new AssertionError("Eek!");
        }
    }

    private void moveUp(int steps) {
        for (int step = 0; step < steps; step++) {
            knots.put(0, knots.get(0).above());
            moveTail();
        }
    }

    private void moveLeft(int steps) {
        for (int step = 0; step < steps; step++) {
            knots.put(0, knots.get(0).left());
            moveTail();
        }
    }

    private void moveDown(int steps) {
        for (int step = 0; step < steps; step++) {
            knots.put(0, knots.get(0).below());
            moveTail();
        }
    }

    private void moveRight(int steps) {
        for (int step = 0; step < steps; step++) {
            knots.put(0, knots.get(0).right());
            moveTail();
        }
    }

    private void moveTail() {
        int tailKnotIndex = knots.keySet()
                           .stream()
                           .max(Integer::compareTo)
                           .orElseThrow(() -> new AssertionError("Eek!"));
        
        for (int knotIndex = 1; knotIndex <= tailKnotIndex; knotIndex++) {
            Coordinate knot = knots.get(knotIndex);
            Coordinate previosKnot = knots.get(knotIndex - 1);

            if (!(knot.equals(previosKnot) || knot.getAllNeighbours().contains(previosKnot))) {
                if (knot.above().above().equals(previosKnot)) {
                    knots.put(knotIndex, knot.above());
                } else if (knot.left().left().equals(previosKnot)) {
                    knots.put(knotIndex, knot.left());
                } else if (knot.below().below().equals(previosKnot)) {
                    knots.put(knotIndex, knot.below());
                } else if (knot.right().right().equals(previosKnot)) {
                    knots.put(knotIndex, knot.right());
                } else if (knot.topRight().getTopRightNeighbours().contains(previosKnot)) {
                    knots.put(knotIndex, knot.topRight());
                } else if (knot.topLeft().getTopLeftNeighbours().contains(previosKnot)) {
                    knots.put(knotIndex, knot.topLeft());
                } else if (knot.bottomLeft().getBottomLeftNeighbours().contains(previosKnot)) {
                    knots.put(knotIndex, knot.bottomLeft());
                } else if (knot.bottomRight().getBottomRightNeighbours().contains(previosKnot)) {
                    knots.put(knotIndex, knot.bottomRight());
                }
            }
        }
        
        tailPositions.add(knots.get(tailKnotIndex));
    }
}
