package shop.controleur;

import shop.donnees.GestionCommande;
import shop.modele.Article;
import shop.modele.Commande;
import shop.ConnexionBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandeControleur {
    private GestionCommande gestionCommande;

    public CommandeControleur() {
        this.gestionCommande = new GestionCommande();
    }

    // Ajouter une commande
    public void ajouterCommande(int clientId, List<Article> articles) {
        System.out.println(articles);
        double PrixUnitaire = articles.stream().mapToDouble(Article::getPrixUnitaire).sum();
        System.out.println(PrixUnitaire);
        int quantite = articles.stream().mapToInt(Article::getQuantiteVrac).sum();
        System.out.println(quantite);

        double PrixTotal = quantite * PrixUnitaire;
        System.out.println(PrixTotal);

        Commande commande = new Commande(0, clientId, new Date(), PrixTotal, articles);
        gestionCommande.ajouterCommande(commande);
    }

    // Supprimer une commande
    public void supprimerCommande(int id) {
        gestionCommande.supprimerCommande(id);
    }

    // Rechercher les commandes d'un client avec détails des articles
    public List<Commande> rechercherCommandesParClient(int clientId) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT c.id, c.client_id, c.date_commande, c.total, a.nom AS article_nom, ca.quantite " +
                "FROM commandes c " +
                "JOIN commande_articles ca ON c.id = ca.commande_id " +
                "JOIN articles a ON ca.article_id = a.id " +
                "WHERE c.client_id = ?";
        System.out.println(sql);
        try (PreparedStatement stmt = ConnexionBDD.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                List<Article> articles = new ArrayList<>();
                articles.add(new Article(0, rs.getString("article_nom"), "", 0, 0, rs.getInt("quantite"),0));

                commandes.add(new Commande(
                        rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getDate("date_commande"),
                        rs.getDouble("total"),
                        articles
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commandes;
    }

    // Récupérer toutes les commandes
    public List<Commande> recupererToutesLesCommandes() {
        return gestionCommande.recupererToutesLesCommandes();
    }

    // Récupérer tous les articles disponibles
    public List<Article> recupererArticles() {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM articles";

        try (PreparedStatement stmt = ConnexionBDD.getConnexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                articles.add(new Article(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("marque"),
                        rs.getDouble("prix_unitaire"),
                        rs.getDouble("prix_vrac"),
                        rs.getInt("quantite_vrac"),
                        rs.getInt("stock")
                ));
                System.out.println(articles);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return articles;
    }
}
