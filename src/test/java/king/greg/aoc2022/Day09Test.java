package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day09Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day09/sampleA.txt"))
            .toURI()));
    final Day09 day09 = new Day09(lines);
    Assertions.assertThat(day09.tailPositions(2)).isEqualTo(13);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day09/input.txt"))
            .toURI()));
    final Day09 day09 = new Day09(lines);
    Assertions.assertThat(day09.tailPositions(2)).isEqualTo(6236);
  }

  @Test
  public void testSample2a() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day09/sampleA.txt"))
            .toURI()));
    final Day09 day09 = new Day09(lines);
    Assertions.assertThat(day09.tailPositions(10)).isEqualTo(1);
  }

  @Test
  public void testSample2b() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day09/sampleB.txt"))
            .toURI()));
    final Day09 day09 = new Day09(lines);
    Assertions.assertThat(day09.tailPositions(10)).isEqualTo(36);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day09/input.txt"))
            .toURI()));
    final Day09 day09 = new Day09(lines);
    Assertions.assertThat(day09.tailPositions(10)).isEqualTo(2449);
  }
}