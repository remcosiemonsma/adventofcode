package nl.remcoder.adventofcode.day7;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()));

        Map<Character, Step> steps = new HashMap<>();

        for (String line : lines) {
            String[] split = line.split(" ");
            char stepId = split[1].charAt(0);

            Step step;

            if (steps.containsKey(stepId)) {
                step = steps.get(stepId);
            } else {
                step = new Step(stepId);
                steps.put(stepId, step);
            }

            char nextStepId = split[7].charAt(0);

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

        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker("1"));
        workers.add(new Worker("2"));
        workers.add(new Worker("3"));
        workers.add(new Worker("4"));
        workers.add(new Worker("5"));

        int secondsToComplete = 0;

        while (!currentSteps.isEmpty() || isWorkerWorking(workers)) {
            if (currentStep == null) {
                currentStep = currentSteps.stream()
                                          .filter(step -> step.previousSteps.stream()
                                                                            .allMatch(step1 -> step1.completed))
                                          .findFirst()
                                          .orElse(null);
            }
            Optional<Worker> nextIdleWorker = findNextIdleWorker(workers);
            while (currentStep != null && nextIdleWorker.isPresent()) {
                if (currentStep.previousSteps.stream().allMatch(step -> step.completed)) {
                    Worker idleWorker = nextIdleWorker.get();

                    idleWorker.step = currentStep;
                    idleWorker.idle = false;

                    currentSteps.remove(currentStep);

                    if (!currentSteps.isEmpty()) {
                        currentStep = currentSteps.first();
                    } else {
                        currentStep = null;
                    }

                    nextIdleWorker = findNextIdleWorker(workers);
                } else {
                    currentStep = currentSteps.lower(currentStep);
                }
            }

            for (Worker worker : workers) {
                if (!worker.idle) {
                    worker.doWork();
                    if (worker.step.secondsToComplete <= 0) {
                        worker.idle = true;
                        worker.step.completed = true;

                        currentSteps.addAll(worker.step.nextSteps);
                        if (!currentSteps.isEmpty()) {
                            currentStep = currentSteps.first();
                        }

                        worker.step = null;
                    }
                }
            }

            secondsToComplete++;
            System.out.println(currentSteps);
            System.out.println(workers);
            System.out.println(secondsToComplete);
        }

        System.out.println(secondsToComplete);
    }

    private static Optional<Worker> findNextIdleWorker(List<Worker> workers) {
        return workers.stream()
                      .filter(worker -> worker.idle)
                      .findFirst();
    }

    private static boolean isWorkerWorking(List<Worker> workers) {
        return workers.stream().anyMatch(worker -> !worker.idle);
    }

    private static class Worker {
        private final String id;
        private Step step;
        private boolean idle = true;

        private Worker(String id) {
            this.id = id;
        }

        void doWork() {
            step.secondsToComplete--;
        }

        @Override
        public String toString() {
            return "Worker{" +
                   "id='" + id + '\'' +
                   ", step=" + step +
                   ", idle=" + idle +
                   '}';
        }
    }

    private static class Step implements Comparable<Step> {
        char stepId;
        List<Step> nextSteps = new ArrayList<>();
        List<Step> previousSteps = new ArrayList<>();
        private boolean completed = false;
        private int secondsToComplete;

        public Step(char stepId) {
            this.stepId = stepId;
            this.secondsToComplete = 60 + stepId - 'A';
        }

        @Override
        public String toString() {
            return "Step{" +
                   "stepId=" + stepId +
                   ", completed=" + completed +
                   ", secondsToComplete=" + secondsToComplete +
                   '}';
        }

        @Override
        public int compareTo(Step o) {
            return Character.compare(stepId, o.stepId);
        }
    }
}
