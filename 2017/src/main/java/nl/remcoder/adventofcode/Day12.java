package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var connectionMap = input.map(this::parseConnection)
                                 .collect(Collectors.toMap(Connection::getId, connection -> connection));

        return countConnections(connectionMap, 0);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var connectionMap = input.map(this::parseConnection)
                                 .collect(Collectors.toMap(Connection::getId, connection -> connection));

        int groups = 0;

        for (int i = 0; i < connectionMap.size(); i++) {
            if (!connectionMap.get(i).isVisited()) {
                countConnections(connectionMap, i);
                groups++;
            }
        }
        
        return groups;
    }

    private static int countConnections(Map<Integer, Connection> connectionMap, int position) {
        var connection = connectionMap.get(position);

        var connections = 1;

        if (!connection.isVisited()) {
            connection.setVisited(true);

            for (var conn : connection.getConnections()) {
                connections += countConnections(connectionMap, conn);
            }
            return connections;
        }

        return 0;
    }

    private Connection parseConnection(String line) {
        String[] data = line.split(" <-> ");
        int id = Integer.parseInt(data[0]);
        var connections = Arrays.stream(data[1].split(", "))
                                .mapToInt(Integer::parseInt)
                                .toArray();

        return new Connection(id, connections, false);
    }

    private static class Connection {
        private final int id;
        private final int[] connections;
        private boolean visited;

        private Connection(int id, int[] connections, boolean visited) {
            this.id = id;
            this.connections = connections;
            this.visited = visited;
        }

        public int getId() {
            return id;
        }

        public int[] getConnections() {
            return connections;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        @Override
        public String toString() {
            return "Connection{" +
                   "id=" + id +
                   ", connections=" + Arrays.toString(connections) +
                   ", visited=" + visited +
                   '}';
        }
    }
}
