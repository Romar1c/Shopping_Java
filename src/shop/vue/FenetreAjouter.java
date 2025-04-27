package shop.vue;

import shop.controleur.ArticleControleur;
import shop.modele.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * FenetreAjouter est une classe permettant d'ajouter un nouvel article dans l'inventaire.
 * L'utilisateur peut saisir les informations relatives à l'article (nom, marque, prix, etc.)
 */
public class FenetreAjouter extends JFrame {
    private int clientId;
    private ArticleControleur articleControleur;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField, nomField, marqueField, prixUnitaireField, prixVracField, quantiteVracField;

    /**
     * Constructeur de la fenetre d'ajout d'un article.
     * @param clientId L'identifiant du client effectuant l'ajout.
     */
    public FenetreAjouter(int clientId) {
        articleControleur = new ArticleControleur();
        setTitle("Ajouter un Article");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        // Panel Formulaire et Boutons
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        nomField = new JTextField();
        marqueField = new JTextField();
        prixUnitaireField = new JTextField();
        prixVracField = new JTextField();
        quantiteVracField = new JTextField();
        JButton addButton = new JButton("Ajouter");

        formPanel.add(new JLabel("Nom: ")); formPanel.add(nomField);
        formPanel.add(new JLabel("Marque: ")); formPanel.add(marqueField);
        formPanel.add(new JLabel("Prix Unitaire: ")); formPanel.add(prixUnitaireField);
        formPanel.add(new JLabel("Prix Vrac: ")); formPanel.add(prixVracField);
        formPanel.add(new JLabel("Quantité Vrac: ")); formPanel.add(quantiteVracField);

        add(formPanel, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String marque = marqueField.getText();
                double prixUnitaire = Double.parseDouble(prixUnitaireField.getText());
                double prixVrac = Double.parseDouble(prixVracField.getText());
                int quantiteVrac = Integer.parseInt(quantiteVracField.getText());
                articleControleur.ajouterArticle(nom, marque, prixUnitaire, prixVrac, quantiteVrac);
                new FenetreInventaire(clientId);
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new FenetreAjouter(1);
    }
}
