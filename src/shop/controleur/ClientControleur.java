package shop.controleur;

import shop.donnees.GestionClient;
import shop.modele.Client;

import java.sql.SQLException;

/**
 * Controleur permettant de gerer les operations relatives aux clients.
 * Cette classe contient des methodes pour inscrire un nouveau client.
 */
public class ClientControleur {
    private GestionClient gestionClient;

    /**
     * Constructeur de la classe ClientControleur.
     * Initialise l'instance de GestionClient pour la gestion des clients.
     */
    public ClientControleur() {
        this.gestionClient = new GestionClient();
    }

    // Inscrit un nouveau client
    public int inscrireClient(String nom, String email, String motDePasse) throws SQLException {
        Client client = new Client(0, nom, email, motDePasse, 0);
        int result = gestionClient.ajouterClient(client);
        return result;
    }
}
