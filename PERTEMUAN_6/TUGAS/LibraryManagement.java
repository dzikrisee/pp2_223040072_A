package PERTEMUAN_6.TUGAS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class LibraryManagement extends JFrame {
    private JMenuBar menuBar;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public LibraryManagement() {
        setTitle("Sistem Manajemen Perpustakaan");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Inisialisasi MenuBar
        menuBar = new JMenuBar();
        JMenu menuData = new JMenu("Data");
        JMenuItem menuBuku = new JMenuItem("Buku");
        JMenuItem menuAnggota = new JMenuItem("Anggota");
        JMenuItem menuPeminjaman = new JMenuItem("Peminjaman");
        
        menuData.add(menuBuku);
        menuData.add(menuAnggota);
        menuData.add(menuPeminjaman);
        menuBar.add(menuData);
        setJMenuBar(menuBar);
        
        // Inisialisasi CardLayout untuk switching panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Tambahkan panel-panel form
        mainPanel.add(new BukuPanel(), "Buku");
        mainPanel.add(new AnggotaPanel(), "Anggota");
        mainPanel.add(new PeminjamanPanel(), "Peminjaman");
        
        add(mainPanel);
        
        // Action Listeners untuk menu items
        menuBuku.addActionListener(e -> cardLayout.show(mainPanel, "Buku"));
        menuAnggota.addActionListener(e -> cardLayout.show(mainPanel, "Anggota"));
        menuPeminjaman.addActionListener(e -> cardLayout.show(mainPanel, "Peminjaman"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryManagement().setVisible(true);
        });
    }
}


// Panel Buku
class BukuPanel extends JPanel {
    private JTextField tfJudul, tfPenulis, tfPenerbit;
    private JSpinner spTahun;
    private JComboBox<String> cbKategori;
    private JTable tableBuku;
    private DefaultTableModel tableModel;
    
