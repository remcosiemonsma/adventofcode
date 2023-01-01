package nl.remcoder.adventofcode.library.model;

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
    
    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
}
