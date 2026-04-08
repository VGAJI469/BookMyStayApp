import java.util.HashMap;
import java.util.Map;

public class BookMyStayApp {

    public static void main(String[] args) {

        // Welcome Message (UC1)
        System.out.println("=======================================");
        System.out.println("        WELCOME TO BOOK MY STAY        ");
        System.out.println("=======================================");
        System.out.println("Find and book the best rooms easily.");
        System.out.println("Your comfortable stay starts here!");
        System.out.println("=======================================");

        // UC2: Room Types and Static Availability
        Map<String, Integer> roomInventory = new HashMap<>();

        roomInventory.put("Single Room", 10);
        roomInventory.put("Double Room", 8);
        roomInventory.put("Deluxe Room", 5);
        roomInventory.put("Suite", 2);

        System.out.println("\nAvailable Room Types:");
        System.out.println("---------------------------");

        for (Map.Entry<String, Integer> room : roomInventory.entrySet()) {
            System.out.println(room.getKey() + " : " + room.getValue() + " rooms available");
        }

        System.out.println("---------------------------");
        System.out.println("Please select a room type to continue booking.");
    }
}