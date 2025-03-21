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
}
