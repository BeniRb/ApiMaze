package org.example;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            List<PointData> points = DataFetcher.fetchData("https://app.seker.live/fm1/get-points");

            JFrame frame = new JFrame("Maze Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GridPanel panel = new GridPanel(points);
            frame.add(new JScrollPane(panel));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load maze data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}