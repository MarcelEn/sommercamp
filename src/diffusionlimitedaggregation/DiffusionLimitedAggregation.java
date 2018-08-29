package diffusionlimitedaggregation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class DiffusionLimitedAggregation {
    public static void main(String[] args) {
        new DiffusionLimitedAggregation();
    }

    public static double CENTER_Y, CENTER_X, currentMax = 0;

    public static final int HEIGHT = 500, //
            WIDTH = 500, //
            MAX_MOVEMENTS = 10000, //
            TOUCH_DISTANCE = 1, //
            SECTORS = 1;

    public static final double SPAWN_RADIUS_PADDING = 5, //
            MAX_RADIUS_PADDING = 5, //
            POINT_MOVEMENT_SPEED = 1;


    private Drawer drawer;

    public static ArrayList<Point> points = new ArrayList();

    public static boolean field[][] = new boolean[WIDTH][HEIGHT];

    public DiffusionLimitedAggregation() {
        JFrame jFrame = new JFrame();

        CENTER_X = WIDTH / 2;
        CENTER_Y = HEIGHT / 2;

        drawer = new Drawer();

        jFrame.add(drawer);
        jFrame.setSize(WIDTH, HEIGHT);
        jFrame.setResizable(false);
        jFrame.setLayout(null);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            field = new boolean[WIDTH][HEIGHT];
            try {
                startCalculationProcess();
            }catch (LeaveFieldException e){}
        }
    }

    private void startCalculationProcess() {
        Point start = new Point(Math.floor(WIDTH / 2), Math.floor(HEIGHT / 2));
        start.setColor(new Color(255, 255, 255));
        points.add(start);
        field[(int) (WIDTH / 2)][(int) (HEIGHT / 2)] = true;
        PointFactory pointFactory = new PointFactory();

        while (true)
            points.add(pointFactory.createNextPoint());

    }

}
