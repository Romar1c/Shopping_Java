package shop.donnees;

import shop.modele.Commande;
import shop.modele.Article;
import shop.ConnexionBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionCommande {
    private Connection connexion;

    public GestionCommande() {
        this.connexion = ConnexionBDD.getConnexion();
    }

    // Ajouter une commande
    public void ajouterCommande(Commande commande) {
        String sql = "INSERT INTO commandes (client_id, date_commande, total) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, commande.getClientId());
            stmt.setTimestamp(2, new Timestamp(commande.getDateCommande().getTime()));
            stmt.setDouble(3, commande.getTotal());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int commandeId = generatedKeys.getInt(1);
                ajouterArticlesCommande(commandeId, commande.getArticles());
            }
            System.out.println("✅ Commande ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ajouter les articles liés à une commande
    private void ajouterArticlesCommande(int commandeId, List<Article> articles) {
        String sql = "INSERT INTO commande_articles (commande_id, article_id, quantite, prix_total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            for (Article article : articles) {
                stmt.setInt(1, commandeId);
                stmt.setInt(2, article.getId());
                stmt.setInt(3, article.getQuantiteVrac()); // Correction ici pour prendre en compte la quantité réelle
                stmt.setDouble(4, article.getPrixUnitaire() * article.getQuantiteVrac()); // Ajout du prix total par article
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer une commande
    public void supprimerCommande(int id) {
        String sql = "DELETE FROM commandes WHERE id = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("✅ Commande supprimée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Rechercher les commandes d'un client
    public List<Commande> rechercherCommandesParClient(int clientId) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT c.id, c.client_id, c.date_commande, ca.prix_total, a.nom, ca.quantite " +
                "FROM commandes c " +
                "JOIN commande_articles ca ON c.id = ca.commande_id " +
                "JOIN articles a ON ca.article_id = a.id " +
                "WHERE c.client_id = ?";


        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                List<Article> articles = new ArrayList<>();
                System.out.println("Prix total récupéré: " + rs.getDouble("article_id"));

                articles.add(new Article(
                        rs.getInt("article_id"),
                        rs.getString("articles"),
                        "",
                        0,
                        rs.getDouble("prix_total"), // Correction ici, récupération du prix total
                        rs.getInt("quantite_totale"),
                        rs.getInt("stock")
                ));



                commandes.add(new Commande(
                        rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getTimestamp("date_commande"),
                        rs.getDouble("total"), // Correction du total affiché dans Voir Commande
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
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT c.id, c.client_id, c.date_commande, SUM(ca.quantite * a.prix_unitaire) AS total " +
                "FROM commandes c " +
                "JOIN commande_articles ca ON c.id = ca.commande_id " +
                "JOIN articles a ON ca.article_id = a.id " +
                "GROUP BY c.id, c.client_id, c.date_commande";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                commandes.add(new Commande(
                        rs.getInt("id"),
                        rs.getInt("client_id"),
                        rs.getTimestamp("date_commande"),
                        rs.getDouble("total"),
                        new ArrayList<>()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }
}