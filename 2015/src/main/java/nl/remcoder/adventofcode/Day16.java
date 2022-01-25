package nl.remcoder.adventofcode;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16 {
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

    public int handlePart1(Stream<String> input) {
        List<Aunt> aunts = input.map(Aunt::new).collect(Collectors.toList());

        int expectedChildren = 3;
        int expectedCats = 7;
        int expectedSamoyeds = 2;
        int expectedPomeranians = 3;
        int expectedAkitas = 0;
        int expectedVizslas = 0;
        int expectedGoldfish = 5;
        int expectedTrees = 3;
        int expectedCars = 2;
        int expectedPerfumes = 1;

        aunts.removeIf(aunt -> aunt.children != null && aunt.children != expectedChildren);
        aunts.removeIf(aunt -> aunt.cats != null && aunt.cats != expectedCats);
        aunts.removeIf(aunt -> aunt.samoyeds != null && aunt.samoyeds != expectedSamoyeds);
        aunts.removeIf(aunt -> aunt.pomeranians != null && aunt.pomeranians != expectedPomeranians);
        aunts.removeIf(aunt -> aunt.akitas != null && aunt.akitas != expectedAkitas);
        aunts.removeIf(aunt -> aunt.vizslas != null && aunt.vizslas != expectedVizslas);
        aunts.removeIf(aunt -> aunt.goldfish != null && aunt.goldfish != expectedGoldfish);
        aunts.removeIf(aunt -> aunt.trees != null && aunt.trees != expectedTrees);
        aunts.removeIf(aunt -> aunt.cars != null && aunt.cars != expectedCars);
        aunts.removeIf(aunt -> aunt.perfumes != null && aunt.perfumes != expectedPerfumes);

        return aunts.get(0).id;
    }

    public int handlePart2(Stream<String> input) {
        List<Aunt> aunts = input.map(Aunt::new).collect(Collectors.toList());

        int expectedChildren = 3;
        int expectedCats = 7;
        int expectedSamoyeds = 2;
        int expectedPomeranians = 3;
        int expectedAkitas = 0;
        int expectedVizslas = 0;
        int expectedGoldfish = 5;
        int expectedTrees = 3;
        int expectedCars = 2;
        int expectedPerfumes = 1;

        aunts.removeIf(aunt -> aunt.children != null && aunt.children != expectedChildren);
        aunts.removeIf(aunt -> aunt.cats != null && aunt.cats <= expectedCats);
        aunts.removeIf(aunt -> aunt.samoyeds != null && aunt.samoyeds != expectedSamoyeds);
        aunts.removeIf(aunt -> aunt.pomeranians != null && aunt.pomeranians >= expectedPomeranians);
        aunts.removeIf(aunt -> aunt.akitas != null && aunt.akitas != expectedAkitas);
        aunts.removeIf(aunt -> aunt.vizslas != null && aunt.vizslas != expectedVizslas);
        aunts.removeIf(aunt -> aunt.goldfish != null && aunt.goldfish >= expectedGoldfish);
        aunts.removeIf(aunt -> aunt.trees != null && aunt.trees <= expectedTrees);
        aunts.removeIf(aunt -> aunt.cars != null && aunt.cars != expectedCars);
        aunts.removeIf(aunt -> aunt.perfumes != null && aunt.perfumes != expectedPerfumes);

        return aunts.get(0).id;
    }

    private static class Aunt {
        private final int id;
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
            Matcher idMatcher = ID_PATTERN.matcher(line);
            if (idMatcher.find()) {
                id = Integer.parseInt(idMatcher.group(1));
            } else {
                throw new AssertionError("Eek!");
            }
            Matcher childrenMatcher = CHILDREN_PATTERN.matcher(line);
            if (childrenMatcher.find()) {
                children = Integer.parseInt(childrenMatcher.group(1));
            } else {
                children = null;
            }
            Matcher catsMatcher = CATS_PATTERN.matcher(line);
            if (catsMatcher.find()) {
                cats = Integer.parseInt(catsMatcher.group(1));
            } else {
                cats = null;
            }
            Matcher samoyedsMatcher = SAMOYEDS_PATTERN.matcher(line);
            if (samoyedsMatcher.find()) {
                samoyeds = Integer.parseInt(samoyedsMatcher.group(1));
            } else {
                samoyeds = null;
            }
            Matcher pomeraniansMatcher = POMERANIANS_PATTERN.matcher(line);
            if (pomeraniansMatcher.find()) {
                pomeranians = Integer.parseInt(pomeraniansMatcher.group(1));
            } else {
                pomeranians = null;
            }
            Matcher akitasMatcher = AKITAS_PATTERN.matcher(line);
            if (akitasMatcher.find()) {
                akitas = Integer.parseInt(akitasMatcher.group(1));
            } else {
                akitas = null;
            }
            Matcher vizslasMatcher = VIZSLAS_PATTERN.matcher(line);
            if (vizslasMatcher.find()) {
                vizslas = Integer.parseInt(vizslasMatcher.group(1));
            } else {
                vizslas = null;
            }
            Matcher goldfishMatcher = GOLDFISH_PATTERN.matcher(line);
            if (goldfishMatcher.find()) {
                goldfish = Integer.parseInt(goldfishMatcher.group(1));
            } else {
                goldfish = null;
            }
            Matcher treesMatcher = TREES_PATTERN.matcher(line);
            if (treesMatcher.find()) {
                trees = Integer.parseInt(treesMatcher.group(1));
            } else {
                trees = null;
            }
            Matcher carsMatcher = CARS_PATTERN.matcher(line);
            if (carsMatcher.find()) {
                cars = Integer.parseInt(carsMatcher.group(1));
            } else {
                cars = null;
            }
            Matcher perfumesMatcher = PERFUMES_PATTERN.matcher(line);
            if (perfumesMatcher.find()) {
                perfumes = Integer.parseInt(perfumesMatcher.group(1));
            } else {
                perfumes = null;
            }
        }
    }
}
