package shop.vue;

import shop.controleur.CommandeControleur;
import shop.controleur.UtilisateurControleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * FenetreAdmin est une classe qui permet d'afficher le panneau admin.
 * L'admin peut acceder à l'inventaire, la liste des clients, et aux statistiques.
 */
public class FenetreAdmin extends JFrame {
    int cliendId;
    private JLabel nomField;
    private JButton inventaireButton, clientButton, statsButton;
    private UtilisateurControleur utilisateurControleur;

    /**
     * Constructeur de la fenetre admin.
     * @param clientId L'identifiant de l'admin.
     */
    public FenetreAdmin(int clientId) {
        this.cliendId = clientId;
        setTitle("Panneau Admin");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        JPanel header = new JPanel(new BorderLayout());
        nomField = new JLabel("Connecte en tant que : " + new UtilisateurControleur().getUsername(cliendId), SwingConstants.CENTER);
        header.add(nomField, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        inventaireButton = new JButton("Inventaire");
        clientButton = new JButton("Client");
        statsButton = new JButton("Statistiques");

        Dimension buttonSize = new Dimension(250, 60);
        inventaireButton.setPreferredSize(buttonSize);
        clientButton.setPreferredSize(buttonSize);
        statsButton.setPreferredSize(buttonSize);

        inventaireButton.setMaximumSize(buttonSize);
        clientButton.setMaximumSize(buttonSize);
        statsButton.setMaximumSize(buttonSize);

        content.add(Box.createVerticalStrut(15));
        content.add(inventaireButton);
        content.add(Box.createVerticalStrut(15));
        content.add(clientButton);
        content.add(Box.createVerticalStrut(15));
        content.add(statsButton);

        center.add(content);
        add(center, BorderLayout.CENTER);

        inventaireButton.addActionListener((ActionEvent e) -> {
            new FenetreInventaire(clientId);
            dispose();
        });

        clientButton.addActionListener((ActionEvent e) -> {
            new FenetreDossierClient(clientId);
            dispose();
        });

        statsButton.addActionListener((ActionEvent e) -> {
            new FenetreStats(clientId);
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new FenetreAdmin(1);
    }
}