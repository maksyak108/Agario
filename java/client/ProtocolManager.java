package client;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import models.Food;
import models.FoodList;
import models.Protocol;

public class ProtocolManager extends Thread {

    private final int PORT = 5555;

    private Socket client;

    private ProtocolManagerListener listener;

    private DataOutputStream out;

    public ProtocolManager(ProtocolManagerListener listener) throws IOException {
        this.listener = listener;
        this.client = new Socket("localhost", PORT);
        this.out = new DataOutputStream(client.getOutputStream());
    }

    public void send(byte command) throws IOException {
        out.write(command);
    }

    public void sendDouble(byte command, double value) throws IOException {
        send(command);
        out.writeDouble(value);
    }

    @Override
    public void run() {
        System.out.println("ProtocolManager started");
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            double read = 0;
            while (!isInterrupted()) {
                read = in.readDouble();
                if (read == Protocol.PLAYER_JOINED) {
                    int id = (int) in.readDouble();
                    Point p = new Point();
                    p.setLocation(in.readDouble(), in.readDouble());
                    ClientPlayer n = new ClientPlayer(id, p);
                    if (GamePanel.player == null) {
                        GamePanel.player = n;
                    }
                    PlayersList.add(n);
                } else if (read == Protocol.PLAYER_LEFT) {
                    PlayersList.remove((int) in.readDouble());
                } else if (read == Protocol.PLAYER_UPDATE) {
                    ClientPlayer pl = PlayersList.get((int) in.readDouble());
                    if (pl != null) {
                        pl.setPoints((int) in.readDouble());
                        pl.getPoint().setLocation(in.readDouble(), in.readDouble());
                    }
                } else if (read == Protocol.FOOD_SPAWNED) {
                    int id = (int) in.readDouble();
                    Point p = new Point();
                    p.setLocation(in.readDouble(), in.readDouble());
                    Food f = new Food(id, p);
                    FoodList.add(f);
                } else if (read == Protocol.FOOD_EATED) {
                    int id = (int) in.readDouble();
                    FoodList.remove(id);
                }
                while (in.readDouble() != Protocol.END) {
                }
                listener.update();
            }
        } catch (IOException ex) {
            System.out.println("Error " + ex.getMessage());
        }
        System.out.println("ProtocolManager stopped");
    }

}
