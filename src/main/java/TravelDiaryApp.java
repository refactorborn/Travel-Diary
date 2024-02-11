import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TravelDiaryApp {

    private List<TravelDiaryEntry> travelDiaryEntries;

    public TravelDiaryApp() {
        this.travelDiaryEntries = new ArrayList<>();
    }

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

    public boolean containsLocation(String locationName) {
        for (TravelDiaryEntry entry : travelDiaryEntries) {
            if (entry.getLocationName().equals(locationName)) {
                return true;
            }
        }
        return false;
    }

    public TravelDiaryEntry getEntry(String locationName) {
        for (TravelDiaryEntry entry : travelDiaryEntries) {
            if (entry.getLocationName().equals(locationName)) {
                return entry;
            }
        }
        return null;
    }

    public List<String> getLocations() {
        List<String> locations = new ArrayList<>();
        for (TravelDiaryEntry entry : travelDiaryEntries) {
            locations.add(entry.getLocationName());
        }
        return locations;
    }

    public List<String> getImagesForLocation(String locationName) {
        TravelDiaryEntry entry = getEntry(locationName);
        if (entry != null) {
            return entry.getImages();
        }
        return Collections.emptyList();
    }

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

    public void addReview(String locationName, String review) {
        TravelDiaryEntry entry = getEntry(locationName);
        if (entry != null) {
            entry.addReview(review);
        }
    }

    public void editLocation(String locationName, String newLocationName) {
        for (TravelDiaryEntry entry : travelDiaryEntries) {
            if (entry.getLocationName().equals(locationName)) {
                entry.setLocationName(newLocationName);
                return;
            }
        }
        System.out.println("Error: Location '" + locationName + "' does not exist.");
    }

    public void deleteLocation(String locationName) {
        for (TravelDiaryEntry entry : travelDiaryEntries) {
            if (entry.getLocationName().equals(locationName)) {
                travelDiaryEntries.remove(entry);
                return;
            }
        }
        System.out.println("Error: Location '" + locationName + "' does not exist.");
    }
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
