package nl.remcoder.adventofcode;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SharedState {
    private final BlockingQueue<Integer> values;

    public SharedState() {
        values = new ArrayBlockingQueue<>(100);
    }

    public void writeValue(int value) {
        try {
            values.put(value);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int readNextValue() {
        try {
            return values.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        }
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }
}
