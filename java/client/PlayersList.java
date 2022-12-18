package client;

import models.Config;

public class PlayersList {

    private static ClientPlayer players[] = new ClientPlayer[Config.MAX_PLAYERS];

    public static ClientPlayer[] getPlayers() {
        return players;
    }

    public static ClientPlayer get(int id) {
        if (id >= 0 && id < players.length) {
            return players[id];
        }
        return null;
    }

    public static void remove(int id) {
        if (id >= 0 && id < players.length) {
            players[id] = null;
        }
    }

    public static void add(ClientPlayer player) {
        int id = player.getId();
        if (id >= 0 && id < players.length) {
            players[id] = player;
        }
    }

}
