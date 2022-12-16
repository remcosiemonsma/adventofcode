package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day16 implements AdventOfCodeSolution<Integer> {
    private static final Pattern VALVE_PATTERN =
            Pattern.compile("Valve (\\w{2}) has flow rate=(\\d+); tunnels? leads? to valves? (.*)");

    @Override
    public Integer handlePart1(Stream<String> input) {
        Map<String, Valve> valveMap = input.map(VALVE_PATTERN::matcher)
                                           .filter(Matcher::matches)
                                           .map(this::parseValve)
                                           .collect(Collectors.toMap(valve -> valve.name, valve -> valve));

        valveMap.values()
                .forEach(valve -> valve.setValves(valveMap));

        var start = valveMap.get("AA");

        var startMinute = new Minute(start, 0, new ArrayList<>(valveMap.values()));

        var allMinutes = List.of(startMinute);
        var bestMinute = startMinute;

        for (var minute = 1; minute < 30; minute++) {
            var newMinutes = new ArrayList<Minute>();

            for (var currentMinute : allMinutes) {
                var closedValves = currentMinute.closedValves();
                if (closedValves.stream().allMatch(valve -> valve.flowRate == 0)) {
                    continue;
                }

                var currentValve = currentMinute.currentValve;

                if (currentValve.flowRate > 0 && closedValves.contains(currentValve)) {
                    var newClosedValves = new ArrayList<>(closedValves);
                    newClosedValves.remove(currentValve);

                    newMinutes.add(new Minute(currentValve,
                                              currentMinute.totalFlowRate + (currentValve.flowRate * (30 - minute)),
                                              newClosedValves));
                }

                for (var valve : currentValve.valves) {
                    newMinutes.add(new Minute(valve, currentMinute.totalFlowRate, closedValves));
                }

            }
            allMinutes = newMinutes.stream()
                                   .sorted(Comparator.comparing(Minute::totalFlowRate).reversed())
                                   .limit(1000)
                                   .toList();

            if (allMinutes.get(0).totalFlowRate > bestMinute.totalFlowRate) {
                bestMinute = allMinutes.get(0);
            }
        }

        return bestMinute.totalFlowRate;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        Map<String, Valve> valveMap = input.map(VALVE_PATTERN::matcher)
                                           .filter(Matcher::matches)
                                           .map(this::parseValve)
                                           .collect(Collectors.toMap(valve -> valve.name, valve -> valve));

        valveMap.values()
                .forEach(valve -> valve.setValves(valveMap));

        var start = valveMap.get("AA");

        var startMinute = new Minute2(start, start, 0, new ArrayList<>(valveMap.values()));

        var allMinutes = List.of(startMinute);
        var bestMinute = startMinute;
        
        for (var minute = 1; minute < 26; minute++) {
            var newMinutes = new ArrayList<Minute2>();

            for (var currentMinute : allMinutes) {
                var closedValves = currentMinute.closedValves();
                if (closedValves.stream().allMatch(valve -> valve.flowRate == 0)) {
                    continue;
                }

                var myValve = currentMinute.myValve;
                var elephantValve = currentMinute.elephantValve;
                
                var canMyOpen = myValve.flowRate > 0 && closedValves.contains(myValve);
                var canElephantOpen = elephantValve.flowRate > 0 && closedValves.contains(elephantValve) && myValve != elephantValve;
                
                if (canMyOpen) {
                    var newClosedValves = new ArrayList<>(closedValves);
                    newClosedValves.remove(myValve);

                    for (var valve : elephantValve.valves) {
                        newMinutes.add(new Minute2(myValve, 
                                                   valve, 
                                                   currentMinute.totalFlowRate + 
                                                   (myValve.flowRate * (26 - minute)),
                                                   newClosedValves));
                    }
                }
                if (canElephantOpen) {
                    var newClosedValves = new ArrayList<>(closedValves);
                    newClosedValves.remove(elephantValve);

                    for (var valve : myValve.valves) {
                        newMinutes.add(new Minute2(valve, 
                                                   elephantValve,
                                                   currentMinute.totalFlowRate +
                                                   (elephantValve.flowRate * (26 - minute)),
                                                   newClosedValves));
                    }
                }
                if (canMyOpen && canElephantOpen) {
                    var newClosedValves = new ArrayList<>(closedValves);
                    newClosedValves.remove(myValve);
                    newClosedValves.remove(elephantValve);
                    
                    newMinutes.add(new Minute2(myValve, 
                                               elephantValve, 
                                               currentMinute.totalFlowRate + 
                                                                       (myValve.flowRate * (26 - minute)) + 
                                                                       (elephantValve.flowRate * (26 - minute)), 
                                               newClosedValves));
                }
                
                for (var myNewValve : myValve.valves) {
                    for (var elephantNewValve : elephantValve.valves) {
                        newMinutes.add(new Minute2(myNewValve, elephantNewValve, currentMinute.totalFlowRate, closedValves));
                    }
                }
            }
            allMinutes = newMinutes.stream()
                                   .sorted(Comparator.comparing(Minute2::totalFlowRate).reversed())
                                   .limit(1000)
                                   .toList();

            if (allMinutes.get(0).totalFlowRate > bestMinute.totalFlowRate) {
                bestMinute = allMinutes.get(0);
            }
        }
        
        return bestMinute.totalFlowRate;
    }

    private Valve parseValve(Matcher matcher) {
        return new Valve(matcher.group(1), Integer.parseInt(matcher.group(2)),
                         Arrays.asList(matcher.group(3).split(", ")));
    }

    private record Minute(Valve currentValve, int totalFlowRate, List<Valve> closedValves) {
    }
    
    private record Minute2(Valve myValve, Valve elephantValve, int totalFlowRate, List<Valve> closedValves) {
    }

    private static class Valve {
        private final String name;
        private final int flowRate;
        private final List<String> connections;
        private List<Valve> valves;

        private Valve(String name, int flowRate, List<String> connections) {
            this.name = name;
            this.flowRate = flowRate;
            this.connections = connections;
        }

        public void setValves(Map<String, Valve> valveMap) {
            this.valves = connections.stream()
                                     .map(valveMap::get)
                                     .toList();
        }

        @Override
        public String toString() {
            return "Valve{" +
                   "name='" + name + '\'' +
                   ", flowRate=" + flowRate +
//                   ", connections=" + connections +
                   '}';
        }
    }
}
