import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TravelDiaryApp {
    private List<TravelDiaryEntry> travelDiaryEntries; // Encapsulation: Private field encapsulated within the class

    // Constructor
    public TravelDiaryApp() {
        this.travelDiaryEntries = new ArrayList<>();
    }

    // Method to add a new entry
    public void addEntry(String locationName) {
        if (containsLocation(locationName)) {
            int option = JOptionPane.showConfirmDialog(null, "Location already exists. Do you want to add to the existing entry?", "Duplicate Location", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                return;
            } else {
                return;
            }
        }
        travelDiaryEntries.add(new TravelDiaryEntry(locationName));
    }

    // Method to check if a location already exists
    public boolean containsLocation(String locationName) {
        for (TravelDiaryEntry entry : travelDiaryEntries) {
            if (entry.getLocationName().equals(locationName)) {
                return true;
            }
        }
        return false;
    }

    // Method to retrieve a diary entry by location name
    public TravelDiaryEntry getEntry(String locationName) {
        for (TravelDiaryEntry entry : travelDiaryEntries) {
            if (entry.getLocationName().equals(locationName)) {
                return entry;
            }
        }
        return null;
    }

    // Method to retrieve a list of all location names
    public List<String> getLocations() {
        List<String> locations = new ArrayList<>();
        for (TravelDiaryEntry entry : travelDiaryEntries) {
            locations.add(entry.getLocationName());
        }
        return locations;
    }

    // Method to retrieve images for a specific location
    public List<String> getImagesForLocation(String locationName) {
        TravelDiaryEntry entry = getEntry(locationName);
        if (entry != null) {
            return entry.getImages();
        }
        return Collections.emptyList();
    }

    // Method to add an image to a location
    public void addImage(String locationName, String imageUrl, String category) {
        TravelDiaryEntry entry = getEntry(locationName);
        if (entry != null) {
            if (!entry.getImages().contains(imageUrl)) {
                entry.addImage(imageUrl, category);
            } else {
                JOptionPane.showMessageDialog(null, "This image already exists for the location.");
            }
        }
    }

    // Method to add a review to a location
    public void addReview(String locationName, String review) {
        TravelDiaryEntry entry = getEntry(locationName);
        if (entry != null) {
            entry.addReview(review);
        }
    }

    // Method to edit the name of a location
    public void editLocation(String locationName, String newLocationName) {
        TravelDiaryEntry entry = getEntry(locationName);
        if (entry != null) {
            entry.setLocationName(newLocationName);
        } else {
            System.out.println("Error: Location '" + locationName + "' does not exist.");
        }
    }

    // Method to delete a location entry
    public void deleteLocation(String locationName) {
        TravelDiaryEntry entry = getEntry(locationName);
        if (entry != null) {
            travelDiaryEntries.remove(entry);
        } else {
            System.out.println("Error: Location '" + locationName + "' does not exist.");
        }
    }

    // Method to perform bubble sort on a list of locations
    public void bubbleSort(List<String> locations) {
        int n = locations.size();
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (locations.get(j).compareTo(locations.get(j + 1)) > 0) {
                    Collections.swap(locations, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }
}
