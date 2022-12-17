package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day17Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day17/sample.txt"))
            .toURI()));
    final Day17 day17 = new Day17(lines);
    Assertions.assertThat(day17.towerHeight(2022)).isEqualTo(3068);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day17/input.txt"))
            .toURI()));
    final Day17 day17 = new Day17(lines);
    Assertions.assertThat(day17.towerHeight(2022)).isEqualTo(3179);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day17/sample.txt"))
            .toURI()));
    final Day17 day17 = new Day17(lines);
    Assertions.assertThat(day17.cyclicTowerHeight(1000000000000L)).isEqualTo(1514285714288L);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day17/input.txt"))
            .toURI()));
    final Day17 day17 = new Day17(lines);
    Assertions.assertThat(day17.cyclicTowerHeight(1000000000000L)).isEqualTo(1567723342929L);
  }
}