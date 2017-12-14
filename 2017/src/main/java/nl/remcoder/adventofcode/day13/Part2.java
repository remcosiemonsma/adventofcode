package nl.remcoder.adventofcode.day13;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Part2 {
    public static void main(String[] args) throws Exception {
        Map<Integer, Scanner> scanners = new HashMap<>();

        Files.lines(Paths.get(ClassLoader.getSystemResource("day13/input").toURI())).forEach(s -> {
            String[] data = s.split(": ");

            Scanner scanner = new Scanner();

            scanner.size = Integer.parseInt(data[1]);
            scanner.position = 1;
            scanner.forwardDirection = true;

            scanners.put(Integer.parseInt(data[0]), scanner);
        });

        int delay = 0;

        int firewallDepth = scanners.keySet().stream().max(Integer::compareTo).get();

        while (!canMoveSafelyThroughFirewall(scanners, firewallDepth, delay)) {
            delay++;
        }

        System.out.println(delay);
    }

    private static boolean canMoveSafelyThroughFirewall(Map<Integer, Scanner> scanners, int firewallDepth, int delay) {
        for (int i = 0; i <= firewallDepth; i++) {
            Scanner scanner = scanners.get(i);
            if (scanner != null) {
                int timeBetween1 = (scanner.size - 1) * 2;
                if ((i + delay) % timeBetween1 == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void incrementScanners(Collection<Scanner> scanners) {
        for (Scanner scanner : scanners) {
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

    private static class Scanner {
        int size;
        int position;
        boolean forwardDirection;

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
