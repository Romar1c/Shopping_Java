package shop.modele;

import java.util.Date;
import java.util.List;

public class Commande {
    private int id;
    private int clientId;
    private Date dateCommande;
    private double total;
    private List<Article> articles;

    public Commande(int id, int clientId, Date dateCommande, double total, List<Article> articles) {
        this.id = id;
        this.clientId = clientId;
        this.dateCommande = dateCommande;
        this.total = total;
        this.articles = articles;
    }

    public Commande(Commande commande) {
        this.id = commande.getId();
        this.clientId = commande.getClientId();
        this.dateCommande = commande.getDateCommande();
        this.total = commande.getTotal();
        this.articles = commande.getArticles();
    }

    // Getters
    public int getId() { return id; }
    public int getClientId() { return clientId; }
    public Date getDateCommande() { return dateCommande; }
    public double getTotal() { return total; }
    public List<Article> getArticles() { return articles; }

    // Setters
    public void setClientId(int clientId) { this.clientId = clientId; }
    public void setDateCommande(Date dateCommande) { this.dateCommande = dateCommande; }
    public void setTotal(double total) { this.total = total; }
    public void setArticles(List<Article> articles) { this.articles = articles; }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", dateCommande=" + dateCommande +
                ", total=" + total +
                ", articles=" + articles +
                '}';
    }
}
