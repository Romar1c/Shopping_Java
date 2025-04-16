package shop.donnees;

import shop.modele.Client;
import shop.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionClient {
    private Connection connexion;

    public GestionClient() {
        this.connexion = ConnexionBDD.getConnexion();
    }

    public int ajouterClient(Client client) throws SQLException {
        int count = 0;
        String sql_verif = "SELECT COUNT(*) FROM `clients` WHERE email = (?);";
        PreparedStatement stmt_verif = connexion.prepareStatement(sql_verif);
        stmt_verif.setString(1, client.getEmail());
        ResultSet rs = stmt_verif.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }
        if(count == 0) {
            String sql = "INSERT INTO clients (nom, email, mot_de_passe, admin) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
                stmt.setString(1, client.getNom());
                stmt.setString(2, client.getEmail());
                stmt.setString(3, client.getMotDePasse());
                stmt.setInt(4, client.getAdmin());
                stmt.executeUpdate();
                System.out.println("Client ajouté avec succès !");
                return 1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Le client n'a pas pu etre ajouter car la mail est deja utilise.");
            return 0;
        }
        return 0;
    }
    public List<Client> getClients(){
        List<Client> clients = new ArrayList<>();

        String sql = "SELECT * FROM `clients` WHERE admin = 0;";
        try (PreparedStatement stmt = ConnexionBDD.getConnexion().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clients.add(new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email")
                ));
                System.out.println(clients);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
