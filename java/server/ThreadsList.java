package server;

import java.io.IOException;
import models.Config;

public class ThreadsList {

    private static ServerThread threads[] = new ServerThread[Config.MAX_PLAYERS];

    public static ServerThread[] getThreads() {
        return threads;
    }

    public static int getFreeIndex() {
        for (int i = 0; i < threads.length; i++) {
            if (threads[i] == null) {
                return i;
            }
        }
        return -1;
    }
    
    public static ServerThread get(int id) {
        if (id >= 0 && id < threads.length) {
            return threads[id];
        }
        return null;
    }

    public static void remove(ServerThread thread) {
        threads[thread.getIndex()] = null;
    }

    public static void add(ServerThread thread) {
        threads[thread.getIndex()] = thread;
    }

    public static synchronized void broadcast(double[] array) {
        for (int i = 0; i < threads.length; i++) {
            if (threads[i] != null) {
                try {
                    threads[i].send(array);
                } catch (IOException ex) {
                    System.out.println("Error sending info");
                }
            }
        }
    }
}