    public BukuPanel() {
        setLayout(new BorderLayout());
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Komponen-komponen form
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Judul:"), gbc);
        gbc.gridx = 1;
        tfJudul = new JTextField(20);
        formPanel.add(tfJudul, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Penulis:"), gbc);
        gbc.gridx = 1;
        tfPenulis = new JTextField(20);
        formPanel.add(tfPenulis, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Penerbit:"), gbc);
        gbc.gridx = 1;
        tfPenerbit = new JTextField(20);
        formPanel.add(tfPenerbit, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Tahun:"), gbc);
        gbc.gridx = 1;
        spTahun = new JSpinner(new SpinnerNumberModel(2024, 1900, 2024, 1));
        formPanel.add(spTahun, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Kategori:"), gbc);
        gbc.gridx = 1;
        String[] kategori = {"Fiksi", "Non-Fiksi", "Pendidikan", "Teknologi"};
        cbKategori = new JComboBox<>(kategori);
        formPanel.add(cbKategori, gbc);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton btnSimpan = new JButton("Simpan");
        buttonPanel.add(btnSimpan);
        
        // Table
        String[] columns = {"Judul", "Penulis", "Penerbit", "Tahun", "Kategori"};
        tableModel = new DefaultTableModel(columns, 0);
        tableBuku = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableBuku);
        
        // Layout
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        
        // Action Listener untuk button simpan
        btnSimpan.addActionListener(e -> {
            Vector<String> row = new Vector<>();
            row.add(tfJudul.getText());
            row.add(tfPenulis.getText());
            row.add(tfPenerbit.getText());
            row.add(spTahun.getValue().toString());
            row.add(cbKategori.getSelectedItem().toString());
            tableModel.addRow(row);
            
            // Clear form
            tfJudul.setText("");
            tfPenulis.setText("");
            tfPenerbit.setText("");
            spTahun.setValue(2024);
            cbKategori.setSelectedIndex(0);
        });
    }
}

// Panel Anggota
class AnggotaPanel extends JPanel {
    private JTextField tfNama, tfAlamat;
    private JRadioButton rbPria, rbWanita;
    private JCheckBox cbPerpus, cbEbook;
    private JTable tableAnggota;
    private DefaultTableModel tableModel;
    
    public AnggotaPanel() {
        setLayout(new BorderLayout());
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Komponen-komponen form
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1;
        tfNama = new JTextField(20);
        formPanel.add(tfNama, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        tfAlamat = new JTextField(20);
        formPanel.add(tfAlamat, gbc);
        
        // Radio Buttons untuk Jenis Kelamin
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Jenis Kelamin:"), gbc);
        gbc.gridx = 1;
        JPanel radioPanel = new JPanel();
        ButtonGroup bg = new ButtonGroup();
        rbPria = new JRadioButton("Pria");
        rbWanita = new JRadioButton("Wanita");
        bg.add(rbPria);
        bg.add(rbWanita);
        radioPanel.add(rbPria);
        radioPanel.add(rbWanita);
        formPanel.add(radioPanel, gbc);
        
        // Checkboxes untuk Layanan
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Layanan:"), gbc);
        gbc.gridx = 1;
        JPanel checkPanel = new JPanel();
        cbPerpus = new JCheckBox("Perpustakaan");
        cbEbook = new JCheckBox("E-Book");
        checkPanel.add(cbPerpus);
        checkPanel.add(cbEbook);
        formPanel.add(checkPanel, gbc);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton btnSimpan = new JButton("Simpan");
        buttonPanel.add(btnSimpan);
        
        // Table
        String[] columns = {"Nama", "Alamat", "Jenis Kelamin", "Layanan"};
        tableModel = new DefaultTableModel(columns, 0);
        tableAnggota = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableAnggota);
        
        // Layout
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        
        // Action Listener untuk button simpan
        btnSimpan.addActionListener(e -> {
            Vector<String> row = new Vector<>();
            row.add(tfNama.getText());
            row.add(tfAlamat.getText());
            row.add(rbPria.isSelected() ? "Pria" : "Wanita");
            
            String layanan = "";
            if (cbPerpus.isSelected()) layanan += "Perpustakaan ";
            if (cbEbook.isSelected()) layanan += "E-Book";
            row.add(layanan.trim());
            
            tableModel.addRow(row);
            
            // Clear form
            tfNama.setText("");
            tfAlamat.setText("");
            bg.clearSelection();
            cbPerpus.setSelected(false);
            cbEbook.setSelected(false);
        });
    }
}

// Panel Peminjaman
class PeminjamanPanel extends JPanel {
    private JComboBox<String> cbAnggota;
    private JList<String> listBuku;
    private JTextArea taCatatan;
    private JSlider slDurasi;
    private JTable tablePeminjaman;
    private DefaultTableModel tableModel;
    
    public PeminjamanPanel() {
        setLayout(new BorderLayout());
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Komponen-komponen form
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Anggota:"), gbc);
        gbc.gridx = 1;
        String[] anggota = {"Dzikri Setiawan", "M Alfath Septian", "M Daffa Musyaffa"};
        cbAnggota = new JComboBox<>(anggota);
        formPanel.add(cbAnggota, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Buku:"), gbc);
        gbc.gridx = 1;
        String[] buku = {"Java Programming", "Python Basics", "Web Development", "Database Design"};
        listBuku = new JList<>(buku);
        listBuku.setVisibleRowCount(4);
        JScrollPane listScroll = new JScrollPane(listBuku);
        formPanel.add(listScroll, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Durasi (hari):"), gbc);
        gbc.gridx = 1;
        slDurasi = new JSlider(JSlider.HORIZONTAL, 1, 14, 7);
        slDurasi.setMajorTickSpacing(1);
        slDurasi.setPaintTicks(true);
        slDurasi.setPaintLabels(true);
        formPanel.add(slDurasi, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Catatan:"), gbc);
        gbc.gridx = 1;
        taCatatan = new JTextArea(3, 20);
        JScrollPane taScroll = new JScrollPane(taCatatan);
        formPanel.add(taScroll, gbc);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton btnSimpan = new JButton("Simpan");
        buttonPanel.add(btnSimpan);
        
        // Table
        String[] columns = {"Anggota", "Buku", "Durasi", "Catatan"};
        tableModel = new DefaultTableModel(columns, 0);
        tablePeminjaman = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablePeminjaman);
        
        // Layout
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        
        // Action Listener untuk button simpan
        btnSimpan.addActionListener(e -> {
            Vector<String> row = new Vector<>();
            row.add(cbAnggota.getSelectedItem().toString());
            
            String selectedBooks = String.join(", ", listBuku.getSelectedValuesList());
            row.add(selectedBooks);
            
            row.add(String.valueOf(slDurasi.getValue()));
            row.add(taCatatan.getText());
            
            tableModel.addRow(row);
            
            // Clear form
            cbAnggota.setSelectedIndex(0);
            listBuku.clearSelection();
            slDurasi.setValue(7);
            taCatatan.setText("");
        });
    }
}