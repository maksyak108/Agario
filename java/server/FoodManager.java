package server;

import models.Config;
import models.Food;
import models.FoodList;

public class FoodManager extends Thread {

    public FoodManager() {
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        System.out.println("FoodManager - Started at " + start);
        while (!isInterrupted()) {
            int index = FoodList.getFreeIndex();
            if (index != -1 && System.currentTimeMillis() - start > Config.FOOD_DELAY) {
                Food f = new Food(index, Server.getRandomPoint(Config.FOOD_RADIUS));
                FoodList.add(f);
                Server.foodSpawned(f);
                start = System.currentTimeMillis();
            }
        }
    }

}
