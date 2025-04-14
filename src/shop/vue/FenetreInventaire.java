package shop.vue;

import shop.controleur.CommandeControleur;
import shop.modele.Article;
import shop.test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FenetreInventaire extends JFrame {

    private CommandeControleur commandeControleur;
    private DefaultTableModel modelArticles;
    private JTable tableArticles;

    public FenetreInventaire() {
        this.commandeControleur = new CommandeControleur();

        setTitle("Inventaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JLabel label = new JLabel("Inventaire", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.NORTH);

        // Creation de la table
        modelArticles = new DefaultTableModel(new String[]{"ID", "Nom", "Marque", "Prix Unitaire", "Stock", "Details"}, 0);
        tableArticles = new JTable(modelArticles);

        // Tri de la table
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableArticles.getModel());
        tableArticles.setRowSorter(sorter);

        tableArticles.getColumn("Details").setCellRenderer(new ButtonRenderer());
        tableArticles.getColumn("Details").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(tableArticles);
        add(scrollPane, BorderLayout.CENTER);

        modelArticles.setRowCount(0);
        List<Article> articles = commandeControleur.recupererArticles();
        for (Article article : articles) {
            modelArticles.addRow(new Object[]{
                    article.getId(),
                    article.getNom(),
                    article.getMarque(),
                    article.getPrixUnitaire(),
                    article.getStock(),
                    "Details"

            });
        }
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
                    fireEditingStopped(); // Nécessaire pour que l'éditeur termine proprement
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
                String idArticle = tableArticles.getValueAt(selectedRow, 0).toString();
                // Ici, tu fais ce que tu veux avec l'ID, par exemple ouvrir une nouvelle fenêtre
                JOptionPane.showMessageDialog(button, "Détails de l'article ID: " + idArticle);
                // OU appeler une méthode pour ouvrir une nouvelle page
            }
            clicked = false;
            return label;
        }
    }

    public static void main(String[] args) {
        new FenetreInventaire();
    }
}