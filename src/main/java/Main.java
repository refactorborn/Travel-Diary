import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DiaryGUI diaryGUI = new DiaryGUI();
            diaryGUI.showMainPage();
        });
    }
}