package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day23 implements BiAdventOfCodeSolution<Integer, String> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var computerMap = new HashMap<String, Computer>();

        input.forEach(s -> {
            var split = s.split("-");
            var first = computerMap.computeIfAbsent(split[0], Computer::new);
            var second = computerMap.computeIfAbsent(split[1], Computer::new);
            first.addConnection(second);
            second.addConnection(first);
        });

        var networks = new HashSet<Set<Computer>>();

        computerMap.values()
                   .forEach(computer -> networks.addAll(computer.createNetworks()));

        return (int) networks.stream()
                             .filter(computers -> computers.stream()
                                                           .anyMatch(computer -> computer.id().charAt(0) == 't'))
                             .count();
    }

    @Override
    public String handlePart2(Stream<String> input) {
        var computerMap = new HashMap<String, Computer>();

        input.forEach(s -> {
            var split = s.split("-");
            var first = computerMap.computeIfAbsent(split[0], Computer::new);
            var second = computerMap.computeIfAbsent(split[1], Computer::new);
            first.addConnection(second);
            second.addConnection(first);
        });

        var lanparties = new HashSet<Set<Computer>>();

        var computers = new HashSet<>(computerMap.values());

        bronKerbosch(new HashSet<>(), computers, new HashSet<>(), lanparties);

        return lanparties.stream()
                         .max(Comparator.comparingInt(Set::size))
                         .map(this::createPassword)
                         .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private String createPassword(Set<Computer> lanParty) {
        return lanParty.stream()
                       .map(Computer::id)
                       .sorted()
                       .collect(Collectors.joining(","));
    }

    private void bronKerbosch(Set<Computer> currentClique, Set<Computer> nodes, Set<Computer> visitedNodes,
                              Set<Set<Computer>> lanparties) {
        if (nodes.isEmpty() && visitedNodes.isEmpty()) {
            lanparties.add(currentClique);
            return;
        }

        var nodesCopy = new HashSet<>(nodes);
        for (var computer : nodesCopy) {
            var newClique = new HashSet<>(currentClique);
            newClique.add(computer);

            var newNodes = new HashSet<>(nodes);
            newNodes.retainAll(computer.connections());

            var newVisited = new HashSet<>(visitedNodes);
            newVisited.retainAll(computer.connections());

            bronKerbosch(newClique, newNodes, newVisited, lanparties);

            nodes.remove(computer);
            visitedNodes.add(computer);
        }
    }

    private static class Computer {
        private final String id;
        private final Set<Computer> connections = new HashSet<>();

        private Computer(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }

        public void addConnection(Computer computer) {
            connections.add(computer);
        }

        public Set<Computer> connections() {
            return connections;
        }

        public Set<Set<Computer>> createNetworks() {
            var networks = new HashSet<Set<Computer>>();
            for (var firstHop : connections) {
                for (var secondHop : firstHop.connections) {
                    if (secondHop.connections.contains(this)) {
                        networks.add(Set.of(this, firstHop, secondHop));
                    }
                }
            }
            return networks;
        }

        @Override
        public String toString() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Computer computer = (Computer) o;
            return Objects.equals(id, computer.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }
}
