import java.util.*;

/**
 * ==========================================================
 * UC11: Concurrent Booking Simulation
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

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {
    private int single = 3;
    private int doubleRoom = 2;
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

    public void display() {
        System.out.println("\nRemaining Inventory:");
        System.out.println("Single: " + single);
        System.out.println("Double: " + doubleRoom);
        System.out.println("Suite: " + suite);
    }
}

class RoomAllocationService {

    private Map<String, Integer> counters = new HashMap<>();

    public String generateRoomId(String type) {
        int count = counters.getOrDefault(type, 0) + 1;
        counters.put(type, count);
        return type + "-" + count;
    }

    public void allocateRoom(Reservation r, RoomInventory inventory) {

        String type = r.getRoomType();

        if (inventory.getAvailability(type) <= 0) return;

        String roomId = generateRoomId(type);
        inventory.reduce(type);

        System.out.println("Booking confirmed for Guest: "
                + r.getGuestName()
                + ", Room ID: " + roomId);
    }
}

class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService) {

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            // Critical section 1 (queue access)
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) break;
                reservation = bookingQueue.getNextRequest();
            }

            // Critical section 2 (inventory + allocation)
            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation");

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        // Add requests
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Double"));
        queue.addRequest(new Reservation("Kural", "Suite"));
        queue.addRequest(new Reservation("Subha", "Single"));

        // Create threads
        Thread t1 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.display();
    }
}