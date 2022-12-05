package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day05Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day05/sample.txt"))
            .toURI()));
    final Day05 day05 = new Day05(lines);
    Assertions.assertThat(day05.topCrates9000()).isEqualTo("CMZ");
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day05/input.txt"))
            .toURI()));
    final Day05 day05 = new Day05(lines);
    Assertions.assertThat(day05.topCrates9000()).isEqualTo("PSNRGBTFT");
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day05/sample.txt"))
            .toURI()));
    final Day05 day05 = new Day05(lines);
    Assertions.assertThat(day05.topCrates9001()).isEqualTo("MCD");
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day05/input.txt"))
            .toURI()));
    final Day05 day05 = new Day05(lines);
    Assertions.assertThat(day05.topCrates9001()).isEqualTo("BNTZFPMMW");
  }

}