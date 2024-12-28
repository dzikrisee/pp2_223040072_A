package model;

public class Mahasiswa {
    private int nim;
    private String nama;
    private String jurusan;
    private String email;
    private String alamat;

    // Konstruktor, getter, setter
    public Mahasiswa(int nim, String nama, String jurusan, String email, String alamat) {
        this.nim = nim;
        this.nama = nama;
        this.jurusan = jurusan;
        this.email = email;
        this.alamat = alamat;
    }

    // Getter dan Setter
    public int getNim() { return nim; }
    public void setNim(int nim) { this.nim = nim; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getJurusan() { return jurusan; }
    public void setJurusan(String jurusan) { this.jurusan = jurusan; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
}