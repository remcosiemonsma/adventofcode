package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.stream.Stream;

public class Day22 implements AdventOfCodeSolution<Long> {
    private int playerHitPoints;
    private int playerMana;

    @Override
    public Long handlePart1(Stream<String> input) {
        var bossData = input.toList();

        var boss = createBoss(bossData);
        var player = createPlayer();

        var turn = new Turn(player, boss, new ArrayList<>(), createSpells(), List.of(), true);
        turn.setDistance(0);

        return Dijkstra.findShortestDistance(turn,
                                             node -> ((Turn) node).isGameFinished() &&
                                                     ((Turn) node).isPlayerAlive())
                       .orElseThrow(() -> new AssertionError("Eek!"))
                       .getDistance();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var bossData = input.toList();

        var boss = createBoss(bossData);
        var player = createPlayer();

        var turn = new Turn2(player, boss, new ArrayList<>(), createSpells(), List.of(), true);
        turn.setDistance(0);

        return Dijkstra.findShortestDistance(turn,
                                             node -> ((Turn2) node).isGameFinished() &&
                                                     ((Turn2) node).isPlayerAlive())
                       .orElseThrow(() -> new AssertionError("Eek!"))
                       .getDistance();
    }

    public void setPlayerHitPoints(int playerHitPoints) {
        this.playerHitPoints = playerHitPoints;
    }

    public void setPlayerMana(int playerMana) {
        this.playerMana = playerMana;
    }

    private Set<Spell> createSpells() {
        var magicMissile = new Spell("Magic Missile", 4, 0, 0, 0, 0, 53);
        var drain = new Spell("Drain", 2, 2, 0, 0, 0, 73);
        var shield = new Spell("Shield", 0, 0, 7, 0, 6, 113);
        var poison = new Spell("Poison", 3, 0, 0, 0, 6, 173);
        var recharge = new Spell("Recharge", 0, 0, 0, 101, 5, 229);

        return Set.of(magicMissile, drain, shield, poison, recharge);
    }

    private Player createPlayer() {
        return new Player(playerHitPoints, playerMana);
    }

    private Boss createBoss(List<String> bossData) {
        return new Boss(Integer.parseInt(bossData.get(0).replace("Hit Points: ", "")),
                        Integer.parseInt(bossData.get(1).replace("Damage: ", "")));
    }

    private static class Turn extends Node<Turn> {
        private final Player player;
        private final Boss boss;
        private final List<ActiveSpell> activeSpells;
        private final Set<Spell> spells;
        private final List<Spell> castedSpells;
        private final boolean isPlayerTurn;

        private Turn(Player player, Boss boss, List<ActiveSpell> activeSpells,
                     Set<Spell> spells, List<Spell> castedSpells,
                     boolean isPlayerTurn) {
            this.player = player;
            this.boss = boss;
            this.activeSpells = activeSpells;
            this.spells = spells;
            this.castedSpells = castedSpells;
            this.isPlayerTurn = isPlayerTurn;
        }

