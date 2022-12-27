package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.*;
import java.util.stream.Stream;

public class Day7 implements BiAdventOfCodeSolution<String, Integer> {
    @Override
    public String handlePart1(Stream<String> input) {
        var programs = new HashSet<Program>();


        input.forEach(s -> {
            var program = parseProgram(s);
            parseChildren(programs, program);
            if (!findParent(programs, program)) {
                programs.add(program);
            }
        });

        return programs.stream().findFirst().orElseThrow(() -> new AssertionError("Eek!")).name;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var programs = new HashSet<Program>();


        input.forEach(s -> {
            var program = parseProgram(s);
            parseChildren(programs, program);
            if (!findParent(programs, program)) {
                programs.add(program);
            }
        });
        
        var root = programs.stream().findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        for (var child : root.children) {
            child.totalWeight = calculateWeight(child.children) + child.weight;
        }

        return findImbalance(root, 0);
    }

    private int findImbalance(Program program, int difference) {
        var weightOccurrences = new HashMap<Integer, Integer>();

        for (var child : program.children) {
            if (weightOccurrences.containsKey(child.totalWeight)) {
                weightOccurrences.put(child.totalWeight, weightOccurrences.get(child.totalWeight) + 1);
            } else {
                weightOccurrences.put(child.totalWeight, 1);
            }
        }

        if (weightOccurrences.size() == 1) {
            return program.weight + difference;
        } else {
            var incorrectWeight = 0;
            var correctWeight = 0;

            for (var weight : weightOccurrences.keySet()) {
                if (weightOccurrences.get(weight).equals(1)) {
                    incorrectWeight = weight;
                } else {
                    correctWeight = weight;
                }
            }

            int determinedDifference = correctWeight - incorrectWeight;

            for (var child : program.children) {
                if (child.totalWeight == incorrectWeight) {
                    var imbalance = findImbalance(child, determinedDifference);
                    if (imbalance != -1) {
                        return imbalance;
                    }
                }
            }
        }
        return -1;
    }

    private static int calculateWeight(Set<Program> programs) {
        var weight = 0;

        for (var program : programs) {
            var programWeight = calculateWeight(program.children) + program.weight;
            program.totalWeight = programWeight;
            weight += programWeight;
        }

        return weight;
    }

    private static boolean findParent(Set<Program> possibleParents, Program orphan) {
        for (var possibleParent : possibleParents) {
            if (possibleParent.children.contains(orphan)) {
                possibleParent.children.remove(orphan);
                possibleParent.children.add(orphan);
                return true;
            }
            for (var child : possibleParent.children) {
                if (child.children.contains(orphan)) {
                    child.children.remove(orphan);
                    child.children.add(orphan);
                    return true;
                } else if (!child.children.isEmpty()) {
                    if (findParent(child.children, orphan)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private Program parseProgram(String s) {
        var program = new Program();

        var data = s.split(" ");

        program.name = data[0];
        program.weight = Integer.parseInt(data[1].replaceAll("[()]", ""));

        if (s.contains("->")) {
            var children = s.split("-> ")[1].split(", ");

            for (var childName : children) {
                Program child = new Program();
                child.name = childName;
                program.children.add(child);
            }
        }

        return program;
    }

    private void parseChildren(Set<Program> possibleChildren, Program parent) {
        var children = parent.children;

        var foundChildren = new ArrayList<Program>();
        for (Program child : children) {
            for (Program possibleChild : possibleChildren) {
                if (possibleChild.equals(child)) {
                    foundChildren.add(possibleChild);
                }
            }
        }

        foundChildren.forEach(children::remove);
        children.addAll(foundChildren);
        foundChildren.forEach(possibleChildren::remove);
    }

    private static class Program {
        private int weight;
        private int totalWeight;
        private final Set<Program> children = new HashSet<>();
        private String name;

        @Override
        public String toString() {
            return "Program{" + "weight=" + weight + ", name='" + name + '\'' + ", children=" + children + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Program program = (Program) o;

            return name != null ? name.equals(program.name) : program.name == null;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }
}
