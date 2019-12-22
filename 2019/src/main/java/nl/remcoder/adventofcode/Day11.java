package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Day11 {
    private int ypos;
    private int xpos;
    private Direction direction;
    private boolean[][] grid;
    private Set<Point> paintedPoints;

    public int handlePart1(Stream<String> inputStream) {
        String line = inputStream.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        BlockingQueue<Long> output = new Painter();
        BlockingQueue<Long> input = new Camera();

        grid = new boolean[96][96];

        ypos = 48;
        xpos = 48;

        direction = Direction.UP;

        paintedPoints = new HashSet<>();

        IntCodeComputer robotBrain = new IntCodeComputer(opcodes, input, output);

        robotBrain.runProgram();

        printGrid();

        return paintedPoints.size();
    }

    private void printGrid() {
        for (boolean[] line : grid) {
            for (boolean pixel : line) {
                if (pixel) {
                    System.out.print('#');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }

    public int handlePart2(Stream<String> inputStream) {
        String line = inputStream.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        BlockingQueue<Long> output = new Painter();
        BlockingQueue<Long> input = new Camera();

        grid = new boolean[96][96];

        ypos = 48;
        xpos = 48;

        direction = Direction.UP;

        grid[ypos][xpos] = true;

        paintedPoints = new HashSet<>();

        IntCodeComputer robotBrain = new IntCodeComputer(opcodes, input, output);

        robotBrain.runProgram();

        printGrid();

        return paintedPoints.size();
    }

    private class Painter implements BlockingQueue<Long> {
        OutputState outputState = OutputState.PAINT;

        @Override
        public boolean add(Long aLong) {
            return false;
        }

        @Override
        public boolean offer(Long aLong) {
            return false;
        }

        @Override
        public Long remove() {
            return null;
        }

        @Override
        public Long poll() {
            return null;
        }

        @Override
        public Long element() {
            return null;
        }

        @Override
        public Long peek() {
            return null;
        }

        @Override
        public void put(Long aLong) throws InterruptedException {
            switch (outputState) {
                case PAINT:
                    paintedPoints.add(new Point(xpos, ypos));
                    if (aLong == 0) {
                        grid[ypos][xpos] = false;
                    } else if (aLong == 1) {
                        grid[ypos][xpos] = true;
                    }
                    outputState = OutputState.DIRECTION;
                    break;
                case DIRECTION:
                    if (aLong == 0) {
                        rotateLeft();
                    } else if (aLong == 1) {
                        rotateRight();
                    }
                    move();
                    outputState = OutputState.PAINT;
                    break;
            }
        }

        @Override
        public boolean offer(Long aLong, long timeout, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public Long take() throws InterruptedException {
            return null;
        }

        @Override
        public Long poll(long timeout, TimeUnit unit) throws InterruptedException {
            return null;
        }

        @Override
        public int remainingCapacity() {
            return 0;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Long> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<Long> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public int drainTo(Collection<? super Long> c) {
            return 0;
        }

        @Override
        public int drainTo(Collection<? super Long> c, int maxElements) {
            return 0;
        }
    }

    private void move() {
        switch (direction) {
            case UP -> ypos--;
            case LEFT -> xpos--;
            case DOWN -> ypos++;
            case RIGHT -> xpos++;
        }
    }

    private void rotateLeft() {
        switch (direction) {
            case UP -> direction = Direction.LEFT;
            case LEFT -> direction = Direction.DOWN;
            case DOWN -> direction = Direction.RIGHT;
            case RIGHT -> direction = Direction.UP;
        }
    }

    private void rotateRight() {
        switch (direction) {
            case UP -> direction = Direction.RIGHT;
            case RIGHT -> direction = Direction.DOWN;
            case DOWN -> direction = Direction.LEFT;
            case LEFT -> direction = Direction.UP;
        }
    }

    private class Camera implements BlockingQueue<Long> {

        @Override
        public boolean add(Long aLong) {
            return false;
        }

        @Override
        public boolean offer(Long aLong) {
            return false;
        }

        @Override
        public Long remove() {
            return null;
        }

        @Override
        public Long poll() {
            return null;
        }

        @Override
        public Long element() {
            return null;
        }

        @Override
        public Long peek() {
            return null;
        }

        @Override
        public void put(Long aLong) throws InterruptedException {

        }

        @Override
        public boolean offer(Long aLong, long timeout, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public Long take() throws InterruptedException {
            return grid[ypos][xpos] ? 1L : 0L;
        }

        @Override
        public Long poll(long timeout, TimeUnit unit) throws InterruptedException {
            return null;
        }

        @Override
        public int remainingCapacity() {
            return 0;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Long> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<Long> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public int drainTo(Collection<? super Long> c) {
            return 0;
        }

        @Override
        public int drainTo(Collection<? super Long> c, int maxElements) {
            return 0;
        }
    }

    private enum Direction {
        LEFT,
        RIGHT,
        DOWN,
        UP
    }

    private enum OutputState {
        PAINT,
        DIRECTION
    }

    private class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return x == point.x &&
                   y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
