import java.util.HashMap;
import java.util.Map;

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return new HashMap<>(inventory);
    }

    public void increaseAvailability(String roomType, int count) {
        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + count);
    }

    public boolean decreaseAvailability(String roomType, int count) {
        int current = inventory.getOrDefault(roomType, 0);
        if (current >= count) {
            inventory.put(roomType, current - count);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 5);
        inventory.addRoomType("Suite", 2);

        inventory.displayInventory();

        inventory.decreaseAvailability("Single", 2);
        inventory.increaseAvailability("Double", 1);

        System.out.println(inventory.getAvailability("Single"));

        inventory.displayInventory();
    }
}