package nl.remcoder.adventofcode.library.model;

import java.util.ArrayList;
import java.util.List;
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
    
    public Set<Coordinate> getTopNeighbours() {
        return Set.of(above(), topRight(), topLeft());
    }
    
    public Set<Coordinate> getLeftNeighbours() {
        return Set.of(left(), bottomLeft(), topLeft());
    }
    
    public Set<Coordinate> getRightNeighbours() {
        return Set.of(right(), topRight(), bottomRight());
    }
    
    public Set<Coordinate> getBottomNeighbours() {
        return Set.of(below(), bottomLeft(), bottomRight());
    }
    
    public List<Coordinate> getAllBetween(Coordinate other) {
        var coordinates = new ArrayList<Coordinate>();
        
        if (this.x == other.x) {
            if (y <= other.y) {
                for (int y = this.y; y <= other.y; y++) {
                    coordinates.add(new Coordinate(x, y));
                }
            } else {
                for (int y = other.y; y <= this.y; y++) {
                    coordinates.add(new Coordinate(x, y));
                }
            }
        } else if (this.y == other.y) {
            if (x <= other.x) {
                for (int x = this.x; x <= other.x; x++) {
                    coordinates.add(new Coordinate(x, y));
                }
            } else {
                for (int x = other.x; x <= this.x; x++) {
                    coordinates.add(new Coordinate(x, y));
                }
            }
        } else {
            throw new UnsupportedOperationException("Diagonal lines are not supported!");
        }
        
        return coordinates;
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
    
    public Coordinate getNeighbor(Direction direction) {
        return switch (direction) {
            case UP -> above();
            case LEFT -> left();
            case DOWN -> below();
            case RIGHT -> right();
        };
    }

    public Direction getDirection(Coordinate other) {
        if (this.equals(other)) {
            throw new IllegalArgumentException("Cannot determine direction for same coordinate!");
        }

        if (x == other.x) {
            if (y < other.y) {
                return Direction.DOWN;
            } else if (y > other.y) {
                return Direction.UP;
            }
        }

        if (y == other.y) {
            if (x < other.x) {
                return Direction.RIGHT;
            } else if (x > other.x) {
                return Direction.LEFT;
            }
        }

        throw new IllegalArgumentException("Diagonal directions not supported!");
    }

    public int getDistanceTo(Coordinate other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }
}
