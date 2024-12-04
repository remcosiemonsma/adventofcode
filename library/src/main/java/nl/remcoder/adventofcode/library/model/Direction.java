package nl.remcoder.adventofcode.library.model;

public enum Direction {
    UP,
    UPLEFT,
    LEFT,
    DOWNLEFT,
    DOWN,
    DOWNRIGHT,
    RIGHT,
    UPRIGHT,;

    public Direction rotateClockWise() {
        return switch (this) {
            case UP -> RIGHT;
            case UPLEFT -> DOWNLEFT;
            case LEFT -> UP;
            case DOWNLEFT -> DOWNRIGHT;
            case RIGHT -> DOWN;
            case DOWNRIGHT -> UPRIGHT;
            case DOWN -> LEFT;
            case UPRIGHT -> UPLEFT;
        };
    }

    public Direction rotateCounterClockWise() {
        return switch (this) {
            case UP -> LEFT;
            case UPLEFT -> UPRIGHT;
            case LEFT -> DOWN;
            case DOWNLEFT -> UPLEFT;
            case DOWN -> RIGHT;
            case DOWNRIGHT -> DOWNLEFT;
            case RIGHT -> UP;
            case UPRIGHT -> DOWNRIGHT;
        };
    }
    
    public Direction flip() {
        return switch (this) {
            case UP -> DOWN;
            case UPLEFT -> DOWNLEFT;
            case LEFT -> RIGHT;
            case DOWNLEFT -> UPLEFT;
            case DOWN -> UP;
            case DOWNRIGHT -> UPRIGHT;
            case RIGHT -> LEFT;
            case UPRIGHT -> DOWNRIGHT;
        };
    }
}
