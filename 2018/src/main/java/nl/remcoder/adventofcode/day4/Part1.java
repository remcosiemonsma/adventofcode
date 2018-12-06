package nl.remcoder.adventofcode.day4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Part1 {
    public static void main(String[] args) throws Exception {
        List<String> strings = Files.lines(Paths.get(ClassLoader.getSystemResource("day4/input").toURI()))
                                    .sorted(Comparator.comparing(s -> s.substring(0, 19)))
                                    .collect(Collectors.toList());

        Map<Integer, Guard> guards = new HashMap<>();

        Guard guard = new Guard();
        int startSleep = 0;

        for (String event : strings) {
            if (event.contains("Guard")) {
                int guardId = Integer.parseInt(event.split(" ")[3].replace("#", ""));

                if (guards.containsKey(guardId)) {
                    guard = guards.get(guardId);
                } else {
                    guard = new Guard();
                    guard.id = guardId;
                    guards.put(guardId, guard);
                }
            } else {
                if (event.contains("falls asleep")) {
                    startSleep = Integer.parseInt(event.substring(15, 17));
                } else {
                    int wake = Integer.parseInt(event.substring(15, 17));

                    for (int sleepminute = startSleep; sleepminute < wake; sleepminute++) {
                        guard.minutesAsleep[sleepminute]++;
                        guard.totalMinutesAsleep++;
                    }
                }
            }
        }

        Guard mostSleepyGuard = guards.values().stream().sorted(Comparator.comparing(guard1 -> guard1.totalMinutesAsleep))
                        .reduce((first, second) -> second).get();

        int highestValue = 0;
        int minute = 0;

        for (int i = 0; i < 60; i++) {
            if (mostSleepyGuard.minutesAsleep[i] > highestValue) {
                highestValue = mostSleepyGuard.minutesAsleep[i];
                minute = i;
            }
        }

        System.out.println(mostSleepyGuard);
        System.out.println(mostSleepyGuard.id * minute);
    }

    static class Guard {
        int id;
        int[] minutesAsleep = new int[60];
        int totalMinutesAsleep = 0;

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
