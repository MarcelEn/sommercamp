package diffusionlimitedaggregation;

import java.awt.*;

public class Point {
    private double x,y;

    private Color color;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "x- " + getX() + " y-" + getY();
    }
}
