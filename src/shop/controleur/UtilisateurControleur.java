package shop.controleur;

import shop.ConnexionBDD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controleur permettant de verifier les informations de connexion d'un utilisateur.
 * Cette classe contient des methodes pour verifier la connexion, les droits d'admin et recuperer le nom d'un utilisateur.
 */
public class UtilisateurControleur {

    /**
     * Verifie la connexion d'un utilisateur en fonction de son email et de son mot de passe.
     *
     * @param email L'email de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     * @return L'ID de l'utilisateur si la connexion est reussie, sinon -1.
     */
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

    /**
     * Verifie si un utilisateur est un administrateur.
     *
     * @param id L'ID de l'utilisateur.
     * @return 1 si l'utilisateur est administrateur, 0 sinon, ou -1 en cas d'erreur.
     */
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

    /**
     * Recupere le nom d'un utilisateur en fonction de son ID.
     *
     * @param id L'ID de l'utilisateur.
     * @return Le nom de l'utilisateur, ou une chaine vide si l'utilisateur n'est pas trouve.
     */
    public String getUsername(int id) {
        String sql = "SELECT nom FROM clients WHERE id = ?";
        String username = "";
        try (PreparedStatement stmt = ConnexionBDD.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, id);
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
