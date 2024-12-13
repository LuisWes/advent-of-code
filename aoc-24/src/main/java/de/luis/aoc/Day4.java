package de.luis.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * s the search for the Chief continues, a small Elf who lives on the station tugs
 * on your shirt; she'd like to know if you could help her with her word search
 * (your puzzle input). She only has to find one word: XMAS.
 *
 * This word search allows words to be horizontal, vertical, diagonal, written
 * backwards, or even overlapping other words. It's a little unusual, though, as
 * you don't merely need to find one instance of XMAS - you need to find all of them.
 * Here are a few ways XMAS might appear, where irrelevant characters have been replaced
 * with .:
 * ..X...
 * .SAMX.
 * .A..A.
 * XMAS.S
 * .X....
 * The actual word search will be full of letters instead. For example:
 *
 * MMMSXXMASM
 * MSAMXMSMSA
 * AMXSXMAAMM
 * MSAMASMSMX
 * XMASAMXAMM
 * XXAMMXXAMA
 * SMSMSASXSS
 * SAXAMASAAA
 * MAMMMXMMMM
 * MXMXAXMASX
 * In this word search, XMAS occurs a total of 18 times; here's the same
 * word search again, but where letters not involved in any XMAS have been replaced with .:
 *
 * ....XXMAS.
 * .SAMXMS...
 * ...S..A...
 * ..A.A.MS.X
 * XMASAMX.MM
 * X.....XA.A
 * S.S.S.S.SS
 * .A.A.A.A.A
 * ..M.M.M.MM
 * .X.X.XMASX
 *
 */
public class Day4 {

    public static void main(String[] args) {

    }
    /* ========== DAY 4 - PART ONE ========== */
    private static boolean countPatterns(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 'X') {
                    continue;
                }

                if (isExpectedNext(j + 1, grid[i], 'M')
                        && isExpectedNext(j + 2, grid[i], 'A')
                        && isExpectedNext(j + 3, grid[i], 'S')) {
                    count++;
                    continue;
                }
                //TODO finish
            }
        }
        return false;
    }

    private static boolean isExpectedNext(int i, char[] line, char expected) {
        if ((i + 1) >= line.length) {
            return false;
        }
        return line[i + 1] == expected;
    }


    /* ========== DAY 4 - UTILITY ========== */
    private static char[][] getGridFromFile(String fileName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            var grid = new char[lines.size()][];

            for (int i = 0; i < lines.size(); i++) {
                var line = lines.get(i);
                for (int j = 0; j < line.length(); j++) {
                    grid[i][j] = line.charAt(j);
                }
            }
            return grid;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
