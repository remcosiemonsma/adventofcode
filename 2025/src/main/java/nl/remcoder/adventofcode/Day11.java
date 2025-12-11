package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day11 implements AdventOfCodeSolution<Long> {

    @Override
    public Long handlePart1(Stream<String> input) {
        var devices = new HashMap<String, Device>();

        input.forEach(line -> createDevice(line, devices));

        var you = devices.get("you");

        return countWaysToDevice(you, "out", new HashMap<>());
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var devices = new HashMap<String, Device>();

        input.forEach(line -> createDevice(line, devices));

        var svr = devices.get("svr");

        var dac = devices.get("dac");

        var fft = devices.get("fft");

        var waysFromDacToFft = countWaysToDevice(dac, "fft", new HashMap<>());
        var waysFromFftToDac = countWaysToDevice(fft, "dac", new HashMap<>());

        if (waysFromDacToFft == 0) {
            var waysFromSvrToFft = countWaysToDevice(svr, "fft", new HashMap<>());
            var waysFromDacToOut = countWaysToDevice(dac, "out", new HashMap<>());

            return waysFromSvrToFft * waysFromFftToDac * waysFromDacToOut;
        } else {
            var waysFromSvrToDac = countWaysToDevice(svr, "dac", new HashMap<>());
            var waysFromFftToOut = countWaysToDevice(fft, "out", new HashMap<>());

            return waysFromSvrToDac * waysFromDacToFft * waysFromFftToOut;
        }
    }

    private long countWaysToDevice(Device device, String target, Map<String, Long> memo) {
        if (device.name().equals(target)) {
            return 1L;
        }
        if (memo.containsKey(device.name())) {
            return memo.get(device.name());
        }
        var ways = device.connections()
                         .stream()
                         .mapToLong(connectedDevice -> countWaysToDevice(connectedDevice, target, memo))
                         .sum();
        memo.put(device.name(), ways);
        return ways;
    }

    private void createDevice(String line, Map<String, Device> devices) {
        var split = line.split(":?\\s");

        var device = devices.computeIfAbsent(split[0], name -> new Device(name, new ArrayList<>()));

        for (var i = 1; i < split.length; i++) {
            device.addDevice(devices.computeIfAbsent(split[i], name -> new Device(name, new ArrayList<>())));
        }
    }

    private record Device(String name, List<Device> connections) {
        public void addDevice(Device device) {
            connections.add(device);
        }
    }
}
