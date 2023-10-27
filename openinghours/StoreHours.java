package openinghours;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class StoreHours {
    private String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private String[] openingHours = new String[7];

    public StoreHours() {
        setDefaultOpeningHours();
        loadOpeningHoursFromFile();
    }

    private void setDefaultOpeningHours() {
        openingHours[0] = "10:00 AM - 6:00 PM";  // Monday
        openingHours[1] = "10:00 AM - 6:00 PM";  // Tuesday
        openingHours[2] = "10:00 AM - 6:00 PM";  // Wednesday
        openingHours[3] = "10:00 AM - 6:00 PM";  // Thursday
        openingHours[4] = "10:00 AM - 6:00 PM";  // Friday
        openingHours[5] = "10:00 AM - 6:00 PM";  // Saturday
        openingHours[6] = "Closed";              // Sunday
    }


        public void displayStoreHours () {
            for (int i = 0; i < daysOfWeek.length; i++) {
                System.out.println(daysOfWeek[i] + ": " + openingHours[i]);
            }
        }

        public void setOpeningHours ( int dayIndex, String hours){
            if (dayIndex >= 0 && dayIndex < openingHours.length) {
                openingHours[dayIndex] = hours;
                saveOpeningHoursToFile();
            } else {
                System.out.println("Invalid day index.");
            }
        }

        private void loadOpeningHoursFromFile () {
            try {
                Path filePath = Paths.get("opening  _hours.txt");
                if (Files.exists(filePath)) {
                    List<String> lines = Files.readAllLines(filePath);
                    for (int i = 0; i < lines.size() && i < openingHours.length; i++) {
                        openingHours[i] = lines.get(i);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void saveOpeningHoursToFile () {
            try {
                Path filePath = Paths.get("opening_hours.txt");
                Files.write(filePath, List.of(openingHours), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}