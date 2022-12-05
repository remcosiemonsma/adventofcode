package nl.remcoder.adventofcode;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class Day3 {
    public int handlePart1(Stream<String> input) {
        var line = input.findFirst()
                           .orElseThrow(() -> new AssertionError("Eek!"));

        var positions = new HashSet<>();

        var position = new Position(0, 0);

        positions.add(position);

        for (var c : line.toCharArray()) {
            position = move(position, c);

            positions.add(position);
        }
        
        return positions.size();
    }

    public int handlePart2(Stream<String> input) {
        var line = input.findFirst()
                           .orElseThrow(() -> new AssertionError("Eek!"));

        var positions = new HashSet<>();

        var positionSanta = new Position(0, 0);
        var positionRoboSanta = new Position(0, 0);

        positions.add(positionSanta);

        var charArray = line.toCharArray();
        for (var i = 0; i < charArray.length; i++) {
            var c = charArray[i];

            if (i % 2 == 0) {
                positionSanta = move(positionSanta, c);

                positions.add(positionSanta);
            } else {
                positionRoboSanta = move(positionRoboSanta, c);

                positions.add(positionRoboSanta);
            }

        }
        
        return positions.size();
    }

    private Position move(Position position, char c) {
        switch (c) {
            case '^' -> position = new Position(position.x + 1, position.y);
            case 'v' -> position = new Position(position.x - 1, position.y);
            case '>' -> position = new Position(position.x, position.y + 1);
            case '<' -> position = new Position(position.x, position.y - 1);
        }
        return position;
    }

    private record Position(int x, int y) {}
}
