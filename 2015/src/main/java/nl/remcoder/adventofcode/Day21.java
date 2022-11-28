package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.stream.Stream;

public class Day21 {
    public int handlePart1(Stream<String> input) {
        List<String> bossData = input.toList();

        Warrior boss = createWarrior(bossData);
        Warrior player = createPlayer();

        Shop shop = createShop();

        Game start = new Game(shop, player, boss);
        start.setDistance(0);

        return (int) Dijkstra.findShortestDistance(start, (node) -> ((Game) node).isPlayerWinner());
    }

    public int handlePart2(Stream<String> input) {
        List<String> bossData = input.toList();

        Warrior boss = createWarrior(bossData);
        Warrior player = createPlayer();

        Shop shop = createShop();

        Game start = new Game(shop, player, boss);
        start.setDistance(0);

        List<Game> games = new ArrayList<>();

        List<Game> nextGames = new ArrayList<>();
        nextGames.add(start);

        while (!nextGames.isEmpty()) {
            Game next = nextGames.remove(0);
            Map<Game, Long> neighbors = next.getNeighbors();
            neighbors.forEach((game, cost) -> game.setDistance(next.distance + cost));
            games.add(next);
            nextGames.addAll(neighbors.keySet());
        }

        return (int) games.stream()
                          .filter(game -> !game.isPlayerWinner())
                          .mapToLong(Game::getDistance)
                          .max()
                          .getAsLong();
    }

    private Shop createShop() {
        Weapon dagger = new Weapon("Dagger", 8, 4);
        Weapon shortsword = new Weapon("Shortsword", 10, 5);
        Weapon warhammer = new Weapon("Warhammer", 25, 6);
        Weapon longsword = new Weapon("Longsword", 40, 7);
        Weapon greataxe = new Weapon("Greataxe", 74, 8);

        Armor leather = new Armor("Leather", 13, 1);
        Armor chainmail = new Armor("Chainmail", 31, 2);
        Armor splintmail = new Armor("Splintmail", 53, 3);
        Armor bandedmail = new Armor("Bandedmail", 75, 4);
        Armor platemail = new Armor("Platemail", 102, 5);

        Ring damage1 = new Ring("Damage +1 ", 25, 1, 0);
        Ring damage2 = new Ring("Damage +2 ", 50, 2, 0);
        Ring damage3 = new Ring("Damage +3 ", 100, 3, 0);
        Ring defense1 = new Ring("Defense +1", 20, 0, 1);
        Ring defense2 = new Ring("Defense +2", 40, 0, 2);
        Ring defense3 = new Ring("Defense +3", 80, 0, 3);

        Shop shop = new Shop(Set.of(leather, chainmail, splintmail, bandedmail, platemail),
                             Set.of(dagger, shortsword, warhammer, longsword, greataxe),
                             Set.of(damage1, damage2, damage3, defense1, defense2, defense3));
        return shop;
    }

    private Warrior createPlayer() {
        Warrior player = new Warrior("Player", 0, 0, null, null, null, null);
        return player;
    }

    private Warrior createWarrior(List<String> bossData) {
        Warrior boss = new Warrior("Boss",
                                   Integer.parseInt(bossData.get(1).replace("Damage: ", "")),
                                   Integer.parseInt(bossData.get(2).replace("Armor: ", "")),
                                   null, null, null, null);
        return boss;
    }

    private static class Game implements Node {
        private final Shop shop;
        private final Warrior player;
        private final Warrior boss;
        private final Warrior winner;
        private long distance = Integer.MAX_VALUE;
        private boolean visited;

        public Game(Shop shop, Warrior player, Warrior boss) {
            this.shop = shop;
            this.player = player;
            this.boss = boss;

            winner = determineWinner(player, boss);
        }

        private Warrior determineWinner(Warrior player, Warrior boss) {
            int playerDamage = player.getTotalDamage();
            int playerDefense = player.getTotalDefense();
            int bossDamage = boss.getTotalDamage();
            int bossDefense = boss.getTotalDefense();

            int playerAttack = Math.max(playerDamage - bossDefense, 1);
            int bossAttack = Math.max(bossDamage - playerDefense, 1);
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
            Map<Game, Long> neighbors = new HashMap<>();

            if (player.weapon == null) {
                for (Weapon weapon : shop.weapons) {
                    Shop newShop = new Shop(shop.armors, Set.of(), shop.rings);
                    Warrior newPlayer =
                            new Warrior(player.name, player.damage, player.defense, player.armor, weapon,
                                        player.leftRing,
                                        player.rightRing);
                    Warrior newBoss =
                            new Warrior(boss.name, boss.damage, boss.defense, boss.armor, boss.weapon, boss.leftRing,
                                        boss.rightRing);

                    Game game = new Game(newShop, newPlayer, newBoss);
                    neighbors.put(game, (long) weapon.cost);
                }
            } else {
                for (Armor armor : shop.armors) {
                    Shop newShop = new Shop(Set.of(), shop.weapons, shop.rings);
                    Warrior newPlayer =
                            new Warrior(player.name, player.damage, player.defense, armor, player.weapon,
                                        player.leftRing,
                                        player.rightRing);
                    Warrior newBoss =
                            new Warrior(boss.name, boss.damage, boss.defense, boss.armor, boss.weapon, boss.leftRing,
                                        boss.rightRing);

                    Game game = new Game(newShop, newPlayer, newBoss);
                    neighbors.put(game, (long) armor.cost);
                }
                if (player.leftRing == null) {
                    for (Ring leftRing : shop.rings) {
                        Set<Ring> newRings = new HashSet<>(shop.rings);
                        newRings.remove(leftRing);
                        Shop newShop = new Shop(shop.armors, shop.weapons, newRings);
                        Warrior newPlayer =
                                new Warrior(player.name, player.damage, player.defense, player.armor, player.weapon,
                                            leftRing,
                                            player.rightRing);
                        Warrior newBoss =
                                new Warrior(boss.name, boss.damage, boss.defense, boss.armor, boss.weapon,
                                            boss.leftRing,
                                            boss.rightRing);

                        Game game = new Game(newShop, newPlayer, newBoss);
                        neighbors.put(game, (long) leftRing.cost);
                    }
                }
                if (player.rightRing == null) {
                    for (Ring rightRing : shop.rings) {
                        Set<Ring> newRings = new HashSet<>(shop.rings);
                        newRings.remove(rightRing);
                        Shop newShop = new Shop(shop.armors, shop.weapons, newRings);
                        Warrior newPlayer =
                                new Warrior(player.name, player.damage, player.defense, player.armor, player.weapon,
                                            player.leftRing,
                                            rightRing);
                        Warrior newBoss =
                                new Warrior(boss.name, boss.damage, boss.defense, boss.armor, boss.weapon,
                                            boss.leftRing,
                                            boss.rightRing);

                        Game game = new Game(newShop, newPlayer, newBoss);
                        neighbors.put(game, (long) rightRing.cost);
                    }
                }
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
            int totalDamage = this.damage;
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
            Warrior warrior = (Warrior) o;
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
