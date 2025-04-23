package shop.vue;

import shop.controleur.UtilisateurControleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreConnexion extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UtilisateurControleur utilisateurControleur;

    public FenetreConnexion() {
        utilisateurControleur = new UtilisateurControleur();
        setTitle("Connexion");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        // Champs de saisie
        add(new JLabel("Email: "));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Mot de passe: "));
        passwordField = new JPasswordField();
        add(passwordField);

        registerButton = new JButton("Creation Compte");
        add(registerButton);

        loginButton = new JButton("Se connecter");
        add(loginButton);

        // Action du bouton de connexion
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                int clientId = utilisateurControleur.verifierConnexion(email, password);

                if (clientId != -1) {
                    JOptionPane.showMessageDialog(null, "Connexion réussie !");
                    int isAdmin = utilisateurControleur.verifierAdmin(clientId);
                    if (isAdmin == 1) {
                        new FenetreAdmin(clientId);
                        dispose();
                    }
                    else{
                        new FenetreShopping(clientId); // Ouvre la fenêtre de shopping avec l'ID client
                        dispose(); // Ferme la fenêtre de connexion
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Email ou mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FenetreInscription();
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new FenetreConnexion(); // Lancer la fenêtre de connexion
    }
}
