/*
    Author: Iiro Koskinen H299947
*/


public class Circle implements IShapeMetrics {
    private double radius;

    public Circle(double radius) {
        // constructor, makes a circle with radius: radius
        this.radius = radius;
    }

    public String toString() {
        return String.format("Circle with radius: %.2f", this.radius);
    }

    public String name() {
        return "circle";
    }

    public double area() {
        double result = PI * this.radius *this.radius;
        return result;
    }

    public double circumference() {
        double result = 2 * PI * this.radius;
        return result;
    }
}
