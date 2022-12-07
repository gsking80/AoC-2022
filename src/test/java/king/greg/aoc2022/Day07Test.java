package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day07Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day07/sample.txt"))
            .toURI()));
    final Day07 day07 = new Day07(lines);
    Assertions.assertThat(day07.totalUnder(100000)).isEqualTo(95437);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day07/input.txt"))
            .toURI()));
    final Day07 day07 = new Day07(lines);
    Assertions.assertThat(day07.totalUnder(100000)).isEqualTo(1367870);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day07/sample.txt"))
            .toURI()));
    final Day07 day07 = new Day07(lines);
    Assertions.assertThat(day07.sizeToDelete(30000000)).isEqualTo(24933642);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day07/input.txt"))
            .toURI()));
    final Day07 day07 = new Day07(lines);
    Assertions.assertThat(day07.sizeToDelete(30000000)).isEqualTo(549173);
  }
}