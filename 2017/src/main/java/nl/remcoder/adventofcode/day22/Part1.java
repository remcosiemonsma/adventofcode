package nl.remcoder.adventofcode.day22;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws Exception {
        List<List<Boolean>> data = new ArrayList<>();

        Files.lines(Paths.get(ClassLoader.getSystemResource("day22/input").toURI())).forEach(s -> {
            List<Boolean> row = new ArrayList<>();

            for (char c : s.toCharArray()) {
                row.add(c == '#');
            }

            data.add(row);
        });

        boolean[][] grid = new boolean[data.size()][data.size()];

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.size(); j++) {
                grid[i][j] = data.get(i).get(j);
            }
        }

        int posx = (int)Math.ceil(grid.length / 2d) - 1;
        int posy = (int)Math.ceil(grid.length / 2d) - 1;

        Direction direction = Direction.NORTH;

        int infections = 0;

        for (int counter = 0; counter < 10000; counter++) {
            if (grid[posx][posy]) {
                switch (direction) {
                    case NORTH:
                        direction = Direction.EAST;
                        break;
                    case EAST:
                        direction = Direction.SOUTH;
                        break;
                    case SOUTH:
                        direction = Direction.WEST;
                        break;
                    case WEST:
                        direction = Direction.NORTH;
                        break;
                }
                grid[posx][posy] = false;
            } else {
                switch (direction) {
                    case NORTH:
                        direction = Direction.WEST;
                        break;
                    case EAST:
                        direction = Direction.NORTH;
                        break;
                    case SOUTH:
                        direction = Direction.EAST;
                        break;
                    case WEST:
                        direction = Direction.SOUTH;
                        break;
                }
                grid[posx][posy] = true;
                infections++;
            }
            switch (direction) {
                case NORTH:
                    posx--;
                    break;
                case SOUTH:
                    posx++;
                    break;
                case EAST:
                    posy++;
                    break;
                case WEST:
                    posy--;
                    break;
            }
            if (0 > posx || posx >= grid.length) {
                grid = enlargeGrid(grid);

                posx++;
                posy++;
            }
            if (0 > posy || posy >= grid.length) {
                grid = enlargeGrid(grid);

                posy++;
                posx++;
            }
        }

        System.out.println(infections);
    }

    private static boolean[][] enlargeGrid(boolean[][] grid) {
        boolean[][] newgrid = new boolean[grid.length + 2][grid.length + 2];

        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, newgrid[i + 1], 1, grid.length);
        }

        grid = newgrid;
        return grid;
    }

    enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }
}
