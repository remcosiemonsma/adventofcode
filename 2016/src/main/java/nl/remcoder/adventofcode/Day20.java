package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Day20 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var ipRanges = input.map(this::parseIpRange)
                            .sorted(Comparator.comparing(IpRange::start))
                            .toList();

        ipRanges = reduceIpRanges(ipRanges);
        
        return ipRanges.get(0).end() + 1;
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var ipRanges = input.map(this::parseIpRange)
                            .sorted(Comparator.comparing(IpRange::start))
                            .toList();

        ipRanges = reduceIpRanges(ipRanges);
        
        var allowedIps = 0L;
        
        for (var i = 0; i < ipRanges.size() - 1; i++) {
            var currentIpRange = ipRanges.get(i);
            var nextIpRange = ipRanges.get(i + 1);
            
            var allowedRange = nextIpRange.start() - currentIpRange.end() - 1;
            
            allowedIps += allowedRange;
        }
        
        var lastIp = 4294967296L;
        
        allowedIps += lastIp - ipRanges.get(ipRanges.size() - 1).end() - 1;
        
        return allowedIps;
    }
    
    private List<IpRange> reduceIpRanges(List<IpRange> ipRanges) {
        var newIpRanges = new ArrayList<IpRange>();
        
        var newStart = ipRanges.get(0).start();
        var newEnd = ipRanges.get(0).end();
        
        for (var i = 1; i < ipRanges.size(); i++) {
            var ipRange = ipRanges.get(i);
            
            if (ipRange.start() <= newEnd + 1) {
                //overlap
                if (ipRange.end() > newEnd) {
                    //partial overlap
                    newEnd = ipRange.end();
                }
                //else complete overlap
            } else {
                //new range
                var newIpRange = new IpRange(newStart, newEnd);
                newIpRanges.add(newIpRange);
                newStart = ipRange.start();
                newEnd = ipRange.end();
            }
        }

        var newIpRange = new IpRange(newStart, newEnd);
        newIpRanges.add(newIpRange);
        
        return newIpRanges;
    }

    private IpRange parseIpRange(String line) {
        var split = line.split("-");
        return new IpRange(Long.parseLong(split[0]), Long.parseLong(split[1]));
    }

    private record IpRange(long start, long end) {
    }
}
