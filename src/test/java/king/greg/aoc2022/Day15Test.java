package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day15Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day15/sample.txt"))
            .toURI()));
    final Day15 day15 = new Day15(lines);
    Assertions.assertThat(day15.beaconlessSpaces(10)).isEqualTo(26);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day15/input.txt"))
            .toURI()));
    final Day15 day15 = new Day15(lines);
    Assertions.assertThat(day15.beaconlessSpaces(2000000)).isEqualTo(4919281);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day15/sample.txt"))
            .toURI()));
    final Day15 day15 = new Day15(lines);
    Assertions.assertThat(day15.beaconFrequency(20)).isEqualTo(56000011);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day15/input.txt"))
            .toURI()));
    final Day15 day15 = new Day15(lines);
    Assertions.assertThat(day15.beaconFrequency(4000000)).isEqualTo(12630143363767L);
  }
}