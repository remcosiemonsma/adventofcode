package nl.remcoder.adventofcode.day13;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Part2 {
    public static void main(String[] args) throws Exception {
        char[][] grid = Files.lines(Paths.get(ClassLoader.getSystemResource("day13/input").toURI()))
                             .map(String::toCharArray)
                             .toArray(char[][]::new);

        List<Cart> carts = readCartsFromGrid(grid);

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

            carts.sort(byPosy.thenComparing(byPosx));
        }

        System.out.println(carts.get(0).posx + "," + carts.get(0).posy);
    }



    private static List<Cart> readCartsFromGrid(char[][] grid) {
        List<Cart> carts = new ArrayList<>();

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
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

    private static Comparator<Cart> byPosy = Comparator.comparingInt(cart -> cart.posy);
    private static Comparator<Cart> byPosx = Comparator.comparingInt(cart -> cart.posx);

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

        void move(char[][] grid) {
            char nextStep;
            switch (direction) {
                case UP:
                    nextStep = grid[posy - 1][posx];
                    posy--;
                    if (nextStep == '\\') {
                        direction = Direction.LEFT;
                    } else if (nextStep == '/') {
                        direction = Direction.RIGHT;
                    } else if (nextStep == '+') {
                        switch (lastTurn) {
                            case RIGHT:
                                lastTurn = Turn.LEFT;
                                direction = Direction.LEFT;
                                break;
                            case LEFT:
                                lastTurn = Turn.STRAIGHT;
                                break;
                            case STRAIGHT:
                                lastTurn = Turn.RIGHT;
                                direction = Direction.RIGHT;
                                break;
                        }
                    }
                    break;
                case DOWN:
                    nextStep = grid[posy + 1][posx];
                    posy++;
                    if (nextStep == '\\') {
                        direction = Direction.RIGHT;
                    } else if (nextStep == '/') {
                        direction = Direction.LEFT;
                    } else if (nextStep == '+') {
                        switch (lastTurn) {
                            case RIGHT:
                                lastTurn = Turn.LEFT;
                                direction = Direction.RIGHT;
                                break;
                            case LEFT:
                                lastTurn = Turn.STRAIGHT;
                                break;
                            case STRAIGHT:
                                lastTurn = Turn.RIGHT;
                                direction = Direction.LEFT;
                                break;
                        }
                    }
                    break;
                case LEFT:
                    nextStep = grid[posy][posx - 1];
                    posx--;
                    if (nextStep == '\\') {
                        direction = Direction.UP;
                    } else if (nextStep == '/') {
                        direction = Direction.DOWN;
                    } else if (nextStep == '+') {
                        switch (lastTurn) {
                            case RIGHT:
                                lastTurn = Turn.LEFT;
                                direction = Direction.DOWN;
                                break;
                            case LEFT:
                                lastTurn = Turn.STRAIGHT;
                                break;
                            case STRAIGHT:
                                lastTurn = Turn.RIGHT;
                                direction = Direction.UP;
                                break;
                        }
                    }
                    break;
                case RIGHT:
                    nextStep = grid[posy][posx + 1];
                    posx++;
                    if (nextStep == '\\') {
                        direction = Direction.DOWN;
                    } else if (nextStep == '/') {
                        direction = Direction.UP;
                    } else if (nextStep == '+') {
                        switch (lastTurn) {
                            case RIGHT:
                                lastTurn = Turn.LEFT;
                                direction = Direction.UP;
                                break;
                            case LEFT:
                                lastTurn = Turn.STRAIGHT;
                                break;
                            case STRAIGHT:
                                lastTurn = Turn.RIGHT;
                                direction = Direction.DOWN;
                                break;
                        }
                    }
                    break;
            }
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
