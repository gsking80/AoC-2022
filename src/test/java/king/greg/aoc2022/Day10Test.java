package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day10Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day10/sample.txt"))
            .toURI()));
    final Day10 day10 = new Day10(lines);
    Assertions.assertThat(day10.strengthTest(20, 40)).isEqualTo(13140);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day10/input.txt"))
            .toURI()));
    final Day10 day10 = new Day10(lines);
    Assertions.assertThat(day10.strengthTest(20, 40)).isEqualTo(15880);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day10/sample.txt"))
            .toURI()));
    final Day10 day10 = new Day10(lines);
    Assertions.assertThat(day10.draw()).isEqualTo(
        """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....""");
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day10/input.txt"))
            .toURI()));
    final Day10 day10 = new Day10(lines);
    Assertions.assertThat(day10.draw()).isEqualTo("""
        ###..#.....##..####.#..#..##..####..##..
        #..#.#....#..#.#....#.#..#..#....#.#..#.
        #..#.#....#....###..##...#..#...#..#....
        ###..#....#.##.#....#.#..####..#...#.##.
        #....#....#..#.#....#.#..#..#.#....#..#.
        #....####..###.#....#..#.#..#.####..###.""");
  }
}