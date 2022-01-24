package nl.remcoder.adventofcode;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 {
    private static final Pattern REINDEER_PATTERN =
            Pattern.compile("(.*) can fly (\\d*) km/s for (\\d*) seconds, but then must rest for (\\d*) seconds.");

    public int handlePart1(Stream<String> input) {
        List<Reindeer> reindeer = input.map(REINDEER_PATTERN::matcher)
                                       .filter(Matcher::matches)
                                       .map(this::mapToReindeer)
                                       .collect(Collectors.toList());

        for (int i = 0; i < 2503; i++) {
            reindeer.forEach(Reindeer::tick);
        }

        return reindeer.stream().mapToInt(Reindeer::getDistance).max().getAsInt();
    }

    private Reindeer mapToReindeer(Matcher matcher) {
        return new Reindeer(matcher.group(1),
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3)),
                            Integer.parseInt(matcher.group(4)));
    }

    public int handlePart2(Stream<String> input) {
        List<Reindeer> reindeer = input.map(REINDEER_PATTERN::matcher)
                                       .filter(Matcher::matches)
                                       .map(this::mapToReindeer)
                                       .collect(Collectors.toList());

        for (int i = 0; i < 2503; i++) {
            reindeer.forEach(Reindeer::tick);
            int maxDistance = reindeer.stream().mapToInt(Reindeer::getDistance).max().getAsInt();
            reindeer.stream()
                    .filter(leadingReindeer -> leadingReindeer.getDistance() == maxDistance)
                    .forEach(Reindeer::incrementScore);
        }

        return reindeer.stream().mapToInt(Reindeer::getScore).max().getAsInt();
    }

    private static class Reindeer {
        private final String name;
        private final int speed;
        private final int runtime;
        private final int resttime;
        private State state;
        private int restperiod;
        private int runperiod;
        private int distance;
        private int score;

        private Reindeer(String name, int speed, int runtime, int resttime) {
            this.name = name;
            this.speed = speed;
            this.runtime = runtime;
            this.resttime = resttime;
            state = State.FLYING;
        }

        public void tick() {
            switch (state) {
                case FLYING:
                    runperiod++;
                    distance += speed;
                    if (runperiod == runtime) {
                        state = State.RESTING;
                        restperiod = resttime;
                    }
                    break;
                case RESTING:
                    restperiod--;
                    if (restperiod == 0) {
                        state = State.FLYING;
                        runperiod = 0;
                    }
                    break;
            }
        }

        public int getDistance() {
            return distance;
        }

        public int getScore() {
            return score;
        }

        public void incrementScore() {
            score++;
        }

        private enum State {
            RESTING,
            FLYING
        }

        @Override
        public String toString() {
            return "Reindeer{" +
                   "name='" + name + '\'' +
                   ", speed=" + speed +
                   ", runtime=" + runtime +
                   ", resttime=" + resttime +
                   ", state=" + state +
                   ", restperiod=" + restperiod +
                   ", runperiod=" + runperiod +
                   ", distance=" + distance +
                   ", score=" + score +
                   '}';
        }
    }
}
