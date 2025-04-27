package shop.modele;

/**
 * La classe Article represente un article dans le systeme de gestion de boutique.
 * Elle contient des informations sur l'article telles que son identifiant, son nom,
 * sa marque, son prix unitaire, son prix en vrac, sa quantite en vrac, son stock,
 * ainsi que la quantite de l'article actuellement en panier.
 */
public class Article {
    private int id;
    private String nom;
    private String marque;
    private double prixUnitaire;
    private double prixVrac;
    private int quantiteVrac;
    private int stock;
    private int quantite;

    /**
     * Constructeur pour initialiser un article avec toutes les informations de base,
     * y compris son stock et sa quantite en vrac.
     * @param id L'id de l'article.
     * @param nom Le nom de l'article.
     * @param marque La marque de l'article.
     * @param prixUnitaire Le prix unitaire de l'article.
     * @param prixVrac Le prix de l'article en vrac.
     * @param quantiteVrac La quantite d'unité en vrac de l'article.
     * @param stock Le stock disponible de l'article.
     */
    public Article(int id, String nom, String marque, double prixUnitaire, double prixVrac, int quantiteVrac, int stock) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.prixUnitaire = prixUnitaire;
        this.prixVrac = prixVrac;
        this.quantiteVrac = quantiteVrac;
        this.stock = stock;
    }

    /**
     * Constructeur pour initialiser un article avec toutes les informations de base,
     * y compris la quantite achetee et stock disponible.
     * @param id L'id de l'article.
     * @param nom Le nom de l'article.
     * @param marque La marque de l'article.
     * @param prixUnitaire Le prix unitaire de l'article.
     * @param prixVrac Le prix de l'article en vrac.
     * @param quantiteVrac La quantite d'unité en vrac de l'article.
     * @param stock Le stock disponible de l'article.
     * @param quantite La quantite achetee de l'article.
     */
    public Article(int id, String nom, String marque, double prixUnitaire, double prixVrac, int quantiteVrac, int stock, int quantite) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.prixUnitaire = prixUnitaire;
        this.prixVrac = prixVrac;
        this.quantiteVrac = quantiteVrac;
        this.stock = stock;
        this.quantite = quantite;
    }

    // Getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getMarque() { return marque; }
    public double getPrixUnitaire() { return prixUnitaire; }
    public double getPrixVrac() { return prixVrac; }
    public int getQuantiteVrac() { return quantiteVrac; }
    public int getStock() { return stock; }
    public int getQuantite() { return quantite; }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setMarque(String marque) { this.marque = marque; }
    public void setPrixUnitaire(double prixUnitaire) { this.prixUnitaire = prixUnitaire; }
    public void setPrixVrac(double prixVrac) { this.prixVrac = prixVrac; }
    public void setQuantiteVrac(int quantiteVrac) { this.quantiteVrac = quantiteVrac; }
    public void setStock(int stock) { this.stock = stock; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", marque='" + marque + '\'' +
                ", prixUnitaire=" + prixUnitaire +
                ", prixVrac=" + prixVrac +
                ", quantiteVrac=" + quantiteVrac +
                ", stock=" + stock +
                ", quantite=" + quantite +
                '}';
    }

    public double getPrixTotal() {
        return ((quantite/quantiteVrac)*prixVrac*quantiteVrac) + (quantite%quantiteVrac * prixUnitaire);
    }


}
