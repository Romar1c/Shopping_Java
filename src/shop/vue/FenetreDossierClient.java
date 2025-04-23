package shop.vue;

import shop.controleur.CommandeControleur;
import shop.donnees.GestionClient;
import shop.modele.Article;
import shop.modele.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FenetreDossierClient extends JFrame {
    private int clientId;
    private GestionClient gestionClient;
    private DefaultTableModel modelArticles;
    private JTable tableArticles;

    public FenetreDossierClient(int clientId) {
        this.clientId = clientId;
        this.gestionClient = new GestionClient();

        setTitle("Liste Client");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel North = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Liste Client");
        North.add(label, BorderLayout.CENTER);

        JButton retour = new JButton("Retour");
        North.add(retour, BorderLayout.WEST);
        add(North, BorderLayout.NORTH);

        // Creation de la table
        modelArticles = new DefaultTableModel(new String[]{"ID", "Nom", "Email", "Details"}, 0);
        tableArticles = new JTable(modelArticles);

        // Tri de la table
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableArticles.getModel());
        tableArticles.setRowSorter(sorter);

        tableArticles.getColumn("Details").setCellRenderer(new FenetreDossierClient.ButtonRenderer());
        tableArticles.getColumn("Details").setCellEditor(new FenetreDossierClient.ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(tableArticles);
        add(scrollPane, BorderLayout.CENTER);

        modelArticles.setRowCount(0);
        List<Client> clients = gestionClient.getClients();
        for (Client client : clients) {
            modelArticles.addRow(new Object[]{
                    client.getId(),
                    client.getNom(),
                    client.getEmail()
            });
        }

        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FenetreAdmin(clientId);
                dispose();
            }
        });

        setVisible(true);
    }
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Voir" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Voir" : value.toString();
            button.setText(label);
            clicked = true;
            selectedRow = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                int idClient = (int) tableArticles.getValueAt(selectedRow, 0);
                new FenetreCommandes(idClient);
            }
            clicked = false;
            return label;
        }
    }

    public static void main(String[] args) {
        new FenetreDossierClient(1);
    }
}
