package StudiKasus4.view;

import StudiKasus4.controller.InventoryController;
import StudiKasus4.model.Inventory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.List;

public class InventoryView {
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private InventoryController controller;

    public InventoryView() {
        controller = new InventoryController();
        JFrame frame = new JFrame("Manajemen Inventory");

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nama", "Kategori", "Harga", "Jumlah", "Aksi Ubah", "Aksi Hapus"}, 0);
        inventoryTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addButton = new JButton("Tambah");

        panel.add(addButton);
        frame.add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addInventory());

        loadInventoryData();

        inventoryTable.getColumn("Aksi Ubah").setCellRenderer(new ButtonRenderer());
        inventoryTable.getColumn("Aksi Hapus").setCellRenderer(new ButtonRenderer());

        inventoryTable.getColumn("Aksi Ubah").setCellEditor(new ButtonEditor(new JCheckBox()));
        inventoryTable.getColumn("Aksi Hapus").setCellEditor(new ButtonEditor(new JCheckBox()));

        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void loadInventoryData() {
        List<Inventory> inventories = controller.getAllInventories();
        tableModel.setRowCount(0);
        for (Inventory inventory : inventories) {
            tableModel.addRow(new Object[]{
                    inventory.getId(),
                    inventory.getName(),
                    inventory.getCategory(),
                    inventory.getPrice(),
                    inventory.getQuantity(),
                    "Ubah",
                    "Hapus"
            });
        }
    }

    private void addInventory() {
        String name = JOptionPane.showInputDialog("Nama:");
        String category = JOptionPane.showInputDialog("Kategori:");
        double price = Double.parseDouble(JOptionPane.showInputDialog("Harga:"));
        int quantity = Integer.parseInt(JOptionPane.showInputDialog("Jumlah:"));

        Inventory inventory = new Inventory();
        inventory.setName(name);
        inventory.setCategory(category);
        inventory.setPrice(price);
        inventory.setQuantity(quantity);

        controller.addInventory(inventory);

        loadInventoryData();
    }

    private void updateInventory(Inventory inventory) {
        if (inventory == null) {
            return;
        }

        String newName = JOptionPane.showInputDialog("Nama Baru:", inventory.getName());
        String newCategory = JOptionPane.showInputDialog("Kategori Baru:", inventory.getCategory());
        double newPrice = Double.parseDouble(JOptionPane.showInputDialog("Harga Baru:", inventory.getPrice()));
        int newQuantity = Integer.parseInt(JOptionPane.showInputDialog("Jumlah Baru:", inventory.getQuantity()));

        inventory.setName(newName);
        inventory.setCategory(newCategory);
        inventory.setPrice(newPrice);
        inventory.setQuantity(newQuantity);

        controller.updateInventory(inventory);

        loadInventoryData();
    }

    private void deleteInventory(int id) {
        if (id <= 0) {
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(null,
                "Apakah Anda yakin ingin menghapus data ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            controller.deleteInventory(id);
            loadInventoryData();
        }
    }

    public static void main(String[] args) {
        new InventoryView();
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

            button.addActionListener(e -> {
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int row = inventoryTable.getSelectedRow();
                if (row >= 0 && row < inventoryTable.getRowCount()) {
                    int id = (int) inventoryTable.getValueAt(row, 0);
                    if (inventoryTable.getSelectedColumn() == 5) {
                        Inventory inventory = controller.getInventoryById(id);
                        updateInventory(inventory);
                    } else if (inventoryTable.getSelectedColumn() == 6) {
                        deleteInventory(id);
                    }
                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}
