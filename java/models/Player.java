package models;

import java.awt.Point;

public class Player {

    private int id;

    private int points = 0;

    protected Point point;

    public double getX() {
        return point.getX();
    }

    public double getY() {
        return point.getY();
    }

    public Point getPoint() {
        return point;
    }

    public int getId() {
        return id;
    }

    public int getRadius() {
        int rad = Config.PLAYER_RADIUS + points / Config.FOOD_TO_RADIUS;
        if (rad > Config.PLAYER_MAX_RADIUS) {
            rad = Config.PLAYER_MAX_RADIUS;
        }
        return rad;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        if (points < 0) {
            points = 0;
        }
        this.points = points;
    }

    public void addPoint() {
        points++;
    }

    public Player(int id, Point point) {
        this.id = id;
        this.point = point;
    }

}
