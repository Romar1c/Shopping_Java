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
import java.util.ArrayList;

public class FenetreStats extends JFrame {
    private int clientId;
    private GestionClient gestionclient;
    private GestionCommande gestioncommande;
    private GestionArticle gestionarticle;

    public FenetreStats(int clientId) {
        this.clientId = clientId;
        gestionclient = new GestionClient();
        gestioncommande = new GestionCommande();
        gestionarticle = new GestionArticle();

        setTitle("Statistique");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JButton retour = new JButton("Retour");
        add(new JLabel("Statistique"), BorderLayout.NORTH);

        JPanel principal = new JPanel();
        JPanel North = new JPanel();

        North.setLayout(new GridLayout(1, 4));
        JButton ButtonClient =  new JButton("Client");
        JButton ButtonCommande =  new JButton("Commande");
        JButton ButtonArticle =  new JButton("Article");
        JButton ButtonFinancier =  new JButton("Financier");

        North.add(retour);
        North.add(ButtonClient);
        North.add(ButtonCommande);
        North.add(ButtonArticle);
        North.add(ButtonFinancier);

        JPanel Center = new JPanel();

        ButtonClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Center.removeAll();

                Center.setLayout(new GridLayout(3, 2));

                JLabel nbrClient = new JLabel("Nombre de client : ");
                Center.add(nbrClient);

                java.util.List<Client> clientList = gestionclient.getClients();
                Center.add(new JTextArea(String.valueOf(clientList.size())));

                JLabel clientFideleEur = new JLabel("Client le plus fidele (en €): ");
                Center.add(clientFideleEur);

                Client FideleEur = gestionclient.FideleEur();
                Center.add(new JTextArea(FideleEur.getNom() + " a depense un total de: " + FideleEur.getDepense()));

                JLabel clientFideleCommande = new JLabel("Client le plus fidele (en nbr de commande): ");
                Center.add(clientFideleCommande);

                Client FideleCommande = gestionclient.FideleCommande();
                Center.add(new JTextArea(FideleCommande.getNom() + " a effectué un total de: " + FideleCommande.getNbrcommande() + " commandes."));

                Center.revalidate();
                Center.repaint();

            }
        });

        ButtonCommande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Center.removeAll();

                Center.setLayout(new GridLayout(3, 2));

                JLabel PrixMoyen = new JLabel("Prix moyen du panier: ");
                Center.add(PrixMoyen);

                double moyen = gestioncommande.MoyennePrix();
                String Pmoyen = String.format("%.2f", moyen);
                Center.add(new JTextArea(Pmoyen));

                JLabel PanierMax = new JLabel("Panier le plus chere: ");
                Center.add(PanierMax);

                double max = gestioncommande.PlusChere();
                Center.add(new JTextArea(String.valueOf(max)));

                JLabel NbrCommande = new JLabel("Nombre de commande: ");
                Center.add(NbrCommande);

                int NbrCommandes = gestioncommande.NbrCommandes();
                Center.add(new JTextArea(String.valueOf(NbrCommandes)));

                Center.revalidate();
                Center.repaint();
            }
        });

        ButtonArticle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Center.removeAll();

                DefaultCategoryDataset ArtEur = gestionarticle.DataSetArtEur();
                JFreeChart BarArtEur = ChartFactory.createBarChart(
                        "Revenu total par article",
                        "Article",
                        "Revenu (€)",
                        ArtEur
                );

                ChartPanel EurParArt = new ChartPanel(BarArtEur);
                Center.add(EurParArt);

                DefaultCategoryDataset ArtUnit = gestionarticle.DataSetArtUnit();
                JFreeChart BarArtUnit = ChartFactory.createBarChart(
                        "Unites Vendues par article",
                        "Article",
                        "Unites Vendues",
                        ArtUnit
                );

                ChartPanel UnitParArt = new ChartPanel(BarArtUnit);
                Center.add(UnitParArt);

                Center.revalidate();
                Center.repaint();
            }
        });

        ButtonFinancier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Center.removeAll();

                Center.setLayout(new GridLayout(3, 2));

                JLabel CaTotal = new JLabel("Chiffre d'affaire total: ");
                Center.add(CaTotal);

                double ArgTot = gestioncommande.ArgentTotal();
                Center.add(new JTextArea(String.valueOf(ArgTot)));

                JLabel CaTotSsReduc = new JLabel("Chiffre d'affaire total (si pas de reduc): ");
                Center.add(CaTotSsReduc);

                double ArgTotSsReduc = gestioncommande.ArgentPotentiel();
                Center.add(new JTextArea(String.valueOf(ArgTotSsReduc)));

                JLabel PrcReduc = new JLabel("Pourcentage de reduction effectue au global: ");
                Center.add(PrcReduc);

                double reduction = ((ArgTotSsReduc - ArgTot)/ArgTotSsReduc) * 100;
                String ReducTxt = String.format("%.2f%%", reduction);
                Center.add(new JTextArea(ReducTxt));

                Center.revalidate();
                Center.repaint();
            }
        });

        retour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FenetreAdmin(clientId);
                dispose();
            }
        });

        principal.add(North, BorderLayout.NORTH);
        principal.add(Center, BorderLayout.CENTER);

        add(principal, BorderLayout.CENTER);

        setVisible(true);

    }
    public static void main(String[] args) {
        new FenetreStats(1);
    }
}
