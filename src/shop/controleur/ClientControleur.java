package shop.controleur;

import shop.donnees.GestionClient;
import shop.modele.Client;

import java.sql.SQLException;

public class ClientControleur {
    private GestionClient gestionClient;

    public ClientControleur() {
        this.gestionClient = new GestionClient();
    }

    public int inscrireClient(String nom, String email, String motDePasse) throws SQLException {
        Client client = new Client(0, nom, email, motDePasse, 0);
        int result = gestionClient.ajouterClient(client);
        return result;
    }
}
