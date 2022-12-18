package server;

import java.awt.Point;
import models.Config;
import models.Food;
import models.FoodList;
import models.Player;
public class ServerPlayer extends Player {

    public void move(double angle) {
        double x = point.getX();
        double y = point.getY();
        x += Math.cos(angle) * Config.PLAYER_SPEED;
        y += Math.sin(angle) * Config.PLAYER_SPEED;

        if (x < getRadius()) {
            x = getRadius();
        } else if (x > Config.FIELD_WIDTH - getRadius()) {
            x = Config.FIELD_WIDTH - getRadius();
        }
        if (y < getRadius()) {
            y = getRadius();
        } else if (y > Config.FIELD_HEIGHT - getRadius()) {
            y = Config.FIELD_HEIGHT - getRadius();
        }

        point.setLocation(x, y);
        collisionsCheck();
    }


    public void respawn() {
        setPoints(0);
        point = Server.getRandomPoint(getRadius());
    }

    public boolean contains(Point p, int pradius) {
        return point.distance(p) + pradius <= getRadius();
    }

    private void collisionsCheck() {
        for (ServerThread thread : ThreadsList.getThreads()) {
            if (thread != null) {
                ServerPlayer sp = thread.getPlayer();
                if (sp != null && sp != this) {
                    if (contains(sp.getPoint(), sp.getRadius())) {
                        setPoints(getPoints() + sp.getPoints() / 2);
                        sp.respawn();
                        Server.playerUpdate(sp);
                    } else if (sp.contains(getPoint(), getRadius())) {
                        sp.setPoints(sp.getPoints() + getPoints() / 2);
                        respawn();
                        Server.playerUpdate(sp);
                    }
                }
            }
        }
        for (Food f : FoodList.getFood()) {
            if (f != null) {
                if (contains(f.getPoint(), Config.FOOD_RADIUS)) {
                    this.addPoint();
                    FoodList.remove(f.getId());
                    Server.foodEated(f);
                }
            }
        }
        Server.playerUpdate(this);
    }

    public ServerPlayer(int id, Point point) {
        super(id, point);
    }

}
