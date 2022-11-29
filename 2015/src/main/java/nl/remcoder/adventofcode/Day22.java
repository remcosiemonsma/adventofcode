package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.stream.Stream;

public class Day22 {
    public int playerHitPoints = 50;
    public int playerMana = 500;

    public int handlePart1(Stream<String> input) {
        List<String> bossData = input.toList();

        Boss boss = createBoss(bossData);
        Player player = createPlayer();

        Turn turn = new Turn(player, boss, new ArrayList<>(), createSpells(), List.of(), true);
        turn.setDistance(0);

        return (int) Dijkstra.findShortestDistance(turn,
                                             node -> ((Turn) node).isGameFinished() && ((Turn) node).isPlayerAlive());
    }

    public int handlePart2(Stream<String> input) {
        return 0;
    }

    private Set<Spell> createSpells() {
        Spell magicMissile = new Spell("Magic Missile", 4, 0, 0, 0, 0, 53);
        Spell drain = new Spell("Drain", 2, 2, 0, 0, 0, 73);
        Spell shield = new Spell("Shield", 0, 0, 7, 0, 6, 113);
        Spell poison = new Spell("Poison", 3, 0, 0, 0, 6, 173);
        Spell recharge = new Spell("Recharge", 0, 0, 0, 101, 5, 229);

        return Set.of(magicMissile, drain, shield, poison, recharge);
    }

    private Player createPlayer() {
        return new Player(playerHitPoints, playerMana);
    }

    private Boss createBoss(List<String> bossData) {
        return new Boss(Integer.parseInt(bossData.get(0).replace("Hit Points: ", "")),
                        Integer.parseInt(bossData.get(1).replace("Damage: ", "")));
    }

    private static class Turn implements Node {
        private final Player player;
        private final Boss boss;
        private final List<ActiveSpell> activeSpells;
        private final Set<Spell> spells;
        private final List<Spell> castedSpells;
        private final boolean isPlayerTurn;
        private long distance = Long.MAX_VALUE;
        private boolean visited;

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
        public Map<? extends Node, Long> getNeighbors() {
            Map<Turn, Long> neighbors = new HashMap<>();

            if (isGameFinished()) {
                return Map.of();
            }

            activeSpells.forEach(ActiveSpell::decrementDuration);
            activeSpells.removeIf(activeSpell -> activeSpell.remainingDuration <= 0);

            int totalDamage = activeSpells.stream().map(ActiveSpell::getSpell).mapToInt(Spell::damage).sum();
            int totalHealing = activeSpells.stream().map(ActiveSpell::getSpell).mapToInt(Spell::healing).sum();
            int totalManaIncrease = activeSpells.stream().map(ActiveSpell::getSpell).mapToInt(Spell::manaIncrease).sum();
            int totalDefense = activeSpells.stream().map(ActiveSpell::getSpell).mapToInt(Spell::defense).sum();

            if (isPlayerTurn) {
                if (boss.hitPoints - totalDamage <= 0) {
                    Player newPlayer = new Player(player.hitPoints + totalHealing, player.mana + totalManaIncrease);
                    Boss newBoss = new Boss(boss.hitPoints - totalDamage, boss.attack);
                    Turn nextTurn = new Turn(newPlayer, newBoss, activeSpells, spells, List.copyOf(castedSpells), false);
                    neighbors.put(nextTurn, 0L);
                }

                for (Spell spell : spells) {
                    if (spell.cost > player.mana + totalManaIncrease) {
                        continue;
                    }
                    if (activeSpells.stream().anyMatch(activeSpell -> activeSpell.spell == spell)) {
                        continue;
                    }

                    List<ActiveSpell> newActiveSpells = new ArrayList<>();

                    activeSpells.forEach(activeSpell -> newActiveSpells.add(new ActiveSpell(activeSpell.spell, activeSpell.remainingDuration)));

                    Player newPlayer;
                    Boss newBoss;
                    if (spell.effectDuration == 0) {
                        newPlayer = new Player(player.hitPoints + totalHealing + spell.healing, player.mana + totalManaIncrease - spell.cost);
                        newBoss = new Boss(boss.hitPoints - (totalDamage + spell.damage), boss.attack);
                    } else {
                        newPlayer = new Player(player.hitPoints + totalHealing, player.mana + totalManaIncrease - spell.cost);
                        newBoss = new Boss(boss.hitPoints - totalDamage, boss.attack);
                        newActiveSpells.add(new ActiveSpell(spell, spell.effectDuration));
                    }

                    List<Spell> newCastedSpells = new ArrayList<>(castedSpells);
                    newCastedSpells.add(spell);

                    Turn nextTurn = new Turn(newPlayer, newBoss, newActiveSpells, spells, List.copyOf(newCastedSpells), false);
                    neighbors.put(nextTurn, (long) spell.cost);
                }
            } else {
                int bossAttack = Math.max(1, boss.attack - totalDefense);

                Boss newBoss = new Boss(boss.hitPoints - totalDamage, boss.attack);

                Player newPlayer;
                if (newBoss.hitPoints <= 0) {
                    newPlayer = new Player(player.hitPoints, player.mana);
                } else {
                    newPlayer = new Player(player.hitPoints + totalHealing - bossAttack, player.mana + totalManaIncrease);
                }

                List<ActiveSpell> newActiveSpells = new ArrayList<>();

                activeSpells.forEach(activeSpell -> newActiveSpells.add(new ActiveSpell(activeSpell.spell, activeSpell.remainingDuration)));

                Turn nextTurn = new Turn(newPlayer, newBoss, newActiveSpells, spells, castedSpells, true);
                neighbors.put(nextTurn, 0L);
            }

            return neighbors;
        }

        @Override
        public long getDistance() {
            return distance;
        }

        @Override
        public boolean isVisited() {
            return visited;
        }

        @Override
        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        @Override
        public void setDistance(long distance) {
            this.distance = distance;
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
