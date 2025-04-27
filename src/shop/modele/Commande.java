package shop.modele;

import java.util.Date;
import java.util.List;

/**
 * La classe Commande represente une commande passee par un client.
 * Elle contient les informations relatives a l'id de la commande, l'id du client,
 * la date de la commande, le total de la commande et la liste des articles commandes.
 */
public class Commande {
    private int id;
    private int clientId;
    private Date dateCommande;
    private double total;
    private List<Article> articles;

    /**
     * Constructeur de la classe Commande.
     * @param id L'id de la commande.
     * @param clientId L'id du client qui a passe la commande.
     * @param dateCommande La date de la commande.
     * @param total Le total de la commande.
     * @param articles La liste des articles de la commande.
     */
    public Commande(int id, int clientId, Date dateCommande, double total, List<Article> articles) {
        this.id = id;
        this.clientId = clientId;
        this.dateCommande = dateCommande;
        this.total = total;
        this.articles = articles;
    }

    /**
     * Constructeur de copie pour la classe Commande.
     * @param commande La commande a copier.
     */
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
