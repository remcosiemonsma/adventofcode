package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day9 implements AdventOfCodeSolution<Long> {

    @Override
    public Long handlePart1(Stream<String> input) {
        return input.map(this::mapToCoordinate)
                    .gather(Gatherer.
                                    <Coordinate, List<Coordinate>, Long>
                                    ofSequential(ArrayList::new,
                                                 (state, element, downstream) -> {
                                                     for (var coordinate : state) {
                                                         var xdist = (long) Math.abs(element.x() - coordinate.x()) + 1;
                                                         var ydist = (long) Math.abs(element.y() - coordinate.y()) + 1;
                                                         downstream.push(xdist * ydist);
                                                     }
                                                     state.add(element);
                                                     return true;
                                                 }))
                    .max(Long::compareTo).orElseThrow(() -> new AssertionError("Eek!"));
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var points = input.map(this::mapToCoordinate)
                          .toList();

        var rectangles = IntStream.range(0, points.size() - 1)
                                  .boxed()
                                  .flatMap(i -> IntStream.range(i + 1, points.size())
                                                         .mapToObj(j -> Rectangle.fromCoords(points.get(i),
                                                                                             points.get(j))))
                                  .toList();
        var lines = IntStream.range(0, points.size())
                             .mapToObj(i -> Rectangle.fromCoords(points.get(i), points.get((i + 1) % points.size())))
                             .toList();

        return rectangles.stream()
                             .filter(r -> lines.stream().noneMatch(r::isOverlap))
                             .mapToLong(Rectangle::area)
                             .max()
                             .orElseThrow();
    }

    private Coordinate mapToCoordinate(String line) {
        var split = line.split(",");
        return new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    private record Rectangle(Coordinate min, Coordinate max) {

        boolean isOverlap(Rectangle other) {
            return min.x() < other.max.x() && max.x() > other.min.x() && min.y() < other.max.y() &&
                    max.y() > other.min.y();
        }

        long area() {
            return (max.x() - min.x() + 1L) * (max.y() - min.y() + 1L);
        }

        static Rectangle fromCoords(Coordinate a, Coordinate b) {
            return new Rectangle(new Coordinate(Math.min(a.x(), b.x()), Math.min(a.y(), b.y())),
                                 new Coordinate(Math.max(a.x(), b.x()), Math.max(a.y(), b.y())));
        }
    }
}
