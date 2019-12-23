package nl.remcoder.adventofcode.intcodecomputer;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConsumingQueue implements BlockingQueue<Long> {
    private final OutputConsumer outputConsumer;

    public ConsumingQueue(OutputConsumer outputConsumer) {
        this.outputConsumer = outputConsumer;
    }

    @Override
    public boolean add(Long aLong) {
        return false;
    }

    @Override
    public boolean offer(Long aLong) {
        return false;
    }

    @Override
    public Long remove() {
        return null;
    }

    @Override
    public Long poll() {
        return null;
    }

    @Override
    public Long element() {
        return null;
    }

    @Override
    public Long peek() {
        return null;
    }

    @Override
    public void put(Long aLong) throws InterruptedException {
        outputConsumer.consumeLongValue(aLong);
    }

    @Override
    public boolean offer(Long aLong, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Long take() throws InterruptedException {
        return null;
    }

    @Override
    public Long poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Long> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Long> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public int drainTo(Collection<? super Long> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super Long> c, int maxElements) {
        return 0;
    }
}