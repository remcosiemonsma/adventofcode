package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day12 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = GridFactory.createTypedGridFromInput(input, GardenPlot::new);
        var regions = calculateRegions(grid);

        return regions.stream()
                      .mapToInt(this::calculateFenceCost)
                      .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = GridFactory.createTypedGridFromInput(input, GardenPlot::new);
        var regions = calculateRegions(grid);

        return regions.stream()
                      .mapToInt(this::calculateFenceCostPart2)
                      .sum();
    }

    private List<Set<Coordinate>> calculateRegions(Grid<GardenPlot> grid) {
        var regions = new ArrayList<Set<Coordinate>>();

        for (var y = grid.getStarty(); y <= grid.getEndy(); y++) {
            for (var x = grid.getStartx(); x <= grid.getEndx(); x++) {
                var coordinate = new Coordinate(x, y);
                var gardenPlot = grid.get(coordinate);
                if (!gardenPlot.isVisited()) {
                    var currentType = gardenPlot.getType();
                    gardenPlot.visit();
                    var currentRegion = new HashSet<Coordinate>();
                    currentRegion.add(coordinate);
                    var gardenPlotsToVisit = Set.of(coordinate);
                    while (!gardenPlotsToVisit.isEmpty()) {
                        gardenPlotsToVisit.stream()
                                          .map(grid::get)
                                          .forEach(GardenPlot::visit);
                        var nextGardenPlots = new HashSet<Coordinate>();
                        for (var plot : gardenPlotsToVisit) {
                            for (var possibleNextPlot : plot.getStraightNeighbours()) {
                                if (!grid.isCoordinateInGrid(possibleNextPlot)) {
                                    continue;
                                }
                                var nextPlot = grid.get(possibleNextPlot);
                                if (nextPlot.getType() == currentType && !nextPlot.isVisited()) {
                                    nextGardenPlots.add(possibleNextPlot);
                                    currentRegion.add(possibleNextPlot);
                                }
                            }
                        }
                        gardenPlotsToVisit = nextGardenPlots;
                    }
                    regions.add(currentRegion);
                }
            }
        }
        return regions;
    }

    private int calculateFenceCost(Set<Coordinate> region) {
        return region.size() * calculatePerimeter(region);
    }

    private int calculatePerimeter(Set<Coordinate> region) {
        var perimeter = 0;

        for (var plot : region) {
            int perimeterForPlot = (int) plot.getStraightNeighbours()
                                             .stream()
                                             .filter(Predicate.not(region::contains))
                                             .count();

            perimeter += perimeterForPlot;
        }

        return perimeter;
    }

    private int calculateFenceCostPart2(Set<Coordinate> region) {
        return region.size() * calculatePerimeterPart2(region);
    }

    private int calculatePerimeterPart2(Set<Coordinate> region) {
        var perimeter = 0;

        var startY = determineSmallestY(region);
        var endY = determineLargestY(region);
        var startX = determineSmallestX(region);
        var endX = determineLargestX(region);

        for (int y = startY; y <= endY; y++) {
            boolean perimeterAboveFound = false;
            boolean perimeterBelowFound = false;

            for (int x = startX; x <= endX; x++) {
                var plot = new Coordinate(x, y);

                if (region.contains(plot)) {
                    var above = plot.above();
                    var below = plot.below();

                    if (!region.contains(above)) {
                        if (!perimeterAboveFound) {
                            perimeter++;
                            perimeterAboveFound = true;
                        }
                    } else {
                        perimeterAboveFound = false;
                    }

                    if (!region.contains(below)) {
                        if (!perimeterBelowFound) {
                            perimeter++;
                            perimeterBelowFound = true;
                        }
                    } else {
                        perimeterBelowFound = false;
                    }
                } else {
                    perimeterAboveFound = false;
                    perimeterBelowFound = false;
                }
            }
        }

        for (int x = startX; x <= endX; x++) {
            boolean perimeterLeftFound = false;
            boolean perimeterRightFound = false;

            for (int y = startY; y <= endY; y++) {
                var plot = new Coordinate(x, y);

                if (region.contains(plot)) {
                    var left = plot.left();
                    var right = plot.right();

                    if (!region.contains(right)) {
                        if (!perimeterRightFound) {
                            perimeter++;
                            perimeterRightFound = true;
                        }
                    } else {
                        perimeterRightFound = false;
                    }

                    if (!region.contains(left)) {
                        if (!perimeterLeftFound) {
                            perimeter++;
                            perimeterLeftFound = true;
                        }
                    } else {
                        perimeterLeftFound = false;
                    }
                } else {
                    perimeterLeftFound = false;
                    perimeterRightFound = false;
                }
            }
        }

        return perimeter;
    }

    private int determineSmallestY(Set<Coordinate> region) {
        return region.stream()
                     .mapToInt(Coordinate::y)
                     .min()
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private int determineLargestY(Set<Coordinate> region) {
        return region.stream()
                     .mapToInt(Coordinate::y)
                     .max()
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private int determineSmallestX(Set<Coordinate> region) {
        return region.stream()
                     .mapToInt(Coordinate::x)
                     .min()
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private int determineLargestX(Set<Coordinate> region) {
        return region.stream()
                     .mapToInt(Coordinate::x)
                     .max()
                     .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private static class GardenPlot {
        private final char type;
        private boolean visited = false;

        private GardenPlot(int type) {
            this.type = (char) type;
        }

        public boolean isVisited() {
            return visited;
        }

        public void visit() {
            visited = true;
        }

        public char getType() {
            return type;
        }
    }
}
