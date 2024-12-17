package nl.remcoder.adventofcode.library.model;

import java.util.Comparator;

public record Vector(Coordinate coordinate, Direction direction) implements Comparable<Vector> {
    @Override
    public int compareTo(Vector o) {
        return Comparator.comparing(Vector::coordinate)
                         .thenComparing(Vector::direction)
                         .compare(this, o);
    }
}
