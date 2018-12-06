package nl.remcoder.adventofcode.day4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Part2 {
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


                        int highestValue = 0;
                        int minute = 0;

                        for (int i = 0; i < 60; i++) {
                            if (guard.minutesAsleep[i] > highestValue) {
                                highestValue = guard.minutesAsleep[i];
                                minute = i;
                            }
                        }

                        guard.highestMinuteAsleep = minute;
                    }
                }
            }
        }

        int highestSleep = 0;
        Guard sleepyGuard = null;

        for (Guard guard1 : guards.values()) {
            if (guard1.minutesAsleep[guard1.highestMinuteAsleep] > highestSleep) {
                sleepyGuard = guard1;
                highestSleep = guard1.minutesAsleep[guard1.highestMinuteAsleep];
            }
        }

        System.out.println(sleepyGuard.highestMinuteAsleep * sleepyGuard.id);
    }

    static class Guard {
        int id;
        int[] minutesAsleep = new int[60];
        int highestMinuteAsleep = 0;

        @Override
        public String toString() {
            return "Guard{" +
                   "id=" + id +
                   ", minutesAsleep=" + Arrays.toString(minutesAsleep) +
                   ", highestMinuteAsleep=" + highestMinuteAsleep +
                   '}';
        }
    }
}
