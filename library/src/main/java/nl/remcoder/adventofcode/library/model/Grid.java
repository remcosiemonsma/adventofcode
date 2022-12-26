package nl.remcoder.adventofcode.library.model;

import java.util.HashMap;
import java.util.Map;

public class Grid<T> {
    private int startx;
    private int starty;
    private int endx;
    private int endy;
    private final Map<Coordinate, T> values = new HashMap<>();

    public Grid(T[][] data) {
        for (var y = 0; y < data.length; y++) {
            for (var x = 0; x < data[y].length; x++) {
                if (data[y][x] != null) {
                    values.put(new Coordinate(x, y), data[y][x]);
                }
            }
        }

        calculateSize();
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
        if (value == null) {
            values.remove(new Coordinate(x, y));
        } else {
            values.put(new Coordinate(x, y), value);
        }
    }

    public void set(Coordinate coordinate, T value) {
        if (value == null) {
            values.remove(coordinate);
        } else {
            values.put(coordinate, value);
        }
    }

    public void calculateSize() {
        startx = values.keySet()
                       .stream()
                       .mapToInt(Coordinate::x)
                       .min()
                       .orElseThrow(() -> new AssertionError("Eek!"));
        endx = values.keySet()
                     .stream()
                     .mapToInt(Coordinate::x)
                     .max()
                     .orElseThrow(() -> new AssertionError("Eek!"));
        starty = values.keySet()
                       .stream()
                       .mapToInt(Coordinate::y)
                       .min()
                       .orElseThrow(() -> new AssertionError("Eek!"));
        endy = values.keySet()
                     .stream()
                     .mapToInt(Coordinate::y)
                     .max()
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    public void printGrid() {
        calculateSize();
        for (int y = starty; y <= endy; y++) {
            for (int x = startx; x <= endx; x++) {
                Coordinate coordinate = new Coordinate(x, y);

                Object value = values.get(coordinate);

                if (value == null) {
                    System.out.print('.');
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

    public void fill(T value) {
        for (int y = starty; y <= endy; y++) {
            for (int x = startx; x <= endx; x++) {
                values.put(new Coordinate(x, y), value);
            }
        }
    }

    public long countElements(T value) {
        return values.values().stream().filter(t -> t.equals(value)).count();
    }

    public Coordinate findValue(T value) {
        return values.entrySet()
                     .stream()
                     .filter(entry -> entry.getValue().equals(value))
                     .findFirst()
                     .map(Map.Entry::getKey)
                     .orElseThrow(() -> new AssertionError("Value not found!"));
    }
}
