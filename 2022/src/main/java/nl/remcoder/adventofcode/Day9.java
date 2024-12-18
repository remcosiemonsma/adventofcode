package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;

import java.util.*;
import java.util.stream.Stream;

public class Day9 implements AdventOfCodeSolution<Integer> {
    private Coordinate[] knots;
    private final Set<Coordinate> tailPositions = new HashSet<>();
    @Override
    public Integer handlePart1(Stream<String> input) {
        knots = new Coordinate[2];

        Arrays.fill(knots, Coordinate.ORIGIN);
        
        tailPositions.add(knots[1]);
        
        input.forEach(this::move);
        
        return tailPositions.size();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        knots = new Coordinate[10];

        Arrays.fill(knots, Coordinate.ORIGIN);

        tailPositions.add(knots[9]);
        
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
            knots[0] = knots[0].above();
            moveTail();
        }
    }

    private void moveLeft(int steps) {
        for (int step = 0; step < steps; step++) {
            knots[0] = knots[0].left();
            moveTail();
        }
    }

    private void moveDown(int steps) {
        for (int step = 0; step < steps; step++) {
            knots[0] = knots[0].below();
            moveTail();
        }
    }

    private void moveRight(int steps) {
        for (int step = 0; step < steps; step++) {
            knots[0] = knots[0].right();
            moveTail();
        }
    }

    private void moveTail() {
        int tailKnotIndex = knots.length - 1;
        
        for (int knotIndex = 1; knotIndex <= tailKnotIndex; knotIndex++) {
            Coordinate knot = knots[knotIndex];
            Coordinate previosKnot = knots[knotIndex - 1];

            if (!(knot.equals(previosKnot) || knot.getAllNeighbours().contains(previosKnot))) {
                if (knot.above().above().equals(previosKnot)) {
                    knots[knotIndex] = knot.above();
                } else if (knot.left().left().equals(previosKnot)) {
                    knots[knotIndex] = knot.left();
                } else if (knot.below().below().equals(previosKnot)) {
                    knots[knotIndex] = knot.below();
                } else if (knot.right().right().equals(previosKnot)) {
                    knots[knotIndex] = knot.right();
                } else if (knot.topRight().getTopRightNeighbours().contains(previosKnot)) {
                    knots[knotIndex] = knot.topRight();
                } else if (knot.topLeft().getTopLeftNeighbours().contains(previosKnot)) {
                    knots[knotIndex] = knot.topLeft();
                } else if (knot.bottomLeft().getBottomLeftNeighbours().contains(previosKnot)) {
                    knots[knotIndex] = knot.bottomLeft();
                } else if (knot.bottomRight().getBottomRightNeighbours().contains(previosKnot)) {
                    knots[knotIndex] = knot.bottomRight();
                }
            }
        }
        
        tailPositions.add(knots[tailKnotIndex]);
    }
}
