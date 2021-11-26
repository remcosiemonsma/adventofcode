package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day9 {
    public long handlePart1(Stream<String> input) {
        String compressed = input.findFirst().get();

        StringBuilder decompressed = new StringBuilder();

        boolean readingMarker = false;

        StringBuilder marker = new StringBuilder();

        for (int position = 0; position < compressed.length(); position++) {
            if (readingMarker) {
                if (compressed.charAt(position) != ')') {
                    marker.append(compressed.charAt(position));
                } else {
                    readingMarker = false;
                    //process marker

                    String[] markerparts = marker.toString().split("x");

                    int amountofchars = Integer.parseInt(markerparts[0]);
                    int amountoftimes = Integer.parseInt(markerparts[1]);

                    String part = compressed.substring(position + 1, position + amountofchars + 1);

                    for (int time = 0; time < amountoftimes; time++) {
                        decompressed.append(part);
                    }

                    marker = new StringBuilder();

                    position += amountofchars;
                }
            } else {
                if (compressed.charAt(position) == '(') {
                    readingMarker = true;
                } else {
                    decompressed.append(compressed.charAt(position));
                }
            }
        }

        return decompressed.length();
    }

    public long handlePart2(Stream<String> input) {
        String compressed = input.findFirst().get();

        return determineDecompressedLength(compressed);
    }

    private long determineDecompressedLength(String compressed) {
        long length = 0;

        boolean readingMarker = false;

        StringBuilder marker = new StringBuilder();

        for (int position = 0; position < compressed.length(); position++) {
            if (readingMarker) {
                if (compressed.charAt(position) != ')') {
                    marker.append(compressed.charAt(position));
                } else {
                    readingMarker = false;
                    //process marker

                    String[] markerparts = marker.toString().split("x");

                    marker = new StringBuilder();

                    int amountofchars = Integer.parseInt(markerparts[0]);
                    int amountoftimes = Integer.parseInt(markerparts[1]);

                    String part = compressed.substring(position + 1, position + amountofchars + 1);

                    length += determineDecompressedLength(part) * amountoftimes;

                    position += amountofchars;
                }
            } else {
                if (compressed.charAt(position) == '(') {
                    readingMarker = true;
                } else {
                    length++;
                }
            }
        }

        return length;
    }
}
