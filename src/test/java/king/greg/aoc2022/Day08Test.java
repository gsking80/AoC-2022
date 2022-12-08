package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day08Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day08/sample.txt"))
            .toURI()));
    final Day08 day08 = new Day08(lines);
    Assertions.assertThat(day08.visibleTrees()).isEqualTo(21);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day08/input.txt"))
            .toURI()));
    final Day08 day08 = new Day08(lines);
    Assertions.assertThat(day08.visibleTrees()).isEqualTo(1798);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day08/sample.txt"))
            .toURI()));
    final Day08 day08 = new Day08(lines);
    Assertions.assertThat(day08.maxScenicScore()).isEqualTo(8);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day08/input.txt"))
            .toURI()));
    final Day08 day08 = new Day08(lines);
    Assertions.assertThat(day08.maxScenicScore()).isEqualTo(259308);
  }
}