import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.MalformedURLException;

public class MenuPage {

    private static final int IMAGE_DISPLAY_WIDTH = 150;
    private static final int IMAGE_DISPLAY_HEIGHT = 100;
    private final JFrame mainFrame;
    private final JPanel mainPanel;
    private final TravelDiaryApp travelDiaryApp;
    private JPanel locationsPanel;

    public MenuPage(JFrame mainFrame, TravelDiaryApp travelDiaryApp) {
        this.mainFrame = mainFrame;
        this.mainPanel = new JPanel(new BorderLayout());
        this.travelDiaryApp = travelDiaryApp;
    }

    public void displayAllLocations() {
        List<String> locations = travelDiaryApp.getLocations();
        if (!locations.isEmpty()) {
            StringBuilder allLocations = new StringBuilder("All Locations:\n");
            for (String location : locations) {
                allLocations.append("- ").append(location).append("\n");
            }
            JOptionPane.showMessageDialog(mainFrame, allLocations.toString());
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No locations found!");
        }
    }

    public void initialize() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel menuLabel = new JLabel("Menu Page");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 24));
        menuLabel.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(menuLabel, BorderLayout.NORTH);

        createSidePanel();

        createLocationsPanel();

        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void createSidePanel() {
        JPanel sidePanel = new JPanel(new GridLayout(5, 1));
        JButton addLocationButton = new JButton("Add Location");
        addLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String locationName = JOptionPane.showInputDialog(mainFrame, "Enter location name:");
                if (locationName != null && !locationName.trim().isEmpty()) {
                    travelDiaryApp.addEntry(locationName);
                    updateLocationsPanel();
                }
            }
        });

        JButton displayLocationsButton = new JButton("Display All Locations");
        displayLocationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllLocations();
            }
        });

        JButton sortLocationsButton = new JButton("Sort Locations");
        sortLocationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortLocations();
            }
        });

        JButton searchLocationButton = new JButton("Search Location");
        searchLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Search by Name", "Search by Category"};
                int choice = JOptionPane.showOptionDialog(mainFrame, "Select search type:", "Search Location", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (choice == 0) {
                    String searchTerm = JOptionPane.showInputDialog(mainFrame, "Enter location name to search:");
                    if (searchTerm != null) {
                        searchLocationByName(searchTerm);
                    }
                } else if (choice == 1) {
                    String category = JOptionPane.showInputDialog(mainFrame, "Enter category to search:");
                    if (category != null) {
                        searchLocationByCategory(category);
                    }
                }
            }
        });


        JButton backToMainButton = new JButton("Back to Main Menu");
        backToMainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainPage();
            }
        });

        sidePanel.add(addLocationButton);
        sidePanel.add(displayLocationsButton);
        sidePanel.add(sortLocationsButton);
        sidePanel.add(searchLocationButton);
        sidePanel.add(backToMainButton);

        mainPanel.add(sidePanel, BorderLayout.WEST);
    }

    private void createLocationsPanel() {
        List<String> locations = travelDiaryApp.getLocations();

        locationsPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 columns, variable rows
        for (String location : locations) {
            JPanel locationPanel = createLocationPanel(location);
            locationsPanel.add(locationPanel);
        }

        JScrollPane scrollPane = new JScrollPane(locationsPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createLocationPanel(String location) {
        JPanel locationPanel = new JPanel(new BorderLayout());
        JButton locationButton = new JButton(location);
        locationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayDetails(location);
            }
        });
        locationPanel.add(locationButton, BorderLayout.CENTER);
        return locationPanel;
    }

    private void updateLocationsPanel() {
        List<String> locations = travelDiaryApp.getLocations();
        locationsPanel.removeAll();
        for (String location : locations) {
            JPanel locationPanel = createLocationPanel(location);
            locationsPanel.add(locationPanel);
        }
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void addImage(String locationName) {
        String imageUrl = JOptionPane.showInputDialog(mainFrame, "Enter image URL for " + locationName + ":");
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            String[] categories = {"People", "Artifacts", "Food"}; // Add more categories as needed
            String selectedCategory = (String) JOptionPane.showInputDialog(mainFrame, "Select image category:", "Image Category", JOptionPane.QUESTION_MESSAGE, null, categories, categories[0]);
            if (selectedCategory != null) {
                travelDiaryApp.addImage(locationName, imageUrl, selectedCategory);
                updateLocationsPanel(); // Update the locations panel to reflect changes
            }
        }
    }

    private void addReview(String locationName) {
        String review = JOptionPane.showInputDialog(mainFrame, "Enter review for " + locationName + ":");
        if (review != null && !review.trim().isEmpty()) {
            travelDiaryApp.addReview(locationName, review);
            updateLocationsPanel(); // Update the locations panel to reflect changes
        }
    }

    private void displayDetails(String locationName) {
        TravelDiaryEntry entry = travelDiaryApp.getEntry(locationName);

        if (entry != null) {
            JDialog detailsDialog = new JDialog(mainFrame, "Details for " + locationName, true);
            detailsDialog.setLayout(new BorderLayout());

            // Display images
            JPanel imagesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            displayImages(entry.getImages(), imagesPanel); // Display existing images

            // Panel for displaying reviews
            JPanel reviewsPanel = new JPanel(new BorderLayout());
            JTextArea reviewsArea = new JTextArea();
            reviewsArea.setEditable(false);
            reviewsArea.setLineWrap(true);
            reviewsArea.setWrapStyleWord(true);
            JScrollPane reviewsScrollPane = new JScrollPane(reviewsArea);
            reviewsPanel.add(new JLabel("Reviews:"), BorderLayout.NORTH);
            reviewsPanel.add(reviewsScrollPane, BorderLayout.CENTER);

            // Populate reviews
            for (String review : entry.getReviews()) {
                reviewsArea.append("- " + review + "\n");
            }

            // Button to add image
            JButton addImageButton = new JButton("Add Image");
            addImageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addImage(locationName);
                    imagesPanel.removeAll();
                    displayImages(entry.getImages(), imagesPanel);
                    detailsDialog.pack();
                }
            });

            // Button to add review
            JButton addReviewButton = new JButton("Add Review");
            addReviewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addReview(locationName);
                    reviewsArea.setText("");
                    for (String review : entry.getReviews()) {
                        reviewsArea.append("- " + review + "\n");
                    }
                }
            });

            // Button to edit location
            JButton editLocationButton = new JButton("Edit Location");
            editLocationButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newLocationName = JOptionPane.showInputDialog(mainFrame, "Enter new location name:");
                    if (newLocationName != null && !newLocationName.trim().isEmpty()) {
                        editLocation(locationName, newLocationName);
                        detailsDialog.dispose();
                    }
                }
            });

            // Button to delete location
            JButton deleteLocationButton = new JButton("Delete Location");
            deleteLocationButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int choice = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to delete this location?", "Delete Location", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        deleteLocation(locationName);
                        detailsDialog.dispose();
                    }
                }
            });

            // Button to close the dialog
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    detailsDialog.dispose();
                }
            });

            // Panel for buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.add(addImageButton);
            buttonPanel.add(addReviewButton);
            buttonPanel.add(editLocationButton);
            buttonPanel.add(deleteLocationButton);
            buttonPanel.add(closeButton);

            // Panel for details
            JPanel detailsPanel = new JPanel(new BorderLayout());
            detailsPanel.add(imagesPanel, BorderLayout.NORTH);
            detailsPanel.add(reviewsPanel, BorderLayout.CENTER);

            // Add components to the dialog
            detailsDialog.add(detailsPanel, BorderLayout.CENTER);
            detailsDialog.add(buttonPanel, BorderLayout.SOUTH);

            detailsDialog.setSize(600, 400);
            detailsDialog.setLocationRelativeTo(mainFrame);
            detailsDialog.setVisible(true);
        }
    }

    private void displayImages(List<String> imageUrls, JPanel imagesPanel) {
        for (String imageUrl : imageUrls) {
            try {
                URI uri = new URI(imageUrl);
                ImageIcon imageIcon = new ImageIcon(uri.toURL());
                Image scaledImage = imageIcon.getImage().getScaledInstance(IMAGE_DISPLAY_WIDTH, IMAGE_DISPLAY_HEIGHT, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                JLabel imageLabel = new JLabel(scaledIcon);
                imagesPanel.add(imageLabel);
            } catch (URISyntaxException | MalformedURLException e) {
                System.out.println("error loading images:");
            }
        }
    }

    private void sortLocations() {
        List<String> locations = travelDiaryApp.getLocations();
        travelDiaryApp.bubbleSort(locations);
        displaySortedLocations(locations);
    }

    private void searchLocationByName(String searchTerm) {
        List<String> matchingLocations = travelDiaryApp.getLocations().stream().filter(location -> location.toLowerCase().contains(searchTerm.toLowerCase())).collect(Collectors.toList());
        displayMatchingLocations(matchingLocations, searchTerm);
    }

    private void searchLocationByCategory(String category) {
        List<String> matchingLocations = travelDiaryApp.getLocations().stream().filter(location -> {
            TravelDiaryEntry entry = travelDiaryApp.getEntry(location);
            return entry != null && entry.getCategories().contains(category);
        }).collect(Collectors.toList());
        displayMatchingLocations(matchingLocations, category);
    }

    private void displaySortedLocations(List<String> sortedLocations) {
        if (!sortedLocations.isEmpty()) {
            StringBuilder sortedLocationsStr = new StringBuilder("Sorted Locations:\n");
            for (String location : sortedLocations) {
                sortedLocationsStr.append("- ").append(location).append("\n");
            }
            JOptionPane.showMessageDialog(mainFrame, sortedLocationsStr.toString());
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No locations found!");
        }
    }

    private void displayMatchingLocations(List<String> matchingLocations, String searchTerm) {
        if (!matchingLocations.isEmpty()) {
            StringBuilder matchingLocationsStr = new StringBuilder("Matching Locations for '" + searchTerm + "':\n");
            for (String location : matchingLocations) {
                matchingLocationsStr.append("- ").append(location).append("\n");
            }
            JOptionPane.showMessageDialog(mainFrame, matchingLocationsStr.toString());
        } else {
            JOptionPane.showMessageDialog(mainFrame, "No matching locations found for '" + searchTerm + "'");
        }
    }

    public void editLocation(String locationName, String newLocationName) {
        travelDiaryApp.editLocation(locationName, newLocationName);
        updateLocationsPanel();
    }

    public void deleteLocation(String locationName) {
        travelDiaryApp.deleteLocation(locationName);
        updateLocationsPanel();
    }

    private void showMainPage() {
        mainFrame.getContentPane().removeAll();
        DiaryGUI diaryGUI = new DiaryGUI();
        diaryGUI.showMainPage();
    }
}
