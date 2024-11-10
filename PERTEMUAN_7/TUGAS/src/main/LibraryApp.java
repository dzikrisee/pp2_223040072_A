package PERTEMUAN_7.TUGAS.src.main;

import javax.swing.SwingUtilities;

import PERTEMUAN_7.TUGAS.src.view.MainFrame;

public class LibraryApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}