        @Override
        public Map<Turn, Long> getNeighbors() {
            var neighbors = new HashMap<Turn, Long>();

            if (isGameFinished()) {
                return Map.of();
            }

            var totalDamage = activeSpells.stream().map(ActiveSpell::getSpell).mapToInt(Spell::damage).sum();
            var totalHealing = activeSpells.stream().map(ActiveSpell::getSpell).mapToInt(Spell::healing).sum();
            var totalManaIncrease =
                    activeSpells.stream().map(ActiveSpell::getSpell).mapToInt(Spell::manaIncrease).sum();
            var totalDefense = activeSpells.stream().map(ActiveSpell::getSpell).mapToInt(Spell::defense).sum();

            activeSpells.forEach(ActiveSpell::decrementDuration);
            activeSpells.removeIf(activeSpell -> activeSpell.remainingDuration <= 0);

            if (isPlayerTurn) {
                if (boss.hitPoints - totalDamage <= 0) {
                    var newPlayer = new Player(player.hitPoints + totalHealing, player.mana + totalManaIncrease);
                    var newBoss = new Boss(boss.hitPoints - totalDamage, boss.attack);
                    var nextTurn = new Turn(newPlayer, newBoss, activeSpells, spells, List.copyOf(castedSpells), false);
                    neighbors.put(nextTurn, 0L);
                } else {
                    for (var spell : spells) {
                        if (spell.cost > player.mana + totalManaIncrease) {
                            continue;
                        }
                        if (activeSpells.stream().anyMatch(activeSpell -> activeSpell.spell == spell)) {
                            continue;
                        }

                        var newActiveSpells = new ArrayList<ActiveSpell>();

                        activeSpells.forEach(activeSpell -> newActiveSpells.add(
                                new ActiveSpell(activeSpell.spell, activeSpell.remainingDuration)));

                        Player newPlayer;
                        Boss newBoss;
                        if (spell.effectDuration == 0) {
                            newPlayer = new Player(player.hitPoints + totalHealing + spell.healing,
                                                   player.mana + totalManaIncrease - spell.cost);
                            newBoss = new Boss(boss.hitPoints - (totalDamage + spell.damage), boss.attack);
                        } else {
                            newPlayer = new Player(player.hitPoints + totalHealing,
                                                   player.mana + totalManaIncrease - spell.cost);
                            newBoss = new Boss(boss.hitPoints - totalDamage, boss.attack);
                            newActiveSpells.add(new ActiveSpell(spell, spell.effectDuration));
                        }

                        var newCastedSpells = new ArrayList<>(castedSpells);
                        newCastedSpells.add(spell);

                        var nextTurn =
                                new Turn(newPlayer, newBoss, newActiveSpells, spells, List.copyOf(newCastedSpells),
                                         false);
                        neighbors.put(nextTurn, (long) spell.cost);
                    }
                }
            } else {
                var bossAttack = Math.max(1, boss.attack - totalDefense);

                var newBoss = new Boss(boss.hitPoints - totalDamage, boss.attack);

                Player newPlayer;
                if (newBoss.hitPoints <= 0) {
                    newPlayer = new Player(player.hitPoints, player.mana);
                } else {
                    newPlayer =
                            new Player(player.hitPoints + totalHealing - bossAttack, player.mana + totalManaIncrease);
                }

                var newActiveSpells = new ArrayList<ActiveSpell>();

                activeSpells.forEach(activeSpell -> newActiveSpells.add(
                        new ActiveSpell(activeSpell.spell, activeSpell.remainingDuration)));

                var nextTurn = new Turn(newPlayer, newBoss, newActiveSpells, spells, castedSpells, true);
                neighbors.put(nextTurn, 0L);
            }

            return neighbors;
        }

        @Override
        public void printStateInformation() {
            System.out.println(castedSpells);
        }

        public boolean isGameFinished() {
            return player.hitPoints <= 0 || boss.hitPoints <= 0 || player.mana <= 0;
        }

        public boolean isPlayerAlive() {
            return player.hitPoints > 0 && player.mana > 0;
        }
    }

    private static class Turn2 extends Node<Turn2> {
        private Player player;
        private final Boss boss;
        private final List<ActiveSpell> activeSpells;
        private final Set<Spell> spells;
        private final List<Spell> castedSpells;
        private final boolean isPlayerTurn;

        private Turn2(Player player, Boss boss, List<ActiveSpell> activeSpells,
                      Set<Spell> spells, List<Spell> castedSpells,
                      boolean isPlayerTurn) {
            this.player = player;
            this.boss = boss;
            this.activeSpells = activeSpells;
            this.spells = spells;
            this.castedSpells = castedSpells;
            this.isPlayerTurn = isPlayerTurn;
        }

