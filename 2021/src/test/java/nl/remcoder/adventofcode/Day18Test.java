package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Day18Test {
    private Day18 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day18();
    }

    @Test
    void testPart1Case1() {
        String data = """
                      [9,1]
                      """;

        assertEquals(29, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart1Case2() {
        String data = """
                      [1,9]
                      """;

        assertEquals(21, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart1Case3() {
        String data = """
                      [[9,1],[1,9]]
                      """;

        assertEquals(129, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart1Case4() {
        String data = """
                      [[1,2],[[3,4],5]]
                      """;

        assertEquals(143, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart1Case5() {
        String data = """
                      [[[[0,7],4],[[7,8],[6,0]]],[8,1]]
                      """;

        assertEquals(1384, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart1Case6() {
        String data = """
                      [[[[1,1],[2,2]],[3,3]],[4,4]]
                      """;

        assertEquals(445, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart1Case7() {
        String data = """
                      [[[[3,0],[5,3]],[4,4]],[5,5]]
                      """;

        assertEquals(791, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart1Case8() {
        String data = """
                      [[[[5,0],[7,4]],[5,5]],[6,6]]
                      """;

        assertEquals(1137, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart1Case9() {
        String data = """
                      [[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]
                      """;

        assertEquals(3488, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart1Case10() {
        String data = """
                      [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
                      [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
                      [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
                      [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
                      [7,[5,[[3,8],[1,4]]]]
                      [[2,[2,2]],[8,[8,1]]]
                      [2,9]
                      [1,[[[9,3],9],[[9,0],[0,7]]]]
                      [[[5,[7,4]],7],1]
                      [[[[4,2],2],6],[8,7]]
                      """;

        assertEquals(4140, testSubject.handlePart1(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(8646, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String data = """
                      target area: x=20..30, y=-10..-5
                      """;

        assertEquals(112, testSubject.handlePart2(Arrays.stream(data.split("\n"))));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(5945, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/input").toURI()))));
    }
}