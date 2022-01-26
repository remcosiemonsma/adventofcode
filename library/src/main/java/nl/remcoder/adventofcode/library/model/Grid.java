package nl.remcoder.adventofcode.library.model;

public abstract class Grid {
    private final int height;
    private final int width;

    protected Grid(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public boolean isCoordinateInGrid(Coordinate coordinate) {
        return coordinate.x() >= 0 && coordinate.x() < width &&
               coordinate.y() >= 0 && coordinate.y() < height;
    }

    public abstract void printGrid();
}
