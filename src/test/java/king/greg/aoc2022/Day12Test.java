package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day12Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day12/sample.txt"))
            .toURI()));
    final Day12 day12 = new Day12(lines);
    Assertions.assertThat(day12.fewestSteps()).isEqualTo(31);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day12/input.txt"))
            .toURI()));
    final Day12 day12 = new Day12(lines);
    Assertions.assertThat(day12.fewestSteps()).isEqualTo(534);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day12/sample.txt"))
            .toURI()));
    final Day12 day12 = new Day12(lines);
    Assertions.assertThat(day12.fewestStepsReverse()).isEqualTo(29);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day12/input.txt"))
            .toURI()));
    final Day12 day12 = new Day12(lines);
    Assertions.assertThat(day12.fewestStepsReverse()).isEqualTo(525);
  }
}