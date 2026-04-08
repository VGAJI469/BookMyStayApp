
import java.util.*;

class Room {
    private String type;
    private double price;
    private List<String> amenities;

    public Room(String type, double price, List<String> amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getAmenities() {
        return amenities;
    }
}

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
}

class SearchService {
    private RoomInventory inventory;
    private Map<String, Room> roomCatalog;

    public SearchService(RoomInventory inventory, Map<String, Room> roomCatalog) {
        this.inventory = inventory;
        this.roomCatalog = roomCatalog;
    }

    public void searchAvailableRooms() {
        Map<String, Integer> availability = inventory.getAllAvailability();

        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            if (entry.getValue() > 0) {
                Room room = roomCatalog.get(entry.getKey());
                if (room != null) {
                    System.out.println(room.getType() + " " + room.getPrice() + " " + room.getAmenities());
                }
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        inventory.addRoomType("Single", 10);
        inventory.addRoomType("Double", 0);
        inventory.addRoomType("Suite", 3);

        Map<String, Room> roomCatalog = new HashMap<>();

        roomCatalog.put("Single", new Room("Single", 1000, Arrays.asList("WiFi", "AC")));
        roomCatalog.put("Double", new Room("Double", 2000, Arrays.asList("WiFi", "AC", "TV")));
        roomCatalog.put("Suite", new Room("Suite", 5000, Arrays.asList("WiFi", "AC", "TV", "MiniBar")));

        SearchService searchService = new SearchService(inventory, roomCatalog);

        searchService.searchAvailableRooms();
    }
}
