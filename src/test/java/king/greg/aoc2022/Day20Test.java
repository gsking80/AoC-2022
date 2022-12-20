package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day20Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day20/sample.txt"))
            .toURI()));
    final Day20 day20 = new Day20(lines);
    Assertions.assertThat(day20.groveCoordinateSum()).isEqualTo(3);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day20/input.txt"))
            .toURI()));
    final Day20 day20 = new Day20(lines);
    Assertions.assertThat(day20.groveCoordinateSum())
        .isEqualTo(6640); //11908 too high //-4698 wrong
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day20/sample.txt"))
            .toURI()));
    final Day20 day20 = new Day20(lines);
    Assertions.assertThat(day20.correctedGroveCoordinateSum()).isEqualTo(1623178306);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day20/input.txt"))
            .toURI()));
    final Day20 day20 = new Day20(lines);
    Assertions.assertThat(day20.correctedGroveCoordinateSum())
        .isEqualTo(11893839037215L); //11908 too high //-4698 wrong
  }
}