package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day24 implements AdventOfCodeSolution<Long> {
    private final Set<Set<Integer>> evaluatedLoadouts = new HashSet<>();

    @Override
    public Long handlePart1(Stream<String> input) {
        var packages = input.map(Integer::parseInt)
                            .collect(Collectors.toSet());

        var wantedWeight = packages.stream()
                                   .mapToInt(Integer::intValue)
                                   .reduce(Integer::sum)
                                   .orElseThrow(() -> new AssertionError("Iik!")) / 3;

        var largest = packages.stream()
                              .max(Integer::compareTo)
                              .orElseThrow(() -> new AssertionError("Aak!"));

        packages.remove(largest);

        var start = new LoadOut(packages, Set.of(largest), largest, 1, wantedWeight, largest);
        start.setDistance(largest);

        return Dijkstra.findShortestDistance(List.of(start), node -> {
            var loadOut = (LoadOut) node;
            return loadOut.getWeight() == wantedWeight;
        }).orElseThrow(() -> new AssertionError("Eek!")).getDistance();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var packages = input.map(Integer::parseInt)
                            .collect(Collectors.toSet());

        var wantedWeight = packages.stream()
                                   .mapToInt(Integer::intValue)
                                   .reduce(Integer::sum)
                                   .orElseThrow(() -> new AssertionError("Iik!")) / 4;

        var largest = packages.stream()
                              .max(Integer::compareTo)
                              .orElseThrow(() -> new AssertionError("Aak!"));

        packages.remove(largest);

        var start = new LoadOut(packages, Set.of(largest), largest, 1, wantedWeight, largest);
        start.setDistance(largest);

        return Dijkstra.findShortestDistance(List.of(start), node -> {
            var loadOut = (LoadOut) node;
            return loadOut.getWeight() == wantedWeight;
        }).orElseThrow(() -> new AssertionError("Eek!")).getDistance();
    }

    private class LoadOut extends Node<LoadOut> {
        private final Set<Integer> remainingPackages;
        private final Set<Integer> loadOut;
        private final long QE;
        private final int legroom;
        private final int wantedWeight;
        private final int weight;

        public LoadOut(Set<Integer> remainingPackages, Set<Integer> loadOut, long QE, int legroom, int wantedWeight,
                       int weight) {
            this.remainingPackages = remainingPackages;
            this.loadOut = loadOut;
            this.QE = QE;
            this.legroom = legroom;
            this.wantedWeight = wantedWeight;
            this.weight = weight;
        }

        @Override
        public Map<LoadOut, Long> getNeighbors() {
            var neighbors = new HashMap<LoadOut, Long>();

            for (var present : remainingPackages) {
                Set<Integer> newLoadout = new HashSet<>(loadOut);
                newLoadout.add(present);
                newLoadout = Set.copyOf(newLoadout);
                if (evaluatedLoadouts.add(newLoadout)) {
                    var newQE = determineQE(newLoadout);
                    var newWeight = determineWeight(newLoadout);
                    if (newWeight <= wantedWeight) {
                        Set<Integer> newRemainingpackages = new HashSet<>(remainingPackages);
                        newRemainingpackages.remove(present);
                        newRemainingpackages = Set.copyOf(newRemainingpackages);

                        long newDistance = newQE - QE;

                        var loadOut =
                                new LoadOut(newRemainingpackages, newLoadout, newQE, newLoadout.size(), wantedWeight,
                                            newWeight);

                        neighbors.put(loadOut, newDistance);
                    }
                }
            }


            return neighbors;
        }

        @Override
        public void printStateInformation() {
            System.out.println(this);
        }

        @Override
        public int compareTo(LoadOut o) {
            return Comparator.comparing(((LoadOut n) -> n.legroom))
                             .thenComparing(((LoadOut n) -> n.QE))
                             .compare(this, o);
        }

        @Override
        public String toString() {
            return "LoadOut{" +
                   "remainingPackages=" + remainingPackages +
                   ", loadOut=" + loadOut +
                   ", QE=" + QE +
                   ", legroom=" + legroom +
                   ", wantedWeight=" + wantedWeight +
                   ", weight=" + weight +
                   ", distance=" + getDistance() +
                   ", visited=" + isVisited() +
                   '}';
        }

        public int getWeight() {
            return weight;
        }

        private long determineQE(Set<Integer> loadout) {
            return loadout.stream()
                          .mapToLong(Integer::intValue)
                          .reduce((i1, i2) -> i1 * i2)
                          .orElseThrow(() -> new AssertionError("Ook!"));
        }

        private int determineWeight(Set<Integer> loadout) {
            return loadout.stream()
                          .mapToInt(Integer::intValue)
                          .reduce(Integer::sum)
                          .orElseThrow(() -> new AssertionError("Ook!"));
        }
    }
}
