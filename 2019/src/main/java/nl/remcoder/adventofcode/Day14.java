package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 {
    public int handlePart1(Stream<String> input) {
        Map<String, Reaction> reactions = input.map(this::createReactionFromString)
                                               .collect(Collectors.toMap(reaction -> reaction.chemical,
                                                                         reaction -> reaction));

        Reaction fuelReaction = reactions.get("FUEL");

        Map<String, Long> waste = new HashMap<>();

        return processReactionToOreNeeded(reactions, fuelReaction, waste);
    }

    public long handlePart2(Stream<String> input) {
        Map<String, Reaction> reactions = input.map(this::createReactionFromString)
                                               .collect(Collectors.toMap(reaction -> reaction.chemical,
                                                                         reaction -> reaction));

        long amountOfOre = 1000000000000L;

        Reaction fuelReaction = reactions.get("FUEL");

        Map<String, Long> waste = new HashMap<>();

        Map<String, Integer> originalInput = fuelReaction.input;

        int amountNeededForFuel = processReactionToOreNeeded(reactions, fuelReaction, waste);

        fuelReaction.input = originalInput;

        long amountFuelProduced = 0;

        while (amountOfOre > amountNeededForFuel) {
            long amountTimesReaction = amountOfOre / amountNeededForFuel;
            System.out.println(amountTimesReaction);

            amountFuelProduced += amountTimesReaction;
            System.out.println(amountFuelProduced);

            HashMap<String, Long> totalWaste = new HashMap<>(waste);

            totalWaste.replaceAll((s, amount) -> amount * amountTimesReaction);

            flattenWasteToOre(totalWaste, reactions);

            amountOfOre = totalWaste.get("ORE");

            System.out.println(amountOfOre);
        }

        return amountFuelProduced;
    }

    private void flattenWasteToOre(Map<String, Long> waste,
                                   Map<String, Reaction> reactions) {
        long amountOre = 0;

        for (String chemical : waste.keySet()) {
            Reaction reaction = reactions.get(chemical);

            long reactionTimes = waste.get(chemical) / reaction.amount;

            int amountOreNeeded = processReactionToOreNeeded(reactions, reaction, new HashMap<>());

            amountOre += amountOreNeeded * reactionTimes;
        }

        waste.clear();

        waste.put("ORE", amountOre);
    }

    private int processReactionToOreNeeded(Map<String, Reaction> reactions, Reaction fuelReaction,
                                           Map<String, Long> waste) {
        boolean reactionFinished = false;

        while (!reactionFinished) {
            Map<String, Integer> newInput = new HashMap<>();

            for (String chemical : fuelReaction.input.keySet()) {
                if ("ORE".equals(chemical)) {
                    continue;
                }

                int amountNeeded = fuelReaction.input.get(chemical);

                if (waste.getOrDefault(chemical, 0L) >= amountNeeded) {
                    int subtract = amountNeeded;
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

                    int reactionTimes = (int) Math.ceil((double) amountNeeded / (double) reaction.amount);

                    int remainder = (reaction.amount * reactionTimes) - amountNeeded;

                    waste.compute(chemical, (s, amount) -> amount == null ? remainder : amount + remainder);

                    for (String inputChemical : reaction.input.keySet()) {
                        int amountRequired = reaction.input.get(inputChemical) * reactionTimes;
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
        String[] split = input.split(" => ");
        String[] inputs = split[0].split(", ");

        Reaction reaction = new Reaction();

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
