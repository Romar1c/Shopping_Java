package shop.donnees;

import shop.modele.Client;
import shop.modele.Commande;
import shop.modele.Article;
import shop.ConnexionBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe GestionCommande permet de gerer les operations sur les commandes dans le systeme de gestion de boutique.
 * Elle permet d'ajouter, supprimer et rechercher des commandes, ainsi que de calculer diverses statistiques sur les commandes.
 */
public class GestionCommande {
    private Connection connexion;

    /**
     * Constructeur de la classe GestionCommande.
     * Initialise la connexion a la base de donnees.
     */
    public GestionCommande() {
        this.connexion = ConnexionBDD.getConnexion();
    }

    /**
     * Verifie une commande en fonction de la disponibilite des articles.
     * @param commande La commande a verifier.
     * @return La commande verifiee avec les articles disponibles et le total recalcule.
     */
    public Commande VerifCommande(Commande commande) {
        Commande commandeVerif = new Commande(commande);
        List<Article> ListArticleVerif = new ArrayList<>();
        GestionArticle gestionArticle = new GestionArticle();

        for (Article article : commande.getArticles()) {
            if(gestionArticle.VerifierDisponibiliteArticle(article)){
                ListArticleVerif.add(article);
            }
        }
        double PrixTotal = ListArticleVerif.stream().mapToDouble(Article::getPrixTotal).sum();
        commandeVerif.setArticles(ListArticleVerif);
        commandeVerif.setTotal(PrixTotal);
        return commandeVerif;
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
            System.out.println("Commande ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ajouter les articles liés à une commande
    private void ajouterArticlesCommande(int commandeId, List<Article> articles) {
        String insertSQL = "INSERT INTO commande_articles (commande_id, article_id, quantite, prix_total) VALUES (?, ?, ?, ?)";
        String selectStockSQL = "SELECT stock FROM articles WHERE id = ?";
        String updateStockSQL = "UPDATE articles SET stock = ? WHERE id = ?";

        try (
                PreparedStatement insertStmt = connexion.prepareStatement(insertSQL);
                PreparedStatement selectStockStmt = connexion.prepareStatement(selectStockSQL);
                PreparedStatement updateStockStmt = connexion.prepareStatement(updateStockSQL)
        ) {
            for (Article article : articles) {
                insertStmt.setInt(1, commandeId);
                insertStmt.setInt(2, article.getId());
                insertStmt.setInt(3, article.getQuantite());
                insertStmt.setDouble(4, article.getPrixTotal());
                insertStmt.executeUpdate();

                selectStockStmt.setInt(1, article.getId());
                ResultSet rs = selectStockStmt.executeQuery();
                if (rs.next()) {
                    int stockActuel = rs.getInt("stock");
                    int nouveauStock = stockActuel - article.getQuantite();

                    updateStockStmt.setInt(1, nouveauStock);
                    updateStockStmt.setInt(2, article.getId());
                    updateStockStmt.executeUpdate();
                }
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
            System.out.println("Commande supprimée avec succès !");
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

    /**
     * Calcule la moyenne du prix des commandes.
     * @return La moyenne du prix des commandes.
     */
    public double MoyennePrix(){
        String sql = "SELECT AVG(total) AS moyen FROM commandes;";
        double moyenne = 0;

        try (PreparedStatement stmt = ConnexionBDD.getConnexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                moyenne = rs.getDouble("moyen");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return moyenne;
    }

    /**
     * Trouve le prix de la commande la plus chere.
     * @return Le prix de la commande la plus chere.
     */
    public double PlusChere(){
        double plusChere = 0;
        String sql = "SELECT total FROM `commandes` ORDER BY total DESC LIMIT 1;";
        try (PreparedStatement stmt = ConnexionBDD.getConnexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                plusChere = rs.getDouble("total");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return plusChere;
    }

    /**
     * Calcule le nombre total de commandes.
     * @return Le nombre total de commandes.
     */
    public int NbrCommandes() {
        int commandes = 0;
        String sql = "SELECT COUNT(*) AS nbrcommandes FROM commandes;";
        try (PreparedStatement stmt = ConnexionBDD.getConnexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                commandes = rs.getInt("nbrcommandes");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }

    /**
     * Calcule l'argent totale que l'on aurait pu gagner si les commandes passees n'avait pas de reduc.
     * @return Le montant total potentiel.
     */
    public double ArgentPotentiel(){
        double argentPotentiel = 0;
        String sql = "SELECT SUM(ca.quantite * a.prix_unitaire) AS PotentielTotal\n" +
                "FROM commande_articles AS ca\n" +
                "JOIN articles AS a ON ca.article_id = a.id;";
        try (PreparedStatement stmt = ConnexionBDD.getConnexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                argentPotentiel = rs.getDouble("PotentielTotal");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return argentPotentiel;
    }

    /**
     * Calcule le montant total des ventes.
     * @return Le montant total des ventes.
     */
    public double ArgentTotal(){
        double total = 0;
        String sql = "SELECT SUM(prix_total) AS ArgentTotal\n" +
                "FROM commande_articles;";
        try (PreparedStatement stmt = ConnexionBDD.getConnexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble("ArgentTotal");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}