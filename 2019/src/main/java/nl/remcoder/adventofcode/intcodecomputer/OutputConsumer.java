package nl.remcoder.adventofcode.intcodecomputer;

@FunctionalInterface
public interface OutputConsumer {
    void consumeLongValue(Long aLong);
}
