package nl.remcoder.adventofcode.day10;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Part1 {
    public static void main(String[] args) throws Exception {
        int[] hashList = IntStream.range(0, 256).toArray();

        int skipSize = 0;
        int position = 0;

        int[] values = Arrays.stream(Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day10/input").toURI())).get(0).split(",")).mapToInt(Integer::parseInt).toArray();

        for (int value : values) {
            if (position + value < hashList.length) {
                for (int i = 0; i < value / 2; i++) {
                    int temp = hashList[position + i];
                    hashList[position + i] = hashList[position + (value - i) - 1];
                    hashList[position + (value - i) - 1] = temp;
                }
            } else {
                for (int i = 0; i < value / 2; i++) {
                    int positioni = position + i;
                    if (positioni >= hashList.length) {
                        positioni -= hashList.length;
                    }
                    int positionj = position + (value - i) - 1;
                    if (positionj >= hashList.length) {
                        positionj -= hashList.length;
                    }
                    int temp = hashList[positioni];
                    hashList[positioni] = hashList[positionj];
                    hashList[positionj] = temp;
                }
            }

            position += value + skipSize;
            while (position >= hashList.length) {
                position -= hashList.length;
            }
            skipSize++;
        }

        System.out.println(hashList[0] * hashList[1]);
    }
}
