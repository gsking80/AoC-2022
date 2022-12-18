package king.greg.aoc2022;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Day18 {

  final List<Cube> cubes;

  public Day18(final List<String> input) {
    cubes = new ArrayList<>();
    for (final var line : input) {
      final String[] parts = line.split(",");
      final var cube = new Cube(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
          Integer.parseInt(parts[2]));
      cubes.add(cube);
    }
  }

  public int surfaceArea() {
    var exposedSides = 0;
    for (final var cube : cubes) {
      var sides = 6;
      for (final var otherCube : cubes) {
        if (otherCube == cube) {
          continue;
        }
        if (Math.abs(cube.x - otherCube.x) + Math.abs(cube.y - otherCube.y) + Math.abs(
            cube.z - otherCube.z) == 1) {
          sides--;
        }
      }
      exposedSides += sides;
    }
    return exposedSides;
  }

  public int externalSurfaceArea() {
    final var minX = cubes.stream().mapToInt(c -> c.x).min().orElse(0) - 1;
    final var maxX = cubes.stream().mapToInt(c -> c.x).max().orElse(0) + 1;
    final var minY = cubes.stream().mapToInt(c -> c.y).min().orElse(0) - 1;
    final var maxY = cubes.stream().mapToInt(c -> c.y).max().orElse(0) + 1;
    final var minZ = cubes.stream().mapToInt(c -> c.z).min().orElse(0) - 1;
    final var maxZ = cubes.stream().mapToInt(c -> c.z).max().orElse(0) + 1;
    var externalSides = 0;
    final var initialCube = new Cube(minX, minY, minZ);
    final var queue = new ArrayDeque<Cube>();
    final var visited = new HashSet<Cube>();
    queue.addLast(initialCube);
    visited.add(initialCube);
    while (!queue.isEmpty()) {
      var current = queue.removeFirst();
      for (final var neighbor : current.getNeighbors()) {
        if (cubes.contains(neighbor)) {
          externalSides++;
        } else if (!visited.contains(neighbor)) {
          if (neighbor.x <= maxX && neighbor.x >= minX && neighbor.y <= maxY && neighbor.y >= minY
              && neighbor.z <= maxZ && neighbor.z >= minZ) {
            visited.add(neighbor);
            queue.add(neighbor);
          }
        }
      }
    }
    return externalSides;
  }

  record Cube(int x, int y, int z) {

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Cube cube = (Cube) o;
      return x == cube.x && y == cube.y && z == cube.z;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y, z);
    }

    public List<Cube> getNeighbors() {
      final var neighbors = new ArrayList<Cube>();
      neighbors.add(new Cube(x - 1, y, z));
      neighbors.add(new Cube(x + 1, y, z));
      neighbors.add(new Cube(x, y - 1, z));
      neighbors.add(new Cube(x, y + 1, z));
      neighbors.add(new Cube(x, y, z - 1));
      neighbors.add(new Cube(x, y, z + 1));
      return neighbors;
    }
  }
}
