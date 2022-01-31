package nl.remcoder.adventofcode.library.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Grid {
    private final int startx;
    private final int starty;
    private final int endx;
    private final int endy;
    private final Map<Coordinate, Object> values = new HashMap<>();

    public Grid(boolean[][] data) {
        startx = 0;
        starty = 0;
        endx = data[0].length;
        endy = data.length;

        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                values.put(new Coordinate(x, y), data[x][y]);
            }
        }
    }

    public Grid(int startx, int starty, int endx, int endy) {
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
    }

    public Grid(int startx, int starty, int endx, int endy, Stream<String> data) {
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
    }

    public boolean isCoordinateInGrid(Coordinate coordinate) {
        return coordinate.x() >= startx && coordinate.x() <= endx &&
               coordinate.y() >= starty && coordinate.y() <= endy;
    }

    public void printGrid() {
        for (int y = starty; y <= endy; y++) {
            for (int x = startx; x <= endx; x++) {
                Coordinate coordinate = new Coordinate(x, y);

                Object value = values.get(coordinate);

                if (value == null) {
                    System.out.print(' ');
                } else {
                    if (value instanceof Boolean b) {
                        if (b) {
                            System.out.print('#');
                        } else {
                            System.out.print('.');
                        }
                    } else {
                        System.out.print(value);
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
