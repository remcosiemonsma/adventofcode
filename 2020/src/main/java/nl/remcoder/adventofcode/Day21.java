package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day21 {
    public int handlePart1(Stream<String> input) {
        Map<String, Integer> ingredientOccurences = new HashMap<>();
        Map<String, List<String>> possibleAllergens = new HashMap<>();

        input.forEach(foodItem -> {
            String[] split = foodItem.split(" \\(contains ");
            String[] ingredients = split[0].split(" ");
            String[] allergens = split[1].replace(")", "").split(", ");

            for (String ingredient : ingredients) {
                ingredientOccurences.compute(ingredient, (key, amount) -> {
                    if (amount == null) {
                        return 1;
                    } else {
                        return amount + 1;
                    }
                });
            }

            List<String> ingredientsList = Arrays.asList(ingredients);

            for (String allergen : allergens) {
                if (possibleAllergens.containsKey(allergen)) {
                    List<String> allergenIngredients = possibleAllergens.get(allergen);
                    allergenIngredients.removeIf(ingredient -> !ingredientsList.contains(ingredient));
                } else {
                    possibleAllergens.put(allergen, new ArrayList<>(ingredientsList));
                }
            }
        });
        Map<String, String> confirmedAllergens = new HashMap<>();

        while (!possibleAllergens.isEmpty()) {
            List<String> foundAllergens = new ArrayList<>();
            for (String allergen : possibleAllergens.keySet()) {
                List<String> ingredients = possibleAllergens.get(allergen);
                if (ingredients.size() == 1) {
                    confirmedAllergens.put(ingredients.get(0), allergen);
                    foundAllergens.add(allergen);
                } else {
                    ingredients.removeIf(confirmedAllergens::containsKey);
                }
            }
            for (String allergen : foundAllergens) {
                possibleAllergens.remove(allergen);
            }
        }

        for (String ingredient : confirmedAllergens.keySet()) {
            ingredientOccurences.remove(ingredient);
        }

        return ingredientOccurences.values().stream().mapToInt(Integer::intValue).sum();
    }

    public String handlePart2(Stream<String> input) {
        Map<String, Integer> ingredientOccurences = new HashMap<>();
        Map<String, List<String>> possibleAllergens = new HashMap<>();

        input.forEach(foodItem -> {
            String[] split = foodItem.split(" \\(contains ");
            String[] ingredients = split[0].split(" ");
            String[] allergens = split[1].replace(")", "").split(", ");

            for (String ingredient : ingredients) {
                ingredientOccurences.compute(ingredient, (key, amount) -> {
                    if (amount == null) {
                        return 1;
                    } else {
                        return amount + 1;
                    }
                });
            }

            List<String> ingredientsList = Arrays.asList(ingredients);

            for (String allergen : allergens) {
                if (possibleAllergens.containsKey(allergen)) {
                    List<String> allergenIngredients = possibleAllergens.get(allergen);
                    allergenIngredients.removeIf(ingredient -> !ingredientsList.contains(ingredient));
                } else {
                    possibleAllergens.put(allergen, new ArrayList<>(ingredientsList));
                }
            }
        });
        Map<String, String> confirmedAllergens = new HashMap<>();

        while (!possibleAllergens.isEmpty()) {
            List<String> foundAllergens = new ArrayList<>();
            for (String allergen : possibleAllergens.keySet()) {
                List<String> ingredients = possibleAllergens.get(allergen);
                if (ingredients.size() == 1) {
                    confirmedAllergens.put(ingredients.get(0), allergen);
                    foundAllergens.add(allergen);
                } else {
                    ingredients.removeIf(confirmedAllergens::containsKey);
                }
            }
            for (String allergen : foundAllergens) {
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
