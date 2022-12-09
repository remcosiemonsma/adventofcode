package nl.remcoder.adventofcode.library.model;

import java.util.Set;

public record Coordinate(int x, int y) {
    public Set<Coordinate> getStraightNeighbours() {
        return Set.of(above(), below(), right(), left());
    }

    public Set<Coordinate> getAllNeighbours() {
        return Set.of(above(), below(), right(), left(), topLeft(), topRight(), bottomLeft(), bottomRight());
    }

    public Set<Coordinate> getTopRightNeighbours() {
        return Set.of(above(), right(), topRight());
    }
    
    public Set<Coordinate> getTopLeftNeighbours() {
        return Set.of(above(), left(), topLeft());
    }
    
    public Set<Coordinate> getBottomLeftNeighbours() {
        return Set.of(below(), left(), bottomLeft());
    }
    
    public Set<Coordinate> getBottomRightNeighbours() {
        return Set.of(below(), right(), bottomRight());
    }
    
    public Coordinate above() {
        return new Coordinate(x, y - 1);
    }

    public Coordinate below() {
        return new Coordinate(x, y + 1);
    }

    public Coordinate left() {
        return new Coordinate(x - 1, y);
    }

    public Coordinate right() {
        return new Coordinate(x + 1, y);
    }

    public Coordinate topLeft() {
        return new Coordinate(x - 1, y - 1);
    }

    public Coordinate topRight() {
        return new Coordinate(x + 1, y - 1);
    }

    public Coordinate bottomLeft() {
        return new Coordinate(x - 1, y + 1);
    }

    public Coordinate bottomRight() {
        return new Coordinate(x + 1, y + 1);
    }
}
