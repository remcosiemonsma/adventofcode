package nl.remcoder.adventofcode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13 {
    public int handlePart1(Stream<String> input) {
        List<String> data = input.collect(Collectors.toList());

        int earliestDepartureTime = Integer.parseInt(data.get(0));

        int busId = Arrays.stream(data.get(1).split(","))
                          .filter(bus -> !"x".equals(bus))
                          .map(Integer::parseInt)
                          .min(Comparator.comparing(bus -> {
                              int nextDepartureTime =
                                      (int) Math.ceil((double) earliestDepartureTime / (double) bus) * bus;
                              return nextDepartureTime - earliestDepartureTime;
                          }))
                          .get();

        int nextDepartureTime = (int) Math.ceil((double) earliestDepartureTime / (double) busId) * busId;
        return (nextDepartureTime - earliestDepartureTime) * busId;
    }

    public long handlePart2(Stream<String> input) {
        List<String> data = input.collect(Collectors.toList());

        String[] busDepartures = data.get(1).split(",");

        List<Bus> buses = new ArrayList<>();

        for (int position = 0; position < busDepartures.length; position++) {
            String busId = busDepartures[position];

            if ("x".equals(busId)) {
                continue;
            }

            Bus bus = new Bus(Integer.parseInt(busId), position);

            buses.add(bus);
        }

        Bus firstBus = buses.stream()
                            .min(Comparator.comparing(Bus::getPosition))
                            .get();

        int amountOfBusesFound = 1;

        long timeDiff = firstBus.busId;

        for (long timestamp = firstBus.busId; timestamp < Long.MAX_VALUE; timestamp += timeDiff) {
            Bus busToVerify = buses.get(amountOfBusesFound);

            if ((timestamp + busToVerify.position) % busToVerify.busId == 0) {
                timeDiff = lcm(timeDiff, busToVerify.busId);
                amountOfBusesFound++;
            }

            if (amountOfBusesFound == buses.size()) {
                return timestamp;
            }
        }

        return -1;
    }

    private long lcm(long number1, long number2) {
        BigInteger bigInteger1 = BigInteger.valueOf(number1);
        BigInteger bigInteger2 = BigInteger.valueOf(number2);

        BigInteger gcd = bigInteger1.gcd(bigInteger2);
        BigInteger absProduct = bigInteger1.multiply(bigInteger2).abs();
        return absProduct.divide(gcd).longValue();
    }

    private static class Bus {
        private final int busId;
        private final int position;

        private Bus(int busId, int position) {
            this.busId = busId;
            this.position = position;
        }

        public int getBusId() {
            return busId;
        }

        public int getPosition() {
            return position;
        }
    }
}
