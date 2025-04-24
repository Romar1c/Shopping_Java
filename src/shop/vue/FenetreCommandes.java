package shop.vue;

import shop.controleur.CommandeControleur;
import shop.modele.Article;
import shop.modele.Commande;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class FenetreCommandes extends JFrame {
    private CommandeControleur commandeControleur;
    private JTable table;
    private DefaultTableModel tableModel;
    private int clientId;

    public FenetreCommandes(int clientId) {
        this.clientId = clientId;
        commandeControleur = new CommandeControleur();

        setTitle("Commandes client " + clientId);
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tableau des commandes du client avec prix total par article
        tableModel = new DefaultTableModel(new String[]{"ID", "Date", "Prix Total", "Article", "Quantité"}, 0);
        table = new JTable(tableModel);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        add(new JScrollPane(table), BorderLayout.CENTER);

        rafraichirTable();
        setVisible(true);
    }

    // Charger uniquement les commandes du client connecté avec détails des articles
    private void rafraichirTable() {
        tableModel.setRowCount(0);
        List<Commande> commandes = commandeControleur.rechercherCommandesParClient(clientId);
        for (Commande commande : commandes) {
            for (Article article : commande.getArticles()) {
                tableModel.addRow(new Object[]{
                        commande.getId(),
                        commande.getDateCommande(),
                        commande.getTotal(),
                        article.getNom(),
                        article.getQuantiteVrac()
                });


            }
        }
    }

    public static void main(String[] args) {
        new FenetreCommandes(1); // Test avec un client ID 1
    }
}
