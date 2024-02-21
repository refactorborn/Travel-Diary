import java.util.ArrayList;
import java.util.List;

// Concrete class representing a travel diary entry
public class TravelDiaryEntry extends DiaryEntry {
    private List<String> categories;
    private List<List<String>> categorizedImages;

    public TravelDiaryEntry(String locationName) {
        super(locationName);
        this.categories = new ArrayList<>();
        this.categorizedImages = new ArrayList<>();
    }

    @Override
    public void addImage(String imageUrl, String category) {
        // Check if the image already exists
        if (getImages().contains(imageUrl)) {
            System.out.println("This image is already added.");
            return;
        }

        getImages().add(imageUrl);

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

    public List<String> getCategories() {
        return new ArrayList<>(categories); // Return a copy to maintain encapsulation
    }
}
