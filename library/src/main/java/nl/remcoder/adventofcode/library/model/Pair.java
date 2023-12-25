package nl.remcoder.adventofcode.library.model;

import java.util.Objects;

public record Pair<LEFT, RIGHT>(LEFT left, RIGHT right) {
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return (Objects.equals(left, pair.left) && Objects.equals(right, pair.right)) ||
               (Objects.equals(left, pair.right) && Objects.equals(right, pair.left));
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
