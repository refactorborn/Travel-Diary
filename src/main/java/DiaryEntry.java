import java.util.ArrayList;
import java.util.List;

// Base class representing a diary entry
public abstract class DiaryEntry {
    private String locationName;
    private List<String> images;
    private List<String> reviews;

    public DiaryEntry(String locationName) {
        this.locationName = locationName;
        this.images = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<String> getImages() {
        return new ArrayList<>(images); // Return a copy to maintain encapsulation
    }

    public List<String> getReviews() {
        return new ArrayList<>(reviews); // Return a copy to maintain encapsulation
    }

    public abstract void addImage(String imageUrl, String category);

    public void addReview(String review) {
        reviews.add(review);
    }
}
