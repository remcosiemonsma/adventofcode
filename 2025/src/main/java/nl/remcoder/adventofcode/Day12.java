package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

public class Day12 implements AdventOfCodeSolution<Long> {

    @Override
    public Long handlePart1(Stream<String> input) {
        return input.gather(Gatherer.<String, RegionBuilder, Boolean>ofSequential
                                            (RegionBuilder::new,
                                             (state, element, downstream) -> {
                                                 if (element.isBlank()) {
                                                     state.buildPresent();
                                                     return true;
                                                 }
                                                 if (element.length() == 2) {
                                                     return true;
                                                 }
                                                 if (element.length() == 3) {
                                                     state.addLineForPresent(element);
                                                     return true;
                                                 }
                                                 var split = element.split("x|:? ");
                                                 var width = Integer.parseInt(split[0]);
                                                 var height = Integer.parseInt(split[1]);

                                                 var wantedPresents = new ArrayList<Integer>();
                                                 for (var i = 2; i < split.length; i++) {
                                                     wantedPresents.add(Integer.parseInt(split[i]));
                                                 }
                                                 downstream.push(state.doPresentsFit(width, height, wantedPresents));

                                                 return true;
                                             }))
                    .filter(Boolean.TRUE::equals)
                    .count();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        return 0L;
    }

    private static class RegionBuilder {

        private final List<String> currentPresent = new ArrayList<>();

        private final List<boolean[][]> presents = new ArrayList<>();

        private final List<Integer> presentsCoverage = new ArrayList<>();

        public void addLineForPresent(String line) {
            currentPresent.add(line);
        }

        public void buildPresent() {
            boolean[][] present = new boolean[currentPresent.size()][];

            for (int i = 0; i < currentPresent.size(); i++) {
                var row = currentPresent.get(i);
                var presentline = new boolean[row.length()];
                for (int j = 0; j < row.length(); j++) {
                    presentline[j] = row.charAt(j) == '#';
                }
                present[i] = presentline;
            }

            presents.add(present);
            presentsCoverage.add(calculatePresentCoverage(present));
            currentPresent.clear();
        }

        public boolean doPresentsFit(int width, int height, List<Integer> wantedPresents) {
            var size = width * height;

            var presentCoverage = 0;
            for (var i = 0; i < wantedPresents.size(); i++) {
                presentCoverage += wantedPresents.get(i) * presentsCoverage.get(i);
            }

            //Really?
            return presentCoverage < size;
        }

        private int calculatePresentCoverage(boolean[][] present) {
            var count = 0;
            for (var row : present) {
                for (var pixel : row) {
                    if (pixel) {
                        count++;
                    }
                }
            }
            return count;
        }
    }
}
