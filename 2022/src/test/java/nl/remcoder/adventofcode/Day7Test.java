package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day7Test {
    private Day7 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day7();
    }

    @Test
    void testPart1Case1() {
        String input = """
                       $ cd /
                       $ ls
                       dir a
                       14848514 b.txt
                       8504156 c.dat
                       dir d
                       $ cd a
                       $ ls
                       dir e
                       29116 f
                       2557 g
                       62596 h.lst
                       $ cd e
                       $ ls
                       584 i
                       $ cd ..
                       $ cd ..
                       $ cd d
                       $ ls
                       4060174 j
                       8033020 d.log
                       5626152 d.ext
                       7214296 k
                       """;

        assertEquals(95437, testSubject.handlePart1(input.lines()));
    }

    @Test
    void testPart1Input() throws Exception {
        assertEquals(1182909, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()))));
    }

    @Test
    void testPart2Case1() {
        String input = """
                       $ cd /
                       $ ls
                       dir a
                       14848514 b.txt
                       8504156 c.dat
                       dir d
                       $ cd a
                       $ ls
                       dir e
                       29116 f
                       2557 g
                       62596 h.lst
                       $ cd e
                       $ ls
                       584 i
                       $ cd ..
                       $ cd ..
                       $ cd d
                       $ ls
                       4060174 j
                       8033020 d.log
                       5626152 d.ext
                       7214296 k
                       """;

        assertEquals(24933642, testSubject.handlePart2(input.lines()));
    }

    @Test
    void testPart2Input() throws Exception {
        assertEquals(2832508, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day7/input").toURI()))));
    }
}