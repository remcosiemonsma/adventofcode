package nl.remcoder.adventofcode.day11;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Part2 {
    public static void main(String[] args) throws Exception {
        int gridSerialNumber = Integer.parseInt(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day11/input").toURI())).findFirst().get());

        int[][] grid = new int[300][300];

        for (int y = 0; y < 300; y++) {
            for (int x = 0; x < 300; x++) {
                int xcoord = x + 1;
                int ycoord = y + 1;

                int rackId = xcoord + 10;
                int beginningpowerlevel = rackId * ycoord;
                int increasedpowerlevel = beginningpowerlevel + gridSerialNumber;
                int multipliedpowerelevel = increasedpowerlevel * rackId;

                int hundredsdigit = (int) Math.floor((multipliedpowerelevel % 1000) / 100d);

                int subtractedpowerlevel = hundredsdigit - 5;

                grid[y][x] = subtractedpowerlevel;
            }
        }

        int previoushighestpowerlevel = Integer.MIN_VALUE;

        int highestX = 0;
        int highestY = 0;
        int size = 0;

        for (int s = 0; s < 300; s++) {
            for (int y = 0; y < 300 - s; y++) {
                for (int x = 0; x < 300 - s; x++) {

                    int powerlevel = 0;

                    for (int i = 0; i < s; i++) {
                        for (int j = 0; j < s; j++) {
                            powerlevel += grid[y + i][x + j];
                        }
                    }

                    if (powerlevel > previoushighestpowerlevel) {
                        previoushighestpowerlevel = powerlevel;
                        highestX = x + 1;
                        highestY = y + 1;
                        size = s;
                    }
                }

            }
            System.out.println(s);
        }

        System.out.println(highestX + "," + highestY + "," + size);
    }
}
