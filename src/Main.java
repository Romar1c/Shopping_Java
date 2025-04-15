import shop.vue.FenetreConnexion;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new FenetreConnexion();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}