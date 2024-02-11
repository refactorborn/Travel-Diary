import javax.swing.*;
import java.awt.*;

public class DiaryGUI {

    private JFrame mainFrame;
    private JPanel mainPanel;
    private TravelDiaryApp travelDiaryApp;

    public DiaryGUI() {
        this.mainFrame = new JFrame("Travel Diary App");
        this.mainPanel = new JPanel(new BorderLayout()) {
            // Override paintComponent to set background image
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("src/main/resources/costarica.jpeg"); // Path to your image file
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        this.travelDiaryApp = new TravelDiaryApp();
    }

    public void showMainPage() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton beginButton = new JButton("Begin");
        beginButton.setFont(new Font("Arial", Font.BOLD, 18));
        beginButton.setFocusPainted(false);
        beginButton.setContentAreaFilled(false);
        beginButton.setBorderPainted(false);
        beginButton.setOpaque(true);
        beginButton.setMargin(new Insets(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("Travel Diary App");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 100, 0));
        mainPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(beginButton, BorderLayout.SOUTH);

        beginButton.addActionListener(e -> showMenuPage());

        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void showMenuPage() {
        mainPanel.removeAll();

        MenuPage menuPage = new MenuPage(mainFrame, travelDiaryApp);
        menuPage.initialize();

        // Displaying all locations when entering the MenuPage
        menuPage.displayAllLocations();
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}