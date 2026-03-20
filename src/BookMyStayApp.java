import java.util.*;

/**
 * ==========================================================
 * UC6: Reservation Confirmation & Room Allocation
 * ==========================================================
 */

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class RoomInventory {
    private int single = 2;
    private int doubleRoom = 1;
    private int suite = 1;

    public int getAvailability(String type) {
        if (type.equals("Single")) return single;
        if (type.equals("Double")) return doubleRoom;
        if (type.equals("Suite")) return suite;
        return 0;
    }

    public void reduce(String type) {
        if (type.equals("Single")) single--;
        else if (type.equals("Double")) doubleRoom--;
        else if (type.equals("Suite")) suite--;
    }
}

class RoomAllocationService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> assignedRoomsByType = new HashMap<>();

    public RoomAllocationService() {
        assignedRoomsByType.put("Single", new HashSet<>());
        assignedRoomsByType.put("Double", new HashSet<>());
        assignedRoomsByType.put("Suite", new HashSet<>());
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String type = reservation.getRoomType();

        if (inventory.getAvailability(type) <= 0) {
            System.out.println("No rooms available for " + reservation.getGuestName());
            return;
        }

        String roomId = generateRoomId(type);

        allocatedRoomIds.add(roomId);
        assignedRoomsByType.get(type).add(roomId);

        inventory.reduce(type);

        System.out.println("Booking confirmed for Guest: "
                + reservation.getGuestName()
                + ", Room ID: " + roomId);
    }

    private String generateRoomId(String roomType) {
        int count = assignedRoomsByType.get(roomType).size() + 1;
        return roomType + "-" + count;
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Single");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        service.allocateRoom(r1, inventory);
        service.allocateRoom(r2, inventory);
        service.allocateRoom(r3, inventory);
    }
}