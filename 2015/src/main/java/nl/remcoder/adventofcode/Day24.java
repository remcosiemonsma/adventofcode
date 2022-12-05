package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day24 {
    private Set<Set<Integer>> evaluatedLoadouts = new HashSet<>();

    public long handlePart1(Stream<String> input) {
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

        return Dijkstra.findShortestDistance(start, node -> {
            var loadOut = (LoadOut) node;
            return loadOut.getWeight() == wantedWeight;
        });
    }

    public long handlePart2(Stream<String> input) {
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

        return Dijkstra.findShortestDistance(start, node -> {
            var loadOut = (LoadOut) node;
            return loadOut.getWeight() == wantedWeight;
        });
    }

    private class LoadOut implements Node {
        private final Set<Integer> remainingPackages;
        private final Set<Integer> loadOut;
        private final long QE;
        private final int legroom;
        private final int wantedWeight;
        private final int weight;
        private long distance = Long.MAX_VALUE;
        private boolean visited;

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
        public Map<? extends Node, Long> getNeighbors() {
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
        public long getDistance() {
            return distance;
        }

        @Override
        public boolean isVisited() {
            return visited;
        }

        @Override
        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        @Override
        public void setDistance(long distance) {
            this.distance = distance;
        }

        @Override
        public void printStateInformation() {
            System.out.println(this);
        }

        @Override
        public int compareTo(Node o) {
            if (o instanceof LoadOut other) {
                return Comparator.comparing(((LoadOut n) -> n.legroom))
                                 .thenComparing(((LoadOut n) -> n.QE))
                                 .compare(this, other);
            } else {
                throw new AssertionError("Eek!");
            }
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
                   ", distance=" + distance +
                   ", visited=" + visited +
                   '}';
        }

        public long getQE() {
            return QE;
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
