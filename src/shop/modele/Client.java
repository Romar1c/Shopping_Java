package shop.modele;

public class Client {
    private int id;
    private String nom;
    private String email;
    private String motDePasse;
    private int admin;

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
}
