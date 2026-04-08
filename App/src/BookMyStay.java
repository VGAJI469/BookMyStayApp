import java.io.*;
import java.util.*;

class Reservation implements Serializable {
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

class RoomInventory implements Serializable {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void display() {
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
    }
}

class BookingHistory implements Serializable {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void add(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAll() {
        return history;
    }

    public void display() {
        for (Reservation r : history) {
            System.out.println(r.getId() + " " + r.getGuestName() + " " + r.getRoomType() + " " + r.getQuantity());
        }
    }
}

class SystemState implements Serializable {
    public RoomInventory inventory;
    public BookingHistory history;

    public SystemState(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }
}

class PersistenceService {
    private String fileName;

    public PersistenceService(String fileName) {
        this.fileName = fileName;
    }

    public void save(SystemState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(state);
        } catch (Exception e) {
            System.out.println("Save failed");
        }
    }

    public SystemState load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (SystemState) in.readObject();
        } catch (Exception e) {
            System.out.println("Load failed, starting fresh");
            return new SystemState(new RoomInventory(), new BookingHistory());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService("system.dat");

        SystemState state = persistence.load();

        RoomInventory inventory = state.inventory;
        BookingHistory history = state.history;

        if (inventory.getInventory().isEmpty()) {
            inventory.addRoomType("Single", 2);
            inventory.addRoomType("Double", 1);
        }

        history.add(new Reservation("R1", "Amit", "Single", 1));
        history.add(new Reservation("R2", "Priya", "Double", 1));

        inventory.display();
        history.display();

        persistence.save(new SystemState(inventory, history));
    }
}