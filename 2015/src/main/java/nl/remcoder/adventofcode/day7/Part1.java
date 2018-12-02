package nl.remcoder.adventofcode.day7;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1 {

    public static final Map<String, Integer> WIRES = new HashMap<>();

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()));

        for (String line : lines) {
            line = line.replace(" ->", "");
            String[] parts = line.split("( )");

            if (parts[0].equals("NOT")) {
                int value = getValueFromWire(parts[1]);
                value = ~value;
                WIRES.put(parts[2], value);
            } else if (parts.length == 2) {
                WIRES.put(parts[1], getValueFromWire(parts[0]));
            } else {
                switch (parts[1]) {
                    case "AND":
                        int andvalue1 = getValueFromWire(parts[0]);
                        int andvalue2 = getValueFromWire(parts[2]);
                        int andresult = andvalue1 & andvalue2;
                        WIRES.put(parts[3], andresult);
                        break;
                    case "OR":
                        int orvalue1 = getValueFromWire(parts[0]);
                        int orvalue2 = getValueFromWire(parts[2]);
                        int orresult = orvalue1 | orvalue2;
                        WIRES.put(parts[3], orresult);
                        break;
                    case "LSHIFT":
                        int lshiftvalue1 = getValueFromWire(parts[0]);
                        int lshiftvalue2 = getValueFromWire(parts[2]);
                        int lshiftresult = lshiftvalue1 << lshiftvalue2;
                        WIRES.put(parts[3], lshiftresult);
                        break;
                    case "RSHIFT":
                        int rshiftvalue1 = getValueFromWire(parts[0]);
                        int rshiftvalue2 = getValueFromWire(parts[2]);
                        int rshiftresult = rshiftvalue1 >>> rshiftvalue2;
                        WIRES.put(parts[3], rshiftresult);
                        break;
                }
            }
        }

        System.out.println(WIRES.get("a"));
    }

    private static int getValueFromWire(String wire) {
        try {
            return Integer.parseInt(wire);
        } catch (NumberFormatException e) {
            if (!WIRES.containsKey(wire)) {
                WIRES.put(wire, 0);
            }
            return WIRES.get(wire);
        }
    }

    private static class Wire {
        private List<Connection> connections;
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            while (value < 0) {
                value += 65535;
            }

            while (value > 65535) {
                value -= 65535;
            }

            this.value = value;

            for (Connection connection : connections) {

            }
        }
    }

    private static class Connection {
        Wire wireDestination;
        String operation;
    }
}
