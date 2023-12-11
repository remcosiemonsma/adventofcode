package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day11 implements AdventOfCodeSolution<Long> {

    private int expansion;

    public void setExpansion(int expansion) {
        this.expansion = expansion - 1;
    }

    @Override
    public Long handlePart1(Stream<String> input) {
        Character[][] image = input.map(s -> s.chars()
                                              .mapToObj(c -> (char) c)
                                              .toArray(Character[]::new))
                                   .toArray(Character[][]::new);

        for (int y = 0; y < image.length; y++) {
            var allEmpty = true;
            var line = image[y];
            for (char c : line) {
                if (c != '.') {
                    allEmpty = false;
                    break;
                }
            }
            if (allEmpty) {
                var newImage = new Character[image.length + 1][image[0].length];
                System.arraycopy(image, 0, newImage, 0, y + 1);
                System.arraycopy(image, y, newImage, y + 1, image.length - y);
                Character[] newLine = new Character[line.length];
                Arrays.fill(newLine, '.');
                newImage[y + 1] = newLine;
                image = newImage;
                y++;
            }
        }

        for (var x = 0; x < image[0].length; x++) {
            var allEmpty = true;
            for (Character[] characters : image) {
                if (characters[x] != '.') {
                    allEmpty = false;
                    break;
                }
            }
            if (allEmpty) {
                var newImage = new Character[image.length][image[0].length + 1];
                for (var y = 0; y < image.length; y++) {
                    var newLine = new Character[newImage[0].length];
                    newLine[x] = '.';
                    System.arraycopy(image[y], 0, newLine, 0, x + 1);
                    System.arraycopy(image[y], x, newLine, x + 1, image[y].length - x);
                    newImage[y] = newLine;
                }
                image = newImage;
                x++;
            }
        }

        var grid = new Grid<>(image);

        var points = grid.findValues('#');

        return points.stream()
                     .mapToLong(point -> getDistanceToOtherPoints(point, points))
                     .sum() / 2;
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var points = new ArrayList<>(grid.findValues('#'));

        var maxY = grid.getEndy();
        var maxX = grid.getEndx();

        for (var y = 0L; y < maxY; y++) {
            var finalY = y;
            if (points.stream().noneMatch(coordinate -> coordinate.y() == finalY)) {
                for (var i = 0; i < points.size(); i++) {
                    var point = points.get(i);
                    if (point.y() > y) {
                        points.set(i, new Coordinate(point.x(), point.y() + expansion));
                    }
                }
                y += expansion;
                maxY += expansion;
            }
        }

        for (var x = 0L; x < maxX; x++) {
            var finalX = x;
            if (points.stream().noneMatch(coordinate -> coordinate.x() == finalX)) {
                for (var i = 0; i < points.size(); i++) {
                    var point = points.get(i);
                    if (point.x() > x) {
                        points.set(i, new Coordinate(point.x() + expansion, point.y()));
                    }
                }
                x += expansion;
                maxX += expansion;
            }
        }

        return points.stream()
                     .mapToLong(point -> getDistanceToOtherPoints(point, points))
                     .sum() / 2;
    }

    private long getDistanceToOtherPoints(Coordinate point, List<Coordinate> otherPoints) {
        return otherPoints.stream()
                          .mapToLong(point::getDistanceTo)
                          .sum();
    }
}
