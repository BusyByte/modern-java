package dev.shawngarner;

public record Point(int x, int y) {
    // constructor validation
    public Point {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates must be non-negative");
        }
    }

    // custom methods
    public float euclideanDistance(Point other) {
        return (float) Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public float manhattanDistance(Point other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }
}
