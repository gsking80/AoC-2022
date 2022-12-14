package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day14Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day14/sample.txt"))
            .toURI()));
    final Day14 day14 = new Day14(lines);
    Assertions.assertThat(day14.restingSand()).isEqualTo(24);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day14/input.txt"))
            .toURI()));
    final Day14 day14 = new Day14(lines);
    Assertions.assertThat(day14.restingSand()).isEqualTo(808);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day14/sample.txt"))
            .toURI()));
    final Day14 day14 = new Day14(lines);
    Assertions.assertThat(day14.restingSandInfiniteFloor()).isEqualTo(93);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day14/input.txt"))
            .toURI()));
    final Day14 day14 = new Day14(lines);
    Assertions.assertThat(day14.restingSandInfiniteFloor()).isEqualTo(26625);
  }
}