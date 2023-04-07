package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day16 implements AdventOfCodeSolution<Integer> {
    private static final Pattern ID_PATTERN = Pattern.compile("Sue (\\d+)");
    private static final Pattern CHILDREN_PATTERN = Pattern.compile("children: (\\d+)");
    private static final Pattern CATS_PATTERN = Pattern.compile("cats: (\\d+)");
    private static final Pattern SAMOYEDS_PATTERN = Pattern.compile("samoyeds: (\\d+)");
    private static final Pattern POMERANIANS_PATTERN = Pattern.compile("pomeranians: (\\d+)");
    private static final Pattern AKITAS_PATTERN = Pattern.compile("akitas: (\\d+)");
    private static final Pattern VIZSLAS_PATTERN = Pattern.compile("vizslas: (\\d+)");
    private static final Pattern GOLDFISH_PATTERN = Pattern.compile("goldfish: (\\d+)");
    private static final Pattern TREES_PATTERN = Pattern.compile("trees: (\\d+)");
    private static final Pattern CARS_PATTERN = Pattern.compile("cars: (\\d+)");
    private static final Pattern PERFUMES_PATTERN = Pattern.compile("perfumes: (\\d+)");
    private final int expectedChildren = 3;
    private final int expectedCats = 7;
    private final int expectedSamoyeds = 2;
    private final int expectedPomeranians = 3;
    private final int expectedAkitas = 0;
    private final int expectedVizslas = 0;
    private final int expectedGoldfish = 5;
    private final int expectedTrees = 3;
    private final int expectedCars = 2;
    private final int expectedPerfumes = 1;
    
    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.map(Aunt::new)
                    .filter(aunt -> aunt.children == null || aunt.children == expectedChildren)
                    .filter(aunt -> aunt.cats == null || aunt.cats == expectedCats)
                    .filter(aunt -> aunt.samoyeds == null || aunt.samoyeds == expectedSamoyeds)
                    .filter(aunt -> aunt.pomeranians == null || aunt.pomeranians == expectedPomeranians)
                    .filter(aunt -> aunt.akitas == null || aunt.akitas == expectedAkitas)
                    .filter(aunt -> aunt.vizslas == null || aunt.vizslas == expectedVizslas)
                    .filter(aunt -> aunt.goldfish == null || aunt.goldfish == expectedGoldfish)
                    .filter(aunt -> aunt.trees == null || aunt.trees == expectedTrees)
                    .filter(aunt -> aunt.cars == null || aunt.cars == expectedCars)
                    .filter(aunt -> aunt.perfumes == null || aunt.perfumes == expectedPerfumes)
                    .findFirst()
                    .map(Aunt::getId)
                    .orElseThrow(() -> new AssertionError("Eek!"));
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return input.map(Aunt::new)
                    .filter(aunt -> aunt.children == null || aunt.children == expectedChildren)
                    .filter(aunt -> aunt.cats == null || aunt.cats > expectedCats)
                    .filter(aunt -> aunt.samoyeds == null || aunt.samoyeds == expectedSamoyeds)
                    .filter(aunt -> aunt.pomeranians == null || aunt.pomeranians < expectedPomeranians)
                    .filter(aunt -> aunt.akitas == null || aunt.akitas == expectedAkitas)
                    .filter(aunt -> aunt.vizslas == null || aunt.vizslas == expectedVizslas)
                    .filter(aunt -> aunt.goldfish == null || aunt.goldfish < expectedGoldfish)
                    .filter(aunt -> aunt.trees == null || aunt.trees > expectedTrees)
                    .filter(aunt -> aunt.cars == null || aunt.cars == expectedCars)
                    .filter(aunt -> aunt.perfumes == null || aunt.perfumes == expectedPerfumes)
                    .findFirst()
                    .map(Aunt::getId)
                    .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private static class Aunt {
        private final Integer id;
        private final Integer children;
        private final Integer cats;
        private final Integer samoyeds;
        private final Integer pomeranians;
        private final Integer akitas;
        private final Integer vizslas;
        private final Integer goldfish;
        private final Integer trees;
        private final Integer cars;
        private final Integer perfumes;

        private Aunt(String line) {
            id = getValueForPattern(ID_PATTERN, line);
            children = getValueForPattern(CHILDREN_PATTERN, line);
            cats = getValueForPattern(CATS_PATTERN, line);
            samoyeds = getValueForPattern(SAMOYEDS_PATTERN, line);
            pomeranians = getValueForPattern(POMERANIANS_PATTERN, line);
            akitas = getValueForPattern(AKITAS_PATTERN, line);
            vizslas = getValueForPattern(VIZSLAS_PATTERN, line);
            goldfish = getValueForPattern(GOLDFISH_PATTERN, line);
            trees = getValueForPattern(TREES_PATTERN, line);
            cars = getValueForPattern(CARS_PATTERN, line);
            perfumes = getValueForPattern(PERFUMES_PATTERN, line);
        }

        private Integer getValueForPattern(Pattern pattern, String line) {
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            } else {
                return null;
            }
        }
        
        public int getId() {
            return id;
        }
    }
}
