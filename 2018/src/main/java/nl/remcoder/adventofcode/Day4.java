package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Day4 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var strings = input.sorted(Comparator.comparing(s -> s.substring(0, 19)))
                           .toList();

        var guards = processSleepingSchedule(strings);

        var mostSleepyGuard = guards.values()
                                    .stream()
                                    .max(Comparator.comparing(Guard::getTotalMinutesAsleep))
                                    .orElseThrow(() -> new AssertionError("Eek!"));

        var highestValue = 0;
        var minute = 0;

        for (var i = 0; i < 60; i++) {
            if (mostSleepyGuard.minutesAsleep[i] > highestValue) {
                highestValue = mostSleepyGuard.minutesAsleep[i];
                minute = i;
            }
        }

        return mostSleepyGuard.id * minute;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var strings = input.sorted(Comparator.comparing(s -> s.substring(0, 19)))
                           .toList();

        var guards = processSleepingSchedule(strings);
        
        guards.values().forEach(Guard::findMostSleepyMinute);

        var highestSleep = 0;
        Guard sleepyGuard = null;

        for (Guard guard : guards.values()) {
            if (guard.minutesAsleep[guard.mostSleepyMinute] > highestSleep) {
                sleepyGuard = guard;
                highestSleep = guard.minutesAsleep[guard.mostSleepyMinute];
            }
        }

        assert sleepyGuard != null;
        
        return sleepyGuard.mostSleepyMinute * sleepyGuard.id;
    }

    private HashMap<Integer, Guard> processSleepingSchedule(List<String> strings) {
        var guards = new HashMap<Integer, Guard>();

        Guard guard = null;
        var startSleep = 0;

        for (var event : strings) {
            if (event.contains("Guard")) {
                var guardId = Integer.parseInt(event.split(" ")[3].replace("#", ""));

                guard = guards.computeIfAbsent(guardId, Guard::new);
            } else if (event.contains("falls asleep")) {
                startSleep = Integer.parseInt(event.substring(15, 17));
            } else {
                var wake = Integer.parseInt(event.substring(15, 17));

                for (int sleepminute = startSleep; sleepminute < wake; sleepminute++) {
                    assert guard != null;
                    
                    guard.minutesAsleep[sleepminute]++;
                    guard.totalMinutesAsleep++;
                }
            }
        }
        return guards;
    }

    private static class Guard {
        private final int id;
        private final int[] minutesAsleep = new int[60];
        private int totalMinutesAsleep = 0;
        private int mostSleepyMinute;

        private Guard(int id) {
            this.id = id;
        }

        public int getTotalMinutesAsleep() {
            return totalMinutesAsleep;
        }

        private void findMostSleepyMinute() {
            var highestValue = Integer.MIN_VALUE;
            
            for (int i = 0; i < 60; i++) {
                if (minutesAsleep[i] > highestValue) {
                    highestValue = minutesAsleep[i];
                    mostSleepyMinute = i;
                }
            }
        }
        
        @Override
        public String toString() {
            return "Guard{" +
                   "id=" + id +
                   ", minutesAsleep=" + Arrays.toString(minutesAsleep) +
                   ", totalMinutesAsleep=" + totalMinutesAsleep +
                   '}';
        }
    }
}
