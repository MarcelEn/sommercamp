package diffusionlimitedaggregation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static diffusionlimitedaggregation.DiffusionLimitedAggregation.*;

public class Drawer extends JPanel {

    private Graphics graphics;

    private static final int POINT_RADIUS = 2;

    private int UPDATE_INTERVAL = 0;

    public Drawer() {
        setBackground(Color.black);
        setSize(DiffusionLimitedAggregation.WIDTH, DiffusionLimitedAggregation.HEIGHT);
        render();
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

        graphics.setColor(Color.white);

        ((ArrayList<Point>) points.clone()).forEach(this::printPointOnUi);
        /*graphics.drawOval((int) (CENTER_X - currentSpawnRadius), //
                (int) (CENTER_Y - currentSpawnRadius), //
                (int) (currentSpawnRadius * 2), //
                (int) (currentSpawnRadius * 2)); */
    }

    private void printPointOnUi(Point point) {
        /* graphics.fillOval(point.getX() - POINT_RADIUS / 2, //
                point.getY() - POINT_RADIUS / 2, //
                POINT_RADIUS, //
                POINT_RADIUS);*/
        graphics.drawLine((int) point.getX(), (int) point.getY(), (int) point.getX(), (int) point.getY());
    }

}
