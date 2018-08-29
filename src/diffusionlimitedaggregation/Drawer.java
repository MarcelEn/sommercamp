package diffusionlimitedaggregation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static diffusionlimitedaggregation.DiffusionLimitedAggregation.*;

public class Drawer extends JPanel {

    private Graphics graphics;

    private int UPDATE_INTERVAL = 0;

    private ArrayList<Point> localPoints;

    public Drawer() {
        setBackground(new Color(0,0,0,0));
        setSize(DiffusionLimitedAggregation.WIDTH, DiffusionLimitedAggregation.HEIGHT);
        render();
    }

    public Drawer(ArrayList<Point> result){
        this.setOpaque(false);
        setBackground(new Color(0,0,0,0));
        localPoints = result;
        repaint();
    }


    private void render() {
        (new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(UPDATE_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.repaint();
            }
        })).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphics = g;

        if (localPoints == null) {
            ((ArrayList<Point>) points.clone()).forEach(this::printPointOnUi);
        } else {
            localPoints.forEach(this::printPointOnUi);
        }

    }

    private void printPointOnUi(Point point) {
        graphics.setColor(point.getColor());

        graphics.drawLine((int) point.getX(), (int) point.getY(), (int) point.getX(), (int) point.getY());
    }

}
