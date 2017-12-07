package nl.remcoder.adventofcode.day7;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2 {

    public static void main(String[] args) throws Exception {
        Set<Program> programs = new HashSet<>();

        Files.lines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI())).forEach(s -> {
            Program program = parseString(s);
            parseChildren(programs, program);
            if (!findParent(programs, program)) {
                programs.add(program);
            }
        });

        Program root = programs.stream().findFirst().get();

        for (Program child : root.children) {
            child.totalWeight = calculateWeight(child.children) + child.weight;
        }

        findImbalance(root, 0);
    }

    private static void findImbalance (Program program, int difference) {
        Map<Integer, Integer> weightOccurrences = new HashMap<>();

        for (Program child : program.children) {
            if (weightOccurrences.containsKey(child.totalWeight)) {
                weightOccurrences.put(child.totalWeight, weightOccurrences.get(child.totalWeight) + 1);
            } else {
                weightOccurrences.put(child.totalWeight, 1);
            }
        }

        if (weightOccurrences.size() == 1) {
            System.out.println("Children are balanced, inccrrect weight is: " + program.weight + " with difference of " + difference + ", so weight should be: " + (program.weight + difference));
        } else {
            int incorrectWeight = 0;
            int correctWeight  = 0;

            for (Integer weight : weightOccurrences.keySet()) {
                if (weightOccurrences.get(weight).equals(1)) {
                    incorrectWeight = weight;
                } else {
                    correctWeight = weight;
                }
            }

            int determinedDifference = correctWeight - incorrectWeight;

            for (Program child : program.children) {
                if (child.totalWeight == incorrectWeight) {
                    findImbalance(child, determinedDifference);
                }
            }
        }
    }

    private static int calculateWeight(Set<Program> programs) {
        int weight = 0;

        for (Program program : programs) {
            int programWeight = calculateWeight(program.children) + program.weight;
            program.totalWeight = programWeight;
            weight += programWeight;
        }

        return weight;
    }

    private static boolean findParent(Set<Program> possibleParents, Program orphan) {
        for (Program possibleParent : possibleParents) {
            if (possibleParent.children.contains(orphan)) {
                possibleParent.children.remove(orphan);
                possibleParent.children.add(orphan);
                return true;
            }
            for (Program child : possibleParent.children) {
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

    private static void parseChildren (Set<Program> possibleChildren, Program parent) {
        Set<Program> children = parent.children;

        List<Program> foundChildren = new ArrayList<>();
        for (Program child : children) {
            for (Program possibleChild : possibleChildren) {
                if (possibleChild.equals(child)) {
                    foundChildren.add(possibleChild);
                }
            }
        }

        children.removeAll(foundChildren);
        children.addAll(foundChildren);
        possibleChildren.removeAll(foundChildren);
    }

    private static Program parseString(String s) {
        Program program = new Program();

        String[] data = s.split(" ");

        program.name = data[0];
        program.weight = Integer.parseInt(data[1].replaceAll("[()]", ""));

        if (s.contains("->")) {
            String[] children = s.split("-> ")[1].split(", ");

            for (String childName : children) {
                Program child = new Program();
                child.name = childName;
                program.children.add(child);
            }
        }

        return program;
    }

    private static class Program {
        int weight;
        String name;
        int totalWeight;
        Set<Program> children = new HashSet<>();

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
