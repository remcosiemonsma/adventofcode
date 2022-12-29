package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13 implements AdventOfCodeSolution<int[]> {
    @Override
    public int[] handlePart1(Stream<String> input) {
        var grid = input.map(String::toCharArray)
                        .toArray(char[][]::new);

        var carts = readCartsFromGrid(grid);

        var collision = false;

        var collisionx = 0;
        var collisiony = 0;

        while (!collision) {
            for (Cart cart : carts) {
                cart.move(grid);
                if (cart.hasCollision(carts)) {
                    collision = true;
                    collisionx = cart.posx;
                    collisiony = cart.posy;
                    break;
                }
            }
            carts.sort(Comparator.comparing(Cart::getPosy).thenComparing(Cart::getPosx));
        }
        
        return new int[] {collisionx, collisiony};
    }

    @Override
    public int[] handlePart2(Stream<String> input) {
        var grid = input.map(String::toCharArray)
                        .toArray(char[][]::new);

        var carts = readCartsFromGrid(grid);

        while (carts.size() > 1) {
            List<Cart> collisionCarts = new ArrayList<>();

            for (Cart cart : carts) {
                cart.move(grid);
                List<Cart> crashingCarts = cart.findCrashingCarts(carts);

                if (crashingCarts.size() > 0) {
                    crashingCarts.add(cart);
                }

                collisionCarts.addAll(crashingCarts);
            }

            carts.removeAll(collisionCarts);

            carts.sort(Comparator.comparing(Cart::getPosy).thenComparing(Cart::getPosx));
        }
        return new int[] {carts.get(0).posx, carts.get(0).posy};
    }

    private static List<Cart> readCartsFromGrid(char[][] grid) {
        var carts = new ArrayList<Cart>();

        for (var y = 0; y < grid.length; y++) {
            for (var x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == '^') {
                    grid[y][x] = '|';
                    carts.add(new Cart(x, y, Turn.RIGHT, Direction.UP));
                } else if (grid[y][x] == 'v') {
                    grid[y][x] = '|';
                    carts.add(new Cart(x, y, Turn.RIGHT, Direction.DOWN));
                } else if (grid[y][x] == '<') {
                    grid[y][x] = '-';
                    carts.add(new Cart(x, y, Turn.RIGHT, Direction.LEFT));
                } else if (grid[y][x] == '>') {
                    grid[y][x] = '-';
                    carts.add(new Cart(x, y, Turn.RIGHT, Direction.RIGHT));
                }
            }
        }

        return carts;
    }

    private static class Cart {
        int posx;
        int posy;
        Turn lastTurn;
        Direction direction;

        public Cart(int posx, int posy, Turn lastTurn, Direction direction) {
            this.posx = posx;
            this.posy = posy;
            this.lastTurn = lastTurn;
            this.direction = direction;
        }

        public int getPosx() {
            return posx;
        }

        public int getPosy() {
            return posy;
        }

        void move(char[][] grid) {
            char nextStep;
            switch (direction) {
                case UP -> {
                    nextStep = grid[posy - 1][posx];
                    posy--;
                    if (nextStep == '\\') {
                        direction = Direction.LEFT;
                    } else if (nextStep == '/') {
                        direction = Direction.RIGHT;
                    } else if (nextStep == '+') {
                        switch (lastTurn) {
                            case RIGHT -> {
                                lastTurn = Turn.LEFT;
                                direction = Direction.LEFT;
                            }
                            case LEFT -> lastTurn = Turn.STRAIGHT;
                            case STRAIGHT -> {
                                lastTurn = Turn.RIGHT;
                                direction = Direction.RIGHT;
                            }
                        }
                    }
                }
                case DOWN -> {
                    nextStep = grid[posy + 1][posx];
                    posy++;
                    if (nextStep == '\\') {
                        direction = Direction.RIGHT;
                    } else if (nextStep == '/') {
                        direction = Direction.LEFT;
                    } else if (nextStep == '+') {
                        switch (lastTurn) {
                            case RIGHT -> {
                                lastTurn = Turn.LEFT;
                                direction = Direction.RIGHT;
                            }
                            case LEFT -> lastTurn = Turn.STRAIGHT;
                            case STRAIGHT -> {
                                lastTurn = Turn.RIGHT;
                                direction = Direction.LEFT;
                            }
                        }
                    }
                }
                case LEFT -> {
                    nextStep = grid[posy][posx - 1];
                    posx--;
                    if (nextStep == '\\') {
                        direction = Direction.UP;
                    } else if (nextStep == '/') {
                        direction = Direction.DOWN;
                    } else if (nextStep == '+') {
                        switch (lastTurn) {
                            case RIGHT -> {
                                lastTurn = Turn.LEFT;
                                direction = Direction.DOWN;
                            }
                            case LEFT -> lastTurn = Turn.STRAIGHT;
                            case STRAIGHT -> {
                                lastTurn = Turn.RIGHT;
                                direction = Direction.UP;
                            }
                        }
                    }
                }
                case RIGHT -> {
                    nextStep = grid[posy][posx + 1];
                    posx++;
                    if (nextStep == '\\') {
                        direction = Direction.DOWN;
                    } else if (nextStep == '/') {
                        direction = Direction.UP;
                    } else if (nextStep == '+') {
                        switch (lastTurn) {
                            case RIGHT -> {
                                lastTurn = Turn.LEFT;
                                direction = Direction.UP;
                            }
                            case LEFT -> lastTurn = Turn.STRAIGHT;
                            case STRAIGHT -> {
                                lastTurn = Turn.RIGHT;
                                direction = Direction.DOWN;
                            }
                        }
                    }
                }
            }
        }

        boolean hasCollision(List<Cart> carts) {
            return carts.stream().anyMatch(cart -> cart != this && cart.posy == this.posy && cart.posx == this.posx);
        }

        List<Cart> findCrashingCarts(List<Cart> carts) {
            return carts.stream()
                        .filter(cart -> cart != this && cart.posy == this.posy && cart.posx == this.posx)
                        .collect(Collectors.toList());
        }
        
        @Override
        public String toString() {
            return "Cart{" +
                   "posx=" + posx +
                   ", posy=" + posy +
                   ", lastTurn=" + lastTurn +
                   '}';
        }
    }

    private enum Turn {
        LEFT,
        STRAIGHT,
        RIGHT
    }

    private enum Direction {
        UP,
        LEFT,
        DOWN,
        RIGHT
    }
}
