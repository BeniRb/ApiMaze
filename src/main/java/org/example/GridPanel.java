package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GridPanel extends JPanel {
    private static final int TILE_SIZE = 15;

    private final Set<Point> whitePoints = new HashSet<>();
    private int maxX = 0;
    private int maxY = 0;

    private BufferedImage whiteTile;
    private BufferedImage blackTile;

    private List<Point> solutionPath;

    public GridPanel(List<PointData> dataList) {
        // Load tile images
        try {
            whiteTile = ImageIO.read(getClass().getResource("/tiles/white.png"));
            blackTile = ImageIO.read(getClass().getResource("/tiles/black.png"));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load tile images.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        for (PointData p : dataList) {
            int x = (int) Math.round(p.getX());
            int y = (int) Math.round(p.getY());
            if (p.isWhite()) {
                whitePoints.add(new Point(x, y));
            }
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }
        System.out.println("Total white tiles: " + whitePoints.size());
        System.out.println("maxX=" + maxX + ", maxY=" + maxY);

        // Debug print start and end tiles white check
        System.out.println("Start (0,0) is white? " + whitePoints.contains(new Point(0, 0)));
        System.out.println("End (" + maxX + "," + maxY + ") is white? " + whitePoints.contains(new Point(maxX, maxY)));

        // Solve maze from (0,0) to (maxX, maxY)
        MazeSolver solver = new MazeSolver(whitePoints, maxX, maxY);
        solutionPath = solver.solve(new Point(0, 0), new Point(maxX, maxY));
        System.out.println("Path length: " + solutionPath.size());

        setPreferredSize(new Dimension((maxX + 1) * TILE_SIZE, (maxY + 1) * TILE_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                BufferedImage tile = whitePoints.contains(new Point(x, y)) ? whiteTile : blackTile;
                int drawX = x * TILE_SIZE;
                int drawY = y * TILE_SIZE;

                if (tile != null) {
                    g2d.drawImage(tile, drawX, drawY, TILE_SIZE, TILE_SIZE, null);
                } else {
                    g2d.setColor(whitePoints.contains(new Point(x, y)) ? Color.WHITE : Color.BLACK);
                    g2d.fillRect(drawX, drawY, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        // Draw solution path overlay in semi-transparent red only if path exists
        if (solutionPath != null && !solutionPath.isEmpty()) {
            g2d.setColor(new Color(255, 0, 0, 128)); // Red with transparency
            for (Point p : solutionPath) {
                int drawX = p.x * TILE_SIZE;
                int drawY = p.y * TILE_SIZE;
                g2d.fillRect(drawX, drawY, TILE_SIZE, TILE_SIZE);
            }
        }
    }
}

