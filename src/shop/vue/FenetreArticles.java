package shop.vue;

import shop.controleur.ArticleControleur;
import shop.modele.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FenetreArticles extends JFrame {
    private ArticleControleur articleControleur;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField, nomField, marqueField, prixUnitaireField, prixVracField, quantiteVracField;

    public FenetreArticles() {
        articleControleur = new ArticleControleur();
        setTitle("Gestion des Articles");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tableau des articles
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Marque", "Prix Unitaire", "Prix Vrac", "Quantité Vrac"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel Recherche
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Rechercher");
        searchPanel.add(new JLabel("Recherche: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Panel Formulaire et Boutons
        JPanel formPanel = new JPanel(new GridLayout(7, 2));
        nomField = new JTextField();
        marqueField = new JTextField();
        prixUnitaireField = new JTextField();
        prixVracField = new JTextField();
        quantiteVracField = new JTextField();
        JButton addButton = new JButton("Ajouter");
        JButton deleteButton = new JButton("Supprimer");

        formPanel.add(new JLabel("Nom: ")); formPanel.add(nomField);
        formPanel.add(new JLabel("Marque: ")); formPanel.add(marqueField);
        formPanel.add(new JLabel("Prix Unitaire: ")); formPanel.add(prixUnitaireField);
        formPanel.add(new JLabel("Prix Vrac: ")); formPanel.add(prixVracField);
        formPanel.add(new JLabel("Quantité Vrac: ")); formPanel.add(quantiteVracField);
        formPanel.add(addButton); formPanel.add(deleteButton);
        add(formPanel, BorderLayout.SOUTH);

        // Bouton Ajouter
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String marque = marqueField.getText();
                double prixUnitaire = Double.parseDouble(prixUnitaireField.getText());
                double prixVrac = Double.parseDouble(prixVracField.getText());
                int quantiteVrac = Integer.parseInt(quantiteVracField.getText());
                articleControleur.ajouterArticle(nom, marque, prixUnitaire, prixVrac, quantiteVrac);
                rafraichirTable();
            }
        });

        // Bouton Supprimer
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    articleControleur.supprimerArticle(id);
                    rafraichirTable();
                }
            }
        });

        // Bouton Recherche
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rafraichirTable(searchField.getText());
            }
        });

        rafraichirTable();
        setVisible(true);
    }

    // Rafraîchir la table
    private void rafraichirTable() {
        rafraichirTable("");
    }

    private void rafraichirTable(String motCle) {
        tableModel.setRowCount(0);
        List<Article> articles = articleControleur.rechercherArticles(motCle);
        for (Article article : articles) {
            tableModel.addRow(new Object[]{
                    article.getId(),
                    article.getNom(),
                    article.getMarque(),
                    article.getPrixUnitaire(),
                    article.getPrixVrac(),
                    article.getQuantiteVrac()
            });
        }
    }

    public static void main(String[] args) {
        new FenetreArticles();
    }
}