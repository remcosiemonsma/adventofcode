package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var scanners = input.map(this::parseScanner)
                            .collect(Collectors.toMap(Scanner::getId, scanner -> scanner));

        var firewallDepth = scanners.keySet()
                                    .stream()
                                    .max(Integer::compareTo)
                                    .orElseThrow(() -> new AssertionError("Eek!"));

        int tripSeverity = 0;

        for (int i = 0; i <= firewallDepth; i++) {
            var scanner = scanners.get(i);
            if (scanner != null) {
                if (scanner.position == 1) {
                    tripSeverity += (i * scanner.size);
                }
            }
            incrementScanners(scanners.values());
        }

        return tripSeverity;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var scanners = input.map(this::parseScanner)
                            .collect(Collectors.toMap(Scanner::getId, scanner -> scanner));

        var firewallDepth = scanners.keySet()
                                    .stream()
                                    .max(Integer::compareTo)
                                    .orElseThrow(() -> new AssertionError("Eek!"));

        var delay = 0;
        
        while (!canMoveSafelyThroughFirewall(scanners, firewallDepth, delay)) {
            delay++;
        }
        
        return delay;
    }

    private Scanner parseScanner(String line) {
        var data = line.split(": ");

        return new Scanner(Integer.parseInt(data[0]), Integer.parseInt(data[1]), 1, true);
    }

    private static void incrementScanners(Collection<Scanner> scanners) {
        for (var scanner : scanners) {
            if (scanner.forwardDirection) {
                scanner.position++;
                if (scanner.position == scanner.size) {
                    scanner.forwardDirection = false;
                }
            } else {
                scanner.position--;
                if (scanner.position == 1) {
                    scanner.forwardDirection = true;
                }
            }
        }
    }

    private static boolean canMoveSafelyThroughFirewall(Map<Integer, Scanner> scanners, int firewallDepth, int delay) {
        for (int i = 0; i <= firewallDepth; i++) {
            var scanner = scanners.get(i);
            if (scanner != null) {
                int timeBetween1 = (scanner.size - 1) * 2;
                if ((i + delay) % timeBetween1 == 0) {
                    return false;
                }
            }
        }

        return true;
    }
    
    private static class Scanner {
        private final int id;
        private final int size;
        private int position;
        private boolean forwardDirection;

        private Scanner(int id, int size, int position, boolean forwardDirection) {
            this.id = id;
            this.size = size;
            this.position = position;
            this.forwardDirection = forwardDirection;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return "Scanner{" +
                   "size=" + size +
                   ", position=" + position +
                   ", forwardDirection=" + forwardDirection +
                   '}';
        }
    }
}
