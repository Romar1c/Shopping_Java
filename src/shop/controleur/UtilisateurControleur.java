package shop.controleur;

import shop.ConnexionBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurControleur {

    public int verifierConnexion(String email, String password) {
        String sql = "SELECT id FROM clients WHERE email = ? AND mot_de_passe = ?";
        try (PreparedStatement stmt = ConnexionBDD.getConnexion().prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); // Retourne l'ID du client si trouvé
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retourne -1 si la connexion échoue
    }
    public int verifierAdmin(int id){
        String sql = "SELECT admin FROM clients WHERE id = ?";
        try (PreparedStatement stmt = ConnexionBDD.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("admin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public String getUsername(int id) {
        String sql = "SELECT nom FROM clients WHERE id = ?";
        String username = "";
        try (PreparedStatement stmt = ConnexionBDD.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
            System.out.println(stmt);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                username = rs.getString("nom");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }
}
