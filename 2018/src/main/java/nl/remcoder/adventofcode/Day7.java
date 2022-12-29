package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day7 implements BiAdventOfCodeSolution<String, Integer> {
    private static int secondsToAdd = 60;
    private int amountOfWorkers;

    @Override
    public String handlePart1(Stream<String> input) {
        var steps = processSteps(input);

        var firstSteps = new ArrayList<Step>();

        for (var step : steps.values()) {
            if (step.previousSteps.isEmpty()) {
                firstSteps.add(step);
            }
        }

        var currentSteps = new TreeSet<>(firstSteps);

        var currentStep = currentSteps.first();

        var order = new StringBuilder();

        while (!currentSteps.isEmpty()) {
            if (currentStep == null) {
                currentStep = currentSteps.stream()
                                          .filter(step -> step.previousSteps.stream()
                                                                            .allMatch(step1 -> step1.completed))
                                          .findFirst()
                                          .orElseThrow(() -> new AssertionError("Eek!"));
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
        }

        return order.toString();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var steps = processSteps(input);

        var firstSteps = new ArrayList<Step>();

        for (Step step : steps.values()) {
            if (step.previousSteps.isEmpty()) {
                firstSteps.add(step);
            }
        }

        var currentSteps = new TreeSet<>(firstSteps);

        var currentStep = currentSteps.first();

        var workers = IntStream.rangeClosed(1, amountOfWorkers)
                               .mapToObj(Worker::new)
                               .toList();
        
        var secondsToComplete = 0;

        while (!currentSteps.isEmpty() || isWorkerWorking(workers)) {
            if (currentStep == null) {
                currentStep = currentSteps.stream()
                                          .filter(step -> step.previousSteps.stream()
                                                                            .allMatch(step1 -> step1.completed))
                                          .findFirst()
                                          .orElse(null);
            }
            var nextIdleWorker = findNextIdleWorker(workers);
            while (currentStep != null && nextIdleWorker.isPresent()) {
                if (currentStep.previousSteps.stream()
                                             .allMatch(step -> step.completed)) {
                    var idleWorker = nextIdleWorker.get();

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

            for (var worker : workers) {
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
        }

        return secondsToComplete;
    }

    public void setAmountOfWorkers(int amountOfWorkers) {
        this.amountOfWorkers = amountOfWorkers;
    }

    public static void setSecondsToAdd(int secondsToAdd) {
        Day7.secondsToAdd = secondsToAdd;
    }

    private HashMap<Character, Step> processSteps(Stream<String> input) {
        var steps = new HashMap<Character, Step>();

        input.forEach(line -> {
            var split = line.split(" ");
            var stepId = split[1].charAt(0);

            var step = steps.computeIfAbsent(stepId, Step::new);

            var nextStepId = split[7].charAt(0);

            var nextStep = steps.computeIfAbsent(nextStepId, Step::new);

            nextStep.previousSteps.add(step);

            step.nextSteps.add(nextStep);
        });
        return steps;
    }

    private Optional<Worker> findNextIdleWorker(List<Worker> workers) {
        return workers.stream()
                      .filter(worker -> worker.idle)
                      .findFirst();
    }

    private boolean isWorkerWorking(List<Worker> workers) {
        return workers.stream()
                      .anyMatch(worker -> !worker.idle);
    }

    private static class Worker {
        private final int id;
        private Step step;
        private boolean idle = true;

        private Worker(int id) {
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
            this.secondsToComplete = secondsToAdd + stepId - 'A';
        }

        @Override
        public String toString() {
            return "Step{" +
                   "stepId='" + stepId + '\'' +
                   '}';
        }

        @Override
        public int compareTo(Step o) {
            return Character.compare(stepId, o.stepId);
        }
    }
}
