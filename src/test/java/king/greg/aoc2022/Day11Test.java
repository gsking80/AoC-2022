package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day11Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day11/sample.txt"))
            .toURI()));
    final Day11 day11 = new Day11(lines);
    Assertions.assertThat(day11.monkeyBusiness(20, true)).isEqualTo(10605);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day11/input.txt"))
            .toURI()));
    final Day11 day11 = new Day11(lines);
    Assertions.assertThat(day11.monkeyBusiness(20, true)).isEqualTo(182293);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day11/sample.txt"))
            .toURI()));
    final Day11 day11 = new Day11(lines);
    Assertions.assertThat(day11.monkeyBusiness(10000, false)).isEqualTo(2713310158L);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day11/input.txt"))
            .toURI()));
    final Day11 day11 = new Day11(lines);
    Assertions.assertThat(day11.monkeyBusiness(10000, false)).isEqualTo(54832778815L);
  }
}