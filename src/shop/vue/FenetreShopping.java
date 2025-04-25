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
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tableau des articles disponibles
        modelArticles = new DefaultTableModel(new String[]{"ID", "Nom", "Marque", "Prix Unitaire", "Quantite Vrac", "Prix Vrac"}, 0);
        tableArticles = new JTable(modelArticles);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableArticles.getModel());
        tableArticles.setRowSorter(sorter);
        add(new JScrollPane(tableArticles), BorderLayout.WEST);

        // North
        JPanel panelNorth = new JPanel(new BorderLayout());

        // Affiche commande
        viewOrdersButton = new JButton("Voir mes Commandes");
        panelNorth.add(viewOrdersButton, BorderLayout.EAST);

        // Barre de Recherche
        JPanel panelRecherche = new JPanel(new BorderLayout());
        JTextField champRecherche = new JTextField();
        panelRecherche.add(new JLabel("Recherche : "), BorderLayout.WEST);
        panelRecherche.add(champRecherche, BorderLayout.CENTER);

        panelNorth.add(panelRecherche, BorderLayout.CENTER);
        add(panelNorth, BorderLayout.NORTH);

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
        JPanel panelPanier = new JPanel(new BorderLayout());

        modelPanier = new DefaultTableModel(new String[]{"ID", "Nom", "Quantité", "Total"}, 0);
        tablePanier = new JTable(modelPanier);

        JLabel TotalPanier = new JLabel( String.valueOf( TotalPanier() ) );

        panelPanier.add(TotalPanier, BorderLayout.SOUTH);
        panelPanier.add(new JScrollPane(tablePanier), BorderLayout.CENTER);

        add(panelPanier, BorderLayout.EAST);

        // Panel d'actions
        JPanel panelActions = new JPanel(new GridLayout(2, 2));

        JPanel panelQuantite = new JPanel(new BorderLayout());
        JTextField quantiteField = new JTextField();

        panelQuantite.add(new JLabel("Quantité : "), BorderLayout.WEST);
        panelQuantite.add(quantiteField, BorderLayout.CENTER);

        panelActions.add(panelQuantite);

        addButton = new JButton("Ajouter au Panier");
        checkoutButton = new JButton("Valider la Commande");

        panelActions.add(new JLabel());
        panelActions.add(addButton);
        panelActions.add(checkoutButton);
        add(panelActions, BorderLayout.SOUTH);

        // Action Ajouter au Panier
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableArticles.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = tableArticles.convertRowIndexToModel(selectedRow);

                    int articleId = (int) modelArticles.getValueAt(modelRow, 0);
                    String nom = (String) modelArticles.getValueAt(modelRow, 1);
                    String marque = (String) modelArticles.getValueAt(modelRow, 2);
                    double prix = (double) modelArticles.getValueAt(modelRow, 3);
                    int quantiteVrac = (int) modelArticles.getValueAt(modelRow, 4);
                    double prixVrac = (double) modelArticles.getValueAt(modelRow, 5);

                    int quantite = Integer.parseInt(quantiteField.getText());

                    panier.add(new Article(articleId, nom, marque, prix, prixVrac, quantiteVrac,0, quantite));
                    TotalPanier.setText(String.valueOf( TotalPanier() ) );
                    rafraichirPanier();
                }
            }
        });

        // Action Valider la Commande
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!panier.isEmpty()) {
                    boolean value = commandeControleur.ajouterCommande(clientId, panier);
                    System.out.println(value);
                    if(value){
                        JOptionPane.showMessageDialog(null, "Commande validée avec succès !");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Certains article de la commande ne sont pas disponible dans la quantite demande.");
                    }
                    panier.clear();
                    rafraichirPanier();
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
        if (texte.trim().isEmpty()) {
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
                    article.getPrixUnitaire(),
                    article.getQuantiteVrac(),
                    article.getPrixVrac()
            });
        }
    }

    private void rafraichirPanier() {
        modelPanier.setRowCount(0);
        for (Article article : panier) {
            modelPanier.addRow(new Object[]{
                    article.getId(),
                    article.getNom(),
                    article.getQuantite(),
                    article.getPrixTotal()
            });
        }
    }

    private double TotalPanier() {
        modelPanier.setRowCount(0);
        double total = 0;
        for (Article article : panier) {
            total += article.getPrixTotal();
        }
        return total;
    }

    public static void main(String[] args) {
        new FenetreShopping(1); // Test avec un client ID 1
    }
}
