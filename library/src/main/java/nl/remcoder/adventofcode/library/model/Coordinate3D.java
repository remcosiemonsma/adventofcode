package nl.remcoder.adventofcode.library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record Coordinate3D(int x, int y, int z) {
    
    public Coordinate3D getLeft() {
        return new Coordinate3D(x - 1, y, z);
    }
    
    public Coordinate3D getRight() {
        return new Coordinate3D(x + 1, y, z);
    }
    
    public Coordinate3D getAbove() {
        return new Coordinate3D(x, y + 1, z);
    }
    
    public Coordinate3D getBelow() {
        return new Coordinate3D(x, y - 1, z);
    }
    
    public Coordinate3D getFront() {
        return new Coordinate3D(x, y, z + 1);
    }
    
    public Coordinate3D getRear() {
        return new Coordinate3D(x, y, z - 1);
    }
    
    public Set<Coordinate3D> getNeighbors() {
        return Set.of(getLeft(), getRight(), getAbove(), getBelow(), getFront(), getRear());
    }

    public int getDistanceTo(Coordinate3D other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z);
    }

    public List<Coordinate3D> getAllBetween(Coordinate3D other) {
        var coordinates = new ArrayList<Coordinate3D>();

        if (this.x == other.x && this.y == other.y) {
            if (z <= other.z) {
                for (int z = this.z; z <= other.z; z++) {
                    coordinates.add(new Coordinate3D(x, y, z));
                }
            } else {
                for (int z = other.z; z <= this.z; z++) {
                    coordinates.add(new Coordinate3D(x, y, z));
                }
            }
        } else if (this.x == other.x && this.z == other.z) {
            if (y <= other.y) {
                for (int y = this.y; y <= other.y; y++) {
                    coordinates.add(new Coordinate3D(x, y, z));
                }
            } else {
                for (int y = other.y; y <= this.y; y++) {
                    coordinates.add(new Coordinate3D(x, y, z));
                }
            }
        } else if (this.y == other.y && this.z == other.z) {
            if (x <= other.x) {
                for (int x = this.x; x <= other.x; x++) {
                    coordinates.add(new Coordinate3D(x, y, z));
                }
            } else {
                for (int x = other.x; x <= this.x; x++) {
                    coordinates.add(new Coordinate3D(x, y, z));
                }
            }
        } else {
            throw new UnsupportedOperationException("Diagonal lines are not supported!");
        }

        return coordinates;
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
}
