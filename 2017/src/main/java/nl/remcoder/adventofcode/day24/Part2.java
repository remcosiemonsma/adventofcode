package nl.remcoder.adventofcode.day24;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws Exception {
        List<Component> components = new ArrayList<>();

        Files.lines(Paths.get(ClassLoader.getSystemResource("day24/input").toURI())).forEach(s -> {
            Component component = new Component();

            for (String port : s.split("/")) {
                component.ports.add(Integer.parseInt(port));
            }

            components.add(component);
        });

        List<Component> componentsToRemove = new ArrayList<>();

        //Remove components with no possible connection
        outer: for (Component component1 : components) {
            for (Component component2 : components) {
                if (!component1.equals(component2)) {
                    for (int port : component1.ports) {
                        if (component2.ports.contains(port)) {
                            continue outer;
                        }
                    }
                }
            }
            componentsToRemove.add(component1);
        }

        components.removeAll(componentsToRemove);

        Bridge strongestBridge = new Bridge();

        for (Component component : components) {
            if (component.ports.contains(0)) {
                //starting component
                List<Component> newPossibleConnections = new ArrayList<>(components);
                newPossibleConnections.remove(component);
                for (int port : component.ports) {
                    if (port != 0) {
                        Bridge possibleBridge = buildBridge(port, newPossibleConnections);
                        possibleBridge.strength += port;
                        possibleBridge.length++;
                        if (possibleBridge.length > strongestBridge.length) {
                            strongestBridge = possibleBridge;
                        }  else if (possibleBridge.length == strongestBridge.length && possibleBridge.strength > strongestBridge.strength) {
                            strongestBridge = possibleBridge;
                        }
                    }
                }
            }
        }

        System.out.println(strongestBridge.strength);
    }

    private static Bridge buildBridge(int connectingComponent, List<Component> possibleConnections) {
        Bridge strongestBridge = new Bridge();

        for (Component component : possibleConnections) {
            if (component.ports.contains(connectingComponent)) {
                List<Component> newPossibleConnections = new ArrayList<>(possibleConnections);
                newPossibleConnections.remove(component);

                Bridge possibleBridge = new Bridge();

                boolean duplicate = false;
                for (int port : component.ports) {
                    if (duplicate || port != connectingComponent) {
                        possibleBridge = buildBridge(port, newPossibleConnections);
                    } else {
                        duplicate = true;
                    }
                }
                possibleBridge.strength += component.ports.get(0) + component.ports.get(1);
                possibleBridge.length++;
                if (possibleBridge.length > strongestBridge.length) {
                    strongestBridge = possibleBridge;
                } else if (possibleBridge.length == strongestBridge.length && possibleBridge.strength > strongestBridge.strength) {
                    strongestBridge = possibleBridge;
                }
            }
        }

        return strongestBridge;
    }

    static class Bridge {
        int strength;
        int length;
    }

    static class Component {
        List<Integer> ports = new ArrayList<>();

        @Override
        public String toString() {
            return "Component{" +
                    "ports=" + ports +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Component component = (Component) o;

            return ports != null ? ports.equals(component.ports) : component.ports == null;
        }

        @Override
        public int hashCode() {
            return ports != null ? ports.hashCode() : 0;
        }
    }
}
