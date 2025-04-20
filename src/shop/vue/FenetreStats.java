package shop.vue;

import shop.donnees.GestionClient;
import shop.donnees.GestionCommande;
import shop.modele.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FenetreStats extends JFrame {
    private GestionClient gestionclient;
    private GestionCommande gestioncommande;

    public FenetreStats() {
        gestionclient = new GestionClient();
        gestioncommande = new GestionCommande();


        setTitle("Statistique");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new JLabel("Statistique"), BorderLayout.NORTH);

        JPanel principal = new JPanel();
        JPanel North = new JPanel();

        North.setLayout(new GridLayout(1, 4));
        JButton ButtonClient =  new JButton("Client");
        JButton ButtonCommande =  new JButton("Commande");
        JButton ButtonArticle =  new JButton("Article");
        JButton ButtonFinancier =  new JButton("Financier");

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
                Center.add(new JTextArea(String.valueOf(moyen)));

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

                JLabel label = new JLabel("Article");
                Center.add(label);

                Center.revalidate();
                Center.repaint();
            }
        });

        ButtonFinancier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Center.removeAll();

                JLabel label = new JLabel("Financier");
                Center.add(label);

                Center.revalidate();
                Center.repaint();
            }
        });

        principal.add(North, BorderLayout.NORTH);
        principal.add(Center, BorderLayout.CENTER);

        add(principal, BorderLayout.CENTER);

        setVisible(true);

    }
    public static void main(String[] args) {
        new FenetreStats();
    }
}
