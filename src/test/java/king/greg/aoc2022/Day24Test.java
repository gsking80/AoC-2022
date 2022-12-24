package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day24Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day24/sample.txt"))
            .toURI()));
    final Day24 day24 = new Day24(lines);
    Assertions.assertThat(day24.fewestMinutes()).isEqualTo(18);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day24/input.txt"))
            .toURI()));
    final Day24 day24 = new Day24(lines);
    Assertions.assertThat(day24.fewestMinutes()).isEqualTo(314);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day24/sample.txt"))
            .toURI()));
    final Day24 day24 = new Day24(lines);
    Assertions.assertThat(day24.fewestMinutesWithSnacks()).isEqualTo(54);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day24/input.txt"))
            .toURI()));
    final Day24 day24 = new Day24(lines);
    Assertions.assertThat(day24.fewestMinutesWithSnacks()).isEqualTo(896);
  }
}