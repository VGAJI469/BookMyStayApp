import java.util.*;
class Reservation {
    private String id;
    private String guestName;
    private String roomType;
    private int quantity;

    public Reservation(String id, String guestName, String roomType, int quantity) {
        this.id = id;
        this.guestName = guestName;
        this.roomType = roomType;
        this.quantity = quantity;
    }
    public String getId() {
        return id;
    }
    public String getGuestName() {
        return guestName;
    }
    public String getRoomType() {
        return roomType;
    }

    public int getQuantity() {
        return quantity;
    }
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrease(String type, int count) {
        inventory.put(type, getAvailability(type) - count);
    }

    public void display() {
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void add(Reservation r) {
        queue.offer(r);
    }

    public Reservation poll() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class BookingService {
    private RoomInventory inventory;
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> roomAllocations;
    private int counter;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocations = new HashMap<>();
        this.counter = 1;
    }

    private String generateRoomId(String roomType) {
        String id;
        do {
            id = roomType.substring(0, 1).toUpperCase() + counter++;
        } while (allocatedRoomIds.contains(id));
        allocatedRoomIds.add(id);
        return id;
    }

    public void processQueue(BookingRequestQueue queue) {
        while (!queue.isEmpty()) {
            Reservation r = queue.poll();
            int available = inventory.getAvailability(r.getRoomType());

            if (available >= r.getQuantity()) {
                for (int i = 0; i < r.getQuantity(); i++) {
                    String roomId = generateRoomId(r.getRoomType());
                    roomAllocations
                            .computeIfAbsent(r.getRoomType(), k -> new HashSet<>())
                            .add(roomId);
                    System.out.println(r.getGuestName() + " " + roomId);
                }
                inventory.decrease(r.getRoomType(), r.getQuantity());
            } else {
                System.out.println("Failed " + r.getGuestName());
            }
        }
    }

    public void displayAllocations() {
        for (Map.Entry<String, Set<String>> e : roomAllocations.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 1);

        BookingRequestQueue queue = new BookingRequestQueue();

        queue.add(new Reservation("R1", "Amit", "Single", 1));
        queue.add(new Reservation("R2", "Priya", "Single", 1));
        queue.add(new Reservation("R3", "Rahul", "Single", 1));
        queue.add(new Reservation("R4", "Neha", "Double", 1));

        BookingService bookingService = new BookingService(inventory);

        bookingService.processQueue(queue);

        inventory.display();

        bookingService.displayAllocations();
    }
}