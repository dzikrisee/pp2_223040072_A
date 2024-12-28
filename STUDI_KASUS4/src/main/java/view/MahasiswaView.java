package view;

// File: MahasiswaView.java (View)
import controller.MahasiswaController;
import model.Mahasiswa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MahasiswaView extends JFrame {
    private JTextField txtNim, txtNama, txtJurusan, txtEmail, txtAlamat;
    private JTable tableMahasiswa;
    private DefaultTableModel tableModel;
    private MahasiswaController controller;

    public MahasiswaView() {
        controller = new MahasiswaController(this);
        initComponents();
    }

    private void initComponents() {
        setTitle("Manajemen Data Mahasiswa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("NIM:"));
        txtNim = new JTextField();
        inputPanel.add(txtNim);
        inputPanel.add(new JLabel("Nama:"));
        txtNama = new JTextField();
        inputPanel.add(txtNama);
        inputPanel.add(new JLabel("Jurusan:"));
        txtJurusan = new JTextField();
        inputPanel.add(txtJurusan);
        inputPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        inputPanel.add(txtEmail);
        inputPanel.add(new JLabel("Alamat:"));
        txtAlamat = new JTextField();
        inputPanel.add(txtAlamat);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton btnTambah = new JButton("Tambah");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");

        btnTambah.addActionListener(e -> tambahMahasiswa());
        btnUbah.addActionListener(e -> ubahMahasiswa());
        btnHapus.addActionListener(e -> hapusMahasiswa());

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUbah);
        buttonPanel.add(btnHapus);

        // Tabel
        String[] columnNames = {"NIM", "Nama", "Jurusan", "Email", "Alamat"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableMahasiswa = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableMahasiswa);

        tableMahasiswa.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableMahasiswa.getSelectedRow();
                if (selectedRow != -1) {
                    tampilkanDataTerpilih(selectedRow);
                }
            }
        });

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        // Muat data awal
        controller.muatDataMahasiswa();
    }

    public void tampilkanDataMahasiswa(List<Mahasiswa> mahasiswaList) {
        tableModel.setRowCount(0);
        for (Mahasiswa mahasiswa : mahasiswaList) {
            tableModel.addRow(new Object[]{
                    mahasiswa.getNim(),
                    mahasiswa.getNama(),
                    mahasiswa.getJurusan(),
                    mahasiswa.getEmail(),
                    mahasiswa.getAlamat()
            });
        }
    }

    private void tampilkanDataTerpilih(int baris) {
        txtNim.setText(tableModel.getValueAt(baris, 0).toString());
        txtNama.setText(tableModel.getValueAt(baris, 1).toString());
        txtJurusan.setText(tableModel.getValueAt(baris, 2).toString());
        txtEmail.setText(tableModel.getValueAt(baris, 3).toString());
        txtAlamat.setText(tableModel.getValueAt(baris, 4).toString());
    }

    private void tambahMahasiswa() {
        int nim = Integer.parseInt(txtNim.getText());
        String nama = txtNama.getText();
        String jurusan = txtJurusan.getText();
        String email = txtEmail.getText();
        String alamat = txtAlamat.getText();

        Mahasiswa mahasiswa = new Mahasiswa(nim, nama, jurusan, email, alamat);
        controller.tambahMahasiswa(mahasiswa);
    }

    private void ubahMahasiswa() {
        int nim = Integer.parseInt(txtNim.getText());
        String nama = txtNama.getText();
        String jurusan = txtJurusan.getText();
        String email = txtEmail.getText();
        String alamat = txtAlamat.getText();

        Mahasiswa mahasiswa = new Mahasiswa(nim, nama, jurusan, email, alamat);
        controller.ubahMahasiswa(mahasiswa);
    }

    private void hapusMahasiswa() {
        int nim = Integer.parseInt(txtNim.getText());
        controller.hapusMahasiswa(nim);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MahasiswaView().setVisible(true);
        });
    }
}

