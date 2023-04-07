package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day19 implements AdventOfCodeSolution<Integer> {
    private final Map<Scanner, List<Scanner>> rotatedScanners = new HashMap<>();

    @Override
    public Integer handlePart1(Stream<String> input) {
        var scanners = parseScanners(input);

        var firstScanner = scanners.remove(0);
        var goodScanners = new ArrayList<ScannerWithDistance>();
        goodScanners.add(new ScannerWithDistance(firstScanner.beacons, new Distance(0, 0, 0)));

        while (!scanners.isEmpty()) {
            outer:
            for (var goodScanner : goodScanners) {
                for (int i = 0; i < scanners.size(); i++) {
                    var other = scanners.get(i);
                    var rotatedScanner = getRotatedScanner(goodScanner, other);
                    if (rotatedScanner != null) {
                        goodScanners.add(rotatedScanner);
                        scanners.remove(other);
                        break outer;
                    }
                }
            }
        }

        return (int) goodScanners.stream()
                                 .map(ScannerWithDistance::beacons)
                                 .flatMap(List::stream)
                                 .distinct()
                                 .count();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var scanners = parseScanners(input);

        var firstScanner = scanners.remove(0);
        var goodScanners = new ArrayList<ScannerWithDistance>();
        goodScanners.add(new ScannerWithDistance(firstScanner.beacons, new Distance(0, 0, 0)));

        while (!scanners.isEmpty()) {
            outer:
            for (var goodScanner : goodScanners) {
                for (var i = 0; i < scanners.size(); i++) {
                    var other = scanners.get(i);
                    var rotatedScanner = getRotatedScanner(goodScanner, other);
                    if (rotatedScanner != null) {
                        goodScanners.add(rotatedScanner);
                        scanners.remove(other);
                        break outer;
                    }
                }
            }
        }

        return goodScanners.stream()
                           .mapToInt(somescanner ->
                                             goodScanners.stream()
                                                         .mapToInt(somescanner::getManhattanDistance)
                                                         .max().getAsInt()
                                    )
                           .max()
                           .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private List<Scanner> parseScanners(Stream<String> input) {
        var lines = input.filter(Predicate.not(String::isBlank))
                         .toList();

        var scanner = new Scanner();

        var scanners = new ArrayList<Scanner>();

        for (var line : lines) {
            if (line.startsWith("--- scanner ")) {
                scanner = new Scanner();
                scanners.add(scanner);
            } else {
                var coords = line.split(",");
                var coord1 = Integer.parseInt(coords[0]);
                var coord2 = Integer.parseInt(coords[1]);
                var coord3 = Integer.parseInt(coords[2]);

                var beacon = new Beacon(coord1, coord2, coord3);

                scanner.beacons.add(beacon);
            }
        }
        return scanners;
    }

    private ScannerWithDistance getRotatedScanner(ScannerWithDistance goodScanner, Scanner unknownScanner) {
        var rotations = rotatedScanners.computeIfAbsent(unknownScanner, this::getAllRotations);

        for (var rotation : rotations) {
            for (var beacon : goodScanner.beacons) {
                for (var rotatedBeacon : rotation.beacons) {
                    int distanceX = beacon.x - rotatedBeacon.x;
                    int distanceY = beacon.y - rotatedBeacon.y;
                    int distanceZ = beacon.z - rotatedBeacon.z;

                    int overlap = 0;

                    int count = 0;
                    for (var goodBeacon : goodScanner.beacons) {
                        if (12 - overlap > goodScanner.beacons.size() - count) {
                            break;
                        }

                        for (var other : rotation.beacons) {
                            int xFromGoodScanner = other.x + distanceX;
                            int yFromGoodScanner = other.y + distanceY;
                            int zFromGoodScanner = other.z + distanceZ;
                            if (xFromGoodScanner == goodBeacon.x &&
                                yFromGoodScanner == goodBeacon.y &
                                zFromGoodScanner == goodBeacon.z) {
                                overlap++;

                                if (overlap >= 12) {
                                    var goodBeacons = new ArrayList<Beacon>();

                                    for (var candidate : rotation.beacons) {
                                        goodBeacons.add(new Beacon(candidate.x + distanceX, candidate.y + distanceY,
                                                                   candidate.z + distanceZ));
                                    }

                                    return new ScannerWithDistance(goodBeacons,
                                                                   new Distance(distanceX,
                                                                                distanceY,
                                                                                distanceZ));
                                }
                                break;
                            }
                        }

                        count++;
                    }
                }
            }
        }

        return null;
    }

    private List<Scanner> getAllRotations(Scanner scanner) {
        var rotatedScanners = new ArrayList<Scanner>();
        for (int c = 0; c < 2; c++) {
            for (int s = 0; s < 3; s++) {
                scanner = roll(scanner);
                rotatedScanners.add(scanner);
                for (int i = 0; i < 3; i++) {
                    scanner = turn(scanner);
                    rotatedScanners.add(scanner);
                }
            }
            scanner = roll(turn(roll(scanner)));
        }
        return rotatedScanners;
    }

    private Scanner turn(Scanner scanner) {
        var result = new Scanner();

        result.beacons.addAll(scanner.beacons.stream()
                                             .map(this::turn)
                                             .collect(Collectors.toSet()));

        return result;
    }

    private Scanner roll(Scanner scanner) {
        var result = new Scanner();

        result.beacons.addAll(scanner.beacons.stream()
                                             .map(this::roll)
                                             .collect(Collectors.toSet()));

        return result;
    }

    private Beacon turn(Beacon beacon) {
        return new Beacon(-beacon.y, beacon.x, beacon.z);
    }

    private Beacon roll(Beacon beacon) {
        return new Beacon(beacon.x, beacon.z, -beacon.y);
    }

    private static class Scanner {
        private final List<Beacon> beacons = new ArrayList<>();
    }

    private record Beacon(int x, int y, int z) {
    }

    private record Distance(int x, int y, int z) {
    }

    private record ScannerWithDistance(List<Beacon> beacons, Distance distance) {
        public int getManhattanDistance(ScannerWithDistance other) {
            return Math.abs(distance.x - other.distance.x) + Math.abs(distance.y - other.distance.y) +
                   Math.abs(distance.z - other.distance.z);
        }
    }
}
