package shop.modele;

public class Article {
    private int id;
    private String nom;
    private String marque;
    private double prixUnitaire;
    private double prixVrac;
    private int quantiteVrac;
    private int stock;
    private int quantite;

    public Article(int id, String nom, String marque, double prixUnitaire, double prixVrac, int quantiteVrac, int stock) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.prixUnitaire = prixUnitaire;
        this.prixVrac = prixVrac;
        this.quantiteVrac = quantiteVrac;
        this.stock = stock;
    }

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
