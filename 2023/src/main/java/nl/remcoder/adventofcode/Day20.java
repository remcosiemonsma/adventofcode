package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.math.Math;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day20 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        PulseCounter.reset();

        var modules = input.map(this::parseModule)
                           .collect(Collectors.toMap(Module::id, Function.identity()));

        var values = new ArrayList<>(modules.values());

        values.forEach(module -> module.setTarget(modules));

        var button = new Button(modules.get("broadcaster"));

        for (int i = 0; i < 1000; i++) {
            pushButton(button);
        }

        return PulseCounter.getPulseCount();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var modules = input.map(this::parseModule)
                           .collect(Collectors.toMap(Module::id, Function.identity()));

        var values = new ArrayList<>(modules.values());

        values.forEach(module -> module.setTarget(modules));

        var rx = (Terminator) modules.get("rx");

        var sources = rx.getSources().getFirst().getSources();

        return sources.stream()
                      .mapToLong(module -> getCountUntilHigh(module, modules))
                      .reduce(Math::lcm)
                      .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private void pushButton(Button button) {
        Queue<Module> queue = new ArrayDeque<>();
        queue.add(button);
        while (!queue.isEmpty()) {
            var next = queue.remove();
            queue.addAll(next.sendPulse());
        }
    }

    private long getCountUntilHigh(Module module, Map<String, Module> modules) {
        var button = new Button(modules.get("broadcaster"));

        var count = 0L;

        var receiver = new Terminator("receiver");

        module.addTarget(receiver);

        while (!receiver.highPulseReceived()) {
            pushButton(button);
            count++;
        }

        modules.values().forEach(Module::reset);

        return count;
    }

    private Module parseModule(String line) {
        return switch (line.charAt(0)) {
            case 'b' -> parseBroadcaster(line);
            case '%' -> parseFlipFlop(line);
            case '&' -> parseConjunction(line);
            default -> throw new AssertionError("Eek!");
        };
    }

    private Broadcaster parseBroadcaster(String line) {
        var split = line.split(" -> ");
        var targets = Arrays.asList(split[1].split(", "));
        return new Broadcaster(split[0], targets);
    }

    private FlipFLop parseFlipFlop(String line) {
        var split = line.split(" -> ");
        var targets = Arrays.asList(split[1].split(", "));
        return new FlipFLop(split[0].substring(1), targets);
    }

    private Conjunction parseConjunction(String line) {
        var split = line.split(" -> ");
        var targets = Arrays.asList(split[1].split(", "));
        return new Conjunction(split[0].substring(1), targets);
    }

    private interface Module {
        String id();

        void acceptPulse(Pulse pulse, Module source);

        List<Module> sendPulse();

        void setTarget(Map<String, Module> modules);
        void addTarget(Module target);

        void addSource(Module source);

        List<Module> getSources();

        void reset();
    }

    private static class Button implements Module {
        private final Module broadcaster;

        private Button(Module broadcaster) {
            this.broadcaster = broadcaster;
            broadcaster.addSource(this);
        }

        @Override
        public String id() {
            return "button";
        }

        @Override
        public void acceptPulse(Pulse pulse, Module source) {
        }

        @Override
        public List<Module> sendPulse() {
            broadcaster.acceptPulse(Pulse.LOW, this);
//            System.out.println("button -low-> broadcaster");
            PulseCounter.countPulse(Pulse.LOW);
            return List.of(broadcaster);
        }

        @Override
        public void setTarget(Map<String, Module> modules) {
        }

        @Override
        public void addTarget(Module target) {}

        @Override
        public void addSource(Module source) {
        }

        @Override
        public List<Module> getSources() {
            return List.of();
        }

        @Override
        public void reset() {
        }
    }

    private static class Terminator implements Module {

        private final String id;
        private final List<Module> sources = new ArrayList<>();

        private Terminator(String id) {
            this.id = id;
        }

        private boolean highPulseReceived = false;

        @Override
        public String id() {
            return id;
        }

        @Override
        public void acceptPulse(Pulse pulse, Module source) {
            if (pulse == Pulse.HIGH) {
                highPulseReceived = true;
            }
        }

        @Override
        public List<Module> sendPulse() {
            return List.of();
        }

        @Override
        public void setTarget(Map<String, Module> modules) {

        }

        @Override
        public void addTarget(Module target) {}

        @Override
        public void addSource(Module source) {
            sources.add(source);
        }

        @Override
        public List<Module> getSources() {
            return sources;
        }

        @Override
        public void reset() {
            highPulseReceived = false;
        }

        public boolean highPulseReceived() {
            return highPulseReceived;
        }
    }

    private static class Broadcaster implements Module {
        private final String id;
        private final List<String> targetIds;
        private final List<Module> targets = new ArrayList<>();
        private final List<Module> sources = new ArrayList<>();
        private Pulse nextPulse;

        private Broadcaster(String id, List<String> targetIds) {
            this.id = id;
            this.targetIds = targetIds;
        }

        @Override
        public String id() {
            return id;
        }

        @Override
        public void acceptPulse(Pulse pulse, Module source) {
            nextPulse = pulse;
        }

        @Override
        public List<Module> sendPulse() {
            targets.forEach(target -> {
//                System.out.println("%s -%s-> %s".formatted(id, nextPulse, target.id()));
                PulseCounter.countPulse(nextPulse);
                target.acceptPulse(nextPulse, this);
            });
            return targets;
        }

        @Override
        public void setTarget(Map<String, Module> modules) {
            for (var targetId : targetIds) {
                var target = modules.computeIfAbsent(targetId, Terminator::new);
                targets.add(target);
                target.addSource(this);
            }
        }

        @Override
        public void addTarget(Module target) {
            targets.add(target);
        }

        @Override
        public void addSource(Module source) {
            sources.add(source);
        }

        @Override
        public List<Module> getSources() {
            return sources;
        }

        @Override
        public void reset() {
            nextPulse = null;
        }
    }

    private static class Conjunction implements Module {
        private final String id;
        private final Map<Module, Pulse> receivedPulses = new HashMap<>();
        private final List<String> targetIds;
        private final List<Module> targets = new ArrayList<>();
        private final List<Module> sources = new ArrayList<>();

        private Conjunction(String id, List<String> targetIds) {
            this.id = id;
            this.targetIds = targetIds;
        }

        @Override
        public String id() {
            return id;
        }

        @Override
        public void acceptPulse(Pulse pulse, Module source) {
            receivedPulses.put(source, pulse);
        }

        @Override
        public List<Module> sendPulse() {
            Pulse pulse;
            if (receivedPulses.values().stream().allMatch(pulse1 -> pulse1 == Pulse.HIGH)) {
                pulse = Pulse.LOW;
            } else {
                pulse = Pulse.HIGH;
            }
            for (var target : targets) {
//                System.out.println("%s -%s-> %s".formatted(id, pulse, target.id()));
                target.acceptPulse(pulse, this);
                PulseCounter.countPulse(pulse);
            }
            return targets;
        }

        @Override
        public void setTarget(Map<String, Module> modules) {
            for (var targetId : targetIds) {
                var target = modules.computeIfAbsent(targetId, Terminator::new);
                targets.add(target);
                target.addSource(this);
            }
        }

        @Override
        public void addTarget(Module target) {
            targets.add(target);
        }

        @Override
        public void addSource(Module source) {
            receivedPulses.put(source, Pulse.LOW);
            sources.add(source);
        }

        @Override
        public List<Module> getSources() {
            return sources;
        }

        @Override
        public void reset() {
            receivedPulses.replaceAll((module, pulse) -> Pulse.LOW);
        }
    }

    private static class FlipFLop implements Module {
        private final String id;
        private final List<String> targetIds;
        private final List<Module> targets = new ArrayList<>();
        private final List<Module> sources = new ArrayList<>();
        private State state = State.OFF;
        private Pulse nextPulse;

        private FlipFLop(String id, List<String> targetIds) {
            this.id = id;
            this.targetIds = targetIds;
        }

        @Override
        public String id() {
            return id;
        }

        @Override
        public void acceptPulse(Pulse pulse, Module source) {
            if (pulse == Pulse.LOW) {
                switchState();
                nextPulse = switch (state) {
                    case OFF -> Pulse.LOW;
                    case ON -> Pulse.HIGH;
                };
            }
        }

        @Override
        public List<Module> sendPulse() {
            if (nextPulse != null) {
                for (var target : targets) {
                    target.acceptPulse(nextPulse, this);
//                    System.out.println("%s -%s-> %s".formatted(id, nextPulse, target.id()));
                    PulseCounter.countPulse(nextPulse);
                }
                nextPulse = null;
                return targets;
            } else {
                return List.of();
            }
        }

        @Override
        public void setTarget(Map<String, Module> modules) {
            for (var targetId : targetIds) {
                var target = modules.computeIfAbsent(targetId, Terminator::new);
                targets.add(target);
                target.addSource(this);
            }
        }

        @Override
        public void addTarget(Module target) {
            targets.add(target);
        }

        @Override
        public void addSource(Module source) {
            sources.add(source);
        }

        @Override
        public List<Module> getSources() {
            return sources;
        }

        @Override
        public void reset() {
            state = State.OFF;
            nextPulse = null;
        }

        private void switchState() {
            state = switch (state) {
                case ON -> State.OFF;
                case OFF -> State.ON;
            };
        }

        private enum State {
            ON,
            OFF
        }
    }

    private static class PulseCounter {
        private static long lowPulses;
        private static long highPulses;

        public static void countPulse(Pulse pulse) {
            switch (pulse) {
                case HIGH -> highPulses++;
                case LOW -> lowPulses++;
            }
        }

        public static long getPulseCount() {
//            System.out.println(lowPulses);
//            System.out.println(highPulses);
            return lowPulses * highPulses;
        }

        public static void reset() {
            lowPulses = 0;
            highPulses = 0;
        }
    }

    private enum Pulse {
        HIGH,
        LOW;

        @Override
        public String toString() {
            return switch (this) {
                case LOW -> "low";
                case HIGH -> "high";
            };
        }
    }
}
