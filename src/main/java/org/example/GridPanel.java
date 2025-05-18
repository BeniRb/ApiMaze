package org.example;

import org.example.PointData;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GridPanel extends JPanel {
    private static final int TILE_SIZE = 5;

    private final Set<Point> whitePoints = new HashSet<>();
    private double maxX = 0;
    private double maxY = 0;

    public GridPanel(List<PointData> dataList) {
        for (PointData p : dataList) {
            if (p.isWhite()) {
                whitePoints.add(new Point((int) p.getX(), (int) p.getY()));
            }
            maxX = Math.max(maxX, p.getX());
            maxY = Math.max(maxY, p.getY());
        }

        System.out.println("maxX = " + maxX + ", maxY = " + maxY);
        setPreferredSize(new Dimension((int) ((maxX + 1) * TILE_SIZE), (int) ((maxY + 1) * TILE_SIZE)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                g.setColor(whitePoints.contains(new Point(x, y)) ? Color.WHITE : Color.BLACK);
                int drawX = x * TILE_SIZE;
                int drawY = y * TILE_SIZE;
                g.fillRect(drawX, drawY, TILE_SIZE, TILE_SIZE);
            }
        }
    }
}