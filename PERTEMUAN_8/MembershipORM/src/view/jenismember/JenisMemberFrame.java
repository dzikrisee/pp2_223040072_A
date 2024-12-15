package view.jenismember;
import dao.JenisMemberDao;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.JenisMember;
import view.jenismemberdetail.JenisMemberDetailFrame;

public class JenisMemberFrame extends JFrame {
    
    private List<JenisMember> jenisMemberList;
    private JTextField textFieldNama;
    private JenisMemberTableModel tableModel;
    private JenisMemberDao jenisMemberDao;
    private JTable table;
    private JButton buttonSimpan, buttonUpdate, buttonDelete;
    private JenisMember selectedJenisMember;

    public JenisMemberFrame(JenisMemberDao jenisMemberDao){
        this.jenisMemberDao = jenisMemberDao;
        this.jenisMemberList = jenisMemberDao.findAll();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Jenis Member Management");

        // Nama Input
        JLabel labelInput = new JLabel("Nama: ");
        labelInput.setBounds(15, 40, 350, 10);
        textFieldNama = new JTextField();
        textFieldNama.setBounds(15, 60, 350, 30);

        // Buttons
        buttonSimpan = new JButton("Simpan");
        buttonSimpan.setBounds(15, 100, 100, 40);

        buttonUpdate = new JButton("Update");
        buttonUpdate.setBounds(125, 100, 100, 40);
        buttonUpdate.setEnabled(false);

        buttonDelete = new JButton("Delete");
        buttonDelete.setBounds(235, 100, 100, 40);
        buttonDelete.setEnabled(false);

        // Table
        table = new JTable();    
        JScrollPane scrollableTable = new JScrollPane(table);
        scrollableTable.setBounds(15, 150, 350, 200); 
        
        tableModel = new JenisMemberTableModel(jenisMemberList); 
        table.setModel(tableModel);

        // Action Listeners
        JenisMemberButtonSimpanActionListener simpanActionListener = 
            new JenisMemberButtonSimpanActionListener(this, jenisMemberDao);
        buttonSimpan.addActionListener(simpanActionListener);

        // Update Button Listener
        buttonUpdate.addActionListener(e -> updateJenisMember());

        // Delete Button Listener
        buttonDelete.addActionListener(e -> deleteJenisMember());

        // Table Mouse Listener for selection and double-click
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    selectedJenisMember = jenisMemberList.get(row);
                    textFieldNama.setText(selectedJenisMember.getNama());
                    
                    // Enable Update and Delete buttons
                    buttonUpdate.setEnabled(true);
                    buttonDelete.setEnabled(true);

                    // Double click to open detail
                    if (e.getClickCount() == 2) { 
                        JenisMemberDetailFrame detailFrame = 
                            new JenisMemberDetailFrame(selectedJenisMember, jenisMemberDao);
                        detailFrame.setVisible(true);
                    }
                }
            }
        });

        // Add components
        this.add(buttonSimpan);
        this.add(buttonUpdate);
        this.add(buttonDelete);
        this.add(textFieldNama);
        this.add(labelInput);
        this.add(scrollableTable);
        
        this.setSize(400, 500);
        this.setLayout(null);
    }

    // Method to update Jenis Member
    private void updateJenisMember() {
        if (selectedJenisMember != null) {
            String newNama = textFieldNama.getText().trim();
            if (!newNama.isEmpty()) {
                selectedJenisMember.setNama(newNama);
                jenisMemberDao.update(selectedJenisMember);
                
                // Refresh the table
                jenisMemberList = jenisMemberDao.findAll();
                tableModel.setData(jenisMemberList);
                
                // Reset selection and fields
                resetForm();
                
                JOptionPane.showMessageDialog(this, "Jenis Member berhasil diupdate");
            } else {
                JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to delete Jenis Member
    private void deleteJenisMember() {
        if (selectedJenisMember != null) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin menghapus Jenis Member ini?", 
                "Konfirmasi Hapus", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                jenisMemberDao.delete(selectedJenisMember);
                
                // Refresh the table
                jenisMemberList = jenisMemberDao.findAll();
                tableModel.setData(jenisMemberList);
                
                // Reset selection and fields
                resetForm();
                
                JOptionPane.showMessageDialog(this, "Jenis Member berhasil dihapus");
            }
        }
    }

    // Reset form and button states
    private void resetForm() {
        textFieldNama.setText("");
        selectedJenisMember = null;
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
    }

    public String getName(){
        return textFieldNama.getText();
    }

    public void addJenisMember(JenisMember jenisMember){
        tableModel.add(jenisMember);
        textFieldNama.setText("");
    }
    
    
}