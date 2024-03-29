package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day17 implements AdventOfCodeSolution<Integer> {
    private static final Pattern PATTERN =
            Pattern.compile("target area: x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)");

    @Override
    public Integer handlePart1(Stream<String> input) {
        var target = input.findFirst()
                          .map(PATTERN::matcher)
                          .filter(Matcher::matches)
                          .map(matcher -> new Target(Integer.parseInt(matcher.group(1)),
                                                     Integer.parseInt(matcher.group(2)),
                                                     Integer.parseInt(matcher.group(4)),
                                                     Integer.parseInt(matcher.group(3))))
                          .orElseThrow(() -> new AssertionError("Eek!"));

        var xVelocity = 1;
        var yVelocity = 1;

        var highestPoint = 0;

        while (yVelocity < 150) {
            var probe = new Probe(xVelocity, yVelocity);

            while (probe.x <= target.x2 && probe.y >= target.y2) {
                probe.move();
                if (probe.x >= target.x1 && probe.x <= target.x2 && probe.y <= target.y1 && probe.y >= target.y2) {
                    if (probe.highestPoint > highestPoint) {
                        highestPoint = probe.highestPoint;
                    }
                    break;
                }
            }
            if (xVelocity < target.x2) {
                xVelocity++;
            } else {
                yVelocity++;
                xVelocity = 1;
            }
        }

        return highestPoint;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var target = input.findFirst()
                          .map(PATTERN::matcher)
                          .filter(Matcher::matches)
                          .map(matcher -> new Target(Integer.parseInt(matcher.group(1)),
                                                     Integer.parseInt(matcher.group(2)),
                                                     Integer.parseInt(matcher.group(4)),
                                                     Integer.parseInt(matcher.group(3))))
                          .orElseThrow(() -> new AssertionError("Eek!"));

        var xVelocity = 1;
        var yVelocity = target.y2;

        var amountOfHits = 0;

        while (yVelocity < 150) {
            var probe = new Probe(xVelocity, yVelocity);

            while (probe.x <= target.x2 && probe.y >= target.y2) {
                probe.move();
                if (probe.x >= target.x1 && probe.x <= target.x2 && probe.y <= target.y1 && probe.y >= target.y2) {
                    amountOfHits++;
                    break;
                }
            }
            if (xVelocity < target.x2) {
                xVelocity++;
            } else {
                yVelocity++;
                xVelocity = 1;
            }
        }

        return amountOfHits;
    }

    private record Target(int x1, int x2, int y1, int y2) {
    }

    private static class Probe {
        private int xVelocity;
        private int yVelocity;
        private int x = 0;
        private int y = 0;
        private int highestPoint = 0;

        public Probe(int xVelocity, int yVelocity) {
            this.xVelocity = xVelocity;
            this.yVelocity = yVelocity;
        }

        public void move() {
            x += xVelocity;
            y += yVelocity;

            yVelocity--;
            xVelocity--;
            if (xVelocity < 0) {
                xVelocity = 0;
            }

            if (y > highestPoint) {
                highestPoint = y;
            }
        }
    }
}
