package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day22Test {

  @Test
  public void testSample1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day22/sample.txt"))
            .toURI()));
    final Day22 day22 = new Day22(lines);
    Assertions.assertThat(day22.getPassword()).isEqualTo(6032);
  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day22/input.txt"))
            .toURI()));
    final Day22 day22 = new Day22(lines);
    Assertions.assertThat(day22.getPassword()).isEqualTo(73346);
  }

  @Test
  public void testSample2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day22/sample.txt"))
            .toURI()));
    final Day22 day22 = new Day22(lines);
    Assertions.assertThat(day22.getPassword2()).isEqualTo(5031);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day22/input.txt"))
            .toURI()));
    final Day22 day22 = new Day22(lines);
    Assertions.assertThat(day22.getPassword2()).isEqualTo(106392);
  }
}