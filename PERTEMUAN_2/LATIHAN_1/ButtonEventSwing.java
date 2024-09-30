package PERTEMUAN_2.LATIHAN_1;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel; // Import JLabel
import java.awt.event.ActionEvent; // Import ActionEvent
import java.awt.event.ActionListener; // Import ActionListener

public class ButtonEventSwing extends JFrame { 

    public ButtonEventSwing() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel label = new JLabel("Hello World");
        label.setBounds(130, 40, 400, 30);

        JButton button = new JButton("Klik");
        button.setBounds(130, 100, 100, 40);
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("Hello Pemrograman II");
            }
        });

        this.add(button);
        this.add(label);

        this.setSize(400, 500);
        this.setLayout(null);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ButtonEventSwing b = new ButtonEventSwing();
                b.setVisible(true);
            }
        });
    }
}
