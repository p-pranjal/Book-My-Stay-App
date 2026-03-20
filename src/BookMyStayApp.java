import java.io.*;

/**
 * ==========================================================
 * UC12: Data Persistence & System Recovery
 * ==========================================================
 */

class RoomInventory {

    private int single = 5;
    private int doubleRoom = 3;
    private int suite = 2;

    public int getSingle() { return single; }
    public int getDoubleRoom() { return doubleRoom; }
    public int getSuite() { return suite; }

    public void setSingle(int single) { this.single = single; }
    public void setDoubleRoom(int doubleRoom) { this.doubleRoom = doubleRoom; }
    public void setSuite(int suite) { this.suite = suite; }

    public void display() {
        System.out.println("\nCurrent Inventory:");
        System.out.println("Single: " + single);
        System.out.println("Double: " + doubleRoom);
        System.out.println("Suite: " + suite);
    }
}

class FilePersistenceService {

    public void saveInventory(RoomInventory inventory, String filePath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("Single=" + inventory.getSingle());
            writer.newLine();

            writer.write("Double=" + inventory.getDoubleRoom());
            writer.newLine();

            writer.write("Suite=" + inventory.getSuite());
            writer.newLine();

            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    public void loadInventory(RoomInventory inventory, String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("=");

                if (parts.length != 2) continue;

                String type = parts[0];
                int value = Integer.parseInt(parts[1]);

                if (type.equals("Single")) inventory.setSingle(value);
                else if (type.equals("Double")) inventory.setDoubleRoom(value);
                else if (type.equals("Suite")) inventory.setSuite(value);
            }

            System.out.println("Inventory loaded successfully.");

        } catch (Exception e) {
            System.out.println("Error reading inventory. Starting fresh.");
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService service = new FilePersistenceService();

        String filePath = "inventory.txt";

        // Load previous state
        service.loadInventory(inventory, filePath);

        // Display inventory
        inventory.display();

        // Save current state
        service.saveInventory(inventory, filePath);
    }
}