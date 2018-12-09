package nl.remcoder.adventofcode.day7;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part1 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()));

        Map<String, Step> steps = new HashMap<>();

        for (String line : lines) {
            String[] split = line.split(" ");
            String stepId = split[1];

            Step step;

            if (steps.containsKey(stepId)) {
                step = steps.get(stepId);
            } else {
                step = new Step(stepId);
                steps.put(stepId, step);
            }

            String nextStepId = split[7];

            Step nextStep;

            if (steps.containsKey(nextStepId)) {
                nextStep = steps.get(nextStepId);
            } else {
                nextStep = new Step(nextStepId);
                steps.put(nextStepId, nextStep);
            }

            nextStep.previousSteps.add(step);

            step.nextSteps.add(nextStep);
        }

        List<Step> firstSteps = new ArrayList<>();

        for (Step step : steps.values()) {
            if (step.previousSteps.isEmpty()) {
                firstSteps.add(step);
            }
        }

        TreeSet<Step> currentSteps = new TreeSet<>(firstSteps);

        Step currentStep = currentSteps.first();

        StringBuilder order = new StringBuilder();

        while (!currentSteps.isEmpty()) {
            if (currentStep == null) {
                currentStep = currentSteps.stream().filter(step -> step.previousSteps.stream().allMatch(step1 -> step1.completed)).findFirst().orElseThrow(AssertionError::new);
            }
            if (currentStep.previousSteps.stream().allMatch(step -> step.completed)) {
                currentStep.completed = true;
                currentSteps.remove(currentStep);
                currentSteps.addAll(currentStep.nextSteps);
                order.append(currentStep.stepId);
                if (!currentSteps.isEmpty()) {
                    currentStep = currentSteps.first();
                }
            } else {
                currentStep = currentSteps.lower(currentStep);
            }

            System.out.println(currentSteps);
        }

        System.out.println(order);
    }

    private static class Step implements Comparable<Step> {
        String stepId;
        List<Step> nextSteps = new ArrayList<>();
        List<Step> previousSteps = new ArrayList<>();
        private boolean completed = false;

        public Step(String stepId) {
            this.stepId = stepId;
        }

        @Override
        public String toString() {
            return "Step{" +
                   "stepId='" + stepId + '\'' +
                   '}';
        }

        @Override
        public int compareTo(Step o) {
            return stepId.compareTo(o.stepId);
        }
    }
}
