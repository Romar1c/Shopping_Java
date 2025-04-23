package shop.vue;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import shop.donnees.GestionArticle;
import shop.donnees.GestionClient;
import shop.donnees.GestionCommande;
import shop.modele.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreStats extends JFrame {
    private int clientId;
    private GestionClient gestionclient;
    private GestionCommande gestioncommande;
    private GestionArticle gestionarticle;
    private JPanel cards;

    public FenetreStats(int clientId) {
        this.clientId = clientId;
        gestionclient = new GestionClient();
        gestioncommande = new GestionCommande();
        gestionarticle = new GestionArticle();

        setTitle("Statistique");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel header = new JPanel(new BorderLayout());
        JButton retour = new JButton("Retour");
        JLabel title = new JLabel("Statistiques", SwingConstants.CENTER);

        header.add(retour, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        JPanel North = new JPanel(new BorderLayout());
        North.add(header, BorderLayout.NORTH);

        JPanel navButtons = new JPanel(new GridLayout(1, 4));
        JButton ButtonClient = new JButton("Client");
        JButton ButtonCommande = new JButton("Commande");
        JButton ButtonArticle = new JButton("Article");
        JButton ButtonFinancier = new JButton("Financier");

        navButtons.add(ButtonClient);
        navButtons.add(ButtonCommande);
        navButtons.add(ButtonArticle);
        navButtons.add(ButtonFinancier);
        North.add(navButtons, BorderLayout.SOUTH);
        add(North, BorderLayout.NORTH);

        cards = new JPanel(new CardLayout());
        JPanel emptyPanel = new JPanel();
        cards.add(emptyPanel, "Empty");

        JPanel panelClient = createPanelClient();
        JPanel panelCommande = createPanelCommande();
        JPanel panelArticle = createPanelArticle();
        JPanel panelFinancier = createPanelFinancier();

        cards.add(panelClient, "Client");
        cards.add(panelCommande, "Commande");
        cards.add(panelArticle, "Article");
        cards.add(panelFinancier, "Financier");

        add(cards, BorderLayout.CENTER);

        ButtonClient.addActionListener(e -> showPanel("Client"));
        ButtonCommande.addActionListener(e -> showPanel("Commande"));
        ButtonArticle.addActionListener(e -> showPanel("Article"));
        ButtonFinancier.addActionListener(e -> showPanel("Financier"));

        retour.addActionListener(e -> {
            new FenetreAdmin(clientId);
            dispose();
        });

        showPanel("Empty");
        setVisible(true);
    }

    private void showPanel(String name) {
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, name);
    }

    private JPanel createPanelClient() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        java.util.List<Client> clientList = gestionclient.getClients();
        Client FideleEur = gestionclient.FideleEur();
        Client FideleCommande = gestionclient.FideleCommande();

        panel.add(new JLabel("Nombre de clients : " + clientList.size()));
        panel.add(new JLabel("Client le plus fidèle (en €): " + FideleEur.getNom() + " a commande pour un total de " + FideleEur.getDepense() + "€"));
        panel.add(new JLabel("Client le plus fidèle (en commandes): " + FideleCommande.getNom() + " a passe " + FideleCommande.getNbrcommande() + " commandes"));

        return panel;
    }

    private JPanel createPanelCommande() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        double moyen = gestioncommande.MoyennePrix();
        double max = gestioncommande.PlusChere();
        int NbrCommandes = gestioncommande.NbrCommandes();

        panel.add(new JLabel("Prix moyen du panier : " + String.format("%.2f €", moyen)));
        panel.add(new JLabel("Panier le plus cher : " + max + " €"));
        panel.add(new JLabel("Nombre total de commandes : " + NbrCommandes));

        return panel;
    }

    private JPanel createPanelArticle() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        DefaultCategoryDataset ArtEur = gestionarticle.DataSetArtEur();
        DefaultCategoryDataset ArtUnit = gestionarticle.DataSetArtUnit();

        JFreeChart BarArtEur = ChartFactory.createBarChart(
                "Revenu total par article", "Article", "Revenu (€)", ArtEur);
        JFreeChart BarArtUnit = ChartFactory.createBarChart(
                "Unités vendues par article", "Article", "Unités", ArtUnit);

        panel.add(new ChartPanel(BarArtEur));
        panel.add(new ChartPanel(BarArtUnit));
        return panel;
    }

    private JPanel createPanelFinancier() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        double ArgTot = gestioncommande.ArgentTotal();
        double ArgTotSsReduc = gestioncommande.ArgentPotentiel();
        double reduction = ((ArgTotSsReduc - ArgTot) / ArgTotSsReduc) * 100;

        panel.add(new JLabel("Chiffre d'affaires total : " + ArgTot + " €"));
        panel.add(new JLabel("Potentiel sans réduction : " + ArgTotSsReduc + " €"));
        panel.add(new JLabel("Réduction totale : " + String.format("%.2f%%", reduction)));

        return panel;
    }

    public static void main(String[] args) {
        new FenetreStats(1);
    }
}
