package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day9 {
    public long handlePart1(Stream<String> input) {
        var compressed = input.findFirst()
                              .orElseThrow(() -> new AssertionError("Eek!"));

        var decompressed = new StringBuilder();

        var readingMarker = false;

        var marker = new StringBuilder();

        for (var position = 0; position < compressed.length(); position++) {
            if (readingMarker) {
                if (compressed.charAt(position) != ')') {
                    marker.append(compressed.charAt(position));
                } else {
                    readingMarker = false;
                    //process marker

                    var markerparts = marker.toString().split("x");

                    var amountofchars = Integer.parseInt(markerparts[0]);
                    var amountoftimes = Integer.parseInt(markerparts[1]);

                    var part = compressed.substring(position + 1, position + amountofchars + 1);

                    decompressed.append(part.repeat(Math.max(0, amountoftimes)));

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
        var compressed = input.findFirst()
                              .orElseThrow(() -> new AssertionError("Eek!"));

        return determineDecompressedLength(compressed);
    }

    private long determineDecompressedLength(String compressed) {
        var length = 0L;

        var readingMarker = false;

        var marker = new StringBuilder();

        for (var position = 0; position < compressed.length(); position++) {
            if (readingMarker) {
                if (compressed.charAt(position) != ')') {
                    marker.append(compressed.charAt(position));
                } else {
                    readingMarker = false;
                    //process marker

                    var markerparts = marker.toString().split("x");

                    marker = new StringBuilder();

                    var amountofchars = Integer.parseInt(markerparts[0]);
                    var amountoftimes = Integer.parseInt(markerparts[1]);

                    var part = compressed.substring(position + 1, position + amountofchars + 1);

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
