package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day15 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var data = input.toList();

        char[][] grid = new char[data.size()][];

        List<Unit> goblins = new ArrayList<>();
        List<Unit> elfs = new ArrayList<>();
        List<Unit> units = new ArrayList<>();

        for (int y = 0; y < data.size(); y++) {
            String s = data.get(y);
            char[] line = new char[s.length()];
            for (int x = 0; x < s.length(); x++) {
                switch (s.charAt(x)) {
                    case '#' -> line[x] = '#';
                    case '.' -> line[x] = '.';
                    case 'G' -> {
                        Unit goblin = new Unit(x, y, UnitType.GOBLIN);
                        goblins.add(goblin);
                        units.add(goblin);
                        line[x] = 'G';
                    }
                    case 'E' -> {
                        Unit elf = new Unit(x, y, UnitType.ELF);
                        elfs.add(elf);
                        units.add(elf);
                        line[x] = 'E';
                    }
                }
            }

            grid[y] = line;
        }

        int rounds = 0;

        game:
        while (goblins.stream().anyMatch(goblin -> goblin.health > 1) &&
               elfs.stream().anyMatch(elf -> elf.health > 1)) {
            units.sort(Comparator.comparing(Unit::getPosy)
                                 .thenComparing(Unit::getPosx));

            for (Unit unit : units) {
                switch (unit.unitType) {
                    case GOBLIN -> {
                        if (elfs.stream().noneMatch(elf -> elf.health > 0)) {
                            break game;
                        }
                    }
                    case ELF -> {
                        if (goblins.stream().noneMatch(goblin -> goblin.health > 0)) {
                            break game;
                        }
                    }
                }

                if (unit.health > 0) {
                    boolean turncomplete = switch (unit.unitType) {
                        case ELF -> unit.attack(goblins, grid);
                        case GOBLIN -> unit.attack(elfs, grid);
                    };

                    if (!turncomplete) {
                        List<Coordinate> possibleAttackVectors = new ArrayList<>();

                        List<Unit> enemies = switch (unit.unitType) {
                            case ELF -> goblins;
                            case GOBLIN -> elfs;
                        };

                        enemies.stream()
                               .filter(enemy -> enemy.health > 0)
                               .forEach(enemy -> {
                                   if (grid[enemy.posy - 1][enemy.posx] == '.') {
                                       possibleAttackVectors.add(new Coordinate(enemy.posx, enemy.posy - 1));
                                   }
                                   if (grid[enemy.posy][enemy.posx - 1] == '.') {
                                       possibleAttackVectors.add(new Coordinate(enemy.posx - 1, enemy.posy));
                                   }
                                   if (grid[enemy.posy][enemy.posx + 1] == '.') {
                                       possibleAttackVectors.add(new Coordinate(enemy.posx + 1, enemy.posy));
                                   }
                                   if (grid[enemy.posy + 1][enemy.posx] == '.') {
                                       possibleAttackVectors.add(new Coordinate(enemy.posx, enemy.posy + 1));
                                   }
                               });

                        List<Move> possibleMoves = new ArrayList<>();

                        for (Coordinate coordinate : possibleAttackVectors) {
                            Optional<Coordinate> nextBestMove = findNextBestMove(grid,
                                                                                       new Coordinate(unit.posx, unit.posy),
                                                                                       coordinate);

                            nextBestMove.map(coordinate1 -> new Move(coordinate1, coordinate))
                                        .ifPresent(possibleMoves::add);
                        }

                        possibleMoves.stream()
                                     .min(byMoveSteps.thenComparing(byMoveTargetY).thenComparing(byMoveTargetX).thenComparing(byMoveNextY).thenComparing(byMoveNextX))
                                     .map(move -> move.nextStep)
                                     .ifPresent(coordinate -> {
                                         char temp = grid[unit.posy][unit.posx];
                                         grid[unit.posy][unit.posx] = '.';
                                         grid[coordinate.y][coordinate.x] = temp;
                                         unit.posx = coordinate.x;
                                         unit.posy = coordinate.y;
                                     });

                        switch (unit.unitType) {
                            case ELF -> unit.attack(goblins, grid);
                            case GOBLIN -> unit.attack(elfs, grid);
                        }
                    }
                }
            }

            units = units.stream()
                         .filter(unit -> unit.health > 0)
                         .collect(Collectors.toList());

            rounds++;
        }

        Integer elfHealth = elfs.stream()
                                .filter(elf -> elf.health > 0)
                                .map(elf -> elf.health)
                                .reduce(0, Integer::sum);

        Integer goblinHealth = goblins.stream()
                                      .filter(goblin -> goblin.health > 0)
                                      .map(goblin -> goblin.health)
                                      .reduce(0, Integer::sum);

        return (goblinHealth + elfHealth) * rounds;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var data = input.toList();

        char[][] grid = new char[data.size()][];

        List<Unit> goblins = new ArrayList<>();
        List<Unit> elfs = new ArrayList<>();
        List<Unit> units = new ArrayList<>();

        int elfAttackIncrementer = 1;

        boolean elfLost = true;

        int rounds = 0;
        while (elfLost) {
            elfAttackIncrementer++;
            elfs.clear();
            goblins.clear();
            units.clear();

            initializeGame(data, grid, goblins, elfs, units, elfAttackIncrementer);

            rounds = 0;

            elfLost = false;

            game:
            while (goblins.stream().anyMatch(goblin -> goblin.health > 1) &&
                   elfs.stream().anyMatch(elf -> elf.health > 1)) {
                units.sort(Comparator.comparing(Unit::getPosy).thenComparing(Unit::getPosx));

                for (Unit unit : units) {
                    switch (unit.unitType) {
                        case GOBLIN -> {
                            if (elfs.stream().noneMatch(elf -> elf.health > 0)) {
                                break game;
                            }
                        }
                        case ELF -> {
                            if (goblins.stream().noneMatch(goblin -> goblin.health > 0)) {
                                break game;
                            }
                        }
                    }

                    if (unit.health > 0) {
                        boolean turncomplete = switch (unit.unitType) {
                            case ELF -> unit.attack(goblins, grid);
                            case GOBLIN -> unit.attack(elfs, grid);
                        };

                        if (!turncomplete) {
                            List<Coordinate> possibleAttackVectors = new ArrayList<>();

                            List<Unit> enemies = switch (unit.unitType) {
                                case ELF -> goblins;
                                case GOBLIN -> elfs;
                            };

                            enemies.stream()
                                   .filter(enemy -> enemy.health > 0)
                                   .forEach(enemy -> {
                                       if (grid[enemy.posy - 1][enemy.posx] == '.') {
                                           possibleAttackVectors.add(new Coordinate(enemy.posx, enemy.posy - 1));
                                       }
                                       if (grid[enemy.posy][enemy.posx - 1] == '.') {
                                           possibleAttackVectors.add(new Coordinate(enemy.posx - 1, enemy.posy));
                                       }
                                       if (grid[enemy.posy][enemy.posx + 1] == '.') {
                                           possibleAttackVectors.add(new Coordinate(enemy.posx + 1, enemy.posy));
                                       }
                                       if (grid[enemy.posy + 1][enemy.posx] == '.') {
                                           possibleAttackVectors.add(new Coordinate(enemy.posx, enemy.posy + 1));
                                       }
                                   });

                            List<Move> possibleMoves = new ArrayList<>();

                            for (Coordinate coordinate : possibleAttackVectors) {
                                Optional<Coordinate> nextBestMove = findNextBestMove(grid,
                                                                                           new Coordinate(unit.posx,
                                                                                                                unit.posy),
                                                                                           coordinate);

                                nextBestMove.map(coordinate1 -> new Move(coordinate1, coordinate))
                                            .ifPresent(possibleMoves::add);
                            }

                            possibleMoves.stream()
                                         .min(byMoveSteps.thenComparing(byMoveTargetY).thenComparing(byMoveTargetX)
                                                         .thenComparing(byMoveNextY).thenComparing(byMoveNextX))
                                         .map(move -> move.nextStep)
                                         .ifPresent(coordinate -> {
                                             char temp = grid[unit.posy][unit.posx];
                                             grid[unit.posy][unit.posx] = '.';
                                             grid[coordinate.y][coordinate.x] = temp;
                                             unit.posx = coordinate.x;
                                             unit.posy = coordinate.y;
                                         });

                            switch (unit.unitType) {
                                case ELF -> unit.attack(goblins, grid);
                                case GOBLIN -> unit.attack(elfs, grid);
                            }
                        }
                    }
                }

                units = units.stream()
                             .filter(unit -> unit.health > 0)
                             .collect(Collectors.toList());

                rounds++;

                if (elfs.stream().anyMatch(elf -> elf.health < 1)) {
                    elfLost = true;
                    break;
                }
            }
        }

        Integer elfHealth = elfs.stream()
                                .filter(elf -> elf.health > 0)
                                .map(elf -> elf.health)
                                .reduce(0, Integer::sum);

        Integer goblinHealth = goblins.stream()
                                      .filter(goblin -> goblin.health > 0)
                                      .map(goblin -> goblin.health)
                                      .reduce(0, Integer::sum);

        return (goblinHealth + elfHealth) * rounds;
    }

    private static void initializeGame(List<String> data, char[][] grid, List<Unit> goblins,
                                       List<Unit> elfs, List<Unit> units, int elfAttackIncrementer) {
        for (int y = 0; y < data.size(); y++) {
            String s = data.get(y);
            char[] line = new char[s.length()];
            for (int x = 0; x < s.length(); x++) {
                switch (s.charAt(x)) {
                    case '#' -> line[x] = '#';
                    case '.' -> line[x] = '.';
                    case 'G' -> {
                        Unit goblin = new Unit(x, y, UnitType.GOBLIN);
                        goblins.add(goblin);
                        units.add(goblin);
                        line[x] = 'G';
                    }
                    case 'E' -> {
                        Unit elf = new Unit(x, y, UnitType.ELF);
                        elf.damage += elfAttackIncrementer;
                        elfs.add(elf);
                        units.add(elf);
                        line[x] = 'E';
                    }
                }
            }

            grid[y] = line;
        }
    }
    
    private static Optional<Coordinate> findNextBestMove(char[][] grid, Coordinate start, Coordinate goal) {
        boolean startFound = false;

        goal.steps = 0;

        List<Coordinate> firstStack = new ArrayList<>();

        firstStack.add(goal);

        List<List<Coordinate>> possibleRoutes = new ArrayList<>();

        possibleRoutes.add(firstStack);

        while (!startFound) {
            List<List<Coordinate>> impossibleRoutes = new ArrayList<>();
            List<List<Coordinate>> newRoutes = new ArrayList<>();

            for (List<Coordinate> possibleRoute : possibleRoutes) {
                Coordinate currentStep = possibleRoute.get(possibleRoute.size() - 1);

                List<Coordinate> nextSteps = new ArrayList<>();

                checkAndAddStep(possibleRoutes, currentStep, nextSteps, grid, currentStep.y, currentStep.x + 1, start);
                checkAndAddStep(possibleRoutes, currentStep, nextSteps, grid, currentStep.y, currentStep.x - 1, start);
                checkAndAddStep(possibleRoutes, currentStep, nextSteps, grid, currentStep.y + 1, currentStep.x, start);
                checkAndAddStep(possibleRoutes, currentStep, nextSteps, grid, currentStep.y - 1, currentStep.x, start);

                if (nextSteps.size() == 0) {
                    impossibleRoutes.add(possibleRoute);
                } else if (nextSteps.size() == 1) {
                    possibleRoute.add(nextSteps.get(0));
                } else {
                    for (int i = nextSteps.size() - 1; i >= 1; i--) {
                        List<Coordinate> newRoute = new ArrayList<>(possibleRoute);
                        newRoute.add(nextSteps.get(i));
                        newRoutes.add(newRoute);
                    }
                    possibleRoute.add(nextSteps.get(0));
                }
            }

            possibleRoutes.removeAll(impossibleRoutes);
            possibleRoutes.addAll(newRoutes);

            startFound = possibleRoutes.isEmpty() || possibleRoutes.stream()
                                                                   .anyMatch(possibleRoute ->
                                                                                     possibleRoute
                                                                                             .get(possibleRoute.size() -
                                                                                                  1).x ==
                                                                                     start.x &&
                                                                                     possibleRoute
                                                                                             .get(possibleRoute.size() -
                                                                                                  1).y == start.y);
        }

        List<List<Coordinate>> validRoutes = possibleRoutes.stream()
                                                                 .filter(possibleRoute ->
                                                                                 possibleRoute
                                                                                         .get(possibleRoute.size() - 1).x ==
                                                                                 start.x &&
                                                                                 possibleRoute
                                                                                         .get(possibleRoute.size() - 1).y ==
                                                                                 start.y)
                                                                 .toList();

        return validRoutes.stream()
                          .map(validRoute -> validRoute.get(validRoute.size() - 2))
                          .min(Comparator.comparing(Coordinate::getY)
                                         .thenComparing(Coordinate::getX));
    }
    
    private static void checkAndAddStep(List<List<Coordinate>> possibleRoutes, Coordinate currentStep,
                                        List<Coordinate> nextSteps, char[][] grid, int i, int x, Coordinate goal) {
        if (goal.x == x && goal.y == i) {
            Coordinate coordinate = new Coordinate(x, i);
            coordinate.steps = currentStep.steps + 1;
            nextSteps.add(coordinate);
        } else {
            if (grid[i][x] == '.') {
                Coordinate coordinate = new Coordinate(x, i);
                coordinate.steps = currentStep.steps + 1;

                if (possibleRoutes.stream()
                                  .noneMatch(coordinates -> coordinates.stream()
                                                                       .anyMatch(othercoordinate ->
                                                                                         othercoordinate.x ==
                                                                                         coordinate.x &&
                                                                                         othercoordinate.y ==
                                                                                         coordinate.y))) {
                    nextSteps.add(coordinate);
                }
            }
        }
    }
    
    private static class Unit {
        int health = 200;
        int damage = 3;
        int posx;
        int posy;
        final UnitType unitType;

        public Unit(int posx, int posy, UnitType unitType) {
            this.posx = posx;
            this.posy = posy;
            this.unitType = unitType;
        }

        public int getPosx() {
            return posx;
        }

        public int getPosy() {
            return posy;
        }

        public int getHealth() {
            return health;
        }

        boolean attack(List<Unit> enemies, char[][] grid) {
            Optional<Unit> optionalUnit = enemies.stream()
                                                       .filter(unit -> unit.health > 0)
                                                       .filter(unit -> (unit.posx == this.posx + 1 &&
                                                                        unit.posy == this.posy) ||
                                                                       (unit.posx == this.posx - 1 &&
                                                                        unit.posy == this.posy) ||
                                                                       (unit.posy == this.posy + 1 &&
                                                                        unit.posx == this.posx) ||
                                                                       (unit.posy == this.posy - 1 &&
                                                                        unit.posx == this.posx))
                                                       .min(Comparator.comparing(Unit::getHealth)
                                                                      .thenComparing(Unit::getPosx)
                                                                      .thenComparing(Unit::getPosy));
            optionalUnit.ifPresent(unit -> unit.health -= this.damage);

            optionalUnit.filter(unit -> unit.health < 1)
                        .ifPresent(unit -> grid[unit.posy][unit.posx] = '.');

            return optionalUnit.isPresent();
        }

        @Override
        public String toString() {
            return "Unit{" +
                   "health=" + health +
                   ", damage=" + damage +
                   ", posx=" + posx +
                   ", posy=" + posy +
                   ", unitType=" + unitType +
                   '}';
        }
    }

    private enum UnitType {
        ELF,
        GOBLIN
    }

    private static class Coordinate {
        int x;
        int y;
        int steps;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Move {
        final Coordinate nextStep;
        final Coordinate target;

        private Move(Coordinate nextStep, Coordinate target) {
            this.nextStep = nextStep;
            this.target = target;
        }
    }

    private final Comparator<Move> byMoveSteps = Comparator.comparing(move -> move.nextStep.steps);
    private final Comparator<Move> byMoveTargetY = Comparator.comparing(move -> move.target.y);
    private final Comparator<Move> byMoveTargetX = Comparator.comparing(move -> move.target.x);
    private final Comparator<Move> byMoveNextY = Comparator.comparing(move -> move.target.y);
    private final Comparator<Move> byMoveNextX = Comparator.comparing(move -> move.target.x);
}
