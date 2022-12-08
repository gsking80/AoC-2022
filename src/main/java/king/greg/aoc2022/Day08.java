package king.greg.aoc2022;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day08 {

  final int[][] forest;
  final int max;

  public Day08(final List<String> input) {
    max = input.size();
    forest = new int[max][max];
    for (var y = 0; y < max; y++) {
      var currentLine = input.get(y);
      for (var x = 0; x < max; x++) {
        forest[x][y] = currentLine.charAt(x) - '0';
      }
    }
  }

  public long visibleTrees() {
    final Set<Point> visibleTrees = new HashSet<>();
    for (var i = 0; i < max; i++) {
      var tallestA = -1;
      var tallestB = -1;
      var tallestC = -1;
      var tallestD = -1;
      for (var j = 0; j < max; j++) {
        var reverse = max - j - 1;
        tallestA = visible(i, j, tallestA, visibleTrees);
        tallestB = visible(i, reverse, tallestB, visibleTrees);
        tallestC = visible(j, i, tallestC, visibleTrees);
        tallestD = visible(reverse, i, tallestD, visibleTrees);
      }
    }
    return visibleTrees.size();
  }

  private int visible(final int x, final int y, int tallest, final Set<Point> visibleTrees) {
    var height = forest[x][y];
    if (height > tallest) {
      tallest = height;
      visibleTrees.add(new Point(x, y));
    }
    return tallest;
  }

  public long maxScenicScore() {
    var bestScore = -1;
    for (var y = 1; y < max - 1; y++) {
      for (var x = 1; x < max - 1; x++) {
        var visibleLeft = directionScore(x, y, -1, 0);
        var visibleRight = directionScore(x, y, 1, 0);
        var visibleUp = directionScore(x, y, 0, -1);
        var visibleDown = directionScore(x, y, 0, 1);
        var score = visibleLeft * visibleRight * visibleUp * visibleDown;
        if (score > bestScore) {
          bestScore = score;
        }
      }
    }
    return bestScore;
  }

  private int directionScore(final int x, final int y, final int deltaX, final int deltaY) {
    final var height = forest[x][y];
    var visible = 0;
    var blocked = false;
    var testX = x + deltaX;
    var testY = y + deltaY;
    while (!blocked && testX >= 0 && testX < max && testY >= 0 && testY < max) {
      visible++;
      if (forest[testX][testY] >= height) {
        blocked = true;
      }
      testX += deltaX;
      testY += deltaY;
    }
    return visible;
  }
}
