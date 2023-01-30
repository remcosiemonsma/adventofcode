package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day24 implements AdventOfCodeSolution<Integer> {
    private static final Pattern UNITS_PATTERN = Pattern.compile("(\\d+) units");
    private static final Pattern HP_PATTERN = Pattern.compile("(\\d+) hit points");
    private static final Pattern IMMUNITIES_PATTERN = Pattern.compile("immune to ([a-z, ]+)");
    private static final Pattern WEAKNESSES_PATTERN = Pattern.compile("weak to ([a-z, ]+)");
    private static final Pattern ATTACK_PATTERN = Pattern.compile("does (\\d+) (\\w+) damage");
    private static final Pattern INITIATIVE_PATTERN = Pattern.compile("initiative (\\d+)");

    @Override
    public Integer handlePart1(Stream<String> input) {
        var allUnits = parseUnits(input);

        var immuneSystem = new ArrayList<Unit>();
        var infection = new ArrayList<Unit>();

        allUnits.forEach(unit -> {
            switch (unit.type) {
                case IMMUNE_SYSTEM -> immuneSystem.add(unit);
                case INFECTION -> infection.add(unit);
            }
        });

        doBattle(allUnits, immuneSystem, infection);

        var aliveImmuneUnits = immuneSystem.stream().mapToInt(Unit::size).sum();
        var aliveInfectionUnits = infection.stream().mapToInt(Unit::size).sum();

        return Math.max(aliveImmuneUnits, aliveInfectionUnits);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var allUnits = parseUnits(input);

        int neededBoost = doLinearSearch(allUnits);

        allUnits.forEach(Unit::reset);

        var battleUnits = new ArrayList<>(allUnits);

        var immuneSystem = new ArrayList<Unit>();
        var infection = new ArrayList<Unit>();

        battleUnits.forEach(unit -> {
            switch (unit.type) {
                case IMMUNE_SYSTEM -> immuneSystem.add(unit);
                case INFECTION -> infection.add(unit);
            }
        });

        immuneSystem.forEach(unit -> unit.setBoost(neededBoost));

        doBattle(battleUnits, immuneSystem, infection);

        return immuneSystem.stream().mapToInt(Unit::size).sum();
    }

    private int doLinearSearch(List<Unit> allUnits) {
        var resultFound = false;

        int boost = 0;

        while (!resultFound) {
            boost++;

            allUnits.forEach(Unit::reset);

            var battleUnits = new ArrayList<>(allUnits);

            var immuneSystem = new ArrayList<Unit>();
            var infection = new ArrayList<Unit>();

            battleUnits.forEach(unit -> {
                switch (unit.type) {
                    case IMMUNE_SYSTEM -> immuneSystem.add(unit);
                    case INFECTION -> infection.add(unit);
                }
            });

            var finalBoost = boost;
            immuneSystem.forEach(unit -> unit.setBoost(finalBoost));

            doBattle(battleUnits, immuneSystem, infection);

            if (infection.isEmpty()) {
                resultFound = true;
            }
        }

        System.out.println(boost);

        return boost;
    }

    private int doBinarySearch(List<Unit> allUnits) {
        var lower = 1;
        var upper = 1;

        boolean upperBoundFound = false;

        while (!upperBoundFound) {
            allUnits.forEach(Unit::reset);

            var battleUnits = new ArrayList<>(allUnits);

            var immuneSystem = new ArrayList<Unit>();
            var infection = new ArrayList<Unit>();

            battleUnits.forEach(unit -> {
                switch (unit.type) {
                    case IMMUNE_SYSTEM -> immuneSystem.add(unit);
                    case INFECTION -> infection.add(unit);
                }
            });

            int currentBoost = lower;

            System.out.println(currentBoost);

            immuneSystem.forEach(unit -> unit.setBoost(currentBoost));

            doBattle(battleUnits, immuneSystem, infection);

            if (!infection.isEmpty()) {
//                System.out.println(infection);
                lower *= 2;
            } else {
                upper = lower;
                lower /= 2;
                upperBoundFound = true;
            }
        }

//        System.out.println(upper);

        var mid = (upper - lower) / 2;

        var resultFound = false;
        var survivedLastRound = true;

        var current = upper - mid;

        while (!resultFound) {
            System.out.println(current);
            allUnits.forEach(Unit::reset);

            var battleUnits = new ArrayList<>(allUnits);

            var immuneSystem = new ArrayList<Unit>();
            var infection = new ArrayList<Unit>();

            battleUnits.forEach(unit -> {
                switch (unit.type) {
                    case IMMUNE_SYSTEM -> immuneSystem.add(unit);
                    case INFECTION -> infection.add(unit);
                }
            });

            int finalCurrent = current;
            immuneSystem.forEach(unit -> unit.setBoost(finalCurrent));

            doBattle(battleUnits, immuneSystem, infection);

            if (infection.isEmpty()) {
                if (mid != 1) {
                    mid /= 2;
                    current -= mid;
                    survivedLastRound = true;
                } else {
                    current--;
                    if (!survivedLastRound) {
                        resultFound = true;
                    }
                }
            } else {
                if (mid != 1) {
                    mid /= 2;
                    current += mid;
                    survivedLastRound = false;
                } else {
                    current++;
                    if (survivedLastRound) {
                        resultFound = true;
                    }
                }
            }
        }
        return current;
    }

    private void doBattle(List<Unit> allUnits, ArrayList<Unit> immuneSystem, ArrayList<Unit> infection) {
        while (!immuneSystem.isEmpty() && !infection.isEmpty()) {
            allUnits.forEach(unit -> unit.nextAttackBy = null);

            var unitsBefore = allUnits.stream().mapToInt(Unit::size).sum();
            infection.stream()
                     .sorted(Comparator.comparing(Unit::getEffectivePower)
                                       .thenComparing(Unit::initiative)
                                       .reversed())
                     .forEach(unit -> unit.determineNextAttack(immuneSystem));
            immuneSystem.stream()
                        .sorted(Comparator.comparing(Unit::getEffectivePower)
                                          .thenComparing(Unit::initiative)
                                          .reversed())
                        .forEach(unit -> unit.determineNextAttack(infection));

            allUnits.stream()
                    .filter(Unit::isAlive)
                    .forEach(Unit::attack);

            infection.removeIf(Predicate.not(Unit::isAlive));
            immuneSystem.removeIf(Predicate.not(Unit::isAlive));
            allUnits.removeIf(Predicate.not(Unit::isAlive));

            var unitsAfter = allUnits.stream().mapToInt(Unit::size).sum();

            if (unitsBefore == unitsAfter) {
//                System.out.println("Stalemate detected!");
                break;
            }
        }
    }

    private List<Unit> parseUnits(Stream<String> input) {
        var lines = input.toList();
        var allUnits = new ArrayList<Unit>();

        var counter = 1;
        var prefix = "Immune System group ";
        var type = Type.IMMUNE_SYSTEM;
        for (String line : lines) {
            if (line.isBlank()) {
                prefix = "Infection group ";
                counter = 1;
                type = Type.INFECTION;
                continue;
            }
            if (line.contains(":")) {
                continue;
            }
            var unit = createUnit(line, prefix + counter++, type);
            allUnits.add(unit);
        }

        allUnits.sort(Comparator.comparing(Unit::initiative).reversed());

        return allUnits;
    }

    private Unit createUnit(String line, String name, Type type) {
        var matcher = UNITS_PATTERN.matcher(line);
        int size = 0;
        if (matcher.find()) {
            size = Integer.parseInt(matcher.group(1));
        }
        matcher = HP_PATTERN.matcher(line);
        int hp = 0;
        if (matcher.find()) {
            hp = Integer.parseInt(matcher.group(1));
        }
        List<String> immunities = List.of();
        matcher = IMMUNITIES_PATTERN.matcher(line);
        if (matcher.find()) {
            immunities = Arrays.asList(matcher.group(1).split(", "));
        }
        List<String> weaknesses = List.of();
        matcher = WEAKNESSES_PATTERN.matcher(line);
        if (matcher.find()) {
            weaknesses = Arrays.asList(matcher.group(1).split(", "));
        }
        String weaponType = "";
        int damage = 0;
        matcher = ATTACK_PATTERN.matcher(line);
        if (matcher.find()) {
            damage = Integer.parseInt(matcher.group(1));
            weaponType = matcher.group(2);
        }
        int initiative = 0;
        matcher = INITIATIVE_PATTERN.matcher(line);
        if (matcher.find()) {
            initiative = Integer.parseInt(matcher.group(1));
        }
        return new Unit(name, size, hp, immunities, weaknesses, weaponType, damage, initiative, type);
    }

    private static final class Unit {
        private final String name;
        private final int originalSize;
        private int size;
        private final int hp;
        private final List<String> immunities;
        private final List<String> weaknesses;
        private final String weaponType;
        private final int originalDamage;
        private int damage;
        private final int initiative;
        private final Type type;
        private Unit nextAttack;
        private Unit nextAttackBy;

        private Unit(String name, int size, int hp, List<String> immunities, List<String> weaknesses, String weaponType,
                     int damage, int initiative, Type type) {
            this.name = name;
            originalSize = size;
            this.size = size;
            this.hp = hp;
            this.immunities = immunities;
            this.weaknesses = weaknesses;
            this.weaponType = weaponType;
            originalDamage = damage;
            this.damage = damage;
            this.initiative = initiative;
            this.type = type;
        }

        public void attack(int damage) {
            if (isAlive()) {
                int killed = (int) Math.floor((double) damage / hp);

                if (killed > size) {
                    killed = size;
                }

//                System.out.println(nextAttackBy.name + " attacks " + name + ", killing " + killed + " units");

                size = size - killed;
            }
            nextAttackBy = null;
        }

        private Type type() {
            return type;
        }

        public int size() {
            return size;
        }

        public int hp() {
            return hp;
        }

        public List<String> immunities() {
            return immunities;
        }

        public List<String> weaknesses() {
            return weaknesses;
        }

        public String weaponType() {
            return weaponType;
        }

        public int damage() {
            return damage;
        }

        public int initiative() {
            return initiative;
        }

        public boolean isAlive() {
            return size > 0;
        }

        public void determineNextAttack(List<Unit> defenders) {
            if (isAlive()) {
                nextAttack = defenders.stream()
                                      .filter(unit -> unit.nextAttackBy == null)
                                      .max(Comparator.comparing(this::calculateDamage)
                                                     .thenComparing(Unit::getEffectivePower)
                                                     .thenComparing(Unit::initiative))
                                      .orElse(null);
                if (nextAttack != null) {
                    nextAttack.nextAttackBy = this;
//                    System.out.println(
//                            name + " would deal " + nextAttack.name + " " + calculateDamage(nextAttack) + " damage");
                }
            }
        }

        private int calculateDamage(Unit unit) {
            if (unit.immunities().contains(weaponType)) {
                return 0;
            } else {
                int totalDamage = size * damage;
                if (unit.weaknesses().contains(weaponType)) {
                    totalDamage *= 2;
                }
                return totalDamage;
            }
        }

        public int getEffectivePower() {
            return damage * size;
        }

        public void attack() {
            if (nextAttack != null) {
                nextAttack.attack(calculateDamage(nextAttack));
                nextAttack = null;
            }
        }

        @Override
        public String toString() {
            return "Unit{" +
                   "name='" + name + '\'' +
                   ", size=" + size +
                   ", hp=" + hp +
                   ", immunities=" + immunities +
                   ", weaknesses=" + weaknesses +
                   ", weaponType='" + weaponType + '\'' +
                   ", damage=" + damage +
                   ", initiative=" + initiative +
                   ", type=" + type +
                   '}';
        }

        public void setBoost(int boost) {
            damage += boost;
        }

        public void reset() {
            damage = originalDamage;
            size = originalSize;
        }
    }

    private enum Type {
        IMMUNE_SYSTEM,
        INFECTION
    }
}