        @Override
        public Map<Turn2, Long> getNeighbors() {
            var neighbors = new HashMap<Turn2, Long>();

            if (isGameFinished()) {
                return Map.of();
            }

            var totalDamage = activeSpells.stream().map(ActiveSpell::getSpell).mapToInt(Spell::damage).sum();
            var totalHealing = activeSpells.stream().map(ActiveSpell::getSpell).mapToInt(Spell::healing).sum();
            var totalManaIncrease =
                    activeSpells.stream().map(ActiveSpell::getSpell).mapToInt(Spell::manaIncrease).sum();
            var totalDefense = activeSpells.stream().map(ActiveSpell::getSpell).mapToInt(Spell::defense).sum();

            activeSpells.forEach(ActiveSpell::decrementDuration);
            activeSpells.removeIf(activeSpell -> activeSpell.remainingDuration <= 0);

            if (isPlayerTurn) {
                player = new Player(player.hitPoints - 1, player.mana);

                if (player.hitPoints <= 0) {
                    return Map.of();
                }
                if (boss.hitPoints - totalDamage <= 0) {
                    var newPlayer = new Player(player.hitPoints + totalHealing, player.mana + totalManaIncrease);
                    var newBoss = new Boss(boss.hitPoints - totalDamage, boss.attack);
                    var nextTurn =
                            new Turn2(newPlayer, newBoss, activeSpells, spells, List.copyOf(castedSpells), false);
                    neighbors.put(nextTurn, 0L);
                } else {
                    for (var spell : spells) {
                        if (spell.cost > player.mana + totalManaIncrease) {
                            continue;
                        }
                        if (activeSpells.stream().anyMatch(activeSpell -> activeSpell.spell == spell)) {
                            continue;
                        }

                        var newActiveSpells = new ArrayList<ActiveSpell>();

                        activeSpells.forEach(activeSpell -> newActiveSpells.add(
                                new ActiveSpell(activeSpell.spell, activeSpell.remainingDuration)));

                        Player newPlayer;
                        Boss newBoss;
                        if (spell.effectDuration == 0) {
                            newPlayer = new Player(player.hitPoints + totalHealing + spell.healing,
                                                   player.mana + totalManaIncrease - spell.cost);
                            newBoss = new Boss(boss.hitPoints - (totalDamage + spell.damage), boss.attack);
                        } else {
                            newPlayer = new Player(player.hitPoints + totalHealing,
                                                   player.mana + totalManaIncrease - spell.cost);
                            newBoss = new Boss(boss.hitPoints - totalDamage, boss.attack);
                            newActiveSpells.add(new ActiveSpell(spell, spell.effectDuration));
                        }

                        var newCastedSpells = new ArrayList<>(castedSpells);
                        newCastedSpells.add(spell);

                        var nextTurn =
                                new Turn2(newPlayer, newBoss, newActiveSpells, spells, List.copyOf(newCastedSpells),
                                          false);
                        neighbors.put(nextTurn, (long) spell.cost);
                    }
                }
            } else {
                var bossAttack = Math.max(1, boss.attack - totalDefense);

                var newBoss = new Boss(boss.hitPoints - totalDamage, boss.attack);

                Player newPlayer;
                if (newBoss.hitPoints <= 0) {
                    newPlayer = new Player(player.hitPoints, player.mana);
                } else {
                    newPlayer =
                            new Player(player.hitPoints + totalHealing - bossAttack, player.mana + totalManaIncrease);
                }

                var newActiveSpells = new ArrayList<ActiveSpell>();

                activeSpells.forEach(activeSpell -> newActiveSpells.add(
                        new ActiveSpell(activeSpell.spell, activeSpell.remainingDuration)));

                var nextTurn = new Turn2(newPlayer, newBoss, newActiveSpells, spells, castedSpells, true);
                neighbors.put(nextTurn, 0L);
            }

            return neighbors;
        }

        @Override
        public void printStateInformation() {
            System.out.println(castedSpells);
        }

        public boolean isGameFinished() {
            return player.hitPoints <= 0 || boss.hitPoints <= 0 || player.mana <= 0;
        }

        public boolean isPlayerAlive() {
            return player.hitPoints > 0 && player.mana > 0;
        }
    }

    private record Boss(int hitPoints, int attack) {
    }

    private record Player(int hitPoints, int mana) {
    }

    private static class ActiveSpell {
        private final Spell spell;
        private int remainingDuration;

        private ActiveSpell(Spell spell, int remainingDuration) {
            this.spell = spell;
            this.remainingDuration = remainingDuration;
        }

        public Spell getSpell() {
            return spell;
        }

        public void decrementDuration() {
            remainingDuration--;
        }
    }

    private record Spell(String name, int damage, int healing, int defense, int manaIncrease, int effectDuration,
                         int cost) {
        @Override
        public String toString() {
            return name;
        }
    }
}
