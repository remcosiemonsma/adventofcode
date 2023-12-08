package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;
import nl.remcoder.adventofcode.library.math.Math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.abs;

public class Day12 implements BiAdventOfCodeSolution<Integer, Long> {
    private int steps;
    
    @Override
    public Integer handlePart1(Stream<String> input) {
        var moons = input.map(this::mapToMoon)
                         .toList();

        for (var step = 0; step < steps; step++) {
            calculateVelocityForMoons(moons);

            moveMoons(moons);
        }

        return calculateMoonEnergyOfAllMoons(moons);
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var originalInput = input.toList();

        var moons = originalInput.stream()
                                 .map(this::mapToMoon)
                                 .toList();

        var periods = new HashSet<Long>();

        var xPeriod = 0L;
        var yPeriod = 0L;
        var zPeriod = 0L;

        var counter = 0;

        while (xPeriod == 0 || yPeriod == 0 || zPeriod == 0) {
            calculateVelocityForMoons(moons);
            moveMoons(moons);
            counter++;

            if (xPeriod == 0 && moons.stream().allMatch(Moon::isAtStartX)) {
                xPeriod = counter;
            }
            if (yPeriod == 0 && moons.stream().allMatch(Moon::isAtStartY)) {
                yPeriod = counter;
            }
            if (zPeriod == 0 && moons.stream().allMatch(Moon::isAtStartZ)) {
                zPeriod = counter;
            }
        }

        periods.add(xPeriod);
        periods.add(yPeriod);
        periods.add(zPeriod);

        return periods.stream().reduce(Math::lcm).orElseThrow(() -> new AssertionError("Eek!"));
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    private void calculateVelocityForMoons(List<Moon> moons) {
        var workingMoons = new ArrayList<>(moons);

        while (!workingMoons.isEmpty()) {
            var moon = workingMoons.removeFirst();

            for (var otherMoon : workingMoons) {
                calculateVelocityForMoon(moon, otherMoon);
            }
        }
    }

    private void moveMoons(List<Moon> moons) {
        moons.forEach(moon -> {
            moon.xpos += moon.xvel;
            moon.ypos += moon.yvel;
            moon.zpos += moon.zvel;
        });
    }

    private void calculateVelocityForMoon(Moon moon, Moon otherMoon) {
        if (moon.xpos > otherMoon.xpos) {
            moon.xvel--;
            otherMoon.xvel++;
        } else if (moon.xpos < otherMoon.xpos) {
            moon.xvel++;
            otherMoon.xvel--;
        }
        if (moon.ypos > otherMoon.ypos) {
            moon.yvel--;
            otherMoon.yvel++;
        } else if (moon.ypos < otherMoon.ypos) {
            moon.yvel++;
            otherMoon.yvel--;
        }
        if (moon.zpos > otherMoon.zpos) {
            moon.zvel--;
            otherMoon.zvel++;
        } else if (moon.zpos < otherMoon.zpos) {
            moon.zvel++;
            otherMoon.zvel--;
        }
    }

    private int calculateMoonEnergyOfAllMoons(List<Moon> moons) {
        return moons.stream()
                    .mapToInt(this::calculateMoonEnergy)
                    .sum();
    }

    private Moon mapToMoon(String moonData) {
        var positions = moonData.replaceAll("[^\\-0-9,]", "").split(",");

        return new Moon(Integer.parseInt(positions[0]), Integer.parseInt(positions[1]), Integer.parseInt(positions[2]));
    }

    private int calculateMoonEnergy(Moon moon) {
        return (abs(moon.xpos) + abs(moon.ypos) + abs(moon.zpos)) *
               (abs(moon.xvel) + abs(moon.yvel) + abs(moon.zvel));
    }

    private static class Moon {
        private final int startxpos;
        private final int startypos;
        private final int startzpos;
        private int xpos;
        private int ypos;
        private int zpos;
        private int xvel = 0;
        private int yvel = 0;
        private int zvel = 0;

        private Moon(int xpos, int ypos, int zpos) {
            this.xpos = xpos;
            this.ypos = ypos;
            this.zpos = zpos;
            this.startxpos = xpos;
            this.startypos = ypos;
            this.startzpos = zpos;
        }

        boolean isAtStartX() {
            return this.startxpos == xpos && xvel == 0;
        }

        boolean isAtStartY() {
            return this.startypos == ypos && yvel == 0;
        }

        boolean isAtStartZ() {
            return this.startzpos == zpos && zvel == 0;
        }

        @Override
        public String toString() {
            return "pos=<x=" + xpos + ", y= " + ypos + ", z= " + zpos + ">, vel=<x= " + zvel + ", y= " + yvel +
                   ", z= " + zvel + ">";
        }
    }
}
