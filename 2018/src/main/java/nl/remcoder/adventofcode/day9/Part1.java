package nl.remcoder.adventofcode.day9;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Part1 {
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
        int currentmarble = 0;

        List<Integer> marbles = new ArrayList<>();

        marbles.add(0);

        for (int marble = 1; marble <= amountOfMarbles; marble++) {
            if (marble % 23 != 0) {
                int newMarblePos = currentmarble + 2;
                if (newMarblePos > marbles.size()) {
                    newMarblePos -= marbles.size();
                }

                marbles.add(newMarblePos, marble);

                currentmarble = newMarblePos;
            } else {
                players[currentplayer].score += marble;

                int newMarblePos = currentmarble - 7;
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

//            System.out.println("Iteration: " + marble + ", marbles: " + marbles);
        }

        System.out.println(Arrays.stream(players).max(Comparator.comparing(player -> player.score)).get());
    }

    private static class Player {
        final int id;
        int score = 0;

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
