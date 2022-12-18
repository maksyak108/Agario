package models;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Food {
    
    private int id;
    
    protected Point point;
    
    public int getId() {
        return id;
    }

    public Point getPoint() {
        return point;
    }
    
    public double getX() {
        return point.getX();
    }
    
    public double getY() {
        return point.getY();
    }

    Random random = new Random();
    Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    public void paint(int rx, int ry, Point p, Graphics g){
        g.setColor(color);
        int x = rx - (p.x - point.x);
        int y = ry - (p.y - point.y);
        g.fillOval(x - Config.FOOD_RADIUS, y - Config.FOOD_RADIUS, Config.FOOD_RADIUS * 2, Config.FOOD_RADIUS * 2);
    }
    

    public Food(int id, Point point) {
        this.id = id;
        this.point = point;
    }
    
}
