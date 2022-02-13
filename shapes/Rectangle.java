/*
    Author: Iiro Koskinen H299947
*/

public class Rectangle implements IShapeMetrics {
    private double height;
    private double width;

    public Rectangle(double height, double width) {
        this.height = height;
        this.width = width;
    }

    public String toString() {
        return String.format("Rectangle with height %.2f and width %.2f", this.height, this.width);
    }

    public String name() {
        return "rectangle";
    }

    public double area() {
        return this.height * this.width;
    }

    public double circumference() {
        return 2 * this.height + 2 * this.width;
    }
}
