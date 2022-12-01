package nl.remcoder.adventofcode.library.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class CombiningCollector<T, R>
        implements Collector<T, List<List<R>>, Stream<List<R>>> {
    private final Function<T, R> mapper;
    private final Predicate<T> newElementPredicate;

    public CombiningCollector(Function<T, R> mapper, Predicate<T> newElementPredicate) {
        this.mapper = mapper;
        this.newElementPredicate = newElementPredicate;
    }

    @Override
    public Supplier<List<List<R>>> supplier() {
        return () -> {
            List<List<R>> bags = new ArrayList<>();
            bags.add(new ArrayList<>());
            return bags;
        };
    }

    @Override
    public BiConsumer<List<List<R>>, T> accumulator() {
        return (bags, line) -> {
            if (isBlank(line)) {
                bags.add(new ArrayList<>());
            } else {
                List<R> currentBag = bags.get(bags.size() - 1);
                currentBag.add(map(line));
            }
        };
    }

    private R map(T line) {
        return mapper.apply(line);
    }

    private boolean isBlank(T line) {
        return newElementPredicate.test(line);
    }

    @Override
    public BinaryOperator<List<List<R>>> combiner() {
        return (objects, objects2) -> {
            objects.addAll(objects2);
            return objects;
        };
    }

    @Override
    public Function<List<List<R>>, Stream<List<R>>> finisher() {
        return Collection::stream;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}
