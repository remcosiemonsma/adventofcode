package nl.remcoder.adventofcode.day9;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2 {
    public static void main(String[] args) throws Exception {
        String line = Files.lines(Paths.get(ClassLoader.getSystemResource("day9/input").toURI())).findFirst().get();

        String[] s = line.split(" ");

        int amountOfPlayers = Integer.parseInt(s[0]);
        int amountOfMarbles = Integer.parseInt(s[6]);

        Player[] players = new Player[amountOfPlayers];

        for (int player = 0; player < amountOfPlayers; player++) {
            players[player] = new Player(player + 1);
        }

        int currentplayer = 0;

        List<Integer> marbles = new LinkedList<>();

        marbles.add(0);

        ListIterator<Integer> marbleIterator = marbles.listIterator(1);

        for (int marble = 1; marble <= amountOfMarbles * 100; marble++) {
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

                int marbleToRemove = 0;

                for (int step = 0; step < 7; step++) {
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

//            if (marble % amountOfMarbles == 0) {
//                System.out.println(marble);
//            }

//            System.out.println("Iteration: " + marble + ", marbles: " + marbles);
        }

        System.out.println(Arrays.stream(players).max(Comparator.comparing(player -> player.score)).get());
    }

    private static class Player {
        final int id;
        long score = 0;

        private Player(int id) {
            this.id = id;
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
