package nl.remcoder.adventofcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class Day24 {
    public long handlePart1(Stream<String> input) {
        Map<Coordinate, Tile> tiles = determineStartState(input);

        return tiles.values()
                    .stream()
                    .filter(Tile::isFlipped)
                    .count();
    }

    public long handlePart2(Stream<String> input) {
        Map<Coordinate, Tile> tiles = determineStartState(input);

        int gridSize = 75;
        for (int z = -gridSize; z <= gridSize; z++) {
            for (int y = -gridSize; y <= gridSize; y++) {
                for (int x = -gridSize; x <= gridSize; x++) {
                    if (x + y + z == 0) {
                        tiles.computeIfAbsent(new Coordinate(x, y, z), Tile::new);
                    }
                }
            }
        }

        for (int day = 0; day < 100; day++) {
            tiles.values().forEach(tile -> tile.determineFLipState(tiles));
            tiles.values()
                 .stream()
                 .filter(tile -> tile.shouldFlip)
                 .forEach(tile -> tile.flipped = !tile.flipped);
        }

        return tiles.values()
                    .stream()
                    .filter(Tile::isFlipped)
                    .count();
    }

    private Map<Coordinate, Tile> determineStartState(Stream<String> input) {
        Map<Coordinate, Tile> tiles = new HashMap<>();

        Coordinate coordinate = new Coordinate(0, 0, 0);
        Tile startTile = new Tile(coordinate);
        tiles.put(coordinate, startTile);

        input.forEach(line -> {
            char[] chars = line.toCharArray();

            Tile currentTile = startTile;

            for (int position = 0; position < chars.length; position++) {
                switch (chars[position]) {
                    case 'e' -> {
                        Coordinate targetCoordinate = new Coordinate(currentTile.coordinate.x + 1,
                                                                     currentTile.coordinate.y - 1,
                                                                     currentTile.coordinate.z);

                        currentTile = tiles.computeIfAbsent(targetCoordinate, Tile::new);
                    }
                    case 's' -> {
                        position++;
                        if (chars[position] == 'e') {
                            Coordinate targetCoordinate = new Coordinate(currentTile.coordinate.x,
                                                                         currentTile.coordinate.y - 1,
                                                                         currentTile.coordinate.z + 1);

                            currentTile = tiles.computeIfAbsent(targetCoordinate, Tile::new);
                        } else {
                            Coordinate targetCoordinate = new Coordinate(currentTile.coordinate.x - 1,
                                                                         currentTile.coordinate.y,
                                                                         currentTile.coordinate.z + 1);

                            currentTile = tiles.computeIfAbsent(targetCoordinate, Tile::new);
                        }
                    }
                    case 'w' -> {
                        Coordinate targetCoordinate = new Coordinate(currentTile.coordinate.x - 1,
                                                                     currentTile.coordinate.y + 1,
                                                                     currentTile.coordinate.z);

                        currentTile = tiles.computeIfAbsent(targetCoordinate, Tile::new);
                    }
                    case 'n' -> {
                        position++;
                        if (chars[position] == 'e') {
                            Coordinate targetCoordinate = new Coordinate(currentTile.coordinate.x + 1,
                                                                         currentTile.coordinate.y,
                                                                         currentTile.coordinate.z - 1);

                            currentTile = tiles.computeIfAbsent(targetCoordinate, Tile::new);
                        } else {
                            Coordinate targetCoordinate = new Coordinate(currentTile.coordinate.x,
                                                                         currentTile.coordinate.y + 1,
                                                                         currentTile.coordinate.z - 1);

                            currentTile = tiles.computeIfAbsent(targetCoordinate, Tile::new);
                        }
                    }
                }
            }
            currentTile.flipped = !currentTile.flipped;
        });
        return tiles;
    }

    private static class Coordinate {
        private final int x;
        private final int y;
        private final int z;

        private Coordinate(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y && z == that.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                   "x=" + x +
                   ", y=" + y +
                   ", z=" + z +
                   '}';
        }
    }

    private static class Tile {
        private final Coordinate coordinate;
        private boolean flipped;
        private boolean shouldFlip;

        private Tile(Coordinate coordinate) {
            this.coordinate = coordinate;
        }

        public boolean isFlipped() {
            return flipped;
        }

        public void determineFLipState(Map<Coordinate, Tile> otherTiles) {
            Tile e = otherTiles.get(new Coordinate(coordinate.x + 1, coordinate.y - 1, coordinate.z));
            Tile se = otherTiles.get(new Coordinate(coordinate.x, coordinate.y - 1, coordinate.z + 1));
            Tile sw = otherTiles.get(new Coordinate(coordinate.x - 1, coordinate.y, coordinate.z + 1));
            Tile w = otherTiles.get(new Coordinate(coordinate.x - 1, coordinate.y + 1, coordinate.z));
            Tile nw = otherTiles.get(new Coordinate(coordinate.x, coordinate.y + 1, coordinate.z - 1));
            Tile ne = otherTiles.get(new Coordinate(coordinate.x + 1, coordinate.y, coordinate.z - 1));

            int amountOfTilesFlipped = 0;

            if (e != null && e.flipped) {
                amountOfTilesFlipped++;
            }
            if (se != null && se.flipped) {
                amountOfTilesFlipped++;
            }
            if (sw != null && sw.flipped) {
                amountOfTilesFlipped++;
            }
            if (w != null && w.flipped) {
                amountOfTilesFlipped++;
            }
            if (nw != null && nw.flipped) {
                amountOfTilesFlipped++;
            }
            if (ne != null && ne.flipped) {
                amountOfTilesFlipped++;
            }

            if (flipped) {
                shouldFlip = amountOfTilesFlipped == 0 || amountOfTilesFlipped > 2;
            } else {
                shouldFlip = amountOfTilesFlipped == 2;
            }
        }

        @Override
        public String toString() {
            return "Tile{" +
                   "coordinate=" + coordinate +
                   ", flipped=" + flipped +
                   '}';
        }
    }
}
