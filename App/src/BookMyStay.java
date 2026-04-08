
import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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

    public boolean hasRoomType(String type) {
        return inventory.containsKey(type);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrease(String type, int count) throws InvalidBookingException {
        int current = getAvailability(type);
        if (current - count < 0) {
            throw new InvalidBookingException("Insufficient rooms for " + type);
        }
        inventory.put(type, current - count);
    }

    public void display() {
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
    }
}

class InvalidBookingValidator {
    public static void validate(Reservation r, RoomInventory inventory) throws InvalidBookingException {
        if (r.getRoomType() == null || r.getRoomType().isEmpty()) {
            throw new InvalidBookingException("Invalid room type");
        }
        if (!inventory.hasRoomType(r.getRoomType())) {
            throw new InvalidBookingException("Room type does not exist: " + r.getRoomType());
        }
        if (r.getQuantity() <= 0) {
            throw new InvalidBookingException("Invalid quantity");
        }
        if (inventory.getAvailability(r.getRoomType()) < r.getQuantity()) {
            throw new InvalidBookingException("Not enough availability for " + r.getRoomType());
        }
    }
}

class BookingService {
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void confirmBooking(Reservation r) {
        try {
            InvalidBookingValidator.validate(r, inventory);
            inventory.decrease(r.getRoomType(), r.getQuantity());
            System.out.println("Confirmed " + r.getGuestName() + " " + r.getRoomType() + " " + r.getQuantity());
        } catch (InvalidBookingException e) {
            System.out.println("Failed " + r.getGuestName() + " " + e.getMessage());
        }
    }
}

public class BookMyStayApp{
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 1);

        BookingService bookingService = new BookingService(inventory);

        bookingService.confirmBooking(new Reservation("R1", "Amit", "Single", 1));
        bookingService.confirmBooking(new Reservation("R2", "Priya", "Single", 3));
        bookingService.confirmBooking(new Reservation("R3", "Rahul", "Suite", 1));
        bookingService.confirmBooking(new Reservation("R4", "Neha", "", 1));

        inventory.display();
    }
}
