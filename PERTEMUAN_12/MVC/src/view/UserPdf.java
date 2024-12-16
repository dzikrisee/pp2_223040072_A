package PERTEMUAN_12.MVC.src.view;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserPdf {

    public void exportPdf(List<String> users) {
        // Membuat dokumen PDF
        try (PDDocument document = new PDDocument()) {
            if (users == null || users.isEmpty()) {
                System.err.println("No users to export.");
                return;
            }

            // Menambahkan halaman ke dokumen
            PDPage page = new PDPage();
            document.addPage(page);

            // Menyiapkan content stream untuk menulis ke halaman
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Mengatur font untuk teks
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.beginText();
            contentStream.newLineAtOffset(220, 750); // Mengatur posisi teks pada halaman
            contentStream.showText("User List");
            contentStream.endText();

            // Menambahkan jarak antara judul dan tabel
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(200, 730);
            contentStream.showText("");
            contentStream.endText();

            // Menambahkan header tabel: Nama dan Email
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700); // Posisi kiri atas untuk header
            contentStream.showText("Name");
            contentStream.newLineAtOffset(300, 0); // Jarak kolom untuk Email
            contentStream.showText("Email");
            contentStream.endText();

            // Menulis data pengguna ke tabel
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            float yPosition = 680; // Posisi awal untuk baris pertama

            for (String user : users) {
                String[] parts = user.split(" \\("); // Memisahkan "Nama (Email)"
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String email = parts[1].substring(0, parts[1].length() - 1).trim(); // Menghapus ")" pada email

                    // Menulis nama dan email ke dalam tabel
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(name);
                    contentStream.newLineAtOffset(300, 0);
                    contentStream.showText(email);
                    contentStream.endText();

                    yPosition -= 20; // Menurunkan posisi baris untuk baris berikutnya
                } else {
                    System.err.println("Skipping invalid user format: " + user);
                }

                // Jika posisi sudah terlalu dekat dengan bagian bawah halaman, tambahkan halaman baru
                if (yPosition < 100) {
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPosition = 750; // Mengatur posisi awal kembali untuk halaman baru
                }
            }

            // Menyelesaikan proses penulisan ke dokumen PDF
            contentStream.close();

            // Menyimpan dokumen PDF ke file
            document.save(new File("users.pdf"));
            System.out.println("PDF exported successfully to users.pdf");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while exporting PDF: " + e.getMessage());
        }
    }
}
