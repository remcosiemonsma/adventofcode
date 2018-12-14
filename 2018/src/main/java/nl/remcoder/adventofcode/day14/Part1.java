package nl.remcoder.adventofcode.day14;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws Exception {
        int numberOfRecipes = Integer.parseInt(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day14/input").toURI())).findFirst().get());

        List<Integer> recipyScores = new ArrayList<>(numberOfRecipes + 10);

        recipyScores.add(3);
        recipyScores.add(7);

        int elf1pos = 0;
        int elf2pos = 1;

        for (int i = 0; i < numberOfRecipes + 10; i++) {
            int recipy1Score = recipyScores.get(elf1pos);
            int recipy2Score = recipyScores.get(elf2pos);

            int newRecipyScore = recipy1Score + recipy2Score;

            if (newRecipyScore > 9) {
                recipyScores.add((int) Math.floor(newRecipyScore / 10d));
                recipyScores.add(newRecipyScore % 10);
            } else {
                recipyScores.add(newRecipyScore);
            }

            elf1pos += 1 + recipyScores.get(elf1pos);
            elf2pos += 1 + recipyScores.get(elf2pos);

            while (elf1pos > recipyScores.size() -1) {
                elf1pos -= recipyScores.size();
            }

            while (elf2pos > recipyScores.size() - 1) {
                elf2pos -= recipyScores.size();
            }
        }

        for (Integer recipyScore : recipyScores.subList(numberOfRecipes, numberOfRecipes + 10)) {
            System.out.print(recipyScore);
        }
    }
}
