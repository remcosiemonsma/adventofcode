package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate3D;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day24 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var tiles = determineStartState(input);

        return tiles.values()
                    .stream()
                    .filter(Tile::isFlipped)
                    .count();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var tiles = determineStartState(input);

        var gridSize = 75;
        for (var z = -gridSize; z <= gridSize; z++) {
            for (var y = -gridSize; y <= gridSize; y++) {
                for (var x = -gridSize; x <= gridSize; x++) {
                    if (x + y + z == 0) {
                        tiles.computeIfAbsent(new Coordinate3D(x, y, z), Tile::new);
                    }
                }
            }
        }

        for (var day = 0; day < 100; day++) {
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

    private Map<Coordinate3D, Tile> determineStartState(Stream<String> input) {
        var tiles = new HashMap<Coordinate3D, Tile>();

        var coordinate = new Coordinate3D(0, 0, 0);
        var startTile = new Tile(coordinate);
        tiles.put(coordinate, startTile);

        input.forEach(line -> {
            var chars = line.toCharArray();

            var currentTile = startTile;

            for (var position = 0; position < chars.length; position++) {
                switch (chars[position]) {
                    case 'e' -> {
                        var targetCoordinate3D = new Coordinate3D(currentTile.coordinate.x() + 1,
                                                                  currentTile.coordinate.y() - 1,
                                                                  currentTile.coordinate.z());

                        currentTile = tiles.computeIfAbsent(targetCoordinate3D, Tile::new);
                    }
                    case 's' -> {
                        position++;
                        if (chars[position] == 'e') {
                            var targetCoordinate3D = new Coordinate3D(currentTile.coordinate.x(),
                                                                      currentTile.coordinate.y() - 1,
                                                                      currentTile.coordinate.z() + 1);

                            currentTile = tiles.computeIfAbsent(targetCoordinate3D, Tile::new);
                        } else {
                            var targetCoordinate3D = new Coordinate3D(currentTile.coordinate.x() - 1,
                                                                      currentTile.coordinate.y(),
                                                                      currentTile.coordinate.z() + 1);

                            currentTile = tiles.computeIfAbsent(targetCoordinate3D, Tile::new);
                        }
                    }
                    case 'w' -> {
                        var targetCoordinate3D = new Coordinate3D(currentTile.coordinate.x() - 1,
                                                                  currentTile.coordinate.y() + 1,
                                                                  currentTile.coordinate.z());

                        currentTile = tiles.computeIfAbsent(targetCoordinate3D, Tile::new);
                    }
                    case 'n' -> {
                        position++;
                        if (chars[position] == 'e') {
                            var targetCoordinate3D = new Coordinate3D(currentTile.coordinate.x() + 1,
                                                                      currentTile.coordinate.y(),
                                                                      currentTile.coordinate.z() - 1);

                            currentTile = tiles.computeIfAbsent(targetCoordinate3D, Tile::new);
                        } else {
                            var targetCoordinate3D = new Coordinate3D(currentTile.coordinate.x(),
                                                                      currentTile.coordinate.y() + 1,
                                                                      currentTile.coordinate.z() - 1);

                            currentTile = tiles.computeIfAbsent(targetCoordinate3D, Tile::new);
                        }
                    }
                }
            }
            currentTile.flipped = !currentTile.flipped;
        });
        return tiles;
    }

    private static class Tile {
        private final Coordinate3D coordinate;
        private boolean flipped;
        private boolean shouldFlip;

        private Tile(Coordinate3D coordinate) {
            this.coordinate = coordinate;
        }

        public boolean isFlipped() {
            return flipped;
        }

        public void determineFLipState(Map<Coordinate3D, Tile> otherTiles) {
            var e = otherTiles.get(new Coordinate3D(coordinate.x() + 1, coordinate.y() - 1, coordinate.z()));
            var se = otherTiles.get(new Coordinate3D(coordinate.x(), coordinate.y() - 1, coordinate.z() + 1));
            var sw = otherTiles.get(new Coordinate3D(coordinate.x() - 1, coordinate.y(), coordinate.z() + 1));
            var w = otherTiles.get(new Coordinate3D(coordinate.x() - 1, coordinate.y() + 1, coordinate.z()));
            var nw = otherTiles.get(new Coordinate3D(coordinate.x(), coordinate.y() + 1, coordinate.z() - 1));
            var ne = otherTiles.get(new Coordinate3D(coordinate.x() + 1, coordinate.y(), coordinate.z() - 1));

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
