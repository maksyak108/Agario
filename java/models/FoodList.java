package models;
public class FoodList {

    private static Food food[] = new Food[Config.MAX_FOOD];

    public static Food[] getFood() {
        return food;
    }

    public static int getFreeIndex() {
        for (int i = 0; i < food.length; i++) {
            if (food[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public static void remove(int id) {
        if (id >= 0 && id < food.length) {
            food[id] = null;
        }
    }

    public static void add(Food f) {
        int id = f.getId();
        if (id >= 0 && id < food.length) {
            food[id] = f;
        }
    }

}
