package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day2 {
    public String handlePart1(Stream<String> input) {
        var digits = new char[][]{{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};

        var result = new StringBuilder();

        var position = new Position(1, 1);

        input.map(String::toCharArray)
             .forEach(steps -> {
                 for (char step : steps) {
                     switch (step) {
                         case 'U' -> {
                             position.posy--;
                             if (position.posy < 0) {
                                 position.posy = 0;
                             }
                         }
                         case 'R' -> {
                             position.posx++;
                             if (position.posx > 2) {
                                 position.posx = 2;
                             }
                         }
                         case 'D' -> {
                             position.posy++;
                             if (position.posy > 2) {
                                 position.posy = 2;
                             }
                         }
                         case 'L' -> {
                             position.posx--;
                             if (position.posx < 0) {
                                 position.posx = 0;
                             }
                         }
                         default -> throw new RuntimeException(step + " was invalid");
                     }
                 }
                 result.append(digits[position.posy][position.posx]);
             });

        return result.toString();
    }

    public String handlePart2(Stream<String> input) {
        var digits =
                new Character[][]{{null, null, '1', null, null},
                                  {null, '2', '3', '4', null},
                                  {'5', '6', '7', '8', '9'},
                                  {null, 'A', 'B', 'C', null},
                                  {null, null, 'D', null, null}};

        StringBuilder result = new StringBuilder();

        Position position = new Position(0, 2);

        input.map(String::toCharArray)
             .forEach(steps -> {
                 for (char step : steps) {
                     switch (step) {
                         case 'U' -> {
                             if (position.posy > 0 && digits[position.posy - 1][position.posx] != null) {
                                 position.posy--;
                             }
                         }
                         case 'R' -> {
                             if (position.posx < 4 && digits[position.posy][position.posx + 1] != null) {
                                 position.posx++;
                             }
                         }
                         case 'D' -> {
                             if (position.posy < 4 && digits[position.posy + 1][position.posx] != null) {
                                 position.posy++;
                             }
                         }
                         case 'L' -> {
                             if (position.posx > 0 && digits[position.posy][position.posx - 1] != null) {
                                 position.posx--;
                             }
                         }
                         default -> throw new RuntimeException(step + " was invalid");
                     }
                 }
                 result.append(digits[position.posy][position.posx]);
             });

        return result.toString();
    }

    private static class Position {
        int posx;
        int posy;

        public Position(int posx, int posy) {
            this.posx = posx;
            this.posy = posy;
        }
    }
}
