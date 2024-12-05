package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.stream.Stream;

public class Day5 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var beforeRules = new HashMap<String, List<String>>();
        var afterRules = new HashMap<String, List<String>>();
        var updates = new ArrayList<String>();

        readInput(input, beforeRules, afterRules, updates);

        var result = 0;

        for (var update : updates) {
            var validatedPages = new ArrayList<String>();
            var pagesToValidate = new ArrayList<>(Arrays.asList(update.split(",")));

            outer:
            while (!pagesToValidate.isEmpty()) {
                var pageToValidate = pagesToValidate.removeFirst();

                for (var page : pagesToValidate) {
                    var after = afterRules.get(page);
                    if (after != null && after.contains(pageToValidate)) {
                        break outer;
                    }
                    var before = beforeRules.get(pageToValidate);
                    if (before != null && before.contains(page)) {
                        break outer;
                    }
                }

                validatedPages.add(pageToValidate);
            }

            if (pagesToValidate.isEmpty()) {
                var middlePage = validatedPages.get(validatedPages.size() / 2);

                result += Integer.parseInt(middlePage);
            }
        }

        return result;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var beforeRules = new HashMap<String, List<String>>();
        var afterRules = new HashMap<String, List<String>>();
        var updates = new ArrayList<String>();

        readInput(input, beforeRules, afterRules, updates);

        var result = 0;

        for (var update : updates) {
            var split = update.split(",");
            var unsortedPages = Arrays.stream(split)
                                      .map(pagenumber -> new Page(pagenumber,
                                                                  Objects.requireNonNullElse(
                                                                          beforeRules.get(pagenumber),
                                                                          new ArrayList<>()),
                                                                  Objects.requireNonNullElse(afterRules.get(pagenumber),
                                                                                             new ArrayList<>())))
                                      .toList();

            var sortedPages = unsortedPages.stream()
                                           .sorted()
                                           .toList();

            if (!unsortedPages.equals(sortedPages)) {
                var middlePage = sortedPages.get(sortedPages.size() / 2);

                result += Integer.parseInt(middlePage.pageNumber());
            }
        }

        return result;
    }

    private void readInput(Stream<String> input, HashMap<String, List<String>> beforeRules,
                           HashMap<String, List<String>> afterRules, ArrayList<String> updates) {
        var lines = input.toList();
        var readingRules = true;

        for (var line : lines) {
            if (readingRules) {
                if (line.isEmpty()) {
                    readingRules = false;
                    continue;
                }
                var split = line.split("\\|");
                if (!beforeRules.containsKey(split[1])) {
                    beforeRules.put(split[1], new ArrayList<>());
                }
                if (!afterRules.containsKey(split[0])) {
                    afterRules.put(split[0], new ArrayList<>());
                }
                beforeRules.get(split[1]).add(split[0]);
                afterRules.get(split[0]).add(split[1]);
            } else {
                updates.add(line);
            }
        }
    }

    private record Page(String pageNumber, List<String> beforePages, List<String> afterPages)
            implements Comparable<Page> {

        @Override
        public int compareTo(Page o) {
            if (beforePages.contains(o.pageNumber)) {
                return 1;
            }
            if (afterPages.contains(o.pageNumber)) {
                return -1;
            }
            return 0;
        }
    }
}
