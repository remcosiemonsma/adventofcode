package nl.remcoder.adventofcode;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.IntNum;
import com.microsoft.z3.Model;
import com.microsoft.z3.Optimize;
import com.microsoft.z3.Status;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day10 implements AdventOfCodeSolution<Long> {

    @Override
    public Long handlePart1(Stream<String> input) {
        return input.map(this::createState)
                    .map(machine -> Dijkstra.findShortestDistance(List.of(machine),
                                                                  node -> ((MachineState) node).isAtWantedState()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .mapToLong(Node::getDistance)
                    .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        return input.map(this::createState)
                    .mapToLong(MachineState::calculateJoltagePresses)
                    .sum();
    }

    private MachineState createState(String line) {
        var split = line.split(" ");

        var stateDescription = split[0];

        var wantedState = new boolean[stateDescription.length() - 2];
        for (var i = 1; i < stateDescription.length() - 1; i++) {
            var c = stateDescription.charAt(i);
            wantedState[i - 1] = c == '#';
        }

        var joltageDescription = split[split.length - 1].substring(1, split[split.length - 1].length() - 1);
        var joltageSplit = joltageDescription.split(",");

        var joltageRequirements = new int[joltageSplit.length];
        for (int i = 0; i < joltageSplit.length; i++) {
            joltageRequirements[i] = Integer.parseInt(joltageSplit[i]);
        }

        var initialState = new boolean[stateDescription.length() - 2];

        var buttons = new ArrayList<List<Integer>>();

        for (var i = 1; i < split.length - 1; i++) {
            var wiringDescription = split[i].substring(1, split[i].length() - 1);
            var wiringSplit = wiringDescription.split(",");

            var button = new ArrayList<Integer>();
            for (var wiring : wiringSplit) {
                button.add(Integer.parseInt(wiring));
            }
            buttons.add(button);
        }

        var state = new MachineState(initialState, wantedState, joltageRequirements, buttons, new HashMap<>());
        state.setDistance(0);
        return state;
    }

    private static class MachineState extends Node<MachineState> {

        private final boolean[] state;

        private final boolean[] wantedState;

        private final int[] joltageRequirements;

        private final List<List<Integer>> buttons;

        private final Map<boolean[], MachineState> states;

        private MachineState(boolean[] state, boolean[] wantedState, int[] joltageRequirements, List<List<Integer>> buttons, Map<boolean[], MachineState> states) {
            this.state = state;
            this.wantedState = wantedState;
            this.joltageRequirements = joltageRequirements;
            this.buttons = buttons;
            this.states = states;
        }

        @Override
        public Map<MachineState, Long> getNeighbors() {
            var nextStates = new HashMap<MachineState, Long>();

            for (var button : buttons) {
                var newState = Arrays.copyOf(state, state.length);
                for (var toggle : button) {
                    newState[toggle] = !newState[toggle];
                }
                var nextState = states.computeIfAbsent(newState,
                                                       state -> new MachineState(state, wantedState,
                                                                                 joltageRequirements, buttons, states));

                nextStates.put(nextState, 1L);
            }

            return nextStates;
        }

        @Override
        public void printStateInformation() {

        }

        @Override
        public String toString() {
            return "MachineState{" +
                    "state=" + Arrays.toString(state) +
                    ", buttons=" + buttons +
                    '}';
        }

        public boolean isAtWantedState() {
            return Arrays.equals(state, wantedState);
        }

        public int calculateJoltagePresses() {
            Optimize opt;
            IntExpr presses;
            BoolExpr totalPressesEq;
            try (Context ctx = new Context()) {
                opt = ctx.mkOptimize();
                presses = ctx.mkIntConst("presses");

                IntExpr[] buttonVars = IntStream.range(0, buttons.size())
                                                .mapToObj(i -> ctx.mkIntConst("button" + i))
                                                .toArray(IntExpr[]::new);

                Map<Integer, List<IntExpr>> countersToButtons = new HashMap<>();

                for (int i = 0; i < buttons.size(); i++) {
                    IntExpr buttonVar = buttonVars[i];
                    for (int flip : buttons.get(i)) {
                        countersToButtons.computeIfAbsent(flip, _ -> new ArrayList<>()).add(buttonVar);
                    }
                }

                for (Map.Entry<Integer, List<IntExpr>> entry : countersToButtons.entrySet()) {
                    int counterIndex = entry.getKey();
                    List<IntExpr> counterButtons = entry.getValue();

                    IntExpr targetValue = ctx.mkInt(joltageRequirements[counterIndex]);

                    IntExpr[] buttonPressesArray = counterButtons.toArray(new IntExpr[0]);

                    IntExpr sumOfButtonPresses = (IntExpr) ctx.mkAdd(buttonPressesArray);

                    BoolExpr equation = ctx.mkEq(targetValue, sumOfButtonPresses);
                    opt.Add(equation);
                }

                IntExpr zero = ctx.mkInt(0);
                for (IntExpr buttonVar : buttonVars) {
                    BoolExpr nonNegative = ctx.mkGe(buttonVar, zero);
                    opt.Add(nonNegative);
                }

                IntExpr sumOfAllButtonVars = (IntExpr) ctx.mkAdd(buttonVars);
                totalPressesEq = ctx.mkEq(presses, sumOfAllButtonVars);

                opt.Add(totalPressesEq);

                opt.MkMinimize(presses);

                Status status = opt.Check();

                if (status == Status.SATISFIABLE) {
                    Model model = opt.getModel();
                    IntNum outputValue = (IntNum) model.evaluate(presses, false);
                    return outputValue.getInt();
                } else if (status == Status.UNSATISFIABLE) {
                    throw new AssertionError("Problem is UNSATISFIABLE (no solution exists).");
                } else {
                    throw new AssertionError("Optimization could not be determined (" + status + ").");
                }
            }
        }
    }
}
