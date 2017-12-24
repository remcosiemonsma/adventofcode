package nl.remcoder.adventofcode.day24;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part1 {
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

        int highestScore = 0;

        for (Component component : components) {
            if (component.ports.contains(0)) {
                //starting component
                List<Component> newPossibleConnections = new ArrayList<>(components);
                newPossibleConnections.remove(component);
                int possibleHighScore;
                for (int port : component.ports) {
                    if (port != 0) {
                        possibleHighScore = port + buildBridge(port, newPossibleConnections);
                        if (possibleHighScore > highestScore) {
                            highestScore = possibleHighScore;
                        }
                    }
                }
            }
        }

        System.out.println(highestScore);
    }

    private static int buildBridge(int connectingComponent, List<Component> possibleConnections) {
        int highestScore = 0;

        for (Component component : possibleConnections) {
            if (component.ports.contains(connectingComponent)) {
                List<Component> newPossibleConnections = new ArrayList<>(possibleConnections);
                newPossibleConnections.remove(component);

                int possibleHighScore = 0;

                boolean duplicate = false;
                for (int port : component.ports) {
                    if (duplicate || port != connectingComponent) {
                        possibleHighScore = buildBridge(port, newPossibleConnections);
                    } else {
                        duplicate = true;
                    }
                }
                possibleHighScore += component.ports.get(0) + component.ports.get(1);
                if (possibleHighScore > highestScore) {
                    highestScore = possibleHighScore;
                }
            }
        }

        return highestScore;
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
