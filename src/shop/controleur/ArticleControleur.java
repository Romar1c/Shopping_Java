package shop.controleur;

import shop.donnees.GestionArticle;
import shop.modele.Article;
import java.util.List;

public class ArticleControleur {
    private GestionArticle gestionArticle;

    public ArticleControleur() {
        this.gestionArticle = new GestionArticle();
    }

    // Ajouter un article
    public void ajouterArticle(String nom, String marque, double prixUnitaire, double prixVrac, int quantiteVrac) {
        Article article = new Article(0, nom, marque, prixUnitaire, prixVrac, quantiteVrac,0);
        gestionArticle.ajouterArticle(article);
    }

    // Supprimer un article
    public void supprimerArticle(int id) {
        gestionArticle.supprimerArticle(id);
    }

    // Modifier un article
    public void modifierArticle(int id, String nom, String marque, double prixUnitaire, double prixVrac, int quantiteVrac, int stock) {
        Article article = new Article(id, nom, marque, prixUnitaire, prixVrac, quantiteVrac, stock);
        gestionArticle.modifierArticle(article);
    }

    // Rechercher des articles
    public List<Article> rechercherArticles(String motCle) {
        return gestionArticle.rechercherArticles(motCle);
    }
}
