package shop.donnees;

import shop.modele.Client;
import shop.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionClient {
    private Connection connexion;

    public GestionClient() {
        this.connexion = ConnexionBDD.getConnexion();
    }

    public void ajouterClient(Client client) {
        String sql = "INSERT INTO clients (nom, email, mot_de_passe) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getMotDePasse());
            stmt.executeUpdate();
            System.out.println("Client ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
