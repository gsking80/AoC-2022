package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day16Test {

  @Test
  public void testTiny1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day16/tiny.txt"))
            .toURI()));
    final Day16 day16 = new Day16(lines);
    Assertions.assertThat(day16.maxPressure(5)).isEqualTo(3);
  }

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day16/sample.txt"))
            .toURI()));
    final Day16 day16 = new Day16(lines);
    Assertions.assertThat(day16.maxPressure(30)).isEqualTo(1651);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day16/input.txt"))
            .toURI()));
    final Day16 day16 = new Day16(lines);
    Assertions.assertThat(day16.maxPressure(30)).isEqualTo(2250);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day16/sample.txt"))
            .toURI()));
    final Day16 day16 = new Day16(lines);
    Assertions.assertThat(day16.maxPressureElephant(26)).isEqualTo(1707);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day16/input.txt"))
            .toURI()));
    final Day16 day16 = new Day16(lines);
    Assertions.assertThat(day16.maxPressureElephant(26)).isEqualTo(3015);
  }
}