package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day22 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var techniques = input.flatMap(this::toTechnique)
                              .toList();
        
        return finalPositionForCard(techniques);
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        return solveB(input.toList());
    }
    
    private Stream<Technique> toTechnique(String operation) {
        if (operation.startsWith("deal into")) {
            return Stream.of(new Increment((long) 10007 - 1), new Cut(1));
        } else if (operation.startsWith("deal with increment")) {
            return Stream.of(new Increment(Integer.parseInt(operation.split(" ")[3])));
        } else if (operation.startsWith("cut")) {
            return Stream.of(new Cut(Integer.parseInt(operation.split(" ")[1])));
        } else {
            throw new AssertionError("Eek!");
        }
    }
    
    private long finalPositionForCard(List<Technique> process) {
        process = reduceShuffleProcess(process);
        long card = 2019;
        
        for (var technique : process) {
            card = technique.nextPositionForCard(card);
        }
        
        return card;
    }

    private List<Technique> reduceShuffleProcess(List<Technique> initialProcess) {
        var process = initialProcess;
        while (process.size() > 2) {
            var offset = 0;
            while (offset < process.size() - 1) {
                if (process.get(offset).canBeCombinedWith(process.get(offset + 1))) {
                    var combined = process.get(offset).combine(process.get(offset + 1), 10007);
                    var newProcess = new ArrayList<Technique>();
                    newProcess.addAll(process.subList(0, offset));
                    newProcess.addAll(combined);
                    newProcess.addAll(process.subList(offset + 2, process.size()));
                    process = newProcess;
                    offset = Math.max(0, offset - 1);
                } else {
                    offset++;
                }
            }
        }
        return process;
    }

    private sealed abstract static class Technique {
        abstract long nextPositionForCard(long card);

        private long mulMod(long a, long b, long mod) {
            return BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)).mod(BigInteger.valueOf(mod)).longValueExact();
        }

        public List<Technique> combine(Technique other, long deckSize) {
            if (this instanceof Cut thisCut && other instanceof Cut otherCut) {
                return List.of(new Cut(Math.floorMod(thisCut.value + otherCut.value, deckSize)));
            } else if (this instanceof Increment thisIncrement && other instanceof Increment otherIncrement) {
                return List.of(new Increment(mulMod(thisIncrement.value, otherIncrement.value, deckSize)));
            } else if (this instanceof Cut cut && other instanceof Increment increment) {
                return List.of(new Increment(increment.value), new Cut(mulMod(cut.value, increment.value, deckSize)));
            } else {
                throw new AssertionError("Eek!");
            }
        }

        public boolean canBeCombinedWith(Technique other) {
            return !(this instanceof Increment && other instanceof Cut);
        }
    }
    
    private static final class Cut extends Technique {
        private final long value;

        private Cut(long value) {
            this.value = value;
        }

        @Override
        long nextPositionForCard(long card) {
            return Math.floorMod(card - value, (long) 10007);
        }
    }
    
    private static final class Increment extends Technique {
        private final long value;

        private Increment(long value) {
            this.value = value;
        }

        @Override
        long nextPositionForCard(long card) {
            return Math.floorMod(card * value, (long) 10007);
        }
    }

    private long solveB(List<String> input) {
        // Had to look at reddit: https://www.reddit.com/r/adventofcode/comments/ee0rqi/2019_day_22_solutions/
        long m = 119315717514047L;
        long n = 101741582076661L;
        long pos = 2020;

        long a = 1;
        long b = 0;
        for (String operation : input) {
            if ("deal into new stack".equals(operation)) {
                a = -a % m;
                b = (m - 1 - b) % m;
            } else if (operation.startsWith("deal")) {
                long increment = Long.parseLong(operation.substring(operation.lastIndexOf(' ') + 1));
                a = (a * increment) % m;
                b = (b * increment) % m;
            } else { // cut
                long cut = Long.parseLong(operation.substring(operation.lastIndexOf(' ') + 1));
                b = (b - cut) % m;
            }
        }

        // r = (b * pow(1-a, m-2, m)) % m
        var mm = BigInteger.valueOf(m);
        var r = BigInteger.valueOf(b).multiply(BigInteger.valueOf(1 - a).modPow(BigInteger.valueOf(m - 2), mm)).mod(BigInteger.valueOf(m));

        // return ((pos - r) * pow(a, n*(m-2), m) + r) % m
        return BigInteger.valueOf(pos - r.longValue()).multiply(BigInteger.valueOf(a).modPow(BigInteger.valueOf(n).multiply(BigInteger.valueOf(m - 2)), mm)).add(r)
                         .mod(BigInteger.valueOf(m)).longValue();
    }
}
