package king.greg.aoc2022;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class Day06Test {

  @Test
  public void testSample1() {

    Assertions.assertThat(Day06.marker("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 4)).isEqualTo(7);
    Assertions.assertThat(Day06.marker("bvwbjplbgvbhsrlpgdmjqwftvncz", 4)).isEqualTo(5);
    Assertions.assertThat(Day06.marker("nppdvjthqldpwncqszvftbrmjlhg", 4)).isEqualTo(6);
    Assertions.assertThat(Day06.marker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 4)).isEqualTo(10);
    Assertions.assertThat(Day06.marker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 4)).isEqualTo(11);

  }

  @Test
  public void testSolution1() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day06/input.txt"))
            .toURI()));
    Assertions.assertThat(Day06.marker(lines.get(0), 4)).isEqualTo(1953);
  }

  @Test
  public void testSample2()  {
    Assertions.assertThat(Day06.marker("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 14)).isEqualTo(19);
    Assertions.assertThat(Day06.marker("bvwbjplbgvbhsrlpgdmjqwftvncz", 14)).isEqualTo(23);
    Assertions.assertThat(Day06.marker("nppdvjthqldpwncqszvftbrmjlhg", 14)).isEqualTo(23);
    Assertions.assertThat(Day06.marker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 14)).isEqualTo(29);
    Assertions.assertThat(Day06.marker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 14)).isEqualTo(26);
  }

  @Test
  public void testSolution2() throws IOException, URISyntaxException {
    final var lines = Files.readAllLines(Paths.get(
        Objects.requireNonNull(getClass().getClassLoader().getResource("Day06/input.txt"))
            .toURI()));
    Assertions.assertThat(Day06.marker(lines.get(0), 14)).isEqualTo(2301);
  }

}