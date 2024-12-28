import view.MahasiswaView;
import javax.swing.SwingUtilities;

public class MainApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MahasiswaView frame = new MahasiswaView();
            frame.setVisible(true);
        });
    }
}
