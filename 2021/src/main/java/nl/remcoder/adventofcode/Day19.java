package nl.remcoder.adventofcode;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day19 {
    private final Map<Scanner, List<Scanner>> rotatedScanners = new HashMap<>();

    public int handlePart1(Stream<String> input) {
        List<String> lines = input.filter(Predicate.not(String::isBlank))
                                  .collect(Collectors.toList());

        Scanner scanner = new Scanner();

        List<Scanner> scanners = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith("--- scanner ")) {
                scanner = new Scanner();
                scanners.add(scanner);
            } else {
                String[] coords = line.split(",");
                int coord1 = Integer.parseInt(coords[0]);
                int coord2 = Integer.parseInt(coords[1]);
                int coord3 = Integer.parseInt(coords[2]);

                Beacon beacon = new Beacon(coord1, coord2, coord3);

                scanner.beacons.add(beacon);
            }
        }

        Scanner firstScanner = scanners.remove(0);
        List<ScannerWithDistance> goodScanners = new ArrayList<>();
        goodScanners.add(new ScannerWithDistance(firstScanner.beacons, new Distance(0, 0, 0)));

        while (!scanners.isEmpty()) {
            System.out.println(scanners.size());
            outer:
            for (ScannerWithDistance goodScanner : goodScanners) {
                for (int i = 0; i < scanners.size(); i++) {
                    Scanner other = scanners.get(i);
                    ScannerWithDistance rotatedScanner = getRotatedScanner(goodScanner, other);
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

    public int handlePart2(Stream<String> input) {
        List<String> lines = input.filter(Predicate.not(String::isBlank))
                                  .collect(Collectors.toList());

        Scanner scanner = new Scanner();

        List<Scanner> scanners = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith("--- scanner ")) {
                scanner = new Scanner();
                scanners.add(scanner);
            } else {
                String[] coords = line.split(",");
                int coord1 = Integer.parseInt(coords[0]);
                int coord2 = Integer.parseInt(coords[1]);
                int coord3 = Integer.parseInt(coords[2]);

                Beacon beacon = new Beacon(coord1, coord2, coord3);

                scanner.beacons.add(beacon);
            }
        }

        Scanner firstScanner = scanners.remove(0);
        List<ScannerWithDistance> goodScanners = new ArrayList<>();
        goodScanners.add(new ScannerWithDistance(firstScanner.beacons, new Distance(0, 0, 0)));

        while (!scanners.isEmpty()) {
            System.out.println(scanners.size());
            outer:
            for (ScannerWithDistance goodScanner : goodScanners) {
                for (int i = 0; i < scanners.size(); i++) {
                    Scanner other = scanners.get(i);
                    ScannerWithDistance rotatedScanner = getRotatedScanner(goodScanner, other);
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
                           .getAsInt();
    }

    private ScannerWithDistance getRotatedScanner(ScannerWithDistance goodScanner, Scanner unknownScanner) {
        List<Scanner> rotations = rotatedScanners.computeIfAbsent(unknownScanner, this::getAllRotations);

        for (Scanner rotation : rotations) {
            for (Beacon beacon : goodScanner.beacons) {
                for (Beacon rotatedBeacon : rotation.beacons) {
                    int distanceX = beacon.x - rotatedBeacon.x;
                    int distanceY = beacon.y - rotatedBeacon.y;
                    int distanceZ = beacon.z - rotatedBeacon.z;

                    int overlap = 0;

                    int count = 0;
                    for (Beacon goodBeacon : goodScanner.beacons) {
                        if (12 - overlap > goodScanner.beacons.size() - count) {
                            break;
                        }

                        for (Beacon other : rotation.beacons) {
                            int xFromGoodScanner = other.x + distanceX;
                            int yFromGoodScanner = other.y + distanceY;
                            int zFromGoodScanner = other.z + distanceZ;
                            if (xFromGoodScanner == goodBeacon.x &&
                                yFromGoodScanner == goodBeacon.y &
                                zFromGoodScanner == goodBeacon.z) {
                                overlap++;

                                if (overlap >= 12) {
                                    List<Beacon> goodBeacons = new ArrayList<>();

                                    for (Beacon candidate : rotation.beacons) {
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
        List<Scanner> rotatedScanners = new ArrayList<>();
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
        Scanner result = new Scanner();

        result.beacons.addAll(scanner.beacons.stream()
                                             .map(this::turn)
                                             .collect(Collectors.toSet()));

        return result;
    }

    private Scanner roll(Scanner scanner) {
        Scanner result = new Scanner();

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
