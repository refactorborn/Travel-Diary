import java.util.ArrayList;
import java.util.List;

public class TravelDiaryEntry {

    private final List<String> images;
    private final List<String> reviews;
    private final List<String> categories; // List to store categories
    private final List<List<String>> categorizedImages; // List of lists to store categorized images
    private String locationName;

    public TravelDiaryEntry(String locationName) {
        this.locationName = locationName;
        this.images = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.categorizedImages = new ArrayList<>();
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<String> getImages() {
        return images;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews.addAll(reviews);
    }

    public void addImage(String imageUrl, String category) {
        // Check if the image already exists
        if (images.contains(imageUrl)) {
            System.out.println("This image is already added.");
            return;
        }

        images.add(imageUrl);

        // Check if the category exists, if not add it
        int categoryIndex = categories.indexOf(category);
        if (categoryIndex == -1) {
            categories.add(category);
            categorizedImages.add(new ArrayList<>());
            categoryIndex = categories.size() - 1;
        }

        // Add image to the specified category
        categorizedImages.get(categoryIndex).add(imageUrl);
    }

    public void addReview(String review) {
        reviews.add(review);
    }

    public List<String> getCategories() {
        return categories;
    }
}
