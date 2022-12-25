package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day25Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day25/sample.txt"))
            .toURI()));
    final Day25 day25 = new Day25(lines);
    Assertions.assertThat(day25.calculateSNAFUsum()).isEqualTo("2=-1=0");
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day25/input.txt"))
            .toURI()));
    final Day25 day25 = new Day25(lines);
    Assertions.assertThat(day25.calculateSNAFUsum()).isEqualTo("2=--00--0220-0-21==1");
  }
}