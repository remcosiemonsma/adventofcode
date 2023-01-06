package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
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
        var lines = input.toList();
        var immuneSystem = new ArrayList<Unit>();
        var infection = new ArrayList<Unit>();
        var allUnits = new ArrayList<Unit>();

        var counter = 1;
        var prefix = "Immune System group ";
        var current = immuneSystem;
        var type = Type.IMMUNE_SYSTEM;
        for (String line : lines) {
            if (line.isBlank()) {
                current = infection;
                prefix = "Infection group ";
                counter = 1;
                type = Type.INFECTION;
                continue;
            }
            if (line.contains(":")) {
                continue;
            }
            var unit = createUnit(line, prefix + counter++, type);
            current.add(unit);
            allUnits.add(unit);
        }

        allUnits.sort(Comparator.comparing(Unit::initiative).reversed());

        while (immuneSystem.stream().anyMatch(Unit::isAlive) &&
               infection.stream().anyMatch(Unit::isAlive)) {
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

            for (var unit : allUnits) {
                if (unit.isAlive()) {
                    unit.attack();
                }
            }
            var unitsAfter = allUnits.stream().mapToInt(Unit::size).sum();
            
            if (unitsBefore == unitsAfter) {
                System.out.println("Stalemate detected!");
                break;
            }
        }

        var aliveImmuneUnits = immuneSystem.stream().mapToInt(Unit::size).sum();
        var aliveInfectionUnits = infection.stream().mapToInt(Unit::size).sum();

        System.out.println(aliveImmuneUnits);
        System.out.println(aliveInfectionUnits);

        return Math.max(aliveImmuneUnits, aliveInfectionUnits);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return null;
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
        private int size;
        private final int hp;
        private final List<String> immunities;
        private final List<String> weaknesses;
        private final String weaponType;
        private final int damage;
        private final int initiative;
        private final Type type;
        private Unit nextAttack;
        private Unit nextAttackBy;


        private Unit(String name, int size, int hp, List<String> immunities, List<String> weaknesses, String weaponType,
                     int damage, int initiative, Type type) {
            this.name = name;
            this.size = size;
            this.hp = hp;
            this.immunities = immunities;
            this.weaknesses = weaknesses;
            this.weaponType = weaponType;
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

                System.out.println(nextAttackBy.name + " attacks " + name + ", killing " + killed + " units");

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
                    System.out.println(name + " would deal " + nextAttack.name + " " + calculateDamage(nextAttack) + " damage");
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
                   '}';
        }
    }

    private enum Type {
        IMMUNE_SYSTEM,
        INFECTION
    }
}
