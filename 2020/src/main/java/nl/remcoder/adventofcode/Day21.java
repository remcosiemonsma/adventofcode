package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day21 implements BiAdventOfCodeSolution<Integer, String> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var ingredientOccurences = new HashMap<String, Integer>();
        var possibleAllergens = new HashMap<String, List<String>>();

        input.forEach(foodItem -> {
            var split = foodItem.split(" \\(contains ");
            var ingredients = split[0].split(" ");
            var allergens = split[1].replace(")", "").split(", ");

            for (var ingredient : ingredients) {
                ingredientOccurences.compute(ingredient, (key, amount) -> {
                    if (amount == null) {
                        return 1;
                    } else {
                        return amount + 1;
                    }
                });
            }

            var ingredientsList = Arrays.asList(ingredients);

            for (var allergen : allergens) {
                if (possibleAllergens.containsKey(allergen)) {
                    var allergenIngredients = possibleAllergens.get(allergen);
                    allergenIngredients.removeIf(ingredient -> !ingredientsList.contains(ingredient));
                } else {
                    possibleAllergens.put(allergen, new ArrayList<>(ingredientsList));
                }
            }
        });
        var confirmedAllergens = new HashMap<String, String>();

        while (!possibleAllergens.isEmpty()) {
            var foundAllergens = new ArrayList<String>();
            for (var allergen : possibleAllergens.keySet()) {
                var ingredients = possibleAllergens.get(allergen);
                if (ingredients.size() == 1) {
                    confirmedAllergens.put(ingredients.get(0), allergen);
                    foundAllergens.add(allergen);
                } else {
                    ingredients.removeIf(confirmedAllergens::containsKey);
                }
            }
            for (var allergen : foundAllergens) {
                possibleAllergens.remove(allergen);
            }
        }

        for (var ingredient : confirmedAllergens.keySet()) {
            ingredientOccurences.remove(ingredient);
        }

        return ingredientOccurences.values().stream().mapToInt(Integer::intValue).sum();
    }

    public String handlePart2(Stream<String> input) {
        var ingredientOccurences = new HashMap<String, Integer>();
        var possibleAllergens = new HashMap<String, List<String>>();

        input.forEach(foodItem -> {
            var split = foodItem.split(" \\(contains ");
            var ingredients = split[0].split(" ");
            var allergens = split[1].replace(")", "").split(", ");

            for (var ingredient : ingredients) {
                ingredientOccurences.compute(ingredient, (key, amount) -> {
                    if (amount == null) {
                        return 1;
                    } else {
                        return amount + 1;
                    }
                });
            }

            var ingredientsList = Arrays.asList(ingredients);

            for (var allergen : allergens) {
                if (possibleAllergens.containsKey(allergen)) {
                    var allergenIngredients = possibleAllergens.get(allergen);
                    allergenIngredients.removeIf(ingredient -> !ingredientsList.contains(ingredient));
                } else {
                    possibleAllergens.put(allergen, new ArrayList<>(ingredientsList));
                }
            }
        });
        var confirmedAllergens = new HashMap<String, String>();

        while (!possibleAllergens.isEmpty()) {
            var foundAllergens = new ArrayList<String>();
            for (var allergen : possibleAllergens.keySet()) {
                var ingredients = possibleAllergens.get(allergen);
                if (ingredients.size() == 1) {
                    confirmedAllergens.put(ingredients.get(0), allergen);
                    foundAllergens.add(allergen);
                } else {
                    ingredients.removeIf(confirmedAllergens::containsKey);
                }
            }
            for (var allergen : foundAllergens) {
                possibleAllergens.remove(allergen);
            }
        }

        return confirmedAllergens.entrySet()
                                 .stream()
                                 .sorted(Map.Entry.comparingByValue())
                                 .map(Map.Entry::getKey)
                                 .reduce((s, s2) -> s + "," + s2)
                                 .orElse("");
    }
}
