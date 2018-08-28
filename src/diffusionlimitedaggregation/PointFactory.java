package diffusionlimitedaggregation;

import java.util.Optional;

import static diffusionlimitedaggregation.DiffusionLimitedAggregation.*;

public class PointFactory {

    Point currentPoint;

    public Point createNextPoint() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("start");
        currentPoint = getStartPosition();

        while (isNotTouchingAOtherPoint()) {
            movePointToNewPosition();
            System.out.println(currentPoint);
            validateNewPosition();
        }

        updateCurrentMax();

        System.out.println("end");
        System.out.println(currentPoint);

        return currentPoint;
    }

    private void validateNewPosition() {
        double distanceToCenter = getDistanceToCenter();

        if(distanceToCenter > (currentSpawnRadius + MAX_RADIUS_PADDING)){
            currentPoint = getStartPosition();
            return;
        }
    }

    private void updateCurrentMax() {
        currentMax = (int) getDistanceToCenter();
        currentSpawnRadius = currentMax + SPAWN_RADIUS_PADDING;
    }

    private double getDistanceToCenter() {
        return getDistanceTo(new Point(CENTER_X, CENTER_Y));
    }

    private void movePointToNewPosition() {
        int x = (int) Math.round(getRandomDouble(POINT_MOVEMENT_SPEED));
        int y = (int) Math.round(getRandomDouble(POINT_MOVEMENT_SPEED));

        if ((int) Math.round(getRandomDouble(1)) == 0)
            x *= -1;
        if ((int) Math.round(getRandomDouble(1)) == 0)
            y *= -1;

        currentPoint = new Point( //
                currentPoint.getX() + x, //
                currentPoint.getY() + y);
    }

    private boolean isNotTouchingAOtherPoint() {
        return !points.stream() //
                .anyMatch(this::isTouching);
    }

    private boolean isTouching(Point point) {
        return getDistanceTo(point) < TOUCH_DISTANCE;
    }

    private double getDistanceTo(Point point) {
        int a = point.getX() - currentPoint.getX();
        int b = point.getY() - currentPoint.getY();

        if (a < 0)
            a *= -1;
        if (b < 0)
            b *= -1;

        return Math.sqrt(a * a + b * b);
    }

    private Point getStartPosition() {
        double angle = getRandomDouble(360);
        return new Point( //
                (int) Math.round(Math.sin(angle) * currentSpawnRadius) + CENTER_X, //
                (int) Math.round(Math.cos(angle) * currentSpawnRadius) + CENTER_Y);

    }

    private double getRandomDouble(double max) {
        return Math.random() * max;
    }


}
