package nl.remcoder.adventofcode;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5 {
    public int handlePart1(Stream<String> input) {
        return input.map(this::mapStringToSeat)
                    .mapToInt(Seat::getId)
                    .max()
                    .orElse(-1);
    }

    public int handlePart2(Stream<String> input) {
        List<Integer> expectedSeats = IntStream.rangeClosed(0, 127)
                                               .mapToObj(row -> IntStream.rangeClosed(0, 7)
                                                                         .map(column -> (row * 8) + column))
                                               .flatMap(IntStream::boxed)
                                               .collect(Collectors.toList());

        input.map(this::mapStringToSeat)
             .map(Seat::getId)
             .forEach(expectedSeats::remove);

        return expectedSeats.stream()
                            .filter(seat -> !expectedSeats.contains(seat - 1) && !expectedSeats.contains(seat + 1))
                            .findAny()
                            .orElse(-1);
    }

    private Seat mapStringToSeat(String s) {
        char[] data = s.toCharArray();

        int row = 0;

        for (int i = 0; i < 7; i++) {
            if (data[i] == 'B') {
                int nibble = 1 << 6 - i;

                row |= nibble;
            }
        }

        int column = 0;

        for (int i = 0; i < 3; i++) {
            if (data[i + 7] == 'R') {
                int nibble = 1 << 2 - i;

                column |= nibble;
            }
        }

        int id = (row * 8) + column;

        return new Seat(row, column, id);
    }

    private static class Seat {
        final int row;
        final int column;
        final int id;

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public int getId() {
            return id;
        }

        private Seat(int row, int column, int id) {
            this.row = row;
            this.column = column;
            this.id = id;
        }
    }
}
