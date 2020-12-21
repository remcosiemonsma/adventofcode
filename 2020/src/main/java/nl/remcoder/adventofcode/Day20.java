package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day20 {
    public long handlePart1(Stream<String> input) {
        List<String> lines = input.collect(Collectors.toList());

        List<Tile> tiles = new ArrayList<>();

        for (int position = 0; position < lines.size(); position += 12) {
            String tileIdLine = lines.get(position);
            int id = Integer.parseInt(tileIdLine.substring(5, tileIdLine.length() - 1));
            char[][] image = new char[10][];
            for (int i = 0; i < 10; i++) {
                image[i] = lines.get(position + 1 + i).toCharArray();
                image[i] = lines.get(position + 1 + i).toCharArray();
            }

            tiles.add(new Tile(id, image));
        }

        List<Tile> cornerTiles = new ArrayList<>();

        for (Tile tile : tiles) {
            long maxTilesMatch = tiles.stream()
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
                          .getAsLong();
    }

    public int handlePart2(Stream<String> input) {
        List<String> lines = input.collect(Collectors.toList());

        List<Tile> tiles = new ArrayList<>();

        for (int position = 0; position < lines.size(); position += 12) {
            String tileIdLine = lines.get(position);
            int id = Integer.parseInt(tileIdLine.substring(5, tileIdLine.length() - 1));
            char[][] image = new char[10][];
            for (int i = 0; i < 10; i++) {
                image[i] = lines.get(position + 1 + i).toCharArray();
                image[i] = lines.get(position + 1 + i).toCharArray();
            }

            tiles.add(new Tile(id, image));
        }

        Tile cornerTile = null;
        List<Edge> matchingEdges = null;

        for (Tile tile : tiles) {
            List<Edge> foundEdges = tiles.stream()
                                         .filter(otherTile -> tile != otherTile)
                                         .map(tile::getMatchingEdge)
                                         .filter(edge -> edge != Edge.NONE)
                                         .collect(Collectors.toList());

            if (foundEdges.size() == 2) {
                cornerTile = tile;
                matchingEdges = foundEdges;
                break;
            }
        }

        int imageSize = (int) (Math.sqrt(tiles.size()) * 8);

        tiles.remove(cornerTile);

        char[][] image = new char[imageSize][imageSize];

        System.out.println(matchingEdges);




        return -1;
    }

    private static class Tile {
        private final int id;
        private final char[][] image;
        private final char[] topBorder;
        private final char[] rightBorder;
        private final char[] bottomBorder;
        private final char[] leftBorder;
        private final char[] reverseTopBorder;
        private final char[] reverseRightBorder;
        private final char[] reverseBottomBorder;
        private final char[] reverseLeftBorder;

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
            reverseTopBorder = reverseArray(topBorder);
            reverseRightBorder = reverseArray(rightBorder);
            reverseBottomBorder = reverseArray(bottomBorder);
            reverseLeftBorder = reverseArray(leftBorder);
        }

        public Edge getMatchingEdge(Tile tile) {
            if (borderMatches(tile, topBorder)) {
                return Edge.TOP;
            }
            if (borderMatches(tile, reverseTopBorder)) {
                return Edge.REVERSE_TOP;
            }
            if (borderMatches(tile, rightBorder)) {
                return Edge.RIGHT;
            }
            if (borderMatches(tile, reverseRightBorder)) {
                return Edge.REVERSE_RIGHT;
            }
            if (borderMatches(tile, bottomBorder)) {
                return Edge.BOTTOM;
            }
            if (borderMatches(tile, reverseBottomBorder)) {
                return Edge.REVERSE_BOTTOM;
            }
            if (borderMatches(tile, leftBorder)) {
                return Edge.LEFT;
            }
            if (borderMatches(tile, reverseLeftBorder)) {
                return Edge.REVERSE_LEFT;
            }

            return Edge.NONE;
        }

        public Edge findOtherMatchingBorder(Tile tile, Edge border) {
            return switch (border) {
                case TOP -> determineMatchingBorder(tile, topBorder);
                case RIGHT -> determineMatchingBorder(tile, rightBorder);
                case BOTTOM -> determineMatchingBorder(tile, bottomBorder);
                case LEFT -> determineMatchingBorder(tile, leftBorder);
                case REVERSE_TOP -> determineMatchingBorder(tile, reverseTopBorder);
                case REVERSE_RIGHT -> determineMatchingBorder(tile, reverseRightBorder);
                case REVERSE_BOTTOM -> determineMatchingBorder(tile, reverseBottomBorder);
                case REVERSE_LEFT -> determineMatchingBorder(tile, reverseLeftBorder);
                case NONE -> Edge.NONE;
            };
        }

        private Edge determineMatchingBorder(Tile tile, char[] border) {
            if (Arrays.equals(border, tile.topBorder)) {
                return Edge.TOP;
            }
            if (Arrays.equals(border, tile.reverseTopBorder)) {
                return Edge.REVERSE_TOP;
            }
            if (Arrays.equals(border, tile.rightBorder)) {
                return Edge.RIGHT;
            }
            if (Arrays.equals(border, tile.reverseRightBorder)) {
                return Edge.REVERSE_RIGHT;
            }
            if (Arrays.equals(border, tile.bottomBorder)) {
                return Edge.BOTTOM;
            }
            if (Arrays.equals(border, tile.reverseRightBorder)) {
                return Edge.REVERSE_BOTTOM;
            }
            if (Arrays.equals(border, tile.leftBorder)) {
                return Edge.LEFT;
            }
            if (Arrays.equals(border, tile.reverseLeftBorder)) {
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

        private char[] reverseArray(char[] array) {
            char[] newArray = new char[array.length];
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[array.length - 1 - i];
            }
            return newArray;
        }
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
