package client;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import javax.swing.JPanel;
import models.Protocol;

public class MovementThread extends Thread implements MouseMotionListener {

    private double angle;

    private ProtocolManager pm;

    private JPanel container;

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public MovementThread(JPanel container, ProtocolManager pm) {
        this.container = container;
        this.pm = pm;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        while (!isInterrupted()) {
            if (System.currentTimeMillis() - start > 15) {
                try {
                    pm.sendDouble(Protocol.PLAYER_MOVE, angle);
                } catch (IOException ex) {
                    System.out.println("Error sending movement");
                }
                start = System.currentTimeMillis();
            }
        }
    }
    
    private double getAngle(Point destination)
    {
        double diffx = destination.getX() - container.getWidth() / 2;
        double diffy = destination.getY() - container.getHeight() / 2;
        return Math.atan2(diffy, diffx);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        GamePanel.mouse = e.getPoint();
        angle = getAngle(e.getPoint());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}
