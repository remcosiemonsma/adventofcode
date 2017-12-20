package nl.remcoder.adventofcode.day20;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Part1 {
    public static void main(String[] args) throws Exception {
        AtomicInteger lowestAValue = new AtomicInteger(Integer.MAX_VALUE);
        AtomicInteger lowestVValue = new AtomicInteger(Integer.MAX_VALUE);
        AtomicInteger lowestPValue = new AtomicInteger(Integer.MAX_VALUE);
        AtomicInteger index = new AtomicInteger(-1);
        AtomicInteger lowestIndex = new AtomicInteger(Integer.MAX_VALUE);

        Files.lines(Paths.get(ClassLoader.getSystemResource("day20/input").toURI())).forEach(s -> {
            index.addAndGet(1);
            String[] values = s.split(" ");
            int asum =
                    Arrays.stream(values[2].replaceAll("[^\\d,]", "").split(",")).mapToInt(Integer::parseInt)
                            .sum();
            int vsum =
                    Arrays.stream(values[1].replaceAll("[^\\d,]", "").split(",")).mapToInt(Integer::parseInt)
                            .sum();
            int psum =
                    Arrays.stream(values[0].replaceAll("[^\\d,]", "").split(",")).mapToInt(Integer::parseInt)
                            .sum();
            if (asum < lowestAValue.get()) {
                lowestAValue.set(asum);
                lowestVValue.set(vsum);
                lowestPValue.set(psum);
                lowestIndex.set(index.get());
            } else if (asum == lowestAValue.get()) {
                if (vsum < lowestVValue.get()) {
                    lowestVValue.set(vsum);
                    lowestPValue.set(psum);
                    lowestIndex.set(index.get());
                } else if (psum < lowestPValue.get()) {
                    lowestPValue.set(psum);
                    lowestIndex.set(index.get());
                }
            }
        });

        System.out.println(lowestIndex);
    }
}
