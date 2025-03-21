package shop.donnees;

import shop.modele.Article;
import shop.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionArticle {
    private Connection connexion;

    public GestionArticle() {
        this.connexion = ConnexionBDD.getConnexion();
    }

    // Ajouter un article
    public void ajouterArticle(Article article) {
        String sql = "INSERT INTO articles (nom, marque, prix_unitaire, prix_vrac, quantite_vrac) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, article.getNom());
            stmt.setString(2, article.getMarque());
            stmt.setDouble(3, article.getPrixUnitaire());
            stmt.setDouble(4, article.getPrixVrac());
            stmt.setInt(5, article.getQuantiteVrac());
            stmt.executeUpdate();
            System.out.println("✅ Article ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer un article
    public void supprimerArticle(int id) {
        String sql = "DELETE FROM articles WHERE id = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("✅ Article supprimé avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Modifier un article
    public void modifierArticle(Article article) {
        String sql = "UPDATE articles SET nom = ?, marque = ?, prix_unitaire = ?, prix_vrac = ?, quantite_vrac = ? WHERE id = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, article.getNom());
            stmt.setString(2, article.getMarque());
            stmt.setDouble(3, article.getPrixUnitaire());
            stmt.setDouble(4, article.getPrixVrac());
            stmt.setInt(5, article.getQuantiteVrac());
            stmt.setInt(6, article.getId());
            stmt.executeUpdate();
            System.out.println("✅ Article modifié avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Rechercher des articles
    public List<Article> rechercherArticles(String motCle) {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM articles WHERE nom LIKE ? OR marque LIKE ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + motCle + "%");
            stmt.setString(2, "%" + motCle + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                articles.add(new Article(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("marque"),
                        rs.getDouble("prix_unitaire"),
                        rs.getDouble("prix_vrac"),
                        rs.getInt("quantite_vrac")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }
}
