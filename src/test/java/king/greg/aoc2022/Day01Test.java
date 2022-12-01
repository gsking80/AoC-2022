package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day01Test {

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day01/input.txt"))
            .toURI()));
    final Day01 day01 = new Day01(lines);
    Assertions.assertThat(day01.topCalories()).isEqualTo(68292);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day01/input.txt"))
            .toURI()));
    final Day01 day01 = new Day01(lines);
    Assertions.assertThat(day01.topThreeCalories()).isEqualTo(203203);
  }

}