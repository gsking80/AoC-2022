package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day21Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day21/sample.txt"))
            .toURI()));
    final Day21 day21 = new Day21(lines);
    Assertions.assertThat(day21.calculateRoot()).isEqualTo(152);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day21/input.txt"))
            .toURI()));
    final Day21 day21 = new Day21(lines);
    Assertions.assertThat(day21.calculateRoot()).isEqualTo(331319379445180L);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day21/sample.txt"))
            .toURI()));
    final Day21 day21 = new Day21(lines);
    Assertions.assertThat(day21.calculateNumberToYell()).isEqualTo(301);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day21/input.txt"))
            .toURI()));
    final Day21 day21 = new Day21(lines);
    Assertions.assertThat(day21.calculateNumberToYell()).isEqualTo(3715799488132L);
  }
}