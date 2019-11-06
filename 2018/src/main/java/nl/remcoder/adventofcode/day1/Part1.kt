package nl.remcoder.adventofcode.day1

import java.nio.file.Files
import java.nio.file.Paths

fun main (args : Array<String>) {
    println(Files.lines(Paths.get(ClassLoader.getSystemResource("day1/input").toURI()))
            .mapToInt(Integer::parseInt)
            .sum())
}