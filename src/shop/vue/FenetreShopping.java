package shop.vue;

import shop.controleur.CommandeControleur;
import shop.modele.Article;
import shop.modele.Commande;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FenetreShopping extends JFrame {
    private CommandeControleur commandeControleur;
    private JTable tableArticles, tablePanier;
    private DefaultTableModel modelArticles, modelPanier;
    private JTextField quantiteField;
    private JButton addButton, checkoutButton, viewOrdersButton;
    private List<Article> panier;
    private int clientId;

    public FenetreShopping(int clientId) {
        this.clientId = clientId;
        this.commandeControleur = new CommandeControleur();
        this.panier = new ArrayList<>();

        setTitle("Shopping - Client ID: " + clientId);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tableau des articles disponibles
        modelArticles = new DefaultTableModel(new String[]{"ID", "Nom", "Marque", "Prix Unitaire"}, 0);
        tableArticles = new JTable(modelArticles);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableArticles.getModel());
        tableArticles.setRowSorter(sorter);
        add(new JScrollPane(tableArticles), BorderLayout.WEST);

        // Barre de Recherche
        JPanel panelRecherche = new JPanel(new BorderLayout());
        JTextField champRecherche = new JTextField();
        panelRecherche.add(new JLabel("Recherche : "), BorderLayout.WEST);
        panelRecherche.add(champRecherche, BorderLayout.CENTER);
        add(panelRecherche, BorderLayout.NORTH);

        champRecherche.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                rechercher(champRecherche.getText(), sorter);
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                rechercher(champRecherche.getText(), sorter);
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                rechercher(champRecherche.getText(), sorter);
            }
        });

        // Tableau du panier
        modelPanier = new DefaultTableModel(new String[]{"ID", "Nom", "Quantité", "Total"}, 0);
        tablePanier = new JTable(modelPanier);
        add(new JScrollPane(tablePanier), BorderLayout.EAST);

        // Panel d'actions
        JPanel panelActions = new JPanel(new GridLayout(3, 2));
        quantiteField = new JTextField();
        addButton = new JButton("Ajouter au Panier");
        checkoutButton = new JButton("Valider la Commande");
        viewOrdersButton = new JButton("Voir mes Commandes");

        panelActions.add(new JLabel("Quantité: "));
        panelActions.add(quantiteField);
        panelActions.add(addButton);
        panelActions.add(checkoutButton);
        panelActions.add(viewOrdersButton);
        add(panelActions, BorderLayout.SOUTH);

        // Action Ajouter au Panier
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableArticles.getSelectedRow();
                if (selectedRow != -1) {
                    int articleId = (int) modelArticles.getValueAt(selectedRow, 0);
                    String nom = (String) modelArticles.getValueAt(selectedRow, 1);
                    double prix = (double) modelArticles.getValueAt(selectedRow, 3);
                    int quantite = Integer.parseInt(quantiteField.getText());

                    panier.add(new Article(articleId, nom, "MarqueTemp", prix, prix, quantite));
                    rafraichirPanier();
                }
            }
        });

        // Action Valider la Commande
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!panier.isEmpty()) {
                    commandeControleur.ajouterCommande(clientId, panier);
                    panier.clear();
                    rafraichirPanier();
                    JOptionPane.showMessageDialog(null, "Commande validée avec succès !");
                } else {
                    JOptionPane.showMessageDialog(null, "Votre panier est vide !");
                }
            }
        });

        // Action Voir Commandes
        viewOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FenetreCommandes(clientId);

            }
        });

        rafraichirArticles();
        setVisible(true);
    }

    private static void rechercher(String texte, TableRowSorter<TableModel> sorter) {
        if (texte.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texte));
        }
    }

    private void rafraichirArticles() {
        modelArticles.setRowCount(0);
        List<Article> articles = commandeControleur.recupererArticles();
        for (Article article : articles) {
            modelArticles.addRow(new Object[]{
                    article.getId(),
                    article.getNom(),
                    article.getMarque(),
                    article.getPrixUnitaire()
            });
        }
    }

    private void rafraichirPanier() {
        modelPanier.setRowCount(0);
        for (Article article : panier) {
            modelPanier.addRow(new Object[]{
                    article.getId(),
                    article.getNom(),
                    article.getQuantiteVrac(),
                    article.getPrixUnitaire() * article.getQuantiteVrac()
            });
        }
    }

    public static void main(String[] args) {
        new FenetreShopping(1); // Test avec un client ID 1
    }
}
