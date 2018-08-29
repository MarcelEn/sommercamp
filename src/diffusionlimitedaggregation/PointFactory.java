package diffusionlimitedaggregation;

import static diffusionlimitedaggregation.DiffusionLimitedAggregation.*;

public class PointFactory {

    Point currentPoint;

    public Point createNextPoint() {
        currentPoint = getStartPosition();

        while (isNotTouchingAOtherPoint()) {
            movePointToNewPosition();
            validateNewPosition();
        }

        updateCurrentMax();
        return currentPoint;
    }

    private void validateNewPosition() {
        if (getDistanceToCenter() > (currentSpawnRadius + MAX_RADIUS_PADDING))
            currentPoint = getStartPosition();
    }

    private void updateCurrentMax() {
        double distanceToCenter = getDistanceToCenter();
        if (distanceToCenter > currentMax) {
            currentMax = (int) getDistanceToCenter();
            currentSpawnRadius = currentMax + SPAWN_RADIUS_PADDING;
        }
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

        int a = CENTER_X - currentPoint.getX();
        int b = CENTER_Y - currentPoint.getY();

        if (a < 0)
            a *= -1;
        if (b < 0)
            b *= -1;

        double multiplier;

        if (a < b) {
            multiplier = a / b;
        } else {
            multiplier = b / a;
        }

        double CONSTANT = 4;

        if (currentPoint.getX() > CENTER_X) {
            if (currentPoint.getY() > CENTER_Y) {
                if (a > b) {
                    x -= CONSTANT * multiplier;
                    y -= CONSTANT * (1 - multiplier);
                }else{
                    x -= CONSTANT * (1 - multiplier);
                    y -= CONSTANT * multiplier;
                }
            } else {
                if (a > b) {
                    x += CONSTANT * multiplier;
                    y -= CONSTANT * (1 - multiplier);
                }else{
                    x += CONSTANT * (1 - multiplier);
                    y -= CONSTANT * multiplier;
                }
            }
        } else {
            if (currentPoint.getY() > CENTER_Y) {
                if (a > b) {
                    x -= CONSTANT * multiplier;
                    y += CONSTANT * (1 - multiplier);
                }else{
                    x -= CONSTANT * (1 - multiplier);
                    y += CONSTANT * multiplier;
                }
            } else {
                if (a > b) {
                    x += CONSTANT * multiplier;
                    y += CONSTANT * (1 - multiplier);
                }else{
                    x += CONSTANT * (1 - multiplier);
                    y += CONSTANT * multiplier;
                }
            }
        }

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
