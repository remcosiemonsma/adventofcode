package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class Day13 implements BiAdventOfCodeSolution<Integer, Long> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var data = input.toList();

        var earliestDepartureTime = Integer.parseInt(data.get(0));

        var busId = Arrays.stream(data.get(1).split(","))
                          .filter(bus -> !"x".equals(bus))
                          .map(Integer::parseInt)
                          .min(Comparator.comparing(bus -> {
                              var nextDepartureTime =
                                      (int) Math.ceil((double) earliestDepartureTime / (double) bus) * bus;
                              return nextDepartureTime - earliestDepartureTime;
                          }))
                          .orElseThrow(() -> new AssertionError("Eek!"));

        var nextDepartureTime = (int) Math.ceil((double) earliestDepartureTime / (double) busId) * busId;
        return (nextDepartureTime - earliestDepartureTime) * busId;
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var data = input.toList();

        var busDepartures = data.get(1).split(",");

        var buses = new ArrayList<Bus>();

        for (var position = 0; position < busDepartures.length; position++) {
            var busId = busDepartures[position];

            if ("x".equals(busId)) {
                continue;
            }

            var bus = new Bus(Integer.parseInt(busId), position);

            buses.add(bus);
        }

        var firstBus = buses.stream()
                            .min(Comparator.comparing(Bus::position))
                            .orElseThrow(() -> new AssertionError("Eek!"));

        var amountOfBusesFound = 1;

        var timeDiff = firstBus.busId;

        for (var timestamp = firstBus.busId; timestamp < Long.MAX_VALUE; timestamp += timeDiff) {
            var busToVerify = buses.get(amountOfBusesFound);

            if ((timestamp + busToVerify.position) % busToVerify.busId == 0) {
                timeDiff = lcm(timeDiff, busToVerify.busId);
                amountOfBusesFound++;
            }

            if (amountOfBusesFound == buses.size()) {
                return timestamp;
            }
        }

        return -1L;
    }

    private long lcm(long number1, long number2) {
        BigInteger bigInteger1 = BigInteger.valueOf(number1);
        BigInteger bigInteger2 = BigInteger.valueOf(number2);

        BigInteger gcd = bigInteger1.gcd(bigInteger2);
        BigInteger absProduct = bigInteger1.multiply(bigInteger2).abs();
        return absProduct.divide(gcd).longValue();
    }

    private record Bus(long busId, int position) {
    }
}
