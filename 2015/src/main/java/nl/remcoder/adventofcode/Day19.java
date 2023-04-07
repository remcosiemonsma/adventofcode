package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day19 implements AdventOfCodeSolution<Integer> {
    private static final Random random = new Random();

    @Override
    public Integer handlePart1(Stream<String> input) {
        var replacements = new HashMap<String, Set<String>>();

        var data = input.filter(Predicate.not(String::isBlank))
                        .toList();

        var molecule = data.get(data.size() - 1);

        fillReplacements(replacements, data);

        var results = createNewMolecules(replacements, molecule);

        return results.size();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var replacements = new HashMap<String, Set<String>>();

        var data = input.filter(Predicate.not(String::isBlank))
                        .sorted((o1, o2) -> randomize())
                        .toList();

        var molecule = new Molecule();

        var originalMolecule = data.stream()
                                   .filter(s -> !s.contains("=>"))
                                   .findFirst()
                                   .orElseThrow(() -> new AssertionError("Eek!"));
        molecule.molecule = originalMolecule;

        fillReplacements(replacements, data);

        var steps = new AtomicInteger(0);

        var previous = molecule.molecule;

        while (!molecule.molecule.equals("e")) {
            replacements.keySet()
                        .stream()
                        .sorted((o1, o2) -> randomize())
                        .forEach(key -> {
                            for (var replacement : replacements.get(key)) {
                                if (molecule.molecule.contains(replacement)) {
                                    molecule.molecule = replace(molecule.molecule, replacement, key,
                                                                molecule.molecule.lastIndexOf(replacement));
                                    steps.incrementAndGet();
                                }
                            }
                        });
            if (previous.equals(molecule.molecule)) {
                molecule.molecule = originalMolecule;
                steps.set(0);
            } else {
                previous = molecule.molecule;
            }
        }

        return steps.get();
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
