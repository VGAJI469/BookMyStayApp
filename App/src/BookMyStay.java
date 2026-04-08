import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;
    private int quantity;

    public Reservation(String guestName, String roomType, int quantity) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.quantity = quantity;
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

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return queue.peek();
    }

    public Reservation processNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void displayQueue() {
        for (Reservation r : queue) {
            System.out.println(r.getGuestName() + " " + r.getRoomType() + " " + r.getQuantity());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        BookingRequestQueue requestQueue = new BookingRequestQueue();

        requestQueue.addRequest(new Reservation("Amit", "Single", 1));
        requestQueue.addRequest(new Reservation("Priya", "Double", 2));
        requestQueue.addRequest(new Reservation("Rahul", "Suite", 1));

        requestQueue.displayQueue();

        Reservation next = requestQueue.processNextRequest();
        System.out.println(next.getGuestName() + " " + next.getRoomType());

        requestQueue.displayQueue();
    }
}