package shop.controleur;

import shop.donnees.GestionClient;
import shop.modele.Client;

public class ClientControleur {
    private GestionClient gestionClient;

    public ClientControleur() {
        this.gestionClient = new GestionClient();
    }

    public void inscrireClient(String nom, String email, String motDePasse) {
        Client client = new Client(0, nom, email, motDePasse);
        gestionClient.ajouterClient(client);
    }
}
