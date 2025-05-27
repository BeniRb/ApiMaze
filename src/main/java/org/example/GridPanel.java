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
    private double maxX = 0;
    private double maxY = 0;

    private BufferedImage whiteTile;
    private BufferedImage blackTile;

    public GridPanel(List<PointData> dataList) {
        // Load tile images
        try {
            whiteTile = ImageIO.read(getClass().getResourceAsStream("/tiles/white.png"));
            blackTile = ImageIO.read(getClass().getResourceAsStream("/tiles/black.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (PointData p : dataList) {
            if (p.isWhite()) {
                whitePoints.add(new Point((int) p.getX(), (int) p.getY()));
            }
            maxX = Math.max(maxX, p.getX());
            maxY = Math.max(maxY, p.getY());
        }

        setPreferredSize(new Dimension((int) ((maxX + 1) * TILE_SIZE), (int) ((maxY + 1) * TILE_SIZE)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                BufferedImage tile = whitePoints.contains(new Point(x, y)) ? whiteTile : blackTile;
                int drawX = x * TILE_SIZE;
                int drawY = y * TILE_SIZE;

                if (tile != null) {
                    g2.drawImage(tile, drawX, drawY, TILE_SIZE, TILE_SIZE, null);
                } else {
                    // Fallback to color if image is missing
                    g2.setColor(whitePoints.contains(new Point(x, y)) ? Color.WHITE : Color.BLACK);
                    g2.fillRect(drawX, drawY, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }
}
