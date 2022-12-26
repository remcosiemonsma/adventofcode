package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.stream.Stream;

public class Day21 {
    public int handlePart1(Stream<String> input) {
        var bossData = input.toList();

        var boss = createWarrior(bossData);
        var player = createPlayer();

        var shop = createShop();

        var start = new Game(shop, player, boss);
        start.setDistance(0);

        return (int) Dijkstra.findShortestDistance(start, (node) -> ((Game) node).isPlayerWinner()).getDistance();
    }

    public int handlePart2(Stream<String> input) {
        var bossData = input.toList();

        var boss = createWarrior(bossData);
        var player = createPlayer();

        var shop = createShop();

        var start = new Game(shop, player, boss);
        start.setDistance(0);

        var games = new ArrayList<Game>();

        var nextGames = new ArrayList<Game>();
        nextGames.add(start);

        while (!nextGames.isEmpty()) {
            Game next = nextGames.remove(0);
            Map<Game, Long> neighbors = next.getNeighbors();
            neighbors.forEach((game, cost) -> game.setDistance(next.getDistance() + cost));
            games.add(next);
            nextGames.addAll(neighbors.keySet());
        }

        return (int) games.stream()
                          .filter(game -> !game.isPlayerWinner())
                          .mapToLong(Game::getDistance)
                          .max()
                          .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private Shop createShop() {
        var dagger = new Weapon("Dagger", 8, 4);
        var shortsword = new Weapon("Shortsword", 10, 5);
        var warhammer = new Weapon("Warhammer", 25, 6);
        var longsword = new Weapon("Longsword", 40, 7);
        var greataxe = new Weapon("Greataxe", 74, 8);

        var leather = new Armor("Leather", 13, 1);
        var chainmail = new Armor("Chainmail", 31, 2);
        var splintmail = new Armor("Splintmail", 53, 3);
        var bandedmail = new Armor("Bandedmail", 75, 4);
        var platemail = new Armor("Platemail", 102, 5);

        var damage1 = new Ring("Damage +1 ", 25, 1, 0);
        var damage2 = new Ring("Damage +2 ", 50, 2, 0);
        var damage3 = new Ring("Damage +3 ", 100, 3, 0);
        var defense1 = new Ring("Defense +1", 20, 0, 1);
        var defense2 = new Ring("Defense +2", 40, 0, 2);
        var defense3 = new Ring("Defense +3", 80, 0, 3);

        return new Shop(Set.of(leather, chainmail, splintmail, bandedmail, platemail),
                        Set.of(dagger, shortsword, warhammer, longsword, greataxe),
                        Set.of(damage1, damage2, damage3, defense1, defense2, defense3));
    }

    private Warrior createPlayer() {
        return new Warrior("Player", 0, 0, null, null, null, null);
    }

    private Warrior createWarrior(List<String> bossData) {
        return new Warrior("Boss",
                           Integer.parseInt(bossData.get(1).replace("Damage: ", "")),
                           Integer.parseInt(bossData.get(2).replace("Armor: ", "")),
                           null, null, null, null);
    }

    private static class Game extends Node {
        private final Shop shop;
        private final Warrior player;
        private final Warrior boss;
        private final Warrior winner;

        public Game(Shop shop, Warrior player, Warrior boss) {
            this.shop = shop;
            this.player = player;
            this.boss = boss;

            winner = determineWinner(player, boss);
        }

        private Warrior determineWinner(Warrior player, Warrior boss) {
            var playerDamage = player.getTotalDamage();
            var playerDefense = player.getTotalDefense();
            var bossDamage = boss.getTotalDamage();
            var bossDefense = boss.getTotalDefense();

            var playerAttack = Math.max(playerDamage - bossDefense, 1);
            var bossAttack = Math.max(bossDamage - playerDefense, 1);
            while (player.health > 0 && boss.health > 0) {
                boss.doDamage(playerAttack);
                if (boss.health > 0) {
                    player.doDamage(bossAttack);
                }
            }
            if (player.health > 0) {
                return player;
            } else {
                return boss;
            }
        }

        @Override
        public Map<Game, Long> getNeighbors() {
            var neighbors = new HashMap<Game, Long>();

            if (player.weapon == null) {
                for (var weapon : shop.weapons) {
                    var newShop = new Shop(shop.armors, Set.of(), shop.rings);
                    var newPlayer =
                            new Warrior(player.name, player.damage, player.defense, player.armor, weapon,
                                        player.leftRing,
                                        player.rightRing);
                    var newBoss =
                            new Warrior(boss.name, boss.damage, boss.defense, boss.armor, boss.weapon, boss.leftRing,
                                        boss.rightRing);

                    var game = new Game(newShop, newPlayer, newBoss);
                    neighbors.put(game, (long) weapon.cost);
                }
            } else {
                for (var armor : shop.armors) {
                    var newShop = new Shop(Set.of(), shop.weapons, shop.rings);
                    var newPlayer =
                            new Warrior(player.name, player.damage, player.defense, armor, player.weapon,
                                        player.leftRing,
                                        player.rightRing);
                    var newBoss =
                            new Warrior(boss.name, boss.damage, boss.defense, boss.armor, boss.weapon, boss.leftRing,
                                        boss.rightRing);

                    var game = new Game(newShop, newPlayer, newBoss);
                    neighbors.put(game, (long) armor.cost);
                }
                if (player.leftRing == null) {
                    for (var leftRing : shop.rings) {
                        var newRings = new HashSet<>(shop.rings);
                        newRings.remove(leftRing);
                        var newShop = new Shop(shop.armors, shop.weapons, newRings);
                        var newPlayer =
                                new Warrior(player.name, player.damage, player.defense, player.armor, player.weapon,
                                            leftRing,
                                            player.rightRing);
                        var newBoss =
                                new Warrior(boss.name, boss.damage, boss.defense, boss.armor, boss.weapon,
                                            boss.leftRing,
                                            boss.rightRing);

                        var game = new Game(newShop, newPlayer, newBoss);
                        neighbors.put(game, (long) leftRing.cost);
                    }
                }
                if (player.rightRing == null) {
                    for (var rightRing : shop.rings) {
                        var newRings = new HashSet<>(shop.rings);
                        newRings.remove(rightRing);
                        var newShop = new Shop(shop.armors, shop.weapons, newRings);
                        var newPlayer =
                                new Warrior(player.name, player.damage, player.defense, player.armor, player.weapon,
                                            player.leftRing,
                                            rightRing);
                        var newBoss =
                                new Warrior(boss.name, boss.damage, boss.defense, boss.armor, boss.weapon,
                                            boss.leftRing,
                                            boss.rightRing);

                        var game = new Game(newShop, newPlayer, newBoss);
                        neighbors.put(game, (long) rightRing.cost);
                    }
                }
            }

            return neighbors;
        }

        @Override
        public void printStateInformation() {}

        public boolean isPlayerWinner() {
            return winner == player;
        }
    }

    private record Shop(Set<Armor> armors,
                        Set<Weapon> weapons,
                        Set<Ring> rings) {
    }

    private static class Warrior {
        private final String name;
        private final int damage;
        private final int defense;
        private final Armor armor;
        private final Weapon weapon;
        private final Ring leftRing;
        private final Ring rightRing;
        private int health;

        private Warrior(String name, int damage, int defense, Armor armor, Weapon weapon,
                        Ring leftRing, Ring rightRing) {
            this.name = name;
            this.damage = damage;
            this.defense = defense;
            this.armor = armor;
            this.weapon = weapon;
            this.leftRing = leftRing;
            this.rightRing = rightRing;
            this.health = 100;
        }

        public int getTotalDamage() {
            var totalDamage = this.damage;
            if (this.weapon != null) {
                totalDamage += weapon.damage;
            }
            if (leftRing != null) {
                totalDamage += leftRing.damage;
            }
            if (rightRing != null) {
                totalDamage += rightRing.damage;
            }
            return totalDamage;
        }

        public int getTotalDefense() {
            int totalDefense = this.defense;
            if (armor != null) {
                totalDefense += armor.defense;
            }
            if (leftRing != null) {
                totalDefense += leftRing.defense;
            }
            if (rightRing != null) {
                totalDefense += rightRing.defense;
            }
            return totalDefense;
        }

        public void doDamage(int damage) {
            health -= damage;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            var warrior = (Warrior) o;
            return Objects.equals(name, warrior.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public String toString() {
            return "Warrior{" +
                   "name='" + name + '\'' +
                   ", damage=" + damage +
                   ", defense=" + defense +
                   ", armor=" + armor +
                   ", weapon=" + weapon +
                   ", leftRing=" + leftRing +
                   ", rightRing=" + rightRing +
                   ", health=" + health +
                   '}';
        }
    }

    private record Armor(String name, int cost, int defense) {
    }

    private record Weapon(String name, int cost, int damage) {
    }

    private record Ring(String name, int cost, int damage, int defense) {
    }
}
