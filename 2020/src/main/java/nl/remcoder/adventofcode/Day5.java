package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.map(this::mapStringToSeat)
                    .mapToInt(Seat::id)
                    .max()
                    .orElse(-1);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var expectedSeats = IntStream.rangeClosed(0, 127)
                                     .mapToObj(row -> IntStream.rangeClosed(0, 7)
                                                               .map(column -> (row * 8) + column))
                                     .flatMap(IntStream::boxed)
                                     .collect(Collectors.toList());

        input.map(this::mapStringToSeat)
             .map(Seat::id)
             .forEach(expectedSeats::remove);

        return expectedSeats.stream()
                            .filter(seat -> !expectedSeats.contains(seat - 1) && !expectedSeats.contains(seat + 1))
                            .findAny()
                            .orElse(-1);
    }

    private Seat mapStringToSeat(String s) {
        var data = s.toCharArray();

        var row = 0;

        for (var i = 0; i < 7; i++) {
            if (data[i] == 'B') {
                var nibble = 1 << 6 - i;

                row |= nibble;
            }
        }

        var column = 0;

        for (var i = 0; i < 3; i++) {
            if (data[i + 7] == 'R') {
                var nibble = 1 << 2 - i;

                column |= nibble;
            }
        }

        var id = (row * 8) + column;

        return new Seat(row, column, id);
    }

    private record Seat(int row, int column, int id) {
    }
}
