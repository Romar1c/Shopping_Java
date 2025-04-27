package shop.modele;

/**
 * La classe Client represente un client dans le systeme de gestion de boutique.
 * Elle contient des informations sur le client telles que son identifiant, son nom,
 * son email, son mot de passe, son statut d'administrateur, ses depenses totales,
 * et le nombre de commandes qu'il a passees.
 */
public class Client {
    private int id;
    private String nom;
    private String email;
    private String motDePasse;
    private int admin;
    private double depense;
    private int nbrCommande;

    /**
     * Constructeur pour initialiser un client avec son id, son nom et ses depenses.
     * @param id L'id du client.
     * @param nom Le nom du client.
     * @param depense Le montant total des depenses du client.
     */
    public Client(int id, String nom, double depense) {
        this.id = id;
        this.nom = nom;
        this.depense = depense;
    }

    /**
     * Constructeur pour initialiser un client avec son id, son nom et son nombre de commandes.
     * @param id L'id du client.
     * @param nom Le nom du client.
     * @param nbrCommande Le nombre de commandes effectuees par le client.
     */
    public Client(int id, String nom, int nbrCommande) {
        this.id = id;
        this.nom = nom;
        this.nbrCommande = nbrCommande;
    }

    /**
     * Constructeur pour initialiser un client avec son id, son nom et son email.
     * @param id L'id du client.
     * @param nom Le nom du client.
     * @param email L'email du client.
     */
    public Client(int id, String nom, String email){
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    /**
     * Constructeur pour initialiser un client avec toutes ses informations.
     * @param id L'id du client.
     * @param nom Le nom du client.
     * @param email L'email du client.
     * @param motDePasse Le mot de passe du client.
     * @param admin Le statut d'admin du client (1 pour admin, 0 pour non-admin).
     */
    public Client(int id, String nom, String email, String motDePasse, int admin) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.admin = admin;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public String getMotDePasse() { return motDePasse; }
    public int getAdmin() { return admin; }
    public double getDepense() { return depense; }
    public int getNbrcommande() { return nbrCommande; }
}
