package shop.vue;

import shop.controleur.CommandeControleur;
import shop.modele.Article;

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

    private int clientId;
    private CommandeControleur commandeControleur;
    private DefaultTableModel modelArticles;
    private JTable tableArticles;

    /**
     * Constructeur de la fenetre d'inventaire.
     * Initialise les composants graphiques, affiche la liste des articles et configure les actions des boutons.
     *
     * @param clientId L'ID de l'admin qui consulte l'inventaire.
     */

    public FenetreInventaire(int clientId) {
        this.clientId = clientId;
        this.commandeControleur = new CommandeControleur();

        setTitle("Inventaire");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel North = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Inventaire");
        North.add(label, BorderLayout.CENTER);

        JButton retour = new JButton("Retour");
        North.add(retour, BorderLayout.WEST);
        add(North, BorderLayout.NORTH);

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

        JButton BtnNouvelArticle = new JButton("Nouvel Article");
        add(BtnNouvelArticle, BorderLayout.SOUTH);

        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FenetreAdmin(clientId);
                dispose();
            }
        });

        BtnNouvelArticle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FenetreAjouter(clientId);
                dispose();
            }
        });
        setVisible(true);
    }

    /**
     * ButtonRenderer est une classe qui permet de personnaliser l'apparence des boutons dans la table des articles.
     */
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Voir" : value.toString());
            return this;
        }
    }

    /**
     * ButtonEditor est une classe qui permet de definir le comportement du bouton dans la table des articles.
     */
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
                int idArticle = (int) tableArticles.getValueAt(selectedRow, 0);
                new FenetreDetail(idArticle, clientId);
                dispose();
            }
            clicked = false;
            return label;
        }
    }

    public static void main(String[] args) {
        new FenetreInventaire(1);
    }
}