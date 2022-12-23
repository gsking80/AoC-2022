package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day23Test {

  @Test
  public void testSampleSmall1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day23/sampleSmall.txt"))
            .toURI()));
    final Day23 day23 = new Day23(lines);
    Assertions.assertThat(day23.smallestRectangleSpaces(10)).isEqualTo(25);
  }

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day23/sample.txt"))
            .toURI()));
    final Day23 day23 = new Day23(lines);
    Assertions.assertThat(day23.smallestRectangleSpaces(10)).isEqualTo(110);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day23/input.txt"))
            .toURI()));
    final Day23 day23 = new Day23(lines);
    Assertions.assertThat(day23.smallestRectangleSpaces(10)).isEqualTo(4218);
  }

  @Test
  public void testSampleSmall2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day23/sampleSmall.txt"))
            .toURI()));
    final Day23 day23 = new Day23(lines);
    Assertions.assertThat(day23.calculateRounds()).isEqualTo(4);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day23/sample.txt"))
            .toURI()));
    final Day23 day23 = new Day23(lines);
    Assertions.assertThat(day23.calculateRounds()).isEqualTo(20);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day23/input.txt"))
            .toURI()));
    final Day23 day23 = new Day23(lines);
    Assertions.assertThat(day23.calculateRounds()).isEqualTo(976);
  }
}