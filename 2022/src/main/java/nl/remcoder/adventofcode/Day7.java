package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day7 {
    private Directory root;
    private Directory currentDirectory;
    private final List<Directory> directories = new ArrayList<>();

    public int handlePart1(Stream<String> input) {
        root = new Directory("/", null);
        
        directories.add(root);

        input.forEach(this::processCommand);

        return directories.stream()
                          .mapToInt(Directory::getSize)
                          .filter(value -> value <= 100000)
                          .sum();
    }

    public int handlePart2(Stream<String> input) {
        root = new Directory("/", null);
        
        directories.add(root);

        input.forEach(this::processCommand);

        int totalSize = root.getSize();

        int totalSpace = 70000000;

        int spaceAvailable = totalSpace - totalSize;
        
        int spaceNeeded = 30000000;
        
        int spaceToFree = spaceNeeded - spaceAvailable;

        return directories.stream()
                          .mapToInt(Directory::getSize)
                          .filter(value -> value >= spaceToFree)
                          .min()
                          .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private void processCommand(String command) {
        String[] parts = command.split(" ");
        if (parts[0].equals("$")) {
            if (parts[1].equals("cd")) {
                switch (parts[2]) {
                    case "/" -> currentDirectory = root;
                    case ".." -> currentDirectory = currentDirectory.parent;
                    default -> currentDirectory = currentDirectory.children.get(parts[2]);
                }
            }
        } else {
            if (parts[0].equals("dir")) {
                Directory directory = new Directory(parts[1], currentDirectory);
                currentDirectory.children.put(parts[1], directory);
                directories.add(directory);
            } else {
                currentDirectory.children.put(parts[1],
                                              new Directory(parts[1], currentDirectory, Integer.parseInt(parts[0])));
            }
        }
    }

    private static class Directory {
        private final String name;
        private final Map<String, Directory> children = new HashMap<>();
        private final Directory parent;
        private int size = 0;

        private Directory(String name, Directory parent) {
            this.name = name;
            this.parent = parent;
        }

        private Directory(String name, Directory parent, int size) {
            this.name = name;
            this.parent = parent;
            this.size = size;
        }

        public int getSize() {
            if (size == 0) {
                for (Directory child : children.values()) {
                    size += child.getSize();
                }
            }
            return size;
        }

        @Override
        public String toString() {
            return "Directory{" +
                   "name='" + name + '\'' +
                   ", size=" + size +
                   '}';
        }
    }
}
