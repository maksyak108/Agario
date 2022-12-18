package server;

import java.awt.Point;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import models.Config;
import models.Food;
import models.Protocol;

public class Server extends Thread {

    private ServerSocket server;

    public static void playerJoined(ServerPlayer p) {
        ThreadsList.broadcast(new double[]{
            Protocol.PLAYER_JOINED,
            p.getId(),
            p.getX(),
            p.getY(),
            Protocol.END
        });
    }

    public static void playerUpdate(ServerPlayer p) {
        ThreadsList.broadcast(new double[]{
            Protocol.PLAYER_UPDATE,
            p.getId(),
            p.getPoints(),
            p.getX(),
            p.getY(),
            Protocol.END
        });
    }

    public static void playerLeft(ServerPlayer p) {
        ThreadsList.broadcast(new double[]{
            Protocol.PLAYER_LEFT,
            p.getId(),
            Protocol.END
        });
    }
    
    public static void foodSpawned(Food f) {
        ThreadsList.broadcast(new double[]{
            Protocol.FOOD_SPAWNED,
            f.getId(),
            f.getX(),
            f.getY(),
            Protocol.END
        });
    }
    
    public static void foodEated(Food f)
    {
        ThreadsList.broadcast(new double[]{
            Protocol.FOOD_EATED,
            f.getId(),
            Protocol.END
        });
    }

    public static Point getRandomPoint(int radius) {
        return new Point(
                (int) (Math.random() * (Config.FIELD_WIDTH - radius - radius)) + radius,
                (int) (Math.random() * (Config.FIELD_HEIGHT - radius - radius)) + radius
        );
    }

    public Server(int port) throws IOException {
        this.server = new ServerSocket(port);
        FoodManager fm = new FoodManager();
        fm.start();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            System.out.println("Waiting for a user...");
            try {
                Socket client = server.accept();
                System.out.println("Client accepted");
                int index = ThreadsList.getFreeIndex();
                if (index != -1) {
                    System.out.println("ServerThread started");
                    ServerThread thread = new ServerThread(client, index);
                    thread.start();
                    ThreadsList.add(thread);
                } else {
                    System.out.println("The server is full, disconnecting client");
                    client.getOutputStream().write(Protocol.SERVER_FULL);
                    client.close();
                }
            } catch (IOException ex) {
                System.out.println("Error accepting user: " + ex);
            }
        }
        System.out.println("Stopped server thread.");
    }
    
    public static void main(String[] args) throws IOException {
        Server s = new Server(5555);
        s.start();
    }
}
