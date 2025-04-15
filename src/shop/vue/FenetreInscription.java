package shop.vue;

import shop.controleur.ClientControleur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class FenetreInscription extends JFrame {
    private JTextField nomField, emailField;
    private JPasswordField passwordField;
    private JButton inscrireButton, retourButton;
    private ClientControleur clientControleur;

    public FenetreInscription() {
        clientControleur = new ClientControleur();

        setTitle("Inscription Client");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel nomLabel = new JLabel("Nom:");
        nomLabel.setBounds(10, 10, 100, 20);
        add(nomLabel);

        nomField = new JTextField();
        nomField.setBounds(120, 10, 150, 20);
        add(nomField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 40, 100, 20);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(120, 40, 150, 20);
        add(emailField);

        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setBounds(10, 70, 100, 20);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 70, 150, 20);
        add(passwordField);

        inscrireButton = new JButton("S'inscrire");
        inscrireButton.setBounds(175, 100, 100, 30);
        add(inscrireButton);

        retourButton = new JButton("Retour");
        retourButton.setBounds(25, 100, 100, 30);
        add(retourButton);

        inscrireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = 0;
                try {
                    result = clientControleur.inscrireClient(nomField.getText(), emailField.getText(), new String(passwordField.getPassword()));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                if(result == 1){
                    JOptionPane.showMessageDialog(null, "Client inscrit avec succès !");
                    new FenetreConnexion();
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Le mail est deja utilise !");
                }
            }
        });

        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FenetreConnexion();
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new FenetreInscription();
    }
}
