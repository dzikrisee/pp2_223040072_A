package PERTEMUAN_7.TUGAS.src.view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    private MemberPanel memberPanel;
    private BookPanel bookPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    public MainFrame() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Setup Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu dataMenu = new JMenu("Data Management");
        
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        
        JMenuItem memberItem = new JMenuItem("Members");
        memberItem.addActionListener(e -> showPanel("members"));
        
        JMenuItem bookItem = new JMenuItem("Books");
        bookItem.addActionListener(e -> showPanel("books"));
        
        fileMenu.add(exitItem);
        dataMenu.add(memberItem);
        dataMenu.add(bookItem);
        
        menuBar.add(fileMenu);
        menuBar.add(dataMenu);
        setJMenuBar(menuBar);
        
        // Setup Panels
        memberPanel = new MemberPanel();
        bookPanel = new BookPanel();
        
        // Use CardLayout for switching between panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(memberPanel, "members");
        mainPanel.add(bookPanel, "books");
        
        // Add mainPanel to JFrame's content pane
        getContentPane().add(mainPanel);
        
        // Show member panel by default
        cardLayout.show(mainPanel, "members");
    }
    
    private void showPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
    cardLayout.show(mainPanel, panelName);

    // Ganti nama halaman (judul JFrame) sesuai panel yang aktif
    if (panelName.equals("members")) {
        setTitle("Library Management System - Members");
    } else if (panelName.equals("books")) {
        setTitle("Library Management System - Books");
    }

    // Pastikan layout diperbarui
    mainPanel.revalidate();
    mainPanel.repaint();
    }
}
