package org.example;

import java.awt.Point;
import java.util.*;

public class MazeSolver {
    private final Set<Point> whitePoints;
    private final int maxX;
    private final int maxY;
    private final Set<Point> visited = new HashSet<>();
    private final Map<Point, Point> cameFrom = new HashMap<>();

    public MazeSolver(Set<Point> whitePoints, int maxX, int maxY) {
        this.whitePoints = whitePoints;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public List<Point> solve(Point start, Point end) {
        if (!whitePoints.contains(start) || !whitePoints.contains(end)) return Collections.emptyList();

        Stack<Point> stack = new Stack<>();
        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            Point current = stack.pop();

            if (current.equals(end)) {
                return reconstructPath(end);
            }

            for (Point neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    stack.push(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        return Collections.emptyList(); // No path found
    }

    private List<Point> getNeighbors(Point p) {
        List<Point> neighbors = new ArrayList<>();
        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        for (int[] d : directions) {
            Point np = new Point(p.x + d[0], p.y + d[1]);
            if (np.x >= 0 && np.x <= maxX && np.y >= 0 && np.y <= maxY) {
                if (whitePoints.contains(np)) {
                    neighbors.add(np);
                } else {
                    // Debug print for black tile neighbors
                    System.out.println("Rejected black tile neighbor: " + np);
                }
            }
        }
        return neighbors;
    }

    private List<Point> reconstructPath(Point end) {
        List<Point> path = new ArrayList<>();
        Point current = end;
        while (current != null) {
            path.add(current);
            if (!whitePoints.contains(current)) {
                System.out.println("Warning: Path includes black tile at " + current);
            }
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }
}
