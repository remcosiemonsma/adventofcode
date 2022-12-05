package nl.remcoder.adventofcode;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day19 {
    private static final Random random = new Random();

    public int handlePart1(Stream<String> input) {
        var replacements = new HashMap<String, Set<String>>();

        var data = input.filter(Predicate.not(String::isBlank))
                        .toList();

        var molecule = data.get(data.size() - 1);

        fillReplacements(replacements, data);

        var results = createNewMolecules(replacements, molecule);

        return results.size();
    }

    private static Set<String> createNewMolecules(Map<String, Set<String>> replacements, String molecule) {
        var results = new HashSet<String>();

        for (var key : replacements.keySet()) {
            for (var replacement : replacements.get(key)) {
                var position = 0;
                while ((position = molecule.indexOf(key, position)) >= 0) {
                    results.add(replace(molecule, key, replacement, position));
                    position += key.length();
                }
            }
        }
        return results;
    }

    public int handlePart2(Stream<String> input) {
        var replacements = new HashMap<String, Set<String>>();

        var data = input.filter(Predicate.not(String::isBlank))
                        .sorted((o1, o2) -> randomize())
                        .toList();

        var molecule = new Molecule();

        molecule.molecule = data.stream()
                                .filter(s -> !s.contains("=>"))
                                .findFirst()
                                .orElseThrow(() -> new AssertionError("Eek!"));

        fillReplacements(replacements, data);

        var steps = new AtomicInteger(0);

        while (!molecule.molecule.equals("e")) {
            replacements.keySet().stream()
                        .sorted((o1, o2) -> randomize())
                        .forEach(key -> {
                            for (var replacement : replacements.get(key)) {
                                if (molecule.molecule.contains(replacement)) {
                                    molecule.molecule = replace(molecule.molecule, replacement, key,
                                                                molecule.molecule.lastIndexOf(replacement));
                                    System.out.println(molecule.molecule);
                                    steps.incrementAndGet();
                                }
                            }
                        });
        }

        return steps.get();
    }

    private int randomize() {
        return random.nextInt(-1, 1);
    }

    private void fillReplacements(Map<String, Set<String>> replacements, List<String> data) {
        data.stream()
            .map(s -> s.split(" => "))
            .filter(split -> split.length > 1).forEach(split -> {
                if (!replacements.containsKey(split[0])) {
                    replacements.put(split[0], new HashSet<>());
                }
                replacements.get(split[0]).add(split[1]);
            });
    }

    private static String replace(String s, String in, String out, int position) {
        return s.substring(0, position) + out + s.substring(position + in.length());
    }

    private static class Molecule {
        private String molecule;
    }
}
