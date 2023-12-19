package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day19 implements AdventOfCodeSolution<Long> {
    private static final Pattern WORKFLOW_PATTERN = Pattern.compile("(\\w+)\\{(.+)}");
    private static final Pattern RULE_PATTERN = Pattern.compile("(\\w+)([<>])(\\d+):(\\w+)");
    private static final Pattern PART_PATTERN = Pattern.compile("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)}");

    @Override
    public Long handlePart1(Stream<String> input) {
        var lines = input.toList();

        var emptyIndex = lines.indexOf("");

        var workflows = lines.stream()
                             .limit(emptyIndex)
                             .map(this::mapWorkflow)
                             .collect(Collectors.toMap(Workflow::id, Function.identity()));

        workflows.values()
                 .stream()
                 .map(ProcessingWorkflow.class::cast)
                 .forEach(workflow -> workflow.setWorkflows(workflows));

        var acceptanceWorkflow = new TerminatingWorkflow("A");
        var rejectanceWorkflow = new TerminatingWorkflow("R");

        workflows.put("A", acceptanceWorkflow);
        workflows.put("R", rejectanceWorkflow);

        var startWorkflow = workflows.get("in");

        lines.stream()
             .skip(emptyIndex + 1)
             .map(this::mapPart)
             .forEach(startWorkflow::accept);

        return acceptanceWorkflow.parts()
                                 .stream()
                                 .mapToLong(Part::rating)
                                 .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var lines = input.toList();

        var emptyIndex = lines.indexOf("");

        var workflows = lines.stream()
                             .limit(emptyIndex)
                             .map(s -> s.split("\\{"))
                             .collect(Collectors.toMap(s -> s[0],
                                                       s -> s[1].replace("}", "")));

        workflows.put("A", "A");
        workflows.put("R", "R");

        var first = workflows.get("in");

        var xRange = List.of(new Range(1, 4000));
        var mRange = List.of(new Range(1, 4000));
        var aRange = List.of(new Range(1, 4000));
        var sRange = List.of(new Range(1, 4000));

        TerminatingNode acceptingNode = new TerminatingNode();
        TerminatingNode rejectingNode = new TerminatingNode();
        processNode(first, xRange, mRange, aRange, sRange, workflows, acceptingNode, rejectingNode);

        return acceptingNode.calculateRanges();
    }

    private void processNode(String line, List<Range> xRange, List<Range> mRange, List<Range> aRange,
                             List<Range> sRange, Map<String, String> workflows, TerminatingNode acceptingNode,
                             TerminatingNode rejectingNode) {
        String leftString;
        String rightString = null;
        if (line.contains(",")) {
            leftString = line.substring(0, line.indexOf(','));
            rightString = line.substring(line.indexOf(',') + 1);
        } else {
            leftString = line;
        }

        if (rightString != null) {
            var leftMatcher = RULE_PATTERN.matcher(leftString);

            if (leftMatcher.matches()) {
                var rating = leftMatcher.group(1);
                var test = leftMatcher.group(2);
                var value = Integer.parseInt(leftMatcher.group(3));

                var rangeToTest = switch (rating) {
                    case "x" -> xRange;
                    case "m" -> mRange;
                    case "a" -> aRange;
                    case "s" -> sRange;
                    default -> throw new AssertionError("Eek!");
                };

                var splitRanges = splitRanges(test, value, rangeToTest);

                switch (rating) {
                    case "x" ->
                            processNode(workflows.get(leftMatcher.group(4)), splitRanges.left(), mRange, aRange, sRange,
                                        workflows, acceptingNode, rejectingNode);
                    case "m" ->
                            processNode(workflows.get(leftMatcher.group(4)), xRange, splitRanges.left(), aRange, sRange,
                                        workflows, acceptingNode, rejectingNode);
                    case "a" ->
                            processNode(workflows.get(leftMatcher.group(4)), xRange, mRange, splitRanges.left(), sRange,
                                        workflows, acceptingNode, rejectingNode);
                    case "s" ->
                            processNode(workflows.get(leftMatcher.group(4)), xRange, mRange, aRange, splitRanges.left(),
                                        workflows, acceptingNode, rejectingNode);
                    default -> throw new AssertionError("Eek!");
                }
                switch (rating) {
                    case "x" -> processNode(rightString, splitRanges.right(), mRange, aRange, sRange, workflows,
                                            acceptingNode, rejectingNode);
                    case "m" -> processNode(rightString, xRange, splitRanges.right(), aRange, sRange, workflows,
                                            acceptingNode, rejectingNode);
                    case "a" -> processNode(rightString, xRange, mRange, splitRanges.right(), sRange, workflows,
                                            acceptingNode, rejectingNode);
                    case "s" -> processNode(rightString, xRange, mRange, aRange, splitRanges.right(), workflows,
                                            acceptingNode, rejectingNode);
                    default -> throw new AssertionError("Eek!");
                }
            }
        } else {
            if ("A".equals(leftString)) {
                acceptingNode.acceptRanges(xRange, mRange, aRange, sRange);
            } else if ("R".equals(leftString)) {
                rejectingNode.acceptRanges(xRange, mRange, aRange, sRange);
            } else {
                processNode(workflows.get(leftString), xRange, mRange, aRange, sRange, workflows, acceptingNode,
                            rejectingNode);
            }
        }
    }

    private Pair<List<Range>, List<Range>> splitRanges(String test, int value, List<Range> ranges) {
        var leftRange = new ArrayList<Range>();
        var rightRange = new ArrayList<Range>();

        if (">".equals(test)) {
            for (var range : ranges) {
                if (range.start > value && range.end > value) {
                    leftRange.add(range);
                } else if (range.start <= value && range.end <= value) {
                    rightRange.add(range);
                } else {
                    rightRange.add(new Range(range.start, value));
                    leftRange.add(new Range(value + 1, range.end));
                }
            }
        } else if ("<".equals(test)) {
            for (var range : ranges) {
                if (range.start < value && range.end < value) {
                    leftRange.add(range);
                } else if (range.start >= value && range.end >= value) {
                    rightRange.add(range);
                } else {
                    leftRange.add(new Range(range.start, value - 1));
                    rightRange.add(new Range(value, range.end));
                }
            }
        }

        return new Pair<>(leftRange, rightRange);
    }

    private Part mapPart(String line) {
        var matcher = PART_PATTERN.matcher(line);

        if (matcher.matches()) {
            return new Part(Integer.parseInt(matcher.group(1)),
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3)),
                            Integer.parseInt(matcher.group(4)));
        } else {
            throw new AssertionError("Eek!");
        }
    }

    private Workflow mapWorkflow(String line) {
        var matcher = WORKFLOW_PATTERN.matcher(line);

        if (matcher.matches()) {
            var id = matcher.group(1);
            var ruleLine = matcher.group(2);

            var rulesSplit = ruleLine.split(",");

            var rules = Arrays.stream(rulesSplit)
                              .map(this::processRule)
                              .toList();

            return new ProcessingWorkflow(id, rules);
        } else {
            throw new AssertionError("Eek!");
        }
    }

    private WorkflowRule processRule(String ruleLine) {
        var matcher = RULE_PATTERN.matcher(ruleLine);

        if (matcher.matches()) {
            Function<Part, Integer> valueFunction = switch (matcher.group(1)) {
                case "x" -> Part::x;
                case "m" -> Part::m;
                case "a" -> Part::a;
                case "s" -> Part::s;
                default -> throw new AssertionError("Ook!");
            };

            Predicate<Integer> test = switch (matcher.group(2)) {
                case "<" -> new Predicate<>() {
                    final int value = Integer.parseInt(matcher.group(3));

                    @Override
                    public boolean test(Integer integer) {
                        return integer < value;
                    }
                };
                case ">" -> new Predicate<>() {
                    final int value = Integer.parseInt(matcher.group(3));

                    @Override
                    public boolean test(Integer integer) {
                        return integer > value;
                    }
                };
                default -> throw new AssertionError("Ook!");
            };

            return new WorkflowRule(valueFunction, test, matcher.group(4));
        } else {
            return new WorkflowRule(part -> 0, integer -> true, ruleLine);
        }
    }

    private interface Workflow {

        String id();

        void accept(Part part);

    }

    private static class TerminatingWorkflow implements Workflow {

        public final String id;
        private final List<Part> parts = new ArrayList<>();

        private TerminatingWorkflow(String id) {
            this.id = id;
        }

        @Override
        public String id() {
            return id;
        }

        @Override
        public void accept(Part part) {
            parts.add(part);
        }

        public List<Part> parts() {
            return parts;
        }

    }

    private static class ProcessingWorkflow implements Workflow {

        private final String id;
        private final List<WorkflowRule> workflowRules;
        private Map<String, Workflow> workflows;

        private ProcessingWorkflow(String id, List<WorkflowRule> workflowRules) {
            this.id = id;
            this.workflowRules = workflowRules;
        }

        @Override
        public String id() {
            return id;
        }

        @Override
        public void accept(Part part) {
            workflows.get(workflowRules.stream()
                                       .filter(workflowRule -> workflowRule.test(part))
                                       .findFirst()
                                       .map(WorkflowRule::target)
                                       .orElseThrow(() -> new AssertionError("Eek!"))).accept(part);
        }

        public void setWorkflows(Map<String, Workflow> workflows) {
            this.workflows = workflows;
        }

    }

    private record WorkflowRule(Function<Part, Integer> valueFunction, Predicate<Integer> test, String target) {

        public boolean test(Part part) {
            return test.test(valueFunction.apply(part));
        }
    }

    private record Part(int x, int m, int a, int s) {

        public int rating() {
            return x + m + a + s;
        }
    }

    private static class TerminatingNode {
        private final List<RangeContainer> rangeContainers = new ArrayList<>();

        public void acceptRanges(List<Range> xRange, List<Range> mRange, List<Range> aRange, List<Range> sRange) {
            rangeContainers.add(new RangeContainer(xRange, mRange, aRange, sRange));
        }

        public long calculateRanges() {
            var rangeSum = 0L;

            for (var rangeContainer : rangeContainers) {
                var rangeX = rangeContainer.xRange().stream().mapToLong(range -> (range.end() - range.start()) + 1).sum();
                var rangeM = rangeContainer.mRange().stream().mapToLong(range -> (range.end() - range.start()) + 1).sum();
                var rangeA = rangeContainer.aRange().stream().mapToLong(range -> (range.end() - range.start()) + 1).sum();
                var rangeS = rangeContainer.sRange().stream().mapToLong(range -> (range.end() - range.start()) + 1).sum();

                rangeSum += rangeX * rangeM * rangeA * rangeS;
            }

            return rangeSum;
        }
    }

    private record RangeContainer(List<Range> xRange, List<Range> mRange, List<Range> aRange, List<Range> sRange) {}

    private record Range(int start, int end) {
    }
}