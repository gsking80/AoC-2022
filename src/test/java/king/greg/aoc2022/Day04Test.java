package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day04Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day04/sample.txt"))
            .toURI()));
    final Day04 day04 = new Day04(lines);
    Assertions.assertThat(day04.fullContainment()).isEqualTo(2);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day04/input.txt"))
            .toURI()));
    final Day04 day04 = new Day04(lines);
    Assertions.assertThat(day04.fullContainment()).isEqualTo(507);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day04/sample.txt"))
            .toURI()));
    final Day04 day04 = new Day04(lines);
    Assertions.assertThat(day04.partialOverlap()).isEqualTo(4);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day04/input.txt"))
            .toURI()));
    final Day04 day04 = new Day04(lines);
    Assertions.assertThat(day04.partialOverlap()).isEqualTo(897);
  }
  
}