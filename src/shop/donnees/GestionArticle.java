package shop.donnees;

import org.jfree.data.category.DefaultCategoryDataset;
import shop.modele.Article;
import shop.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant la gestion des articles dans l'application.
 * Cette classe permet d'ajouter, supprimer, modifier, rechercher et verifier la disponibilite des articles.
 * Elle permet aussi de generer des jeux de donnees pour l'analyse des revenus et des ventes.
 */
public class GestionArticle {
    private Connection connexion;

    /**
     * Constructeur de la classe GestionArticle. Il initialise la connexion a la base de donnees.
     */
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
            System.out.println("Article ajouté avec succès !");
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
            System.out.println("Article supprimé avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Modifier un article
    public void modifierArticle(Article article) {
        String sql = "UPDATE articles SET nom = ?, marque = ?, prix_unitaire = ?, prix_vrac = ?, quantite_vrac = ?, stock = ? WHERE id = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, article.getNom());
            stmt.setString(2, article.getMarque());
            stmt.setDouble(3, article.getPrixUnitaire());
            stmt.setDouble(4, article.getPrixVrac());
            stmt.setInt(5, article.getQuantiteVrac());
            stmt.setInt(6, article.getStock());
            stmt.setInt(7, article.getId());

            stmt.executeUpdate();
            System.out.println("Article modifié avec succès !");
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
                        rs.getInt("quantite_vrac"),
                        rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    /**
     * Recherche un article par son ID.
     *
     * @param id L'ID de l'article chercher.
     * @return Une liste contenant l'article correspondant.
     */
    public List<Article> rechercherArticles(int id) {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM articles WHERE id = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1,  id);
            ResultSet rs = stmt.executeQuery();
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    /**
     * Verifie la disponibilite d'un article en comparant son stock avec le stock requis.
     *
     * @param article L'article a verifier.
     * @return true si l'article est disponible, false sinon.
     */
    public boolean VerifierDisponibiliteArticle(Article article) {
        int stock = article.getStock();
        int idArticle = article.getId();
        int stockArticle = 0;

        String sql = "SELECT stock FROM articles WHERE id = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)){
            stmt.setInt(1, idArticle);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                stockArticle = rs.getInt("stock");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (stockArticle > stock) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Genere un dataset pour l'analyse des revenus par article.
     *
     * @return Un objet DefaultCategoryDataset contenant les revenus par article.
     */
    public DefaultCategoryDataset DataSetArtEur() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT a.nom, SUM(ca.prix_total) AS revenutotal " +
                "FROM commande_articles ca " +
                "JOIN articles a ON a.id = ca.article_id " +
                "GROUP BY a.nom";

        try (PreparedStatement stmt = ConnexionBDD.getConnexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String nomArticle = rs.getString("nom");
                double revenu = rs.getDouble("revenutotal");
                dataset.addValue(revenu, "Argent Genere", nomArticle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataset;
    }

    /**
     * Genere un dataset pour l'analyse des quantites d'articles vendus.
     *
     * @return Un objet DefaultCategoryDataset contenant les quantites vendues par article.
     */
    public DefaultCategoryDataset DataSetArtUnit() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT a.nom, SUM(ca.quantite) AS unitesvendues\n" +
                "FROM commande_articles ca\n" +
                "JOIN articles a ON a.id = ca.article_id\n" +
                "GROUP BY a.nom";

        try (PreparedStatement stmt = ConnexionBDD.getConnexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String nomArticle = rs.getString("nom");
                double revenu = rs.getDouble("unitesvendues");
                dataset.addValue(revenu, "Unites Vendues", nomArticle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataset;
    }
}
