package client;

import java.awt.*;
import java.io.IOException;
import javax.swing.JPanel;
import models.Food;
import models.FoodList;

public class GamePanel extends JPanel implements ProtocolManagerListener {
    private MovementThread mt;

    public static ClientPlayer player = null;
    private String status = "";
    
    public static Point mouse = null;

    public static Leaderboard leaderboard;

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawString("AdarIo", 3, getHeight() - 5);
        if (player != null) {
            
            Point mainPlayer = player.getPoint();

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            for (Food f : FoodList.getFood()) {
                if (f != null) {
                    f.paint(centerX, centerY, mainPlayer, g);
                }
            }

            for (ClientPlayer player : PlayersList.getPlayers()) {
                if (player != null) {
                    player.paint(centerX, centerY, mainPlayer, g);
                }
            }

            leaderboard.setLocation(this.getWidth()-leaderboard.getWidth(), 0);      
            leaderboard.paint(g);
        } else {
            g.setColor(Color.BLACK);
            int sWidth = g.getFontMetrics().stringWidth(status);
            g.drawString(status, (getWidth() - sWidth) / 2, getHeight() / 2);
        }

    }

    public GamePanel() {
        try {
            leaderboard = new Leaderboard();
            
            status = "Connecting to the server..";
            ProtocolManager pm = new ProtocolManager(this);
            pm.start();

            mt = new MovementThread(this, pm);
            this.addMouseMotionListener(mt);
            
            mt.start();
        } catch (IOException ex) {
            System.out.println("Error can't connect to the server");
            status = "Can't connect to the server!";
        }
    }

    @Override
    public void update() {
        repaint();
    }
}
