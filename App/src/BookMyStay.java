import java.util.*;
import java.util.concurrent.*;

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

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public int getQuantity() { return quantity; }
}

class RoomInventory {
    private final Map<String, Integer> inventory = new ConcurrentHashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public synchronized boolean bookRooms(String type, int count) {
        int available = inventory.getOrDefault(type, 0);

        if (available >= count) {
            inventory.put(type, available - count);
            return true;
        }
        return false;
    }

    public void display() {
        inventory.forEach((k, v) -> System.out.println(k + " " + v));
    }
}

class BookingService {
    private RoomInventory inventory;
    private Set<String> allocatedRoomIds = ConcurrentHashMap.newKeySet();
    private Map<String, Set<String>> roomAllocations = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(1);

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    private String generateRoomId(String roomType) {
        String id;
        do {
            id = roomType.substring(0, 1).toUpperCase() + counter.getAndIncrement();
        } while (allocatedRoomIds.contains(id));

        allocatedRoomIds.add(id);
        return id;
    }

    public void processReservation(Reservation r) {
        boolean success = inventory.bookRooms(r.getRoomType(), r.getQuantity());

        if (success) {
            for (int i = 0; i < r.getQuantity(); i++) {
                String roomId = generateRoomId(r.getRoomType());

                roomAllocations
                        .computeIfAbsent(r.getRoomType(), k -> ConcurrentHashMap.newKeySet())
                        .add(roomId);

                System.out.println(Thread.currentThread().getName() +
                        " -> " + r.getGuestName() + " booked " + roomId);
            }
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " -> Failed " + r.getGuestName());
        }
    }

    public void displayAllocations() {
        roomAllocations.forEach((k, v) -> System.out.println(k + " " + v));
    }
}

public class BookMyStayApp {
    public static void main(String[] args) throws InterruptedException {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 1);

        List<Reservation> requests = Arrays.asList(
                new Reservation("R1", "Amit", "Single", 1),
                new Reservation("R2", "Priya", "Single", 1),
                new Reservation("R3", "Rahul", "Single", 1),
                new Reservation("R4", "Neha", "Double", 1)
        );

        BookingService bookingService = new BookingService(inventory);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (Reservation r : requests) {
            executor.submit(() -> bookingService.processReservation(r));
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("\nFinal Inventory:");
        inventory.display();

        System.out.println("\nAllocations:");
        bookingService.displayAllocations();
    }
}