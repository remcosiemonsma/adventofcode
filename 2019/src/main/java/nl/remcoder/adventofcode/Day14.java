package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 implements BiAdventOfCodeSolution<Integer, Long> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var reactions = input.map(this::createReactionFromString)
                             .collect(Collectors.toMap(reaction -> reaction.chemical,
                                                       reaction -> reaction));

        var fuelReaction = reactions.get("FUEL");

        var waste = new HashMap<String, Long>();

        return processReactionToOreNeeded(reactions, fuelReaction, waste);
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        Map<String, Reaction> reactions = input.map(this::createReactionFromString)
                                               .collect(Collectors.toMap(reaction -> reaction.chemical,
                                                                         reaction -> reaction));

        var amountOfOre = 1000000000000L;

        var fuelReaction = reactions.get("FUEL");

        var waste = new HashMap<String, Long>();

        var originalInput = new HashMap<>(fuelReaction.input);

        var amountNeededForFuel = processReactionToOreNeeded(reactions, fuelReaction, waste);

        fuelReaction.input = new HashMap<>(originalInput);

        long amountFuelProduced = 0;

        while (amountOfOre > amountNeededForFuel) {
            var amountTimesReaction = amountOfOre / amountNeededForFuel;
            System.out.println(amountTimesReaction);

            amountFuelProduced += amountTimesReaction;
            System.out.println(amountFuelProduced);

            var totalWaste = new HashMap<>(waste);

            totalWaste.replaceAll((s, amount) -> amount * amountTimesReaction);

            flattenWasteToOre(totalWaste, reactions);

            amountOfOre = totalWaste.get("ORE");

            System.out.println(amountOfOre);
        }

        return amountFuelProduced;
    }

    private void flattenWasteToOre(Map<String, Long> waste,
                                   Map<String, Reaction> reactions) {
        var amountOre = 0L;

        for (var chemical : waste.keySet()) {
            var reaction = reactions.get(chemical);

            var reactionTimes = waste.get(chemical) / reaction.amount;

            var amountOreNeeded = processReactionToOreNeeded(reactions, reaction, new HashMap<>());

            amountOre += amountOreNeeded * reactionTimes;
        }

        waste.clear();

        waste.put("ORE", amountOre);
    }

    private int processReactionToOreNeeded(Map<String, Reaction> reactions, Reaction fuelReaction,
                                           Map<String, Long> waste) {
        var reactionFinished = false;

        while (!reactionFinished) {
            var newInput = new HashMap<String, Integer>();

            for (var chemical : fuelReaction.input.keySet()) {
                if ("ORE".equals(chemical)) {
                    continue;
                }

                var amountNeeded = (long) fuelReaction.input.get(chemical);

                if (waste.getOrDefault(chemical, 0L) >= amountNeeded) {
                    var subtract = amountNeeded;
                    waste.compute(chemical, (s, amount) -> {
                        if (amount == null) {
                            throw new AssertionError();
                        }
                        return amount - subtract;
                    });
                } else {
                    amountNeeded -= waste.getOrDefault(chemical, 0L);
                    waste.put(chemical, 0L);

                    Reaction reaction = reactions.get(chemical);

                    var reactionTimes = (int) Math.ceil((double) amountNeeded / (double) reaction.amount);

                    var remainder = ((long) reaction.amount * reactionTimes) - amountNeeded;

                    waste.compute(chemical, (s, amount) -> amount == null ? remainder : amount + remainder);

                    for (var inputChemical : reaction.input.keySet()) {
                        var amountRequired = reaction.input.get(inputChemical) * reactionTimes;
                        newInput.compute(inputChemical,
                                         (s, amount) -> amount == null ? amountRequired : amount + amountRequired);
                    }
                }
            }

            if (fuelReaction.input.containsKey("ORE")) {
                newInput.compute("ORE", (s, amount) -> amount == null ? fuelReaction.input.get("ORE") :
                                                       amount + fuelReaction.input.get("ORE"));
            }

            fuelReaction.input = newInput;

            if (fuelReaction.input.keySet().equals(Set.of("ORE"))) {
                reactionFinished = true;
            }
        }

        return fuelReaction.input.get("ORE");
    }

    private Reaction createReactionFromString(String input) {
        var split = input.split(" => ");
        var inputs = split[0].split(", ");

        var reaction = new Reaction();

        reaction.input = Arrays.stream(inputs)
                               .map(s -> s.split(" "))
                               .collect(Collectors.toMap(strings -> strings[1],
                                                         strings -> Integer.parseInt(strings[0])));

        reaction.chemical = split[1].split(" ")[1];
        reaction.amount = Integer.parseInt(split[1].split(" ")[0]);

        return reaction;
    }

    private static class Reaction {
        String chemical;
        int amount;
        Map<String, Integer> input;

        @Override
        public String toString() {
            return "Reaction{" +
                   "chemical='" + chemical + '\'' +
                   ", amount=" + amount +
                   ", input=" + input +
                   '}';
        }
    }
}
