package de.luis.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

/**
 * Problem 3:
 * It seems like the goal of the program is just to multiply some numbers.
 * It does that with instructions like mul(X,Y), where X and Y are each 1-3 digit numbers.
 * For instance,
 * <li>mul(44,46) multiplies 44 by 46 to get a result of 2024. Similarly, mul(123,4)
 * would multiply 123 by 4.</li>
 *<br>
 * However, because the program's memory has been corrupted, there are also many invalid characters
 * that should be ignored, even if they look like part of a mul instruction. Sequences like
 * mul(4*, mul(6,9!, ?(12,34), or mul ( 2 , 4 ) do nothing.
 *<br>
 * For example, consider the following section of corrupted memory:
 *<br>
 * <li>xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))</li>
 * Only the four highlighted sections are real mul instructions. Adding up the result of each
 * instruction produces 161 (2*4 + 5*5 + 11*8 + 8*5).
 * <br>
 * Part 2:
 * There are two new instructions you'll need to handle:
 * <ul>
 *     <li>The do() instruction enables future mul instructions.</li>
 *     <li>The don't() instruction disables future mul instructions.</li>
 * </ul>
 * Only the most recent do() or don't() instruction applies. At the beginning of the program,
 * mul instructions are enabled.
 *<br>
 * For example:
 * <br>
 * <li>xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))</li>
 * This corrupted memory is similar to the example from before, but this time the mul(5,5)
 * and mul(11,8) instructions are disabled because there is a don't() instruction before them.
 * The other mul instructions function normally, including the one at the end that gets
 * re-enabled by a do() instruction.
 *<br>
 * This time, the sum of the results is 48 (2*4 + 8*5).
 */
public class Day3 {
    private static final String REGEX_A = "mul\\((?<lhs>\\d{1,3}),(?<rhs>\\d{1,3})\\)";
    private static final String REGEX_B = "(?<mul>mul)\\((?<lhs>\\d{1,3}),(?<rhs>\\d{1,3})\\)|(?<do>do)\\(\\)|(?<dont>don't)\\(\\)";

    public static void main(String[] args) {
        var input = getInputFromFile("input.txt");
        System.out.printf("[PART A] Result: %s", runPartA(input));
        System.out.printf("[PART B] Result: %s", runPartB(input));
    }

    /* ========== DAY 3 - PART ONE ========== */

    private static long runPartA(String input) {
        var pattern = Pattern.compile(REGEX_A);
        var matcher = pattern.matcher(input);

        long res = 0;
        while (matcher.find()) {
            var a = Integer.parseInt(matcher.group("lhs"));
            var b = Integer.parseInt(matcher.group("rhs"));

            res += (long) a * b;
        }
        return res;
    }

    /* ========== DAY 3 - PART TWO ========== */

    private static long runPartB(String input) {
        var pattern = Pattern.compile(REGEX_B);
        var matcher = pattern.matcher(input);
        var enabled = true;

        long res = 0;
        while (matcher.find()) {
            if (matcher.group("do") != null) {
                enabled = true;
                continue;
            }

            if (matcher.group("dont") != null) {
                enabled = false;
                continue;
            }
            assert (matcher.group("mul") != null);

            if (!enabled) {
                continue;
            }
            res += ((long) Integer.parseInt(matcher.group("lhs")) * Integer.parseInt(matcher.group("rhs")));
        }
        return res;
    }


    /* ========== DAY 3 - UTILITY ========== */

    private static String getInputFromFile(String fileName) {
        try {
            return Files.readString(Paths.get(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
