package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day8 {

    public int handlePart1(Stream<String> input, int width, int height, int fewestDigit, int multiply1, int multiply2) {
        String imageData = input.findFirst().orElseThrow(AssertionError::new);

        int amountOfLayers = imageData.length() / (width * height);

        List<Layer> layers = new ArrayList<>();

        for (int layerPosition = 0; layerPosition < amountOfLayers; layerPosition++) {
            Layer layer = new Layer();
            layers.add(layer);
            for (int position = 0; position < width * height; position++) {
                char c = imageData.charAt((layerPosition * width * height) + position);

                int value = c - '0';

                if (value == fewestDigit) {
                    layer.amountFewest++;
                }
                if (value == multiply1) {
                    layer.amountMultiply1++;
                }
                if (value == multiply2) {
                    layer.amountMultiply2++;
                }
            }
        }

        Layer lowestAmount = layers.get(0);

        for (Layer layer : layers) {
            if (layer.amountFewest < lowestAmount.amountFewest) {
                lowestAmount = layer;
            }
        }

        return lowestAmount.amountMultiply1 * lowestAmount.amountMultiply2;
    }

    public String handlePart2(Stream<String> input, int width, int height, int valueBlack, int valueWhite, int valueTransparent) {
        String imageData = input.findFirst().orElseThrow(AssertionError::new);

        PixelState[][] image = new PixelState[height][width];

        for (PixelState[] line : image) {
            Arrays.fill(line, PixelState.TRANSPARENT);
        }

        int amountOfLayers = imageData.length() / (width * height);

        for (int layerPosition = 0; layerPosition < amountOfLayers; layerPosition++) {
            int offset = layerPosition * width * height;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (image[y][x] == PixelState.TRANSPARENT) {
                        char c = imageData.charAt(offset + (y * width) + x);

                        int value = c - '0';

                        if (value == valueBlack) {
                            image[y][x] = PixelState.BLACK;
                        } else if (value == valueWhite) {
                            image[y][x] = PixelState.WHITE;
                        }
                    }
                }
            }
        }

        return parseImageToString(image, valueBlack, valueWhite, valueTransparent);
    }


    private String parseImageToString(PixelState[][] image, int valueBlack, int valueWhite, int valueTransparent) {
        StringBuilder stringBuilder = new StringBuilder();

        for (PixelState[] line : image) {
            for (PixelState pixel : line) {
                switch (pixel) {
                    case WHITE -> stringBuilder.append(valueWhite);
                    case BLACK -> stringBuilder.append(valueBlack);
                    case TRANSPARENT -> stringBuilder.append(valueTransparent);
                }
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    private static class Layer {
        private int amountFewest = 0;
        private int amountMultiply1 = 0;
        private int amountMultiply2 = 0;
    }

    private enum PixelState {
        TRANSPARENT,
        BLACK,
        WHITE
    }
}
