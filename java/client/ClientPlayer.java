package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import models.Player;

public class ClientPlayer extends Player {

    Random random = new Random();
    Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));

    public void paint(int rx, int ry, Point p, Graphics g) {
        int radius = this.getRadius();
        int x = rx;
        int y = ry;
        g.setColor(color);
        if (this.getId() != GamePanel.player.getId()) {
            g.setColor(Color.RED);
            x = rx - (p.x - point.x);
            y = ry - (p.y - point.y);
        }
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        g.setColor(Color.BLACK);
        g.drawString("Size: " + getPoints(), x, y);
    }

    public ClientPlayer(int id, Point point) {
        super(id, point);
    }

}
