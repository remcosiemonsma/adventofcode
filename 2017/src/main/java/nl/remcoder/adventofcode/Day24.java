package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day24 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var components = new ArrayList<>(input.map(this::parseComponent).toList());

        var componentsToRemove = new ArrayList<Component>();

        //Remove components with no possible connection
        outer: for (var component1 : components) {
            for (var component2 : components) {
                if (!component1.equals(component2)) {
                    for (var port : component1.ports) {
                        if (component2.ports.contains(port)) {
                            continue outer;
                        }
                    }
                }
            }
            componentsToRemove.add(component1);
        }

        components.removeAll(componentsToRemove);

        var highestScore = 0;

        for (var component : components) {
            if (component.ports.contains(0)) {
                //starting component
                var newPossibleConnections = new ArrayList<>(components);
                newPossibleConnections.remove(component);
                for (var port : component.ports) {
                    if (port != 0) {
                        var possibleHighScore = port + buildBridge1(port, newPossibleConnections);
                        if (possibleHighScore > highestScore) {
                            highestScore = possibleHighScore;
                        }
                    }
                }
            }
        }
        
        return highestScore;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var components = new ArrayList<>(input.map(this::parseComponent).toList());

        var componentsToRemove = new ArrayList<Component>();

        //Remove components with no possible connection
        outer: for (var component1 : components) {
            for (var component2 : components) {
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

        var strongestBridge = new Bridge();

        for (var component : components) {
            if (component.ports.contains(0)) {
                //starting component
                var newPossibleConnections = new ArrayList<>(components);
                newPossibleConnections.remove(component);
                for (int port : component.ports) {
                    if (port != 0) {
                        var possibleBridge = buildBridge2(port, newPossibleConnections);
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
        
        return strongestBridge.strength;
    }

    private Component parseComponent(String line) {
        var component = new Component();

        for (var port : line.split("/")) {
            component.ports.add(Integer.parseInt(port));
        }
        
        return component;
    }

    private Bridge buildBridge2(int connectingComponent, List<Component> possibleConnections) {
        var strongestBridge = new Bridge();

        for (var component : possibleConnections) {
            if (component.ports.contains(connectingComponent)) {
                var newPossibleConnections = new ArrayList<>(possibleConnections);
                newPossibleConnections.remove(component);

                var possibleBridge = new Bridge();

                boolean duplicate = false;
                for (int port : component.ports) {
                    if (duplicate || port != connectingComponent) {
                        possibleBridge = buildBridge2(port, newPossibleConnections);
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
    
    private int buildBridge1(int connectingComponent, List<Component> possibleConnections) {
        var highestScore = 0;

        for (var component : possibleConnections) {
            if (component.ports.contains(connectingComponent)) {
                var newPossibleConnections = new ArrayList<>(possibleConnections);
                newPossibleConnections.remove(component);

                int possibleHighScore = 0;

                var duplicate = false;
                for (var port : component.ports) {
                    if (duplicate || port != connectingComponent) {
                        possibleHighScore = buildBridge1(port, newPossibleConnections);
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

    private static class Bridge {
        int strength;
        int length;
    }
    
    private static class Component {
        private final List<Integer> ports = new ArrayList<>();

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

            var component = (Component) o;

            return ports.equals(component.ports);
        }

        @Override
        public int hashCode() {
            return ports.hashCode();
        }
    }
}
