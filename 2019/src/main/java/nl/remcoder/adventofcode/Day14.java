package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var reactions = input.map(this::createReactionFromString)
                             .collect(Collectors.toMap(reaction -> reaction.chemical,
                                                       reaction -> reaction));

        return calculateRequiredOre(reactions, 1);
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var reactions = input.map(this::createReactionFromString)
                             .collect(Collectors.toMap(reaction -> reaction.chemical,
                                                       reaction -> reaction));

        var requiredOre = calculateRequiredOre(reactions, 1);

        var target = 1000000000000L;
        var lower = (target / requiredOre) - 1000;
        var higher = (target / requiredOre) + 1000000000;
        while (lower < higher) {
            long mid = (lower + higher) / 2;
            long guess = calculateRequiredOre(reactions, mid);
            if (guess > target) {
                higher = mid;
            } else if (guess < target) {
                if (mid == lower) {
                    break;
                }
                lower = mid;
            } else {
                lower = mid;
                break;
            }
        }

        return lower;
    }

    private long calculateRequiredOre(Map<String, Reaction> reactions, long fuelQuantity) {
        var requiredOre = 0L;
        var leftovers = new HashMap<String, Long>();
        var queue = new LinkedList<Pair<String, Long>>();

        queue.add(new Pair<>("FUEL", fuelQuantity));
        while (!queue.isEmpty()) {
            var element = queue.remove();
            var requiredChemical = element.left();
            var requiredQuantity = element.right();

            if ("ORE".equals(requiredChemical)) {
                requiredOre += requiredQuantity;
                continue;
            }

            var leftoverQuantity = leftovers.getOrDefault(requiredChemical, 0L);
            if (leftoverQuantity < requiredQuantity) {
                leftovers.remove(requiredChemical);
                var quantityToProduce = requiredQuantity - leftoverQuantity;
                var reaction = reactions.get(requiredChemical);
                var producedQuantity = reaction.amount;
                var inputChemicals = reaction.input;

                var batchesRequired = (quantityToProduce + producedQuantity - 1) / producedQuantity;
                if (producedQuantity * batchesRequired > quantityToProduce) {
                    leftovers.put(requiredChemical, (producedQuantity * batchesRequired) - quantityToProduce);
                }
                inputChemicals.forEach(
                        (chemical, amount) -> queue.add(new Pair<>(chemical, (long) amount * batchesRequired)));
            } else if (leftoverQuantity.equals(requiredQuantity)) {
                leftovers.remove(requiredChemical);
            } else {
                leftovers.put(requiredChemical, leftoverQuantity - requiredQuantity);
            }
        }
        return requiredOre;
    }

    private Reaction createReactionFromString(String reactionString) {
        var split = reactionString.split(" => ");
        var inputs = split[0].split(", ");

        var input = Arrays.stream(inputs)
                          .map(s -> s.split(" "))
                          .collect(Collectors.toMap(strings -> strings[1],
                                                    strings -> Integer.parseInt(strings[0])));
        var chemical = split[1].split(" ")[1];
        var amount = Integer.parseInt(split[1].split(" ")[0]);

        return new Reaction(chemical, amount, input);
    }

    private record Reaction(String chemical, int amount, Map<String, Integer> input) {
    }
}
