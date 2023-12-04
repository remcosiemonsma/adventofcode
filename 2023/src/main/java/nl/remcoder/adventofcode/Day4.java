package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Day4 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.map(this::mapToCard)
                    .mapToInt(Card::score)
                    .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        List<Card> cards = input.map(this::mapToCard)
                                .toList();

        while (cards.stream().anyMatch(card -> card.process(cards))) {}

        return cards.stream()
                    .mapToInt(Card::amountOfCopies)
                    .sum();
    }

    private Card mapToCard(String line) {
        var firstSplit = line.split(":");

        var id = Integer.parseInt(firstSplit[0].substring(4).strip());

        var secondSplit = firstSplit[1].strip().split(" \\| {1,2}");

        var winningNumbers = Arrays.stream(secondSplit[0].split(" {1,2}"))
                                   .map(Integer::parseInt)
                                   .toList();

        var numbers = Arrays.stream(secondSplit[1].split(" {1,2}"))
                            .map(Integer::parseInt)
                            .toList();

        return new Card(id, numbers, winningNumbers);
    }

    private static final class Card {
        private final int id;
        private final List<Integer> numbers;
        private final List<Integer> winningNumbers;
        private final int amountOfWins;
        private final int score;
        private int amountOfCopies = 1;
        private int timesProcessed = 0;

        private Card(int id, List<Integer> numbers, List<Integer> winningNumbers) {
            this.id = id;
            this.numbers = numbers;
            this.winningNumbers = winningNumbers;
            amountOfWins = countWins();
            score = calculateScore();
        }

        public boolean process(List<Card> cards) {
            if (timesProcessed < amountOfCopies) {
                for (int i = 0; i < amountOfWins; i++) {
                    cards.get(id + i).copy();
                }

                timesProcessed++;
                return true;
            } else {
                return false;
            }
        }

        private int countWins() {
            int wins = 0;

            for (var number : numbers) {
                if (winningNumbers.contains(number)) {
                    wins++;
                }
            }

            return wins;
        }

        private int calculateScore() {
            var score = 0;

            for (var number : numbers) {
                if (winningNumbers.contains(number)) {
                    if (score == 0) {
                        score = 1;
                    } else {
                        score *= 2;
                    }
                }
            }

            return score;
        }

        public int id() {
            return id;
        }

        public int amountOfWins() {
            return amountOfWins;
        }

        public int score() {
            return score;
        }

        public void copy() {
            amountOfCopies++;
        }

        public int amountOfCopies() {
            return amountOfCopies;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            var that = (Card) obj;
            return Objects.equals(this.id, that.id) &&
                   Objects.equals(this.numbers, that.numbers) &&
                   Objects.equals(this.winningNumbers, that.winningNumbers);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, numbers, winningNumbers);
        }

        @Override
        public String toString() {
            return "Card{" +
                   "id=" + id +
                   ", amountOfWins=" + amountOfWins +
                   ", score=" + score +
                   ", amountOfCopies=" + amountOfCopies +
                   ", timesProcessed=" + timesProcessed +
                   '}';
        }

    }
}
