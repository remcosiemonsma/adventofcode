package nl.remcoder.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class Day24Test {
    private Day24 testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Day24();
    }

    @Test
    public void testPart1Case1() {
        String input = """
                       sesenwnenenewseeswwswswwnenewsewsw
                       neeenesenwnwwswnenewnwwsewnenwseswesw
                       seswneswswsenwwnwse
                       nwnwneseeswswnenewneswwnewseswneseene
                       swweswneswnenwsewnwneneseenw
                       eesenwseswswnenwswnwnwsewwnwsene
                       sewnenenenesenwsewnenwwwse
                       wenwwweseeeweswwwnwwe
                       wsweesenenewnwwnwsenewsenwwsesesenwne
                       neeswseenwwswnwswswnw
                       nenwswwsewswnenenewsenwsenwnesesenew
                       enewnwewneswsewnwswenweswnenwsenwsw
                       sweneswneswneneenwnewenewwneswswnese
                       swwesenesewenwneswnwwneseswwne
                       enesenwswwswneneswsenwnewswseenwsese
                       wnwnesenesenenwwnenwsewesewsesesew
                       nenewswnwewswnenesenwnesewesw
                       eneswnwswnwsenenwnwnwwseeswneewsenese
                       neswnwewnwnwseenwseesewsenwsweewe
                       wseweeenwnesenwwwswnew
                       """;

        assertEquals(10, testSubject.handlePart1(input.lines()));
    }

    @Test
    public void testPart1Input() throws Exception {
        assertEquals(512, testSubject.handlePart1(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day24/input").toURI()))));
    }

    @Test
    public void testPart2Case1() {
        String input = """
                       sesenwnenenewseeswwswswwnenewsewsw
                       neeenesenwnwwswnenewnwwsewnenwseswesw
                       seswneswswsenwwnwse
                       nwnwneseeswswnenewneswwnewseswneseene
                       swweswneswnenwsewnwneneseenw
                       eesenwseswswnenwswnwnwsewwnwsene
                       sewnenenenesenwsewnenwwwse
                       wenwwweseeeweswwwnwwe
                       wsweesenenewnwwnwsenewsenwwsesesenwne
                       neeswseenwwswnwswswnw
                       nenwswwsewswnenenewsenwsenwnesesenew
                       enewnwewneswsewnwswenweswnenwsenwsw
                       sweneswneswneneenwnewenewwneswswnese
                       swwesenesewenwneswnwwneseswwne
                       enesenwswwswneneswsenwnewswseenwsese
                       wnwnesenesenenwwnenwsewesewsesesew
                       nenewswnwewswnenesenwnesewesw
                       eneswnwswnwsenenwnwnwwseeswneewsenese
                       neswnwewnwnwseenwseesewsenwsweewe
                       wseweeenwnesenwwwswnew
                       """;

        assertEquals(2208, testSubject.handlePart2(input.lines()));
    }

    @Test
    public void testPart2Input() throws Exception {
        assertEquals(4120, testSubject.handlePart2(
                Files.lines(Paths.get(ClassLoader.getSystemResource("day24/input").toURI()))));
    }
}