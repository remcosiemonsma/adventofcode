package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day5 implements AdventOfCodeSolution<Long> {
    private final Pattern MAP_PATTERN = Pattern.compile("(\\w*)-to-(\\w*)");

    @Override
    public Long handlePart1(Stream<String> input) {
        var lines = input.toList();

        var seeds = Arrays.stream(lines.getFirst()
                                       .substring(7)
                                       .split(" "))
                          .map(Long::parseLong).toList();

        var mappings = new HashMap<String, XToXMap>();

        XToXMap map = null;

        for (int i = 2; i < lines.size(); i++) {
            var line = lines.get(i);
            if (map == null) {
                Matcher matcher = MAP_PATTERN.matcher(line);
                if (matcher.find()) {
                    String source = matcher.group(1);
                    String target = matcher.group(2);
                    map = new XToXMap(source, target, new ArrayList<>());
                } else {
                    throw new AssertionError("Eek!");
                }
            } else if (line.isBlank()) {
                mappings.put(map.source, map);
                map = null;
            } else {
                var data = line.split(" ");
                map.mappings().add(new XToXMap.Mapping(Long.parseLong(data[1]),
                                                       Long.parseLong(data[0]),
                                                       Long.parseLong(data[2])));
            }
        }

        if (map != null) {
            mappings.put(map.source(), map);
        }

        return seeds.stream()
                    .mapToLong(seed -> mapSeedToLocation(seed, "seed", mappings))
                    .min()
                    .orElseThrow(() -> new AssertionError("Ook!"));
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var lines = input.toList();

        var mappings = new HashMap<String, XToXMap>();

        XToXMap map = null;

        for (int i = 2; i < lines.size(); i++) {
            var line = lines.get(i);
            if (map == null) {
                Matcher matcher = MAP_PATTERN.matcher(line);
                if (matcher.find()) {
                    String source = matcher.group(1);
                    String target = matcher.group(2);
                    map = new XToXMap(source, target, new ArrayList<>());
                } else {
                    throw new AssertionError("Eek!");
                }
            } else if (line.isBlank()) {
                mappings.put(map.target(), map);
                map = null;
            } else {
                var data = line.split(" ");
                map.mappings().add(new XToXMap.Mapping(Long.parseLong(data[1]),
                                                       Long.parseLong(data[0]),
                                                       Long.parseLong(data[2])));
            }
        }

        if (map != null) {
            mappings.put(map.target(), map);
        }

        var seeds = mapSeeds(lines.getFirst());

        var validSeedFound = false;

        var location = 0L;

        while(!validSeedFound) {
            var seed = mapLocationToSeed(location, "location", mappings);
            if (seeds.stream().anyMatch(seedRange -> seedRange.isSeedInRange(seed))) {
                validSeedFound = true;
            } else {
                location++;
            }
        }

        return location;
    }

    private List<SeedRange> mapSeeds(String line) {
        var seeds = new ArrayList<SeedRange>();

        var split = line.substring(7).split(" ");

        for (var i = 0; i < split.length; i += 2) {
            seeds.add(new SeedRange(Long.parseLong(split[i]), Long.parseLong(split[i + 1])));
        }

        return seeds;
    }

    private long mapSeedToLocation(long seed, String source, Map<String, XToXMap> mappings) {
        if (mappings.containsKey(source)) {
            var map = mappings.get(source);
            var result = map.mapSourceToDestination(seed);
//            System.out.printf("Mapping source %d from %s to destination %d%n", seed, source, result);
            return mapSeedToLocation(result, map.target(), mappings);
        } else {
            return seed;
        }
    }

    private long mapLocationToSeed(long location, String destination, Map<String, XToXMap> mappings) {
        if (mappings.containsKey(destination)) {
            var map = mappings.get(destination);
            var result = map.mapDestinationToSource(location);
//            System.out.printf("Mapping location %d from %s to start %d%n", location, destination, result);
            return mapLocationToSeed(result, map.source(), mappings);
        } else {
            return location;
        }
    }

    private record SeedRange(long start, long range) {
        public boolean isSeedInRange(long seed) {
            return seed >= start && seed < start + range;
        }
    }

    private record XToXMap(String source, String target, List<Mapping> mappings) {
        public long mapSourceToDestination(long source) {
            return mappings.stream()
                           .filter(mapping -> mapping.isMappingForStartNumber(source))
                           .findFirst()
                           .map(mapping -> mapping.mapNumberToDestination(source))
                           .orElse(source);
        }

        public long mapDestinationToSource(long destination) {
            return mappings.stream()
                           .filter(mapping -> mapping.isMappingForDestinationNumber(destination))
                           .findFirst()
                           .map(mapping -> mapping.mapNumberToStart(destination))
                           .orElse(destination);
        }

        private record Mapping(long sourceStart, long destinationStart, long length) {

            public boolean isMappingForStartNumber(long number) {
                return number >= sourceStart && number < sourceStart + length;
            }

            public boolean isMappingForDestinationNumber(long number) {
                return number >= destinationStart && number < destinationStart + length;
            }

            public long mapNumberToDestination(long number) {
                var positionInSourceRange = number - sourceStart;
                return destinationStart + positionInSourceRange;
            }

            public long mapNumberToStart(long number) {
                var positionInSourceRange = number - destinationStart;
                return sourceStart + positionInSourceRange;
            }
        }
    }
}
