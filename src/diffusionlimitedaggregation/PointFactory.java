package diffusionlimitedaggregation;

import java.awt.*;

import static diffusionlimitedaggregation.DiffusionLimitedAggregation.*;

public class PointFactory {

    Point currentPoint;

    int currentMoves;

    private double[] sectorSpawnRadius = new double[SECTORS];

    private final double SECTOR_SIZE_IN_DEGREE = 360 / SECTORS;

    public Point createNextPoint() throws LeaveFieldException {
        currentPoint = getStartPosition();
        while (isNotTouchingAOtherPoint()) {
            movePointToNewPosition();
            validateNewPosition();
            currentMoves++;
        }

        updateCurrentMax();

        int x = (int) currentPoint.getX();
        int y = (int) currentPoint.getY();


        int range = HEIGHT;
        if (WIDTH > HEIGHT)
            range = WIDTH;

        int c = 255 - (int) (255 * (getDistanceToCenter() * 2) / range);

        if (c < 0)
            c = 0;

        currentPoint.setColor(new Color(c, 255, c));
        try {
            field[x][y] = true;
        } catch (Exception e) {
            throw new LeaveFieldException();
        }
        return currentPoint;
    }

    private void validateNewPosition() {
        if (getDistanceToCenter() > (getCurrentMaxSector() + MAX_RADIUS_PADDING) || currentMoves > MAX_MOVEMENTS)
            currentPoint = getStartPosition();
    }

    private double getCurrentMaxSector() {
        double max = 0;
        for (int i = 0; i < sectorSpawnRadius.length; i++)
            if (max < sectorSpawnRadius[i])
                max = sectorSpawnRadius[i];
        return max;
    }

    private void updateCurrentMax() {
        double distanceToCenter = getDistanceToCenter();
        int sector = getCurrentSector();
        if (distanceToCenter > sectorSpawnRadius[sector]) {
            currentMax = distanceToCenter;
            sectorSpawnRadius[sector] = distanceToCenter + SPAWN_RADIUS_PADDING;
        }
    }

    private int getCurrentSector() {
        return degreeToSector(getCurrentDegree());
    }

    private double getCurrentDegree() {
        double x = CENTER_X - currentPoint.getX();
        double y = CENTER_Y - currentPoint.getY();

        if (x < 0)
            x *= -1;
        if (y < 0)
            y *= -1;

        switch (getQuadrant()) {
            case 1:
                return Math.toDegrees(Math.atan(x / y));
            case 2:
                return 90 + Math.toDegrees(Math.atan(y / x));
            case 3:
                return 180 + Math.toDegrees(Math.atan(x / y));
            case 4:
                return 270 + Math.toDegrees(Math.atan(y / x));
        }
        return 0;
    }

    private int getQuadrant() {
        if (currentPoint.getX() > CENTER_X) {
            if (currentPoint.getY() < CENTER_Y)
                return 1;
            return 2;
        }

        if (currentPoint.getY() < CENTER_Y)
            return 4;
        return 3;
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
        for (int x = (int) currentPoint.getX() - TOUCH_DISTANCE; x < (int) currentPoint.getX() + TOUCH_DISTANCE + 1; x++) {
            for (int y = (int) currentPoint.getY() - TOUCH_DISTANCE; y < (int) currentPoint.getY() + TOUCH_DISTANCE + 1; y++) {
                try {
                    if (field[x][y])
                        return false;
                } catch (Exception e) {
                }
            }
        }
        return true;
    }

    private double getDistanceTo(Point point) {
        double a = point.getX() - currentPoint.getX();
        double b = point.getY() - currentPoint.getY();

        if (a < 0)
            a *= -1;
        if (b < 0)
            b *= -1;

        return Math.sqrt(a * a + b * b);
    }

    private Point getStartPosition() {
        currentMoves = 0;
        double angle = getRandomDouble(360);
        return new Point( //
                Math.round(Math.sin(angle) * getSpawnRadiusByDegree(angle)) + CENTER_X, //
                Math.round(Math.cos(angle) * getSpawnRadiusByDegree(angle)) + CENTER_Y);

    }

    double getSpawnRadiusByDegree(double degree) {
        int sector = degreeToSector(degree);
        if (sectorSpawnRadius[sector] == 0) {
            return SPAWN_RADIUS_PADDING;
        }
        return sectorSpawnRadius[sector] + SPAWN_RADIUS_PADDING;
    }

    int degreeToSector(double degree) {
        double degreeCounter = 0;
        int sector = -1;
        while (degreeCounter < 360) {
            sector++;
            degreeCounter += SECTOR_SIZE_IN_DEGREE;
            if (degree < degreeCounter && degree > degreeCounter - SECTOR_SIZE_IN_DEGREE) {
                return sector;
            }
        }
        return sector;
    }

    private double getRandomDouble(double max) {
        return Math.random() * max;
    }


}
