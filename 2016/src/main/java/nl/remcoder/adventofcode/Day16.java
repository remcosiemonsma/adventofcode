package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day16 implements AdventOfCodeSolution<String> {
    private int length;
    
    @Override
    public String handlePart1(Stream<String> input) {
        var data = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));
        
        while (data.length() < length) {
            data = createDragon(data);
        }
        
        data = data.substring(0, length);
        
        return generateChecksum(data);
    }

    @Override
    public String handlePart2(Stream<String> input) {
        var data = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        while (data.length() < length) {
            data = createDragon(data);
        }

        data = data.substring(0, length);

        return generateChecksum(data);    }

    public void setLength(int length) {
        this.length = length;
    }

    private String generateChecksum(String data) {
        var checksum = createChecksum(data);
        
        while (checksum.length() % 2 == 0) {
            checksum = createChecksum(checksum);
        }
        
        return checksum;
    }
    
    private String createChecksum(String data) {
        StringBuilder checksumBuilder = new StringBuilder();
        
        for (int i = 0; i < data.length(); i += 2) {
            if (data.charAt(i) == data.charAt(i + 1)) {
                checksumBuilder.append('1');
            } else {
                checksumBuilder.append('0');
            }
        }
        
        return checksumBuilder.toString();
    }
    
    private String createDragon(String input) {
        var data = input.toCharArray();

        var reverse = reverse(data);

        return String.valueOf(data) + '0' + String.valueOf(reverse);
    }
    
    private char[] reverse (char[] data) {
        char[] result = new char[data.length];
        
        for (int i = 0; i < data.length; i++) {
            char c = data[data.length - i - 1];
            if (c == '0') {
                c = '1';
            } else {
                c = '0';
            }
            result[i] = c;
        }
        
        return result;
    }
}
