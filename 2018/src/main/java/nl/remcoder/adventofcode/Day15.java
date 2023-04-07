package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day15 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var data = input.map(String::toCharArray).toArray(char[][]::new);

        var grid = new Grid<Square>(0, 0, data.length, data[0].length);

        var units = new ArrayList<Unit>();
        var elves = new ArrayList<Unit>();
        var goblins = new ArrayList<Unit>();

        for (var y = 0; y < data.length; y++) {
            for (var x = 0; x < data[y].length; x++) {
                var c = data[y][x];

                var position = new Coordinate(x, y);
                switch (c) {
                    case 'E' -> {
                        var elf = new Unit(position, Unit.Type.ELF, grid, goblins, 200);
                        elves.add(elf);
                        units.add(elf);
                        grid.set(position, elf);
                    }
                    case 'G' -> {
                        var goblin = new Unit(new Coordinate(x, y), Unit.Type.GOBLIN, grid, elves, 200);
                        goblins.add(goblin);
                        units.add(goblin);
                        grid.set(position, goblin);
                    }
                    case '#' -> grid.set(position, new Wall(position));
                }
            }
        }

        var rounds = 0;

        while (elves.stream().anyMatch(Unit::isAlive) && goblins.stream().anyMatch(Unit::isAlive)) {
            units.sort(Comparator.comparing((Unit unit) -> unit.getPosition().y())
                                 .thenComparing(unit -> unit.getPosition().x()));

            units.stream()
                 .filter(Unit::isAlive)
                 .forEach(Unit::takeTurn);

            rounds++;
        }

        if (units.stream().anyMatch(unit -> !unit.turnTaken)) {
            return (rounds - 1) * units.stream().filter(Unit::isAlive).mapToInt(Unit::getHealth).sum();
        } else {
            return rounds * units.stream().filter(Unit::isAlive).mapToInt(Unit::getHealth).sum();
        }
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var data = input.map(String::toCharArray).toArray(char[][]::new);
        
        var units = new ArrayList<Unit>();
        var rounds = 0;
        var strengthIncrement = 1;
        
        var elfDied = true;
        
        while (elfDied) {
            rounds = 0;
            var grid = new Grid<Square>(0, 0, data.length, data[0].length);
            var elves = new ArrayList<Unit>();
            var goblins = new ArrayList<Unit>();
            units = new ArrayList<>();

            for (var y = 0; y < data.length; y++) {
                for (var x = 0; x < data[y].length; x++) {
                    char c = data[y][x];

                    var position = new Coordinate(x, y);
                    switch (c) {
                        case 'E' -> {
                            var elf = new Unit(position, Unit.Type.ELF, grid, goblins, 200);
                            elves.add(elf);
                            units.add(elf);
                            grid.set(position, elf);
                        }
                        case 'G' -> {
                            var goblin = new Unit(new Coordinate(x, y), Unit.Type.GOBLIN, grid, elves, 200);
                            goblins.add(goblin);
                            units.add(goblin);
                            grid.set(position, goblin);
                        }
                        case '#' -> grid.set(position, new Wall(position));
                    }
                }
            }

            for (Unit elf : elves) {
                elf.increaseStrength(strengthIncrement);
            }

            while (elves.stream().allMatch(Unit::isAlive) && goblins.stream().anyMatch(Unit::isAlive)) {
                units.sort(Comparator.comparing((Unit unit) -> unit.getPosition().y())
                                     .thenComparing(unit -> unit.getPosition().x()));

                units.stream()
                     .filter(Unit::isAlive)
                     .forEach(Unit::takeTurn);

                rounds++;
            }
            
            if (elves.stream().allMatch(Unit::isAlive)) {
                elfDied = false;
            } else {
                strengthIncrement *= 2;
            }
        }
        
        var diff = strengthIncrement / 4;
        
        var survivedLastRound = true;
        
        var resultFound = false;
        
        while (!resultFound) {
            if (diff > 0) {
                if (survivedLastRound) {
                    strengthIncrement -= diff;
                } else {
                    strengthIncrement += diff;
                }
                if (diff != 1) {
                    diff /= 2;
                } else {
                    diff = 0;
                }
            } else {
                if (survivedLastRound) {
                    resultFound = true;
                } else {
                    strengthIncrement += 1;
                }
            }

            rounds = 0;
            var grid = new Grid<Square>(0, 0, data.length, data[0].length);
            var elves = new ArrayList<Unit>();
            var goblins = new ArrayList<Unit>();
            units = new ArrayList<>();

            for (var y = 0; y < data.length; y++) {
                for (var x = 0; x < data[y].length; x++) {
                    var c = data[y][x];

                    var position = new Coordinate(x, y);
                    switch (c) {
                        case 'E' -> {
                            var elf = new Unit(position, Unit.Type.ELF, grid, goblins, 200);
                            elves.add(elf);
                            units.add(elf);
                            grid.set(position, elf);
                        }
                        case 'G' -> {
                            var goblin = new Unit(new Coordinate(x, y), Unit.Type.GOBLIN, grid, elves, 200);
                            goblins.add(goblin);
                            units.add(goblin);
                            grid.set(position, goblin);
                        }
                        case '#' -> grid.set(position, new Wall(position));
                    }
                }
            }

            for (var elf : elves) {
                elf.increaseStrength(strengthIncrement);
            }

            while (elves.stream().allMatch(Unit::isAlive) && goblins.stream().anyMatch(Unit::isAlive)) {
                units.sort(Comparator.comparing((Unit unit) -> unit.getPosition().y())
                                     .thenComparing(unit -> unit.getPosition().x()));

                units.stream()
                     .filter(Unit::isAlive)
                     .forEach(Unit::takeTurn);

                rounds++;
            }

            survivedLastRound = elves.stream().allMatch(Unit::isAlive);
        }

        if (units.stream().anyMatch(unit -> !unit.turnTaken)) {
            return (rounds - 1) * units.stream().filter(Unit::isAlive).mapToInt(Unit::getHealth).sum();
        } else {
            return rounds * units.stream().filter(Unit::isAlive).mapToInt(Unit::getHealth).sum();
        }
    }

    private static abstract class Square {
        Coordinate position;

        public Square(Coordinate position) {
            this.position = position;
        }
    }

    private static class Wall extends Square {
        public Wall(Coordinate position) {
            super(position);
        }

        @Override
        public String toString() {
            return "#";
        }
    }

    private static class Unit extends Square {
        private final Type type;
        private final Grid<Square> grid;
        private final List<Unit> enemies;
        private int health;
        private int strength = 3;
        private boolean turnTaken;

        public Unit(Coordinate position, Type type, Grid<Square> grid, List<Unit> enemies, int health) {
            super(position);
            this.type = type;
            this.grid = grid;
            this.enemies = enemies;
            this.health = health;
        }

        public void takeTurn() {
            turnTaken = enemies.stream().anyMatch(Unit::isAlive);
            if (isAlive()) {
                if (!attack()) {
                    move();
                    attack();
                }
            }
        }

        private void move() {
            var reachableCoordinates = findReachableCoordinates();

            var optionalNextTarget = enemies.stream()
                                            .filter(Unit::isAlive)
                                            .flatMap(unit -> unit.getPosition()
                                                                 .getStraightNeighbours()
                                                                 .stream())
                                            .filter(coordinate -> grid.get(coordinate) == null)
                                            .filter(reachableCoordinates::contains)
                                            .min(Comparator.comparing(this::getDistanceTo)
                                                           .thenComparing(Coordinate::y)
                                                           .thenComparing(Coordinate::x));

            if (optionalNextTarget.isPresent()) {
                var nextTarget = optionalNextTarget.get();

                Coordinate nextPosition;
                if (position.getStraightNeighbours().contains(nextTarget)) {
                    nextPosition = nextTarget;
                } else {
                    nextPosition = findNextPositionForTarget(nextTarget);
                }

                grid.set(position, null);
                grid.set(nextPosition, this);
                position = nextPosition;
            }
        }

        private Coordinate findNextPositionForTarget(Coordinate nextTarget) {
            return position.getStraightNeighbours()
                           .stream()
                           .filter(coordinate -> grid.get(coordinate) == null)
                           .min(Comparator.comparing((Coordinate coordinate) ->
                                                             getDistanceBetween(coordinate, nextTarget))
                                          .thenComparing(Coordinate::y)
                                          .thenComparing(Coordinate::x))
                           .orElseThrow(() -> new AssertionError("Eek!"));
        }

        private int getDistanceTo(Coordinate target) {
            return getDistanceBetween(position, target);
        }

        private int getDistanceBetween(Coordinate source, Coordinate target) {
            var checkedCoordinates = new HashSet<Coordinate>();
            var coordinatesToCheck = new ArrayList<>(source.getStraightNeighbours());
            coordinatesToCheck.removeIf(coordinate -> grid.get(coordinate) != null);

            int distance = 0;

            while (!checkedCoordinates.contains(target) && !coordinatesToCheck.isEmpty()) {
                distance++;
                var nextCoordinatesToCheck = new ArrayList<Coordinate>();

                for (var coordinate : coordinatesToCheck) {
                    coordinate.getStraightNeighbours()
                              .stream()
                              .filter(coordinate1 -> grid.get(coordinate1) == null)
                              .filter(Predicate.not(checkedCoordinates::contains))
                              .filter(Predicate.not(nextCoordinatesToCheck::contains))
                              .forEach(nextCoordinatesToCheck::add);
                }

                checkedCoordinates.addAll(coordinatesToCheck);
                coordinatesToCheck = nextCoordinatesToCheck;
            }

            if (checkedCoordinates.contains(target)) {
                return distance;
            } else {
                return Integer.MAX_VALUE;
            }
        }

        public void takeDamage(int damage) {
            health -= damage;
            if (health < 0) {
                grid.set(position, null);
            }
        }

        private boolean attack() {
            Optional<Unit> optionalTarget = position.getStraightNeighbours()
                                                    .stream()
                                                    .map(grid::get)
                                                    .filter(square -> square instanceof Unit)
                                                    .map(square -> (Unit) square)
                                                    .sorted(Comparator.comparing(Unit::getHealth)
                                                                      .thenComparing(unit -> unit.getPosition().y())
                                                                      .thenComparing(unit -> unit.getPosition().x()))
                                                    .filter(this::isUnitEnemy)
                                                    .filter(Unit::isAlive)
                                                    .findFirst();

            optionalTarget.ifPresent(unit -> unit.takeDamage(strength));

            return optionalTarget.isPresent();
        }

        private Set<Coordinate> findReachableCoordinates() {
            var reachableCoordinates = new HashSet<Coordinate>();

            var coordinatesToInvestigate = new ArrayList<>(position.getStraightNeighbours());
            coordinatesToInvestigate.removeIf(coordinate -> grid.get(coordinate) != null);

            while (!coordinatesToInvestigate.isEmpty()) {
                var nextCoordinatesToInvestigate = new ArrayList<Coordinate>();

                for (var coordinate : coordinatesToInvestigate) {
                    coordinate.getStraightNeighbours()
                              .stream()
                              .filter(coordinate1 -> grid.get(coordinate1) == null)
                              .filter(Predicate.not(reachableCoordinates::contains))
                              .filter(Predicate.not(nextCoordinatesToInvestigate::contains))
                              .forEach(nextCoordinatesToInvestigate::add);
                }

                reachableCoordinates.addAll(coordinatesToInvestigate);
                coordinatesToInvestigate = nextCoordinatesToInvestigate;
            }

            return reachableCoordinates;
        }

        private boolean isUnitEnemy(Unit other) {
            return this.type != other.type;
        }

        public Coordinate getPosition() {
            return position;
        }

        public int getHealth() {
            return health;
        }

        private boolean isAlive() {
            return health > 0;
        }

        private enum Type {
            GOBLIN,
            ELF
        }
        
        public void increaseStrength(int amount) {
            strength += amount;
        }

        @Override
        public String toString() {
            return switch (type) {
                case ELF -> "E";
                case GOBLIN -> "G";
            };
        }
    }
}
