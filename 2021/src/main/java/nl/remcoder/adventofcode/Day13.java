package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;
import nl.remcoder.adventofcode.library.drawing.Screen;
import nl.remcoder.adventofcode.library.model.Coordinate;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13 implements BiAdventOfCodeSolution<Integer, String> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var foldInstructions = new ArrayList<String>();
        var coordinates = new HashSet<Coordinate>();

        input.filter(Predicate.not(String::isBlank))
             .forEach(s -> {
                 if (s.startsWith("fold along")) {
                     foldInstructions.add(s);
                 } else {
                     String[] split = s.split(",");

                     coordinates.add(new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
                 }
             });

        switch (foldInstructions.getFirst().charAt(11)) {
            case 'x' -> foldVertical(coordinates, Integer.parseInt(foldInstructions.getFirst().substring(13)));
            case 'y' -> foldHorizontal(coordinates, Integer.parseInt(foldInstructions.getFirst().substring(13)));
        }

        return coordinates.size();
    }

    @Override
    public String handlePart2(Stream<String> input) {
        var foldInstructions = new ArrayList<String>();
        var coordinates = new HashSet<Coordinate>();

        input.filter(Predicate.not(String::isBlank))
             .forEach(s -> {
                 if (s.startsWith("fold along")) {
                     foldInstructions.add(s);
                 } else {
                     String[] split = s.split(",");

                     coordinates.add(new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
                 }
             });

        foldInstructions.forEach(instruction -> {
            switch (instruction.charAt(11)) {
                case 'x' -> foldVertical(coordinates, Integer.parseInt(instruction.substring(13)));
                case 'y' -> foldHorizontal(coordinates, Integer.parseInt(instruction.substring(13)));
            }
        });

        var highestY = coordinates.stream()
                                  .mapToLong(Coordinate::y)
                                  .max()
                                  .orElseThrow(() -> new AssertionError("Eek!"));
        var highestX = coordinates.stream()
                                  .mapToLong(Coordinate::x)
                                  .max()
                                  .orElseThrow(() -> new AssertionError("Eek!"));

        var screen = new Screen((int) highestX + 2, (int) highestY + 1);

        coordinates.forEach(coordinate -> screen.drawPixel((int) coordinate.x(), (int) coordinate.y()));

        return screen.readScreen();
    }

    private void foldHorizontal(Set<Coordinate> coordinates, int foldY) {
        var coordinatesToRemove = new HashSet<Coordinate>();
        var coordinatesToAdd = coordinates.stream()
                                          .filter(coordinate -> coordinate.y() > foldY)
                                          .peek(coordinatesToRemove::add)
                                          .map(coordinate -> new Coordinate(coordinate.x(),
                                                                            foldY - (coordinate.y() - foldY)))
                                          .collect(Collectors.toSet());

        coordinates.removeAll(coordinatesToRemove);
        coordinates.addAll(coordinatesToAdd);
    }

    private void foldVertical(Set<Coordinate> coordinates, int foldX) {
        var coordinatesToRemove = new HashSet<Coordinate>();
        var coordinatesToAdd = coordinates.stream()
                                          .filter(coordinate -> coordinate.x() > foldX)
                                          .peek(coordinatesToRemove::add)
                                          .map(coordinate -> new Coordinate(
                                                  foldX - (coordinate.x() - foldX), coordinate.y()))
                                          .collect(Collectors.toSet());

        coordinates.removeAll(coordinatesToRemove);
        coordinates.addAll(coordinatesToAdd);
    }
}
