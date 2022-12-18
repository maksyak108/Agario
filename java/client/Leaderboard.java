
package client;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import models.Player;
public class Leaderboard extends JPanel {

    public Leaderboard() {
        this.setSize(200,PlayersList.getPlayers().length * 25);
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(0 +this.getX(), 0 + this.getY(), this.getWidth(), this.getHeight());
        int i = 0;
        if (PlayersList.getPlayers() != null) {
            for (Player p : sortArray(PlayersList.getPlayers())) {
                if(p != null){
                    g.drawString(
                            p.getId() + ":" + p.getPoints(),
                            this.getX()+5,
                            this.getY() + 10 + (int) (g.getFontMetrics().getStringBounds(p.getId() + ":" + p.getPoints(), g)).getHeight() * i
                    );
                    i++;
                }
            }
        }
    }

    public Player[] sortArray(Player[] players) {
        Player[] playerSort = new Player[players.length];
        for (int i = 0; i < playerSort.length; i++) {
            playerSort[i] = players[i];
        }
        int n = playerSort.length;
        Player temp = players[0];

        if (temp != null) {
            for (int i = 0; i < n; i++) {
                for (int j = 1; j < (n - i); j++) {
                    if (playerSort[j - 1] != null && playerSort[j] != null) {
                        if (playerSort[j - 1].getPoints() < playerSort[j].getPoints()) {
                            temp = playerSort[j - 1];
                            playerSort[j - 1] = playerSort[j];
                            playerSort[j] = temp;
                        }
                    }
                }
            }
        }
        return playerSort;
    }

}
