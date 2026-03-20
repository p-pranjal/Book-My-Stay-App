import java.util.*;


class RoomInventory {
    private int single = 5;

    public void increase(String roomType) {
        if (roomType.equals("Single")) {
            single++;
        }
    }

    public int getSingle() {
        return single;
    }
}

class CancellationService {

    // Stack for rollback (LIFO)
    private Stack<String> releasedRoomIds;

    // Map reservationId -> roomType
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    // Register confirmed booking
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    // Cancel booking
    public void cancelBooking(String reservationId, RoomInventory inventory) {

        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Invalid reservation ID.");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);

        // Push to stack (rollback tracking)
        releasedRoomIds.push(reservationId);

        // Restore inventory
        inventory.increase(roomType);

        // Remove from active bookings
        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    // Show rollback history
    public void showRollbackHistory() {

        System.out.println("\nRollback History (Most Recent First):");

        for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + releasedRoomIds.get(i));
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation");

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        // Register booking (simulate confirmed booking)
        service.registerBooking("Single-1", "Single");

        // Cancel booking
        service.cancelBooking("Single-1", inventory);

        // Show rollback history
        service.showRollbackHistory();

        // Show updated inventory
        System.out.println("\nUpdated Single Room Availability: " + inventory.getSingle());
    }
}