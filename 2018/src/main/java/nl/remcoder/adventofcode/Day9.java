package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.stream.Stream;

public class Day9 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var line = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        var s = line.split(" ");

        var amountOfPlayers = Integer.parseInt(s[0]);
        var amountOfMarbles = Integer.parseInt(s[6]);

        var players = new Player[amountOfPlayers];

        for (var player = 0; player < amountOfPlayers; player++) {
            players[player] = new Player(player + 1);
        }

        var currentplayer = 0;
        var currentmarble = 0;

        var marbles = new ArrayList<Integer>();

        marbles.add(0);

        for (var marble = 1; marble <= amountOfMarbles; marble++) {
            if (marble % 23 != 0) {
                int newMarblePos = currentmarble + 2;
                if (newMarblePos > marbles.size()) {
                    newMarblePos -= marbles.size();
                }

                marbles.add(newMarblePos, marble);

                currentmarble = newMarblePos;
            } else {
                players[currentplayer].score += marble;

                var newMarblePos = currentmarble - 7;
                if (newMarblePos < 0) {
                    newMarblePos += marbles.size();
                }

                players[currentplayer].score += marbles.remove(newMarblePos);

                currentmarble = newMarblePos;
            }

            currentplayer++;

            if (currentplayer >= players.length) {
                currentplayer = 0;
            }
        }

        return Arrays.stream(players)
                     .max(Comparator.comparing(Player::getScore))
                     .orElseThrow(() -> new AssertionError("Ook!"))
                     .getScore();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var line = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        var s = line.split(" ");

        var amountOfPlayers = Integer.parseInt(s[0]);
        var amountOfMarbles = Integer.parseInt(s[6]);

        var players = new Player[amountOfPlayers];

        for (var player = 0; player < amountOfPlayers; player++) {
            players[player] = new Player(player + 1);
        }

        var currentplayer = 0;

        var marbles = new LinkedList<Integer>();

        marbles.add(0);

        var marbleIterator = marbles.listIterator(1);

        for (var marble = 1; marble <= amountOfMarbles * 100; marble++) {
            if (marble % 23 != 0) {
                if (marbleIterator.hasNext()) {
                    marbleIterator.next();
                } else {
                    marbleIterator = marbles.listIterator();
                }
                if (marbleIterator.hasNext()) {
                    marbleIterator.next();
                } else if (marbles.size() > 2) {
                    marbleIterator = marbles.listIterator(1);
                }

                marbleIterator.add(marble);
                if (marbleIterator.hasNext()) {
                    marbleIterator.previous();
                }
            } else {
                players[currentplayer].score += marble;

                var marbleToRemove = 0;

                for (var step = 0; step < 7; step++) {
                    if (marbleIterator.hasPrevious()) {
                        marbleToRemove = marbleIterator.previous();
                    } else {
                        marbleIterator = marbles.listIterator(marbles.size() - 1);
                        marbleIterator.next();
                        marbleToRemove = marbleIterator.previous();
                    }
                }

                players[currentplayer].score += marbleToRemove;

                marbleIterator.remove();
            }

            currentplayer++;

            if (currentplayer >= players.length) {
                currentplayer = 0;
            }
        }

        return Arrays.stream(players)
                     .max(Comparator.comparing(Player::getScore))
                     .orElseThrow(() -> new AssertionError("Ook!"))
                     .getScore();
    }

    private static class Player {
        private final int id;
        private long score = 0;

        private Player(int id) {
            this.id = id;
        }

        public long getScore() {
            return score;
        }

        @Override
        public String toString() {
            return "Player{" +
                   "id=" + id +
                   ", score=" + score +
                   '}';
        }
    }
}
