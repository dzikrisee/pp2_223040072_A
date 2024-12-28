package controller;

import dao.MahasiswaDAO;
import model.Mahasiswa;
import view.MahasiswaView;

import javax.swing.*;
import java.util.List;

public class MahasiswaController {
    private MahasiswaDAO mahasiswaDAO;
    private MahasiswaView view;

    public MahasiswaController(MahasiswaView view) {
        this.mahasiswaDAO = new MahasiswaDAO();
        this.view = view;
    }

    public void muatDataMahasiswa() {
        List<Mahasiswa> mahasiswaList = mahasiswaDAO.getAllMahasiswa();
        view.tampilkanDataMahasiswa(mahasiswaList);
    }

    public void tambahMahasiswa(Mahasiswa mahasiswa) {
        try {
            mahasiswaDAO.addMahasiswa(mahasiswa);
            muatDataMahasiswa();
            JOptionPane.showMessageDialog(null, "Data mahasiswa berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menambahkan data mahasiswa: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ubahMahasiswa(Mahasiswa mahasiswa) {
        try {
            mahasiswaDAO.updateMahasiswa(mahasiswa);
            muatDataMahasiswa();
            JOptionPane.showMessageDialog(null, "Data mahasiswa berhasil diubah!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mengubah data mahasiswa: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void hapusMahasiswa(int nim) {
        int konfirmasi = JOptionPane.showConfirmDialog(null,
                "Apakah Anda yakin ingin menghapus data mahasiswa dengan NIM: " + nim + "?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                mahasiswaDAO.deleteMahasiswa(nim);
                muatDataMahasiswa();
                JOptionPane.showMessageDialog(null, "Data mahasiswa berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal menghapus data mahasiswa: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
