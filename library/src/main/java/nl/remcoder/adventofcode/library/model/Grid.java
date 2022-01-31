package nl.remcoder.adventofcode.library.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Grid<T> {
    private final int startx;
    private final int starty;
    private final int endx;
    private final int endy;
    private final Map<Coordinate, T> values = new HashMap<>();

    public Grid(T[][] data) {
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                values.put(new Coordinate(x, y), data[y][x]);
            }
        }

        startx = values.keySet().stream().mapToInt(Coordinate::x).min().getAsInt();
        endx = values.keySet().stream().mapToInt(Coordinate::x).max().getAsInt();
        starty = values.keySet().stream().mapToInt(Coordinate::y).min().getAsInt();
        endy = values.keySet().stream().mapToInt(Coordinate::y).max().getAsInt();
    }

    public Grid(int startx, int starty, int endx, int endy) {
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
    }

    public boolean isCoordinateInGrid(Coordinate coordinate) {
        return coordinate.x() >= startx && coordinate.x() <= endx &&
               coordinate.y() >= starty && coordinate.y() <= endy;
    }

    public int getStartx() {
        return startx;
    }

    public int getStarty() {
        return starty;
    }

    public int getEndx() {
        return endx;
    }

    public int getEndy() {
        return endy;
    }

    public T get(int x, int y) {
        return values.get(new Coordinate(x, y));
    }

    public T get(Coordinate coordinate) {
        return values.get(coordinate);
    }

    public long countState(T state) {
        return values.values().stream().filter(value -> value.equals(state)).count();
    }

    public void set(int x, int y, T value) {
        values.put(new Coordinate(x, y), value);
    }

    public void set(Coordinate coordinate, T value) {
        values.put(coordinate, value);
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
