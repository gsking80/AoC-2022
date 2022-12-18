package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day18Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day18/sample.txt"))
            .toURI()));
    final Day18 day18 = new Day18(lines);
    Assertions.assertThat(day18.surfaceArea()).isEqualTo(64);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day18/input.txt"))
            .toURI()));
    final Day18 day18 = new Day18(lines);
    Assertions.assertThat(day18.surfaceArea()).isEqualTo(3494);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day18/sample.txt"))
            .toURI()));
    final Day18 day18 = new Day18(lines);
    Assertions.assertThat(day18.externalSurfaceArea()).isEqualTo(58);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day18/input.txt"))
            .toURI()));
    final Day18 day18 = new Day18(lines);
    Assertions.assertThat(day18.externalSurfaceArea()).isEqualTo(2062);
  }
}