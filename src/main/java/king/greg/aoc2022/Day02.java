package king.greg.aoc2022;

import java.util.List;

public class Day02 {

  private final List<String> rounds;

  Day02(final List<String> lines) {
    rounds = lines;
  }

  public long totalScore() {
    long totalScore = 0;

    for (final String round : rounds) {
      final var them = round.charAt(0) - 'A';
      final var me = round.charAt(2) - 'X';
      totalScore += (((4 + me - them) % 3) * 3) + me + 1;
    }
    return totalScore;
  }

  public long totalDecodedScore() {
    long totalScore = 0;

    for (final String round : rounds) {
      final var them = round.charAt(0) - 'A';
      final var result = round.charAt(2) - 'X';
      totalScore += (result * 3) + ((them + result + 2) % 3) + 1;
    }
    return totalScore;
  }
}
