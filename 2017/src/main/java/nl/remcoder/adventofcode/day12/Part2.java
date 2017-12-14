package nl.remcoder.adventofcode.day12;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Part2 {
    public static void main(String[] args) throws Exception {
        Map<Integer, Connection> connectionMap = new HashMap<>();

        Files.lines(Paths.get(ClassLoader.getSystemResource("day12/input").toURI())).forEach(s -> {
            String[] data = s.split(" <-> ");
            int id = Integer.parseInt(data[0]);
            String[] conns = data[1].split(", ");
            int[] connections = new int[conns.length];
            for (int i = 0; i < conns.length; i++) {
                connections[i] = Integer.parseInt(conns[i]);
            }

            Connection connection = new Connection();
            connection.id = id;
            connection.connections = connections;
            connection.visited = false;

            connectionMap.put(id, connection);
        });

        int groups = 0;

        for (int i = 0; i < connectionMap.size(); i++) {
            if (!connectionMap.get(i).visited) {
                countConnections(connectionMap, i);
                groups++;
            }
        }

        System.out.println(groups);
    }

    private static int countConnections(Map<Integer, Connection> connectionMap, int position) {
        Connection connection = connectionMap.get(position);

        int connections = 1;

        if (!connection.visited) {
            connection.visited = true;

            for (int conn : connection.connections) {
                connections += countConnections(connectionMap, conn);
            }
            return connections;
        }

        return 0;
    }

    private static class Connection {
        private int id;
        private int[] connections;
        private boolean visited;

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
