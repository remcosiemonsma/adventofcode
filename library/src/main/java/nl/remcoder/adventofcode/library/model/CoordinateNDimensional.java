package nl.remcoder.adventofcode.library.model;

import java.util.Arrays;

public record CoordinateNDimensional(int[] point) {
    public long getDistanceTo(CoordinateNDimensional other) {
        var distance = 0L;
        
        if (point.length != other.point.length) {
            throw new AssertionError("Different point lengths received!");
        }
        
        for (int i = 0; i < point.length; i++) {
            distance += Math.abs(point[i] - other.point[i]);
        }

        return distance;
    }

    @Override
    public String toString() {
        return "CoordinateNDimensional{" +
               "point=" + Arrays.toString(point) +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoordinateNDimensional that = (CoordinateNDimensional) o;
        return Arrays.equals(point, that.point);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(point);
    }
}
