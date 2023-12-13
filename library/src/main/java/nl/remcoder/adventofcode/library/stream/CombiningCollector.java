package nl.remcoder.adventofcode.library.stream;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class CombiningCollector<T, R>
        implements Collector<T, List<CombiningCollector.Bag<R>>, Stream<CombiningCollector.Bag<R>>> {
    private final Function<T, R> mapper;
    private final Predicate<T> newElementPredicate;
    private final Supplier<Bag<R>> bagSupplier;

    public CombiningCollector(Function<T, R> mapper, Predicate<T> newElementPredicate, Supplier<Bag<R>> bagSupplier) {
        this.mapper = mapper;
        this.newElementPredicate = newElementPredicate;
        this.bagSupplier = bagSupplier;
    }

    @Override
    public Supplier<List<Bag<R>>> supplier() {
        return () -> {
            List<Bag<R>> bags = new ArrayList<>();
            bags.add(bagSupplier.get());
            return bags;
        };
    }

    @Override
    public BiConsumer<List<Bag<R>>, T> accumulator() {
        return (bags, line) -> {
            if (isBlank(line)) {
                bags.add(bagSupplier.get());
            } else {
                Bag<R> currentBag = bags.getLast();
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
    public BinaryOperator<List<Bag<R>>> combiner() {
        return (objects, objects2) -> {
            objects.addAll(objects2);
            return objects;
        };
    }

    @Override
    public Function<List<Bag<R>>, Stream<Bag<R>>> finisher() {
        return Collection::stream;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }

    public interface Bag<R> {
        void add(R r);
    }

    public static class ListBag<R> implements Bag<R> {
        private final List<R> container = new ArrayList<>();

        @Override
        public void add(R r) {
            container.add(r);
        }

        public List<R> toList() {
            return container;
        }
    }
}
