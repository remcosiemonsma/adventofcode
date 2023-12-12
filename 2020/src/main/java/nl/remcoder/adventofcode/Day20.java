package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static nl.remcoder.adventofcode.library.Arrays.reverse;

public class Day20 implements BiAdventOfCodeSolution<Long, Integer> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var lines = input.toList();

        var tiles = new ArrayList<Tile>();

        for (var position = 0; position < lines.size(); position += 12) {
            var tileIdLine = lines.get(position);
            var id = Integer.parseInt(tileIdLine.substring(5, tileIdLine.length() - 1));
            var image = new char[10][];
            for (var i = 0; i < 10; i++) {
                image[i] = lines.get(position + 1 + i).toCharArray();
                image[i] = lines.get(position + 1 + i).toCharArray();
            }

            tiles.add(new Tile(id, image));
        }

        var cornerTiles = new ArrayList<Tile>();

        for (var tile : tiles) {
            var maxTilesMatch = tiles.stream()
                                     .filter(otherTile -> tile != otherTile)
                                     .map(tile::getMatchingEdge)
                                     .filter(edge -> edge != Edge.NONE)
                                     .count();

            if (maxTilesMatch == 2) {
                cornerTiles.add(tile);

                if (cornerTiles.size() == 4) {
                    break;
                }
            }
        }

        return cornerTiles.stream()
                          .mapToLong(Tile::getId)
                          .reduce((left, right) -> left * right)
                          .orElseThrow(() -> new AssertionError("Eek!"));
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var lines = input.toList();

        var tiles = new ArrayList<Tile>();

        for (var position = 0; position < lines.size(); position += 12) {
            var tileIdLine = lines.get(position);
            var id = Integer.parseInt(tileIdLine.substring(5, tileIdLine.length() - 1));
            var image = new char[10][];
            for (var i = 0; i < 10; i++) {
                image[i] = lines.get(position + 1 + i).toCharArray();
                image[i] = lines.get(position + 1 + i).toCharArray();
            }

            tiles.add(new Tile(id, image));
        }

        var cornerTiles = new ArrayList<Tile>();

        for (var tile : tiles) {
            var foundEdges = tiles.stream()
                                  .filter(otherTile -> tile != otherTile)
                                  .map(tile::getMatchingEdge)
                                  .filter(edge -> edge != Edge.NONE)
                                  .toList();

            if (foundEdges.size() == 2) {
                cornerTiles.add(tile);
            }
        }

        var tempImageSize = (int) (Math.sqrt(tiles.size()) * 10);
        var finalImageSize = (int) (Math.sqrt(tiles.size()) * 8);

        var currentLeftEdgeTile = cornerTiles.getFirst();

        tiles.remove(currentLeftEdgeTile);

        if (currentLeftEdgeTile.topTile != null && currentLeftEdgeTile.rightTile != null) {
            currentLeftEdgeTile.flipUpsideDown();
        }

        var tempImage = new char[tempImageSize][tempImageSize];

        for (var y = 0; y < currentLeftEdgeTile.image.length; y++) {
            System.arraycopy(currentLeftEdgeTile.image[y], 0, tempImage[y], 0, currentLeftEdgeTile.image.length);
        }

        var y = 0;
        var x = 10;

        cornerTiles.remove(currentLeftEdgeTile);

        var previousTile = currentLeftEdgeTile;

        while (!tiles.isEmpty()) {
            Tile nextTile;
            if (previousTile.rightTile != null || previousTile.reverseRightTile != null) {
                if (previousTile.rightTile == null) {
                    nextTile = previousTile.reverseRightTile;
                    nextTile.flipUpsideDown();
                } else {
                    nextTile = previousTile.rightTile;
                }
                var edge = nextTile.determineBorder(previousTile.rightBorder);

                switch (edge) {
                    case TOP -> {
                        nextTile.rotateLeft();
                        nextTile.flipUpsideDown();
                    }
                    case RIGHT -> nextTile.flipLeftRight();
                    case BOTTOM -> nextTile.rotateRight();
                    case REVERSE_TOP -> nextTile.rotateLeft();
                    case REVERSE_RIGHT -> {
                        nextTile.flipUpsideDown();
                        nextTile.flipLeftRight();
                    }
                    case REVERSE_BOTTOM -> {
                        nextTile.rotateRight();
                        nextTile.flipUpsideDown();
                    }
                    case REVERSE_LEFT -> nextTile.flipUpsideDown();
                }
            } else {
                if (currentLeftEdgeTile.bottomTile != null) {
                    nextTile = currentLeftEdgeTile.bottomTile;
                } else {
                    nextTile = currentLeftEdgeTile.reverseBottomTile;
                }
                var edge = nextTile.determineBorder(currentLeftEdgeTile.bottomBorder);

                switch (edge) {
                    case RIGHT -> nextTile.rotateLeft();
                    case BOTTOM -> nextTile.flipUpsideDown();
                    case LEFT, REVERSE_LEFT -> {
                        nextTile.rotateRight();
                        nextTile.flipLeftRight();
                    }
                    case REVERSE_TOP -> nextTile.flipLeftRight();
                    case REVERSE_RIGHT -> {
                        nextTile.rotateLeft();
                        nextTile.flipLeftRight();
                    }
                    case REVERSE_BOTTOM -> {
                        nextTile.flipUpsideDown();
                        nextTile.flipLeftRight();
                    }
                }
                currentLeftEdgeTile = nextTile;
                y += 10;
                x = 0;
            }

            for (var y1 = 0; y1 < nextTile.image.length; y1++) {
                System.arraycopy(nextTile.image[y1], 0, tempImage[y1 + y], x, nextTile.image.length);
            }

            x += 10;

            tiles.remove(nextTile);
            previousTile = nextTile;
        }

        var finalY = 0;
        var finalX = 0;

        var finalImage = new char[finalImageSize][finalImageSize];

        for (var tempY = 0; tempY < tempImageSize; tempY++) {
            if (tempY % 10 == 0 || tempY % 10 == 9) {
                continue;
            }
            for (var tempX = 0; tempX < tempImageSize; tempX++) {
                if (tempX % 10 == 0 || tempX % 10 == 9) {
                    continue;
                }
                finalImage[finalY][finalX++] = tempImage[tempY][tempX];
            }
            finalX = 0;
            finalY++;
        }


        if (doesImageNotContainMonster(finalImage)) {
            finalImage = rotateRight(finalImage);
            if (doesImageNotContainMonster(finalImage)) {
                finalImage = rotateRight(finalImage);
                if (doesImageNotContainMonster(finalImage)) {
                    finalImage = rotateRight(finalImage);
                    if (doesImageNotContainMonster(finalImage)) {
                        finalImage = rotateRight(finalImage);
                        finalImage = flipUpsideDown(finalImage);
                        if (doesImageNotContainMonster(finalImage)) {
                            finalImage = rotateRight(finalImage);
                            if (doesImageNotContainMonster(finalImage)) {
                                finalImage = rotateRight(finalImage);
                                if (doesImageNotContainMonster(finalImage)) {
                                    finalImage = rotateRight(finalImage);
                                    if (doesImageNotContainMonster(finalImage)) {
                                        finalImage = rotateRight(finalImage);
                                        finalImage = flipUpsideDown(finalImage);
                                        finalImage = nl.remcoder.adventofcode.library.Arrays.reverse(finalImage);
                                        if (doesImageNotContainMonster(finalImage)) {
                                            finalImage = rotateRight(finalImage);
                                            if (doesImageNotContainMonster(finalImage)) {
                                                finalImage = rotateRight(finalImage);
                                                if (doesImageNotContainMonster(finalImage)) {
                                                    finalImage = rotateRight(finalImage);
                                                    if (doesImageNotContainMonster(finalImage)) {
                                                        finalImage = rotateRight(finalImage);
                                                        finalImage = flipUpsideDown(finalImage);
                                                        if (doesImageNotContainMonster(finalImage)) {
                                                            finalImage = rotateRight(finalImage);
                                                            if (doesImageNotContainMonster(finalImage)) {
                                                                finalImage = rotateRight(finalImage);
                                                                if (doesImageNotContainMonster(finalImage)) {
                                                                    finalImage = rotateRight(finalImage);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        markMonsters(finalImage);
        
        return countSeaRoughness(finalImage);
    }

    private int countSeaRoughness(char[][] image) {
        var amountOfWaves = 0;

        for (var line : image) {
            for (var pixel : line) {
                if (pixel == '#') {
                    amountOfWaves++;
                }
            }
        }

        return amountOfWaves;
    }

    private boolean doesImageNotContainMonster(char[][] finalImage) {
        for (var yForMonster = 0; yForMonster < finalImage.length - 3; yForMonster++) {
            for (var xForMonster = 0; xForMonster < finalImage.length - 20; xForMonster++) {
                if (finalImage[yForMonster][xForMonster + 18] == '#' &&
                    finalImage[yForMonster + 1][xForMonster] == '#' &&
                    finalImage[yForMonster + 1][xForMonster + 5] == '#' &&
                    finalImage[yForMonster + 1][xForMonster + 6] == '#' &&
                    finalImage[yForMonster + 1][xForMonster + 11] == '#' &&
                    finalImage[yForMonster + 1][xForMonster + 12] == '#' &&
                    finalImage[yForMonster + 1][xForMonster + 17] == '#' &&
                    finalImage[yForMonster + 1][xForMonster + 18] == '#' &&
                    finalImage[yForMonster + 1][xForMonster + 19] == '#' &&
                    finalImage[yForMonster + 2][xForMonster + 1] == '#' &&
                    finalImage[yForMonster + 2][xForMonster + 4] == '#' &&
                    finalImage[yForMonster + 2][xForMonster + 7] == '#' &&
                    finalImage[yForMonster + 2][xForMonster + 10] == '#' &&
                    finalImage[yForMonster + 2][xForMonster + 13] == '#' &&
                    finalImage[yForMonster + 2][xForMonster + 16] == '#') {
                    return false;
                }
            }
        }
        return true;
    }

    private void markMonsters(char[][] finalImage) {
        for (var yForMonster = 0; yForMonster < finalImage.length - 3; yForMonster++) {
            for (var xForMonster = 0; xForMonster < finalImage.length - 20; xForMonster++) {
                if (finalImage[yForMonster][xForMonster + 18] == '#' &&
                    finalImage[yForMonster + 1][xForMonster] == '#' &&
                    finalImage[yForMonster + 1][xForMonster + 5] == '#' &&
                    finalImage[yForMonster + 1][xForMonster + 6] == '#' &&
                    finalImage[yForMonster + 1][xForMonster + 11] == '#' &&
                    finalImage[yForMonster + 1][xForMonster + 12] == '#' &&
                    finalImage[yForMonster + 1][xForMonster + 17] == '#' &&
                    finalImage[yForMonster + 1][xForMonster + 18] == '#' &&
                    finalImage[yForMonster + 1][xForMonster + 19] == '#' &&
                    finalImage[yForMonster + 2][xForMonster + 1] == '#' &&
                    finalImage[yForMonster + 2][xForMonster + 4] == '#' &&
                    finalImage[yForMonster + 2][xForMonster + 7] == '#' &&
                    finalImage[yForMonster + 2][xForMonster + 10] == '#' &&
                    finalImage[yForMonster + 2][xForMonster + 13] == '#' &&
                    finalImage[yForMonster + 2][xForMonster + 16] == '#') {

                    finalImage[yForMonster][xForMonster + 18] = 'O';
                    finalImage[yForMonster + 1][xForMonster] = 'O';
                    finalImage[yForMonster + 1][xForMonster + 5] = 'O';
                    finalImage[yForMonster + 1][xForMonster + 6] = 'O';
                    finalImage[yForMonster + 1][xForMonster + 11] = 'O';
                    finalImage[yForMonster + 1][xForMonster + 12] = 'O';
                    finalImage[yForMonster + 1][xForMonster + 17] = 'O';
                    finalImage[yForMonster + 1][xForMonster + 18] = 'O';
                    finalImage[yForMonster + 1][xForMonster + 19] = 'O';
                    finalImage[yForMonster + 2][xForMonster + 1] = 'O';
                    finalImage[yForMonster + 2][xForMonster + 4] = 'O';
                    finalImage[yForMonster + 2][xForMonster + 7] = 'O';
                    finalImage[yForMonster + 2][xForMonster + 10] = 'O';
                    finalImage[yForMonster + 2][xForMonster + 13] = 'O';
                    finalImage[yForMonster + 2][xForMonster + 16] = 'O';
                }
            }
        }
    }

    private static class Tile {
        private final int id;
        private char[][] image;
        private char[] topBorder;
        private char[] rightBorder;
        private char[] bottomBorder;
        private char[] leftBorder;
        private char[] reverseTopBorder;
        private char[] reverseRightBorder;
        private char[] reverseBottomBorder;
        private char[] reverseLeftBorder;
        private Tile topTile;
        private Tile rightTile;
        private Tile bottomTile;
        private Tile leftTile;
        private Tile reverseTopTile;
        private Tile reverseRightTile;
        private Tile reverseBottomTile;
        private Tile reverseLeftTile;
        private final List<Tile> edgeTiles = new ArrayList<>();

        private Tile(int id, char[][] image) {
            this.id = id;
            this.image = image;
            this.topBorder = image[0];
            this.bottomBorder = image[9];
            leftBorder = new char[10];
            rightBorder = new char[10];
            for (int i = 0; i < 10; i++) {
                leftBorder[i] = image[i][0];
                rightBorder[i] = image[i][9];
            }
            reverseTopBorder = reverse(topBorder);
            reverseRightBorder = reverse(rightBorder);
            reverseBottomBorder = reverse(bottomBorder);
            reverseLeftBorder = reverse(leftBorder);
        }

        public Edge getMatchingEdge(Tile tile) {
            if (borderMatches(tile, topBorder)) {
                topTile = tile;
                edgeTiles.add(tile);
                return Edge.TOP;
            }
            if (borderMatches(tile, reverseTopBorder)) {
                reverseTopTile = tile;
                edgeTiles.add(tile);
                return Edge.REVERSE_TOP;
            }
            if (borderMatches(tile, rightBorder)) {
                rightTile = tile;
                edgeTiles.add(tile);
                return Edge.RIGHT;
            }
            if (borderMatches(tile, reverseRightBorder)) {
                reverseRightTile = tile;
                edgeTiles.add(tile);
                return Edge.REVERSE_RIGHT;
            }
            if (borderMatches(tile, bottomBorder)) {
                bottomTile = tile;
                edgeTiles.add(tile);
                return Edge.BOTTOM;
            }
            if (borderMatches(tile, reverseBottomBorder)) {
                reverseBottomTile = tile;
                edgeTiles.add(tile);
                return Edge.REVERSE_BOTTOM;
            }
            if (borderMatches(tile, leftBorder)) {
                leftTile = tile;
                edgeTiles.add(tile);
                return Edge.LEFT;
            }
            if (borderMatches(tile, reverseLeftBorder)) {
                reverseLeftTile = tile;
                edgeTiles.add(tile);
                return Edge.REVERSE_LEFT;
            }

            return Edge.NONE;
        }

        private boolean borderMatches(Tile tile, char[] border) {
            return Arrays.equals(border, tile.topBorder) || Arrays.equals(border, tile.reverseTopBorder) ||
                   Arrays.equals(border, tile.rightBorder) || Arrays.equals(border, tile.reverseRightBorder) ||
                   Arrays.equals(border, tile.bottomBorder) || Arrays.equals(border, tile.reverseBottomBorder) ||
                   Arrays.equals(border, tile.leftBorder) || Arrays.equals(border, tile.reverseLeftBorder);
        }

        public int getId() {
            return id;
        }

        public void rotateRight() {
            image = Day20.rotateRight(image);
            redetermineBorders();
        }

        public void rotateLeft() {
            image = Day20.rotateLeft(this.image);
            redetermineBorders();
        }

        public void flipLeftRight() {
            for (int position = 0; position < image.length; position++) {
                image[position] = reverse(image[position]);
            }
            redetermineBorders();
        }

        public void flipUpsideDown() {
            image = nl.remcoder.adventofcode.library.Arrays.reverse(image);
            redetermineBorders();
        }

        public Edge determineBorder(char[] otherBorder) {
            if (Arrays.equals(topBorder, otherBorder)) {
                return Edge.TOP;
            }
            if (Arrays.equals(rightBorder, otherBorder)) {
                return Edge.RIGHT;
            }
            if (Arrays.equals(bottomBorder, otherBorder)) {
                return Edge.BOTTOM;
            }
            if (Arrays.equals(leftBorder, otherBorder)) {
                return Edge.LEFT;
            }
            if (Arrays.equals(reverseTopBorder, otherBorder)) {
                return Edge.REVERSE_TOP;
            }
            if (Arrays.equals(reverseRightBorder, otherBorder)) {
                return Edge.REVERSE_RIGHT;
            }
            if (Arrays.equals(reverseBottomBorder, otherBorder)) {
                return Edge.REVERSE_BOTTOM;
            }
            if (Arrays.equals(reverseLeftBorder, otherBorder)) {
                return Edge.REVERSE_LEFT;
            }
            return Edge.NONE;
        }

        private void redetermineBorders() {
            this.topBorder = image[0];
            this.bottomBorder = image[9];
            leftBorder = new char[10];
            rightBorder = new char[10];
            for (int i = 0; i < 10; i++) {
                leftBorder[i] = image[i][0];
                rightBorder[i] = image[i][9];
            }
            reverseTopBorder = reverse(topBorder);
            reverseRightBorder = reverse(rightBorder);
            reverseBottomBorder = reverse(bottomBorder);
            reverseLeftBorder = reverse(leftBorder);

            topTile = null;
            rightTile = null;
            bottomTile = null;
            leftTile = null;
            reverseTopTile = null;
            reverseRightTile = null;
            reverseBottomTile = null;
            reverseLeftTile = null;

            for (Tile tile : edgeTiles) {
                if (borderMatches(tile, topBorder)) {
                    topTile = tile;
                    continue;
                }
                if (borderMatches(tile, reverseTopBorder)) {
                    reverseTopTile = tile;
                    continue;
                }
                if (borderMatches(tile, rightBorder)) {
                    rightTile = tile;
                    continue;
                }
                if (borderMatches(tile, reverseRightBorder)) {
                    reverseRightTile = tile;
                    continue;
                }
                if (borderMatches(tile, bottomBorder)) {
                    bottomTile = tile;
                    continue;
                }
                if (borderMatches(tile, reverseBottomBorder)) {
                    reverseBottomTile = tile;
                    continue;
                }
                if (borderMatches(tile, leftBorder)) {
                    leftTile = tile;
                    continue;
                }
                if (borderMatches(tile, reverseLeftBorder)) {
                    reverseLeftTile = tile;
                }
            }
        }
    }

    private char[][] flipUpsideDown(char[][] image) {
        var newArray = new char[image.length][];
        for (var i = 0; i < image.length; i++) {
            newArray[i] = image[image.length - 1 - i];
        }
        return newArray;
    }

    private static char[][] rotateRight(char[][] image) {
        var size = image.length;
        var ret = new char[size][size];

        for (var i = 0; i < size; ++i) {
            for (var j = 0; j < size; ++j) {
                ret[i][j] = image[size - j - 1][i];
            }
        }

        return ret;
    }

    private static char[][] rotateLeft(char[][] image) {
        var size = image.length;
        var ret = new char[size][size];

        for (var i = 0; i < size; ++i) {
            for (var j = 0; j < size; ++j) {
                ret[i][j] = image[j][size - i - 1];
            }
        }

        return ret;
    }

    private enum Edge {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT,
        REVERSE_TOP,
        REVERSE_RIGHT,
        REVERSE_BOTTOM,
        REVERSE_LEFT,
        NONE
    }
}
