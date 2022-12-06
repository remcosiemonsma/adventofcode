package nl.remcoder.adventofcode.library.stream;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CountingCollector<T> implements Collector<T, Map<T, Integer>, Map<T, Integer>> {
    @Override
    public Supplier<Map<T, Integer>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<T, Integer>, T> accumulator() {
        return (map, t) -> map.compute(t, (t1, value) -> {
            if (value == null) {
                return 1;
            } else {
                return value + 1;
            }
        });
    }

    @Override
    public BinaryOperator<Map<T, Integer>> combiner() {
        return (map1, map2) -> {
            for (T key : map2.keySet()) {
                map1.compute(key, (t, value) -> {
                    if (value == null) {
                        return map2.get(t);
                    } else {
                        return value + map2.get(t);
                    }
                });
            }
            return map1;
        };
    }

    @Override
    public Function<Map<T, Integer>, Map<T, Integer>> finisher() {
        return tIntegerMap -> tIntegerMap;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
