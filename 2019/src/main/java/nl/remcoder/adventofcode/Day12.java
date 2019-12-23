package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 {
    public int handlePart1(Stream<String> input, int steps) {
        List<Moon> moons = input.map(this::mapToMoon)
                                .collect(Collectors.toList());

        for (int step = 0; step < steps; step++) {
            calculateVelocityForMoons(moons);

            moveMoons(moons);
        }

        return calculateMoonEnergyOfAllMoons(moons);
    }

    public long handlePart2(Stream<String> input) {
        List<String> originalInput = input.collect(Collectors.toList());

        List<Moon> moons = originalInput.stream()
                                        .map(this::mapToMoon)
                                        .collect(Collectors.toList());

        Set<Long> periods = new HashSet<>();

        long xPeriod = 0;
        long yPeriod = 0;
        long zPeriod = 0;

        long counter = 0;

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

        return determineLowestCommonMultiple(periods);
    }

    private void calculateVelocityForMoons(List<Moon> moons) {
        List<Moon> workingMoons = new ArrayList<>(moons);

        while (!workingMoons.isEmpty()) {
            Moon moon = workingMoons.remove(0);

            for (Moon otherMoon : workingMoons) {
                calculateVelocityForMoon(moon, otherMoon);
            }
        }
    }

    private long determineLowestCommonMultiple(Set<Long> numbers) {
        List<List<Long>> primeFactors = numbers.stream()
                                               .map(this::primeFactors)
                                               .collect(Collectors.toList());

        Set<Long> primes = primeFactors.stream()
                                       .flatMap(List::stream)
                                       .collect(Collectors.toSet());

        Map<Long, Long> highestCountPerPrime = primes.stream()
                                                     .collect(Collectors.toMap(prime -> prime, prime ->
                                                                                       primeFactors.stream()
                                                                                                   .mapToLong(primes1 -> primes1.stream()
                                                                                                                                .filter(prime1 -> prime1
                                                                                                                                        .equals(prime))
                                                                                                                                .count())
                                                                                                   .max()
                                                                                                   .orElseThrow(AssertionError::new)
                                                                              ));

        long lcm = 1;

        for (long prime : highestCountPerPrime.keySet()) {
            lcm *= Math.pow(prime, highestCountPerPrime.get(prime));
        }

        return lcm;
    }

    private List<Long> primeFactors(long number) {
        List<Long> factors = new ArrayList<>();
        for (long possibleFactor = 2; possibleFactor <= number; possibleFactor++) {
            while (number % possibleFactor == 0) {
                factors.add(possibleFactor);
                number /= possibleFactor;
            }
        }
        if (number > 1) {
            factors.add(number);
        }
        return factors;
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
        String[] positions = moonData.replaceAll("[^\\-0-9,]", "").split(",");

        return new Moon(Integer.parseInt(positions[0]), Integer.parseInt(positions[1]), Integer.parseInt(positions[2]));
    }

    private int calculateMoonEnergy(Moon moon) {
        return (Math.abs(moon.xpos) + Math.abs(moon.ypos) + Math.abs(moon.zpos)) *
               (Math.abs(moon.xvel) + Math.abs(moon.yvel) + Math.abs(moon.zvel));
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
