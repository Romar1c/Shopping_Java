package shop.vue;

import shop.controleur.CommandeControleur;
import shop.donnees.GestionArticle;
import shop.modele.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class FenetreDetail extends JFrame {
    GestionArticle gestionArticle;
    Article article;
    private int clientId;

    public FenetreDetail(int id, int clienID) {
        this.clientId = clienID;

        setTitle("Detail produit n°" + Integer.toString(id));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        String[] champs = {"id", "nom", "marque", "prix unitaire", "prix vrac", "quantite vrac", "stock"};
        gestionArticle = new GestionArticle();
        List<Article> articles = gestionArticle.rechercherArticles(id);

        JTextField[] textFields = new JTextField[champs.length];

        JPanel panel = new JPanel(new GridLayout(champs.length, 3, 10, 10));
        if (!articles.isEmpty()) {
            article = articles.get(0);

            String[] values = {
                    String.valueOf(article.getId()),
                    article.getNom(),
                    article.getMarque(),
                    String.valueOf(article.getPrixUnitaire()),
                    String.valueOf(article.getPrixVrac()),
                    String.valueOf(article.getQuantiteVrac()),
                    String.valueOf(article.getStock())
            };

            for (int i = 0; i < champs.length; i++) {
                panel.add(new JLabel(champs[i]));
                panel.add(new JLabel(values[i]));
                JTextField tf = new JTextField(values[i]);
                textFields[i] = tf;
                panel.add(tf);
            }
        }
        JLabel lblTitre = new JLabel("Detail Article n°" + Integer.toString(id));

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnValider = new JButton("Valider changement");
        JButton deleteButton = new JButton("Supprimer");

        southPanel.add(deleteButton, BorderLayout.WEST);
        southPanel.add(btnValider, BorderLayout.EAST);

        add(lblTitre, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
        setVisible(true);

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                article.setNom(textFields[1].getText());
                article.setMarque(textFields[2].getText());
                article.setPrixUnitaire(Double.parseDouble(textFields[3].getText()));
                article.setPrixVrac(Double.parseDouble(textFields[4].getText()));
                article.setQuantiteVrac(Integer.parseInt(textFields[5].getText()));
                article.setStock(Integer.parseInt(textFields[6].getText()));

                gestionArticle.modifierArticle(article);

                new FenetreInventaire(clientId);
                dispose();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionArticle.supprimerArticle(article.getId());

                new FenetreInventaire(clientId);
                dispose();
            }
        });

    }
    public static void main(String[] args) {
        new FenetreDetail(1, 1);
    }
}
