package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;
import nl.remcoder.adventofcode.library.drawing.Screen;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Day8 implements BiAdventOfCodeSolution<Integer, String> {
    private int width;
    private int height;

    @Override
    public Integer handlePart1(Stream<String> input) {
        var imageData = input.findFirst()
                             .orElseThrow(() -> new AssertionError("Eek!"));

        var amountOfLayers = imageData.length() / (width * height);

        var layers = new ArrayList<Layer>();

        for (var layerPosition = 0; layerPosition < amountOfLayers; layerPosition++) {
            var amountFewest = 0;
            var amountMultiply1 = 0;
            var amountMultiply2 = 0;
            for (var position = 0; position < width * height; position++) {
                var c = imageData.charAt((layerPosition * width * height) + position);

                var value = c - '0';

                if (value == 0) {
                    amountFewest++;
                }
                if (value == 1) {
                    amountMultiply1++;
                }
                if (value == 2) {
                    amountMultiply2++;
                }
            }
            var layer = new Layer(amountFewest, amountMultiply1, amountMultiply2);
            layers.add(layer);
        }

        var lowestAmount = layers.get(0);

        for (var layer : layers) {
            if (layer.amountFewest < lowestAmount.amountFewest) {
                lowestAmount = layer;
            }
        }

        return lowestAmount.amountMultiply1 * lowestAmount.amountMultiply2;
    }

    public String handlePart2(Stream<String> input) {
        var imageData = input.findFirst()
                             .orElseThrow(() -> new AssertionError("Eek!"));

        var screen = new Screen(width, height);

        int amountOfLayers = imageData.length() / (width * height);

        for (var layerPosition = amountOfLayers - 1; layerPosition >= 0; layerPosition--) {
            var offset = layerPosition * width * height;
            for (var y = 0; y < height; y++) {
                for (var x = 0; x < width; x++) {
                    char c = imageData.charAt(offset + (y * width) + x);

                    int value = c - '0';

                    if (value == 0) {
                        screen.drawPixel(x, y, false);
                    } else if (value == 1) {
                        screen.drawPixel(x, y, true);
                    }
                }
            }
        }
        
        return screen.readScreen();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private record Layer(int amountFewest, int amountMultiply1, int amountMultiply2) {
    }
}
