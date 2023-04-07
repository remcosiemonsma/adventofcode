package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day15 implements AdventOfCodeSolution<Integer> {
    private static final Pattern INGREDIENT_PATTERN = Pattern.compile(
            "(.*): capacity (-?\\d*), durability (-?\\d*), flavor (-?\\d*), texture (-?\\d*), calories (-?\\d*)");

    @Override
    public Integer handlePart1(Stream<String> input) {
        var ingredients = input.map(INGREDIENT_PATTERN::matcher)
                               .filter(Matcher::matches)
                               .map(this::mapToIngredient)
                               .toList();

        var amounts = new int[ingredients.size()];

        var memo = new HashMap<Amounts, Integer>();

        return calculateHighestScore(new Amounts(amounts), ingredients, memo);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var ingredients = input.map(INGREDIENT_PATTERN::matcher)
                               .filter(Matcher::matches)
                               .map(this::mapToIngredient)
                               .toList();

        var amounts = new int[ingredients.size()];

        var memo = new HashMap<Amounts, Integer>();

        return calculateHighestScoreWithCalories(new Amounts(amounts), ingredients, memo);
    }

    private Integer calculateHighestScoreWithCalories(Amounts amounts, List<Ingredient> ingredients,
                                                      Map<Amounts, Integer> memo) {
        if (memo.containsKey(amounts)) {
            return memo.get(amounts);
        }
        if (countCalories(amounts.amounts, ingredients) > 500) {
            memo.put(amounts, 0);
            return 0;
        }
        if (Arrays.stream(amounts.amounts).sum() == 100) {
            if (countCalories(amounts.amounts, ingredients) == 500) {
                int score = calculateScore(amounts.amounts, ingredients);
                memo.put(amounts, score);
                return score;
            } else {
                memo.put(amounts, 0);
                return 0;
            }
        } else {
            int highestScore = Integer.MIN_VALUE;
            for (int i = 0; i < amounts.amounts.length; i++) {
                int[] newAmounts = Arrays.copyOf(amounts.amounts, amounts.amounts.length);
                newAmounts[i]++;
                int score = calculateHighestScoreWithCalories(new Amounts(newAmounts), ingredients, memo);
                if (score > highestScore) {
                    highestScore = score;
                }
            }
            memo.put(amounts, highestScore);
            return highestScore;
        }
    }

    private Integer calculateHighestScore(Amounts amounts,
                                          List<Ingredient> ingredients,
                                          Map<Amounts, Integer> memo) {
        if (memo.containsKey(amounts)) {
            return memo.get(amounts);
        }

        if (Arrays.stream(amounts.amounts).sum() == 100) {
            var score = calculateScore(amounts.amounts, ingredients);
            memo.put(amounts, score);
            return score;
        } else {
            var highestScore = Integer.MIN_VALUE;
            for (int i = 0; i < amounts.amounts.length; i++) {
                var newAmounts = Arrays.copyOf(amounts.amounts, amounts.amounts.length);
                newAmounts[i]++;
                var score = calculateHighestScore(new Amounts(newAmounts), ingredients, memo);
                if (score > highestScore) {
                    highestScore = score;
                }
            }
            memo.put(amounts, highestScore);
            return highestScore;
        }
    }

    private int countCalories(int[] amounts, List<Ingredient> ingredients) {
        var totalCalories = 0;

        for (var i = 0; i < amounts.length; i++) {
            totalCalories += ingredients.get(i).calories * amounts[i];
        }

        return totalCalories;
    }

    private int calculateScore(int[] amounts, List<Ingredient> ingredients) {
        var totalCapacity = 0;
        var totalDurability = 0;
        var totalFlavor = 0;
        var totalTexture = 0;
        for (var i = 0; i < amounts.length; i++) {
            totalCapacity += ingredients.get(i).capacity * amounts[i];
            totalDurability += ingredients.get(i).durability * amounts[i];
            totalFlavor += ingredients.get(i).flavor * amounts[i];
            totalTexture += ingredients.get(i).texture * amounts[i];
        }

        if (totalCapacity < 0) {
            totalCapacity = 0;
        }
        if (totalDurability < 0) {
            totalDurability = 0;
        }
        if (totalFlavor < 0) {
            totalFlavor = 0;
        }
        if (totalTexture < 0) {
            totalTexture = 0;
        }

        return totalCapacity * totalDurability * totalFlavor * totalTexture;
    }

    private Ingredient mapToIngredient(Matcher matcher) {
        return new Ingredient(Integer.parseInt(matcher.group(2)),
                              Integer.parseInt(matcher.group(3)),
                              Integer.parseInt(matcher.group(4)),
                              Integer.parseInt(matcher.group(5)),
                              Integer.parseInt(matcher.group(6)));
    }

    private record Amounts(int[] amounts) {
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Amounts amounts1 = (Amounts) o;
            return Arrays.equals(amounts, amounts1.amounts);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(amounts);
        }
    }

    private record Ingredient(int capacity, int durability, int flavor, int texture, int calories) {
    }
}
