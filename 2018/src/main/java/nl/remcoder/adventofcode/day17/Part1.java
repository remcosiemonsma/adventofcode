package nl.remcoder.adventofcode.day17;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part1 {
    public static void main(String[] args) throws Exception {
        int smallestx = Integer.MAX_VALUE;
        int largestx = Integer.MIN_VALUE;
        int smallesty = Integer.MAX_VALUE;
        int largesty = Integer.MIN_VALUE;

        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day17/input").toURI()));

        for (String line : lines) {
            String[] split = line.split(", ");

            boolean startingWithX = split[0].startsWith("x");
            if (startingWithX) {
                int x = Integer.parseInt(split[0].substring(2));

                if (x > largestx) {
                    largestx = x;
                }
                if (x < smallestx) {
                    smallestx = x;
                }
            } else {
                int y = Integer.parseInt(split[0].substring(2));

                if (y > largesty) {
                    largesty = y;
                }
                if (y < smallesty) {
                    smallesty = y;
                }
            }

            String part2 = split[1].substring(2);

            split = part2.split("\\.\\.");

            if (startingWithX) {
                int y1 = Integer.parseInt(split[0]);
                int y2 = Integer.parseInt(split[1]);

                if (y1 > largesty) {
                    largesty = y1;
                }
                if (y2 > largesty) {
                    largesty = y2;
                }
                if (y1 < smallesty) {
                    smallesty = y1;
                }
                if (y2 < smallesty) {
                    smallesty = y2;
                }
            } else {
                int x1 = Integer.parseInt(split[0]);
                int x2 = Integer.parseInt(split[1]);

                if (x1 > largestx) {
                    largestx = x1;
                }
                if (x2 > largestx) {
                    largestx = x2;
                }
                if (x1 < smallestx) {
                    smallestx = x1;
                }
                if (x2 < smallestx) {
                    smallestx = x2;
                }
            }
        }

        System.out.println(smallesty);

        char[][] grid = new char[largesty + 1][700];

        for (char[] line : grid) {
            Arrays.fill(line, '.');
        }

        grid[0][500] = '@';

        for (String line : lines) {
            String[] split = line.split(", ");
            String part2 = split[1].substring(2);
            String[] split2 = part2.split("\\.\\.");

            boolean vertical = split[0].startsWith("x");

            if (vertical) {
                int x = Integer.parseInt(split[0].substring(2));

                int ystart = Integer.parseInt(split2[0]);
                int yend = Integer.parseInt(split2[1]);

                for (int y = ystart; y <= yend; y++) {
                    grid[y][x] = '#';
                }
            } else {
                int y = Integer.parseInt(split[0].substring(2));

                int xstart = Integer.parseInt(split2[0]);
                int xend = Integer.parseInt(split2[1]);

                for (int x = xstart; x <= xend; x++) {
                    grid[y][x] = '#';
                }
            }
        }

        Set<WaterSource> waterSources = new HashSet<>();

        waterSources.add(new WaterSource(500, 0));

        while (waterSources.size() > 0) {
            Set<WaterSource> newWaterSources = new HashSet<>();

            for (WaterSource waterSource : waterSources) {
                boolean bottomfound = false;

                int y = waterSource.y;

                while (!bottomfound && y < largesty) {
                    y++;

                    if (grid[y][waterSource.x] == '#') {
                        bottomfound = true;
                    } else {
                        grid[y][waterSource.x] = '|';
                    }
                }

                if (bottomfound) {
                    boolean overFlow = false;

                    while (!overFlow) {
                        y--;

                        for (int x = waterSource.x; x > 0; x--) {
                            if (grid[y][x] == '#') {
                                break;
                            } else if (grid[y + 1][x - 1] == '.') {
                                overFlow = true;
                                if (grid[y + 1][x] == '#') {
                                    newWaterSources.add(new WaterSource(x - 1, y));
                                    grid[y][x] = '~';
                                    grid[y][x - 1] = '+';
                                    break;
                                }
                            } else {
                                grid[y][x] = '~';
                            }
                        }

                        for (int x = waterSource.x; x < grid[y].length - 1; x++) {
                            if (grid[y][x] == '#') {
                                break;
                            } else if (grid[y + 1][x + 1] == '.') {
                                overFlow = true;
                                if (grid[y + 1][x] == '#') {
                                    newWaterSources.add(new WaterSource(x + 1, y));
                                    grid[y][x] = '~';
                                    grid[y][x + 1] = '+';
                                    break;
                                }
                            } else {
                                grid[y][x] = '~';
                            }
                        }
                    }
                }
            }

            newWaterSources.removeAll(waterSources);

            waterSources.clear();

            waterSources.addAll(newWaterSources);

            System.out.println(waterSources);
        }

        for (int i = 0; i < grid.length; i++) {
            System.out.print(String.format("%04d:", i));
            char[] line = grid[i];
            for (char pixel : line) {
                System.out.print(pixel);
            }
            System.out.println();
        }

        int wateramount = 0;

        for (int i = smallesty; i < grid.length; i++) {
            char[] line = grid[i];
            for (char pixel : line) {
                switch (pixel) {
                    case '|':
                        wateramount++;
                        break;
                    case '~':
                        wateramount++;
                        break;
                    case '+':
                        wateramount++;
                        break;
                }
            }
        }

        System.out.println(wateramount);
    }

    private static class WaterSource {
        final int x;
        final int y;

        private WaterSource(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "WaterSource{" +
                   "x=" + x +
                   ", y=" + y +
                   '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof WaterSource)) {
                return false;
            }

            WaterSource that = (WaterSource) o;

            if (x != that.x) {
                return false;
            }
            return y == that.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
