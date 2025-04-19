package shop;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tableau avec recherche");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        String[][] donnees = {
                {"Alice", "20", "tttgggf"},
                {"Bobmarleuy", "22", "Développeur"},
                {"Charlie", "19", "DesignRROOOer rowwwwwwww"},
                {"David", "257827", "TT"}
        };
        String[] colonnes = {"Nomtalia", "Age", "Profession"};

        JTable table = new JTable(donnees, colonnes);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);

        // Barre de recherche
        JPanel panelRecherche = new JPanel(new BorderLayout());
        JTextField champRecherche = new JTextField();
        panelRecherche.add(new JLabel("RechercheR LA LISTE : "), BorderLayout.WEST);
        panelRecherche.add(champRecherche, BorderLayout.CENTER);

        // Quand on tape dans la barre de recherche
        champRecherche.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                rechercher(champRecherche.getText(), sorter);
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                rechercher(champRecherche.getText(), sorter);
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                rechercher(champRecherche.getText(), sorter);
            }
        });

        // Ajouter tout à la fenêtre et supprimer les trucs
        frame.add(panelRecherche, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // Fonction pour filtrer
    private static void rechercher(String texte, TableRowSorter<TableModel> sorter) {
        if (texte.trim().length() == 0) {
            sorter.setRowFilter(null); // Pas de filtre POUR LA FONCTION
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texte)); // Filtre insensible à la casse
        }
    }
}
