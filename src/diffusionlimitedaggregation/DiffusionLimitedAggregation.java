package diffusionlimitedaggregation;

import javax.swing.*;
import java.util.ArrayList;


public class DiffusionLimitedAggregation {
    public static void main(String[] args) {
        new DiffusionLimitedAggregation();

    }

    public static int CENTER_Y, CENTER_X, currentMax = 0;

    public static final int HEIGHT = 600, //
            WIDTH = 600, //
            SPAWN_RADIUS_PADDING = 40, //
            MAX_RADIUS_PADDING = 10, //
            TOUCH_DISTANCE = 1, //
            POINT_MOVEMENT_SPEED = 4;

    public static int currentSpawnRadius = SPAWN_RADIUS_PADDING;

    private Drawer drawer;

    public static ArrayList<Point> points = new ArrayList();

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

        startCalculationProcess();
    }

    private void startCalculationProcess() {
        points.add(new Point(WIDTH / 2, HEIGHT / 2));
        PointFactory pointFactory = new PointFactory();

        while(currentMax < WIDTH / 2 || currentMax < HEIGHT / 2)
            points.add(pointFactory.createNextPoint());

        System.out.println("finished");
    }

}
