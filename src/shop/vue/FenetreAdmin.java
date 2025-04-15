package shop.vue;

import shop.controleur.CommandeControleur;
import shop.controleur.UtilisateurControleur;
import shop.modele.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FenetreAdmin extends JFrame {
    int cliendId;
    private JLabel nomField;
    private JButton inventaireButton, clientButton, statsButton;
    private UtilisateurControleur utilisateurControleur;

    public FenetreAdmin(int clientId) {
        this.cliendId = clientId;
        setTitle("Pannel Admin");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2));

        utilisateurControleur = new UtilisateurControleur();
        nomField = new JLabel(utilisateurControleur.getUsername(cliendId));
        add(nomField);

        inventaireButton = new JButton("Inventaire");
        add(inventaireButton);

        clientButton = new JButton("Client");
        add(clientButton);

        statsButton = new JButton("Stats");
        add(statsButton);

        inventaireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FenetreInventaire();
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new FenetreAdmin(1);
    }
}
