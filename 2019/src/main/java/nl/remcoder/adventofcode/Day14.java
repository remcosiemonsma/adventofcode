package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 {
    public int handlePart1(Stream<String> input) {
        Map<String, Reaction> reactions = input.map(this::createReactionFromString)
                                               .collect(Collectors.toMap(reaction -> reaction.chemical,
                                                                         reaction -> reaction));

        Reaction fuelReaction = reactions.get("FUEL");

        Map<String, Integer> waste = new HashMap<>();

        boolean reactionFinished = false;

        while (!reactionFinished) {
            Map<String, Integer> newInput = new HashMap<>();

            for (String chemical : fuelReaction.input.keySet()) {
                if ("ORE".equals(chemical)) {
                    continue;
                }

                int amountNeeded = fuelReaction.input.get(chemical);

                if (waste.getOrDefault(chemical, 0) >= amountNeeded) {
                    int subtract = amountNeeded;
                    waste.compute(chemical, (s, amount) -> {
                        if (amount == null) {
                            throw new AssertionError();
                        }
                        return amount - subtract;
                    });
                } else {
                    amountNeeded -= waste.getOrDefault(chemical, 0);
                    waste.put(chemical, 0);

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

        System.out.println(fuelReaction);

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
