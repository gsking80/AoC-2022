package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day02Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day02/sample.txt"))
            .toURI()));
    final Day02 day02 = new Day02(lines);
    Assertions.assertThat(day02.totalScore()).isEqualTo(15);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day02/input.txt"))
            .toURI()));
    final Day02 day02 = new Day02(lines);
    Assertions.assertThat(day02.totalScore()).isEqualTo(13268);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day02/sample.txt"))
            .toURI()));
    final Day02 day02 = new Day02(lines);
    Assertions.assertThat(day02.totalDecodedScore()).isEqualTo(12);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day02/input.txt"))
            .toURI()));
    final Day02 day02 = new Day02(lines);
    Assertions.assertThat(day02.totalDecodedScore()).isEqualTo(15508);
  }
}