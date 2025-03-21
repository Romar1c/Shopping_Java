package shop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBDD {
    private static final String URL = "jdbc:mysql://localhost:3306/shopping_db";
    private static final String UTILISATEUR = "root";  // Change si ton utilisateur est différent
    private static final String MOT_DE_PASSE = "01234";  // Mets ton mot de passe ici
    private static Connection connexion;

    public static Connection getConnexion() {
        if (connexion == null) {
            try {
                // Charger le driver JDBC MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Établir la connexion
                connexion = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
                System.out.println("✅ Connexion réussie à la base de données !");
            } catch (ClassNotFoundException e) {
                System.out.println("🚨 Driver MySQL JDBC introuvable. Vérifie que le `.jar` est bien ajouté.");
            } catch (SQLException e) {
                System.out.println("🚨 Erreur SQL : " + e.getMessage());
            }
        }
        return connexion;
    }

    public static void fermerConnexion() {
        if (connexion != null) {
            try {
                connexion.close();
                System.out.println("✅ Connexion fermée !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
