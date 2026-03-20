
class RoomInventory {

    private int singleRoomAvailable = 5;
    private int doubleRoomAvailable = 3;
    private int suiteRoomAvailable = 2;

    public int getSingleRoomAvailable() {
        return singleRoomAvailable;
    }

    public int getDoubleRoomAvailable() {
        return doubleRoomAvailable;
    }

    public int getSuiteRoomAvailable() {
        return suiteRoomAvailable;
    }
}


abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}


/* Room Types */

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}


class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        System.out.println("Room Search\n");

        // Single Room
        if (inventory.getSingleRoomAvailable() > 0) {
            System.out.println("Single Room:");
            singleRoom.displayRoomDetails();
            System.out.println("Available: " + inventory.getSingleRoomAvailable());
            System.out.println();
        }

        // Double Room
        if (inventory.getDoubleRoomAvailable() > 0) {
            System.out.println("Double Room:");
            doubleRoom.displayRoomDetails();
            System.out.println("Available: " + inventory.getDoubleRoomAvailable());
            System.out.println();
        }

        // Suite Room
        if (inventory.getSuiteRoomAvailable() > 0) {
            System.out.println("Suite Room:");
            suiteRoom.displayRoomDetails();
            System.out.println("Available: " + inventory.getSuiteRoomAvailable());
        }
    }
}


public class UseCase4RoomSearch {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        RoomSearchService service = new RoomSearchService();

        service.searchAvailableRooms(inventory, single, doubleRoom, suite);
    }
}