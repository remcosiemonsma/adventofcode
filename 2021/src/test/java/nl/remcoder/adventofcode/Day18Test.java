package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day18Test {
    private Day18 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day18();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       [9,1]
                       """;

        assertEquals(29, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case2() {
        String input = """
                       [1,9]
                       """;

        assertEquals(21, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case3() {
        String input = """
                       [[9,1],[1,9]]
                       """;

        assertEquals(129, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case4() {
        String input = """
                       [[1,2],[[3,4],5]]
                       """;

        assertEquals(143, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case5() {
        String input = """
                       [[[[0,7],4],[[7,8],[6,0]]],[8,1]]
                       """;

        assertEquals(1384, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case6() {
        String input = """
                       [[[[1,1],[2,2]],[3,3]],[4,4]]
                       """;

        assertEquals(445, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case7() {
        String input = """
                       [[[[3,0],[5,3]],[4,4]],[5,5]]
                       """;

        assertEquals(791, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case8() {
        String input = """
                       [[[[5,0],[7,4]],[5,5]],[6,6]]
                       """;

        assertEquals(1137, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case9() {
        String input = """
                       [[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]
                       """;

        assertEquals(3488, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Case10() {
        String input = """
                       [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
                       [[[5,[2,8]],4],[5,[[9,9],0]]]
                       [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
                       [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
                       [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
                       [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
                       [[[[5,4],[7,7]],8],[[8,3],8]]
                       [[9,3],[[9,9],[6,[4,9]]]]
                       [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
                       [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
                       """;

        assertEquals(4140, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(3691, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
                       [[[5,[2,8]],4],[5,[[9,9],0]]]
                       [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
                       [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
                       [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
                       [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
                       [[[[5,4],[7,7]],8],[[8,3],8]]
                       [[9,3],[[9,9],[6,[4,9]]]]
                       [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
                       [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
                       """;

        assertEquals(3993, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(4756, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day18/input").toURI()))));
    }
